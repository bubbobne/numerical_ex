package eu.danieleandreis.reservoiresolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

		double h0 = 180.0; // initial hydraulic head [m]

		double[] state = new double[] { h0 };
		int n = (int) (Math.floor((tEnd - t0) / dt) + 1);

		double[] hRk = new double[n];
		ReservoirApacheMathODE ode = new ReservoirApacheMathODE(responseTime, effectiveStorage, recharge, pumping);

		ClassicalRungeKuttaIntegrator integrator = new ClassicalRungeKuttaIntegrator(dt);

		int idx = 0;

		integrator.addStepHandler(new StepHandler() {

			@Override
			public void init(double t0, double[] y0, double t) {

				//time[0] = t0;

				hRk[0] = y0[0];

				idx = 1;
			}

			@Override
			public void handleStep(StepInterpolator interpolator, boolean isLast) {
				//time[idx] = interpolator.getCurrentTime();

				hRk[idx] = interpolator.getInterpolatedState()[0];

				idx++;
			}
		});

		integrator.integrate(ode, t0, state, tEnd, state);

		ReservoirExactSolutionERM exactSolution = new ReservoirExactSolutionERM(responseTime, effectiveStorage, h0,
				recharge, pumping);

		Path output = Path.of("out_step1.csv");

		try (BufferedWriter writer = Files.newBufferedWriter(output)) {
			writer.write("time,h_rk,h_exact,error,q_rk,q_exact");
			for (int i = 0; i < idx; i++) {
				double t = t0 + i * dt;
				double hExact = exactSolution.getH(t);
				double qExact = exactSolution.getQ(t);
				double qNumerical = 0;
				double error = hRk[i] - hExact;
				writer.newLine();
				writer.write(t + "," + hRk[i] + "," + hExact + "," + error + "," + qNumerical + "," + qExact);
				writer.newLine();
			}

		}catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Simulation completed.");
		System.out.println("Final numerical h = " + state[0]);
	}
}
