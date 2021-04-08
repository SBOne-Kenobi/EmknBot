version in Global := "0.1"
scalaVersion in Global := "2.13.5"

lazy val root = project.in(file("."))
    .settings(
        name := "EmknBot",
        Compile / run / mainClass := Some("ru.codegen.emknbot.Main")
    )
    .settings(
        libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % "test"
    )
    .settings(
        assembly / assemblyJarName  := "EmknBot.jar",
        assembly / test := {},
        assembly / mainClass := Some("ru.codegen.emknbot.Main")
    )
    .settings(
        semanticdbEnabled := true,
        semanticdbVersion := scalafixSemanticdb.revision,
        scalacOptions += "-Ywarn-unused"
    )