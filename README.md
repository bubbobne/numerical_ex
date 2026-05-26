# Reservoir Routing with Runge-Kutta and Apache Commons Math

## Overview

Model groundwater discharge dynamics using a simplified conceptual reservoir approach and numerical integration techniques available in Apache Commons Math.

References:

> Rimmer, A. & Hartmann, A.  
> *Simplified Conceptual Structures and Analytical
>Solutions for Groundwater Discharge
> Using Reservoir Equationss*

The goal is to simulate the temporal evolution of hydraulic head using ordinary differential equations (ODEs).

The numerical Runge-Kutta solution is compared against the analytical solution derived from the reservoir equation.

---

# Model Parameters

The exercise uses the following values adapted from the Uja Spring aquifer example:

| Parameter | Value | Description |
|---|---|---|
| \(A n\) | 80000 m² | Effective aquifer storage |
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

Before pumping begins, the aquifer is assumed to be at steady state.

The initial hydraulic head is:

\[
h_0=-100 \text{ m}
\]

corresponding to groundwater levels measured before intensive pumping activities.

The long-term steady-state solution after pumping is approximately:

\[
h_{\infty}=-280 \text{ m}
\]

---

# References

Rimmer, A. & Hartmann, A.

> *Simplified Conceptual Structures and Analytical Solutions for Groundwater Discharge Using Reservoir Equations*

Hydrogeology references:

- Bear, J. — *Hydraulics of Groundwater*
- Freeze & Cherry — *Groundwater*
- Todd & Mays — *Groundwater Hydrology*

Apache Commons Math:

- https://commons.apache.org/proper/commons-math/
