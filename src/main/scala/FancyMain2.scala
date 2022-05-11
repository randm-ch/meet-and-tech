package com.leonteq.meetandtech

import cats.effect.{IO, IOApp}

import java.text.ParseException
import scala.util.control.NonFatal

object FancyMain2 extends IOApp.Simple {

  private val program =
    for {
      conf <- getConfig
      conn <- getDbConnection(conf.db)
      repo  = getEntryRepository(conn)
      res  <- repo.get(1L)
      _    <- IO.println(res)
    } yield ()

  override val run: IO[Unit] =
    program.handleErrorWith {
      case e: ParseException => IO.println(s"Could not parse config; error is on line ${e.getErrorOffset}")
      case NonFatal(e) => IO.println(s"Something else went wrong: ${e.getMessage}")
    }

  // Read from conf file and parse into `Config` case class
  private def getConfig: IO[Config] = ???

  // Open a socket connection to the DB
  private def getDbConnection(config: DbConfig): IO[DbConnection] = ???

  // Create a new instance of `Repository` (no side-effects)
  private def getEntryRepository(connection: DbConnection): Repository[Entry] =
    new Repository[Entry] {
      override def get(id: Long): IO[Option[Entry]] =
        connection.transact(s"select * from entries where id = $id")
      override def insert(t: Entry): IO[UpdateResult] =
        connection.transact(s"insert into entries values (${t.param})")
    }

}

// Some definitions
trait DbConnection { // This will be implemented by a (orm, jdbc, ...) library
  def transact[T](dbQuery: String): IO[T]
}
trait Repository[T] {
  type UpdateResult = Int
  def get(id: Long): IO[Option[T]]
  def insert(t: T): IO[UpdateResult]
}
case class Config(db: DbConfig)
case class DbConfig(url: String, credentials: Option[CredentialsConfig])
case class CredentialsConfig(user: String, password: Option[String])
