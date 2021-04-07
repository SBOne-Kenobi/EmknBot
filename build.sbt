version in Global := "0.1"
scalaVersion in Global := "2.13.5"

lazy val root = project.in(file("."))
    .settings(
        name := "EmknBot",
        run := run.in(Compile, run).evaluated,

        libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % Test
    )