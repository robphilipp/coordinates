package spiked.topology.coords.dimensionless

import com.digitalcipher.spiked.topology.coords.dimensionless.Coordinates.{Cartesian, Cylindrical, Spherical}
import spiked.BaseSpec

/**
  * Created by rob on 12/25/16.
  */
class DimensionlessCoordTest extends BaseSpec {

  "A unit vector" should "have length 1" in {
    assert(Cartesian(1, 0, 0).norm == 1)
    assert(Cylindrical(1, 0, 0).norm == 1)
    assert(Spherical(1, 0, 0).norm == 1)

    assertResult(1) {
      Spherical(1, math.Pi, 0).norm
    }
  }
}
