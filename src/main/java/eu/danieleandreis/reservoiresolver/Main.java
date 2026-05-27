package eu.danieleandreis.reservoiresolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import eu.danieleandreis.reservoiresolver.ode.ReservoirApacheMathODE;
import eu.danieleandreis.reservoiresolver.ode.ReservoirExactSolutionERM;

/**
 * Simple reservoir example
 *
 * @author Daniele Andreis and Giuseppe Formetta
 */
public class Main {
	public static void main(String[] args) throws IOException {

		double effectiveStorage = 90000.0; // A*n [m2]
		double pumping = 8200.0; // Qp [m3/day]
		double recharge = 8200.0; // Qin [m3/day]
		double responseTime = 1980.0; // K [days]

		double dt = 1.0;
		double t0 = 0.0;
		double tEnd = 12000.0;

		double h0 = responseTime / effectiveStorage * recharge;; // initial hydraulic head [m]

		double[] state = new double[] { h0 };

		ReservoirApacheMathODE ode = new ReservoirApacheMathODE(responseTime, effectiveStorage, recharge, pumping);

		ClassicalRungeKuttaIntegrator integrator = new ClassicalRungeKuttaIntegrator(dt / 10);

		ReservoirExactSolutionERM exactSolution = new ReservoirExactSolutionERM(responseTime, effectiveStorage,
				recharge, pumping);
		
		Path output = Path.of("/home/andreisd/Desktop/out_step2.csv");

		try (BufferedWriter writer = Files.newBufferedWriter(output)) {
			writer.write("time,h_rk,h_exact,error,q_rk,q_exact");
			writer.newLine();
			writer.write("" + 0 + "," + h0 + "," + exactSolution.getH(0) + "," + 0 + "," + ode.computeDischarge(h0)
					+ "," + exactSolution.getQ(0));
			for (double t = t0; t <= tEnd; t = t + dt) {
				double evaluationTime = t + dt;
				integrator.integrate(ode, t, state, evaluationTime, state);
				double hExact = exactSolution.getH(evaluationTime);
				double qExact = exactSolution.getQ(evaluationTime);
				double hEstimate = state[0];
				double qNumerical = ode.computeDischarge(hEstimate);
				double error = hEstimate - hExact;
				writer.newLine();
				writer.write(evaluationTime + "," + hEstimate + "," + hExact + "," + error + "," + qNumerical + "," + qExact);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Simulation completed.");
		System.out.println("Final numerical h = " + state[0]);
		System.out.println("Final exact h = " + exactSolution.getH(tEnd));

	}
}
