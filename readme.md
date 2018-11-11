# scala coordinate systems for spikes

![image](https://travis-ci.com/robphilipp/coordinates.svg?branch=master)

## quick start
The `spikes-coords` library can be found on maven central at

```sbtshell
libraryDependencies += "com.digitalcipher.spiked" % "spikes-coords_2.12" % "0.1.1"
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

## conversions
There are a set of functions that allow conversion between coordinate systems. 

| function | arguments | description |
| -------- | --- | --- |
| `toCartesian(...)` | `Cylindrical` or `Spherical` | Converts cylindrical and spherical coordinates to cartesian coordinates |
| `to Cylindrical(...)` | `Cartesian` or `Spherical` | Converts cartesian and spherical coordinates to cylindrical coordinates |
| `toSpherical(...)` | `Cartesian` or `Spherical` | Converts cartesian and cylindrical coordinates to spherical coordinates |

The conversion functions for spatial coordinate systems is in the 
[`com.digitalcipher.spiked.topology.coords.spatial`](src/main/scala/com/digitalcipher/spiked/topology/coords/spatial/package.scala) 
package object. And the conversion functions for dimensionless coordinates are in the 
[`com.digitalcipher.spiked.topology.coords.dimensionless`](src/main/scala/com/digitalcipher/spiked/topology/coords/dimensionless/package.scala)
package object. 
 
For example, to convert a spatial cylindrical coordinate to a cartesian coordinate
```scala
import com.digitalcipher.spiked.topology.coords.spatial

toCartesian(Cylindrical(1, 0, 1)) should equal(Cartesian(1, 0, 1))
```

or using `squants` natural language DSL
```scala
import scala.language.postfixOps
import squants.space.LengthConversions._
import squants.space.AngleConversions._

import com.digitalcipher.spiked.topology.coords.spatial

toCartesian(Cylindrical(1 µm, 0 radians, 1 µm)) should equal(Cartesian(1 µm, 0 µm, 1 µm))
toCartesian(Cylindrical(1 µm, math.Pi / 2 radians, 0 µm)) should equal(Cartesian(0 µm, 1 µm, 0 µm))
```

## distance and norm
All the coordinates provide a `norm` method, which is the distance to the origin. For example,
```scala
Cartesian(1, 0, 0).norm should be (1)
```

And with the beuauty of `squants` you can write this in a natural way as
```scala
import scala.language.postfixOps
import squants.space.LengthConversions._
import squants.space.AngleConversions._

Spherical(1 µm, 0 radians, 0 radians).norm should be (1 µm)
```

Coordinates also provide a method to calculate the distance to another coordinate. For example,
to calculate the distance between two dimensionless coordinates.
```scala
Cartesian(1, 0, 0).distanceTo(Cartesian(2, 0, 0)) should be (1)
```

