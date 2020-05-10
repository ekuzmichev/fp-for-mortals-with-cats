val projectName = "project-name" // TODO: Change it

ThisBuild / organization := "io.github.ekuzmichev"
ThisBuild / organizationName := "ekuzmichev"
ThisBuild / organizationHomepage := Some(url("https://github.com/ekuzmichev"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/ekuzmichev/$projectName"),
    s"scm:git@github.com:ekuzmichev/$projectName.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "ekuzmichev",
    name = "Evgenii Kuzmichev",
    email = "evgenii.e.kuzmichev@gmail.com",
    url = url("https://github.com/ekuzmichev")
  )
)

ThisBuild / description := "Some description" // TODO: Change it
ThisBuild / licenses := List("MIT" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage := Some(url(s"https://github.com/ekuzmichev/$projectName"))

ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / crossScalaVersions := Seq("2.12.8", "2.13.1")
ThisBuild / scalacOptions ++= scalacOptionsVersion(scalaVersion.value)
ThisBuild / releaseCrossBuild := true

val crossScalaOptions = Seq("-unchecked", "-deprecation")
def scalacOptionsVersion(scalaVersion: String) =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, 13)) => crossScalaOptions
    case _             => crossScalaOptions :+ "-Ypartial-unification"
  }

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    libraryDependencies ++= Seq(
      libs.scalaTest % Test
    ),
    releasePublishArtifactsAction := PgpKeys.publishSigned.value
  )

lazy val libs = new {
  val scalaTestV = "3.1.1"

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestV
}
