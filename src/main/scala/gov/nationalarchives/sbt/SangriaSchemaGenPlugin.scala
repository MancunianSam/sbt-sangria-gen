package gov.nationalarchives.sbt

import sbt._
import sbt.plugins.JvmPlugin

object SangriaSchemaGenPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {
    val generate = inputKey[Unit]("Generates a sangria CRUD app")
    val schemaLocation: SettingKey[String] =
      settingKey[String]("The location of the schema")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    generate := {
      println(schemaLocation.value)
    }
  )
}
