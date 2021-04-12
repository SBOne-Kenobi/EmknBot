version in Global := "0.1"
scalaVersion in Global := "2.13.5"

lazy val root = project.in(file("."))
    .settings(
        name := "EmknBot",
        Compile / run / mainClass := Some("ru.codegen.emknbot.Main")
    )
    .settings( // dependencies
        libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",
        libraryDependencies += "org.mnode.ical4j" % "ical4j" % "3.0.25",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"
    )
    .settings( // sbt-assembly
        assembly / assemblyJarName := "EmknBot.jar",
        assembly / test := {},
        assembly / mainClass := Some("ru.codegen.emknbot.Main")
    )
    .settings( // scalafix
        semanticdbEnabled := true,
        semanticdbVersion := scalafixSemanticdb.revision,
        scalacOptions += "-Ywarn-unused"
    )
    .settings( // sbt-updates
        dependencyUpdatesFailBuild := true
    )