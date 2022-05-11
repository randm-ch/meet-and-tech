package com.leonteq.meetandtech

trait Db {

  def getEntry(id: Long): Option[Entry]

  def getUnsafeEntry(id: Long): Entry = getEntry(id).orNull

  def insertEntry(e: Entry): Either[Exception, Int]

}

object Db {

  def apply(): Db =
    new Db() {
      override def getEntry(id: Long): Option[Entry] =
        if (id % 2 != 0) Some(Entry(id))
        else None

      override def insertEntry(e: Entry): Either[Exception, Int] = ???
    }

}
