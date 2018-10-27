package spiked.topology.coords.spatial

import com.digitalcipher.spiked.topology.coords.spatial
import com.digitalcipher.spiked.topology.coords.spatial.Coordinates.{Cartesian, Cylindrical, Spherical}
import spiked.BaseSpec
import squants.space.{Length, Microns, Nanometers, Radians}

/**
  * Created by rob on 12/26/16.
  */
class SpatialCoordTest extends BaseSpec {

  import com.digitalcipher.spiked.topology.coords.spatial._

  "A unit vector" should "have length 1 mm" in {
    assert(Cartesian(1, 0, 0).norm == Microns(1))
    assert(Cylindrical(1, 0, 0).norm == Microns(1))
    assert(Cylindrical(0, 0, 1).norm == Microns(1))

    assert(Spherical(1, 0, 0).norm == Microns(1))
    assert(Spherical(1, math.Pi, 0).norm == Microns(1))

    assert(Spherical(Microns(1), Radians(0), Radians(0)).norm == Microns(1))
  }

  it should "be able to add coordinates of different types" in {
    implicit val tolerance: Length = Nanometers(1)
    assert((Cartesian(1, 0, 0) + Cylindrical(1, math.Pi, 0)).norm ~= Microns(0))
    assert((Cartesian(1, 0, 0) + Cylindrical(1, math.Pi / 2, 0)).norm ~= Microns(math.sqrt(2)))
    assert((Cylindrical(1, math.Pi, 0) + Cartesian(1, 0, 0)).norm ~= Microns(0))
    assert((Cylindrical(1, math.Pi / 2, 0) + Cartesian(1, 0, 0)).norm ~= Microns(math.sqrt(2)))
  }

  it should "convert from one type and back without effecting its norm" in {
    implicit val tolerance: Length = Nanometers(1)
    assert(spatial.toCartesian(Cylindrical(1, 2, 3)).norm - Cylindrical(1, 2, 3).norm ~= Microns(0))
    assert(spatial.toCylindrical(spatial.toCartesian(Cylindrical(1, 2, 3))).norm - Cylindrical(1, 2, 3).norm ~= Microns(0))
  }
}
