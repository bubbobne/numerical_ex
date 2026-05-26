package eu.danieleandreis.reservoiresolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import eu.danieleandreis.reservoiresolver.ode.ReservoirApacheMathODE;
import eu.danieleandreis.reservoiresolver.ode.ReservoirExactSolutionERM;

public class Main {
	public static void main(String[] args) throws IOException {

		double effectiveStorage = 90000.0; // A*n [m2]
		double pumping = 8200.0; // Qp [m3/day]
		double recharge = 8200.0; // Qin [m3/day]
		double responseTime = 1980.0; // K [days]

		double dt = 1.0;
		double t0 = 0.0;
		double tEnd = 12000.0;

		double h0 = 180.3999; // initial hydraulic head [m]

		double[] state = new double[] { h0 };
		var hRk = new ArrayList<Double>();

		ReservoirApacheMathODE ode = new ReservoirApacheMathODE(responseTime, effectiveStorage, recharge, pumping);

		ClassicalRungeKuttaIntegrator integrator = new ClassicalRungeKuttaIntegrator(dt);

		integrator.addStepHandler(new StepHandler() {

			@Override
			public void init(double t0, double[] y0, double t) {
				hRk.add(y0[0]);
			}

			@Override
			public void handleStep(StepInterpolator interpolator, boolean isLast) {
				hRk.add(interpolator.getInterpolatedState()[0]);
			}
		});

		integrator.integrate(ode, t0, state, tEnd, state);

		ReservoirExactSolutionERM exactSolution = new ReservoirExactSolutionERM(responseTime, effectiveStorage,
				recharge, pumping);

		Path output = Path.of("/home/andreisd/Desktop/out_step1.csv");

		try (BufferedWriter writer = Files.newBufferedWriter(output)) {
			writer.write("time,h_rk,h_exact,error,q_rk,q_exact");
			int i = 0;
			for (var hEstimate : hRk) {
				double t = t0 + i * dt;
				double hExact = exactSolution.getH(t);
				double qExact = exactSolution.getQ(t);
				double qNumerical = ode.computeDischarge(hEstimate);
				double error = hEstimate - hExact;
				writer.newLine();
				writer.write(t + "," + hEstimate + "," + hExact + "," + error + "," + qNumerical + "," + qExact);
				writer.newLine();
				i++;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Simulation completed.");
		System.out.println("Final numerical h = " + state[0]);
	}
}
