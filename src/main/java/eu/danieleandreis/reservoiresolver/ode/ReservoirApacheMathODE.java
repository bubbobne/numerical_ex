package eu.danieleandreis.reservoiresolver.ode;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class ReservoirApacheMathODE implements FirstOrderDifferentialEquations {

	private final double axn;

	private final double k;

	private double qin;

	private double qp;

	public ReservoirApacheMathODE(double k, double axn, double qin, double qp) {
		this.qin = qin;
		this.k = k;
		this.axn = axn;
		this.qp = qp;
	}

	public int getDimension() {
		return 1;
	}

	public void computeDerivatives(double t, double[] y, double[] yDot) {
		yDot[0] = -1 / k * y[0] + (qin - qp) / axn;
	}

	public double computeOutflow(double h) {

		return (axn / k) * h;
	}

	public double computeDischarge(double hNumerical) {
		// TODO Auto-generated method stub
		return 0;
	}

}
