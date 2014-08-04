import play.{PlayJava, PlayImport, PlaySettings}

name := """conline-rest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  PlayImport.javaCore,
  PlayImport.javaJpa,
  "org.apache.commons" % "commons-email" % "1.3.3",
  "com.amazonaws" % "aws-java-sdk" % "1.8.4",
  "org.springframework" % "spring-context" % "3.2.2.RELEASE",
  "javax.inject" % "javax.inject" % "1",
  "org.springframework.data" % "spring-data-jpa" % "1.3.2.RELEASE",
  "org.springframework" % "spring-expression" % "3.2.2.RELEASE",
  "org.hibernate" % "hibernate-entitymanager" % "3.6.10.Final",
  PlayImport.cache
  //javaJdbc,
  //javaEbean,
  //javaWs
)