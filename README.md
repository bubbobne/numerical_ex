# Reservoir Routing with Runge-Kutta and Apache Commons Math

## Overview

Solve a reservoir routing problem using numerical integration techniques available in Apache Commons Math.

The goal is to model the temporal evolution of water storage inside a reservoir using a (non) linear ordinary differential equation (ODE). The exercise is inspired by:

> *Demonstrating Reservoir Routing in the Classroom: Physical and Mathematical Modeling*  
> James Kilduff, Assistant Professor

In the exercise we compare the numerical Runge-Kutta solution against an analytical solution.

---

# Physical Problem

We consider a reservoir with:

- constant surface area
- inflow hydrograph
- nonlinear outflow relation

The reservoir storage changes according to the continuity equation:

\[
\frac{dS}{dt}=I(t)-Q(t)
\]

where:

- \(S\) = reservoir storage \([L^3]\)
- \(I(t)\) = inflow discharge \([L^3/T]\)
- \(Q(t)\) = outflow discharge \([L^3/T]\)

---

# Reservoir Geometry

The reservoir is assumed to have a constant surface area:

\[
A = 71
\]

The water level \(h\) is related to storage by:

\[
S=A\,h
\]

where:

- \(A = 71\)
- \(h\) = water depth

---

# Outflow Equation

The outflow is governed by a nonlinear square-root relationship:

\[
Q=c_d\sqrt{h}
\]

with:

\[
c_d = 5.59 \times 0.78
\]

Since:

\[
h = \frac{S}{A}
\]

the discharge equation becomes:

\[
Q=c_d\sqrt{\frac{S}{A}}
\]

This nonlinear equation makes the problem suitable for numerical integration methods such as Runge-Kutta.

---

# Simulation Setup

## Time Step

```text
Δt = 10
```

---

## Inflow Phase

For:

```text
0 < t < 300
```

a constant inflow is applied:

\[
I = 12.3
\]

The reservoir progressively fills.

---

## Drainage Phase

For:

```text
t > 300
```

the inflow becomes zero:

\[
I = 0
\]

The reservoir drains only through the outlet.


# References

Kilduff, J.

> *Demonstrating Reservoir Routing in the Classroom: Physical and Mathematical Modeling*

Apache Commons Math Documentation:

- https://commons.apache.org/proper/commons-math/

Hydrological background:

- Chow, Maidment & Mays — *Applied Hydrology*
- Dingman — *Physical Hydrology*
