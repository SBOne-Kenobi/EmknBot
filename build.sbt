version in Global := "0.1"
scalaVersion in Global := "2.13.5"

lazy val root = project.in(file("."))
    .settings(
        name := "EmknBot",
        mainClass in (Compile, run) := Some("ru.codegen.emknbot.Main")
    )
    .settings(
        libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % "test"
    )
    .settings(
        assemblyJarName in assembly := "EmknBot.jar",
        test in assembly := {},
        mainClass in assembly := Some("ru.codegen.emknbot.Main")
    )