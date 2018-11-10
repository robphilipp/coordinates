# scala coordinate systems for spikes

![image](https://travis-ci.com/robphilipp/coordinates.svg?branch=master)

## quick start
The `spikes-coords` library can be found on maven central at

```sbtshell
libraryDependencies += "com.digitalcipher.spiked" % "spikes-coords_2.12" % "0.0.2"
```

## motivation
Compared to second generation neural networks, spiking neural networks add a temporal dimension. Spikes
occur at a specific point in time, and have a finite duration. Because signals travel down the neuron's axon
at finite speed, the distance between neurons plays an important role in spiking neural networks. Signal
timing is determined by incoming signals, and also by the physical topology of the network.

The `spikes-coord` library provides a spatial (and dimensionless) coordinate system for describing neural
network topology. To account for different symmetries, and organizational structures, a neuron's coordinate
can be defined using cartesian, cylindrical, and spherical coordinate systems. And to facilitate mixing 
coordinate systems, conversion between coordinate types are handled using implicits. For example, With 
`spikes-coords` you can add or subtract cartesian and cylindrical coordinates. For example,

```scala
import com.digitalcipher.spiked.topology.coords.dimensionless._

val origin = Cartesian(1, 0, 0) - Cylindrical(1, 0, 0)
```

yields the cartesian origin `Cartesian(0, 0, 0)`.

The `spikes-coord` library also provides spatial coordinate systems. These are coordinate systems with
a measure of distance attached. The spatial units default to µm for distance, which are biologically 
sensible for describing neuronal locations. The angle default to radians. In this way

```scala
import com.digitalcipher.spiked.topology.coords.spatial._
import squants.space._

Cylindrical(1, math.Pi / 2, 0) should equal(Cylindrical(Microns(1), Radians(math.Pi / 2), Microns(0)))
```

## coordinate systems

The three types of coordinate systems for spatial and dimensionless coordinates offer by the `spikes-coords` 
library are:
1. Cartesian (x, y, z)
2. Cylindrical (r, φ, z)
3. Spherical (r, φ, θ)

## distance and norm
All the coordinates provide a `norm` method, which is the distance to the origin.

