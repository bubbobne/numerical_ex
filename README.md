# Reservoir Routing with Runge-Kutta and Apache Commons Math

## Overview

Model groundwater discharge dynamics using a simplified conceptual reservoir approach and numerical integration techniques available in Apache Commons Math.

This example is from Rimmer et al. (2012) paper, the goal is to simulate the temporal evolution of hydraulic head using ordinary differential equations (ODEs).

The numerical Runge-Kutta solution is compared against the analytical solution derived from the reservoir equation in Rimmer et al. (2012).

---

# Model Parameters

The exercise uses the following values adapted from the Uja Spring aquifer example:

| Parameter | Value | Description |
|---|---|---|
| \(A n\) | 80000 m² | Effective aquifer storage A * n in the paper |
| \(Q_p\) | 8200 m³/day | Pumping rate |
| \(Q_{in}\) | 8200 m³/day | Recharge inflow |
| \(K\) | 1980 days | Aquifer response time |

Java initialization:

```java
double axn = 80000.0;
double qp = 8200.0;
double qin = 8200.0;
double k = 1980.0;
```

---

# Initial Conditions

Before pumping begins, the aquifer is assumed to be at steady state. The initial hydraulic head, corresponding to the groundwater level, is:
\[
h_0=-100 \text{ m asl}
\]

Then an intensive pumping activities was started and the long-term steady-state (after about 30 years) solution after pumping is approximately:

\[
h_{\infty}=-280 \text{ m}
\]

---

# References

Rimmer, A., & Hartmann, A. J. (2012). Simplified conceptual structures and analytical solutions for groundwater discharge using reservoir equations. In D. P. C. Nayak (Ed.), Water Resources Management and Modeling. InTech. https://doi.org/10.5772/34803

Apache Commons Math:

- https://commons.apache.org/proper/commons-math/
