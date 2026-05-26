package eu.danieleandreis.reservoiresolver.ode;

public class ReservoirExactSolutionERM {
	private double k = 0;
	private double h0 = 0;
	private double aXn = 0;
	private double qin = 0;
	private double qp = 0;

	public ReservoirExactSolutionERM(double k, double aXn, double h0, double qin, double qp) {
		this.aXn = aXn;
		this.k = k;
		this.h0 = h0;
		this.qin = qin;
		this.qp = qp;
	}

	public double getQ(double t) {
		return aXn / k * getH(t);
	}

	public double getH(double t) {
		double coeff = k / aXn;
		if(t==0) {
			return h0 =coeff * qin;

		}
		return (coeff * qin - coeff * (qin - qp)) * Math.exp(-t / k) + coeff * (qin - qp);
	}

}
