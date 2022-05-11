package com.leonteq.meetandtech

import cats.effect.{IO, IOApp}

object FancyMain extends IOApp.Simple {

  override val run: IO[Unit] =
    IO.println("Hello World!")

}
