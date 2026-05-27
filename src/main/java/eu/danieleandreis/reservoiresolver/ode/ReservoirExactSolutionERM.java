package eu.danieleandreis.reservoiresolver.ode;

/**
 * Exact Solution
 *
 * @author Daniele Andreis and Giuseppe Formetta
 */
public class ReservoirExactSolutionERM {
	private double k = 0;
	private double aXn = 0;
	private double qin = 0;
	private double qp = 0;
	private double h0 = 0;
	private double coeff = 0;

	public ReservoirExactSolutionERM(double k, double aXn, double qin, double qp) {
		this.aXn = aXn;
		this.k = k;
		this.qin = qin;
		this.qp = qp;
		coeff = k / aXn;
		h0 = coeff * qin;
	}

	public double getQ(double t) {
		return aXn / k * getH(t);
	}

	public double getH(double t) {
		if (t == 0) {
			return h0;
		}

		return (h0 - coeff * (qin - qp)) * Math.exp(-t / k) + coeff * (qin - qp);
	}

}
