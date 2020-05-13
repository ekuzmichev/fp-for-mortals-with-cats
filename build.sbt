val projectName = "fp-for-mortals-with-cats"

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

ThisBuild / description := "Solutions/samples for \"Functional Programming for Mortals with Cats\""
ThisBuild / licenses := List("MIT" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage := Some(url(s"https://github.com/ekuzmichev/$projectName"))

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / scalacOptions ++= Seq(
  "-language:_",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    libraryDependencies ++= Seq(
      libs.catsCore,
      libs.simulacrum,
      libs.scalaTest % Test
    )
  )

lazy val libs = new {
  val catsV       = "2.1.1"
  val scalaTestV  = "3.1.1"
  val simulacrumV = "1.0.0"

  val scalaTest  = "org.scalatest" %% "scalatest"  % scalaTestV
  val simulacrum = "org.typelevel" %% "simulacrum" % simulacrumV
  val catsCore   = "org.typelevel" %% "cats-core"  % catsV
}
