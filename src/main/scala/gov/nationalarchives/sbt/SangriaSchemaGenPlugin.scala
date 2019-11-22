package gov.nationalarchives.sbt

import sbt._
import sbt.plugins.JvmPlugin
import treehugger.forest._
import java.io.File

import definitions._
import graphql.schema.idl.{SchemaParser, TypeDefinitionRegistry}
import treehuggerDSL._
import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaGenerator
import scala.util.{Failure, Success}



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
      val query = "type Mutation {\n    upvotePost (\n      postId: Int!\n    ): Post\n  }"
      val schemaParser = new SchemaParser()
      val parsed: TypeDefinitionRegistry = schemaParser.parse(query)
      println(parsed.getType("Mutation").get().getChildren.get(0).getChildren.get(0))
      val tree = BLOCK(
        IMPORT("sangria.macros.derive.GraphQLField"),
        CASECLASSDEF("User")
          withParams PARAM("name", StringClass) : Tree,
        TRAITDEF("Mutation") := BLOCK(
          PROC("addUser") withParams PARAM("name", StringClass) withAnnots ANNOT(TYPE_REF("GraphQLField")) := BLOCK(
            VAL("user", TYPE_REF(REF("User"))) := REF("User") APPLY REF("name")
          )
        )
      ) inPackage("gov.nationalarchives")

      val file = new File("src/main/scala/GraphQlTypes.scala")
      IO.write(file, treeToString(tree))
    }
  )
}
