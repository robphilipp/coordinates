// language features
scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

//
// common settings
//
lazy val commonSettings = Seq(
  organization := "com.digitalcipher.spiked",
  version := "0.0.1-snapshot",
  scalaVersion := "2.12.7"
)

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "spikes",

    //
    // dependencies
    //
    libraryDependencies ++= Seq(
      // logging
      "ch.qos.logback" % "logback-classic" % "1.2.3",

      // dimensions
      "org.typelevel" %% "squants" % "1.3.0",

      // testing
      //      "org.scalactic" %% "scalactic" % "3.0.1",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )