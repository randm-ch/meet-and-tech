package com.leonteq.meetandtech

object Examples {

  /** Some nice things about Scala */
  Main // Hello world!

  // String interpolation
  val typeOfMessage = "fancy"
  println(s"I am a $typeOfMessage debug message")

  // Everything is an object
  val oneString: String = 1.toString
  val result: Int = 1 + 2
  val sameResult: Int = 1.+(2)

  // Everything returns a value
  val world = "still fine for now"
  val behaviour: String = if (world == "ending") "PANIC" else "go on"

  // Much less boilerplate
  // @see case class Entry










  /** Option: No more NPEs! */
  val db: Db = Db()

  val one: Long = db.getUnsafeEntry(1L).param // That's fine
  val two: Long = db.getUnsafeEntry(2L).param // BOOM!

  val maybeOne: Option[Long] = db.getEntry(1L).map(_.param) // Some(1L)
  val maybeTwo: Option[Long] = db.getEntry(2L).map(_.param) // None










  /** Pattern matching */
  def multiplyEntryValue(id: Long, min: Long): Long = db.getEntry(id) match {
    case Some(Entry(value)) if value >= min => value * 10
    case Some(Entry(value)) => value
    case _ => 0
  }










  /** Scala collections are awesome :) */
  val ids: List[Long] = (1L to 5L).toList // List(1,2,3,4,5)
  ids.map((l: Long) => db.getEntry(l)) // List[Option[Entry]]
  ids.map(db.getEntry) // Make it nice
  ids.map(db.getEntry).filter(_.isDefined).map(_.get) // List[Entry]
  ids.map(db.getEntry).flatten // Option is a collection with max length 1
  ids.flatMap(db.getEntry) // Even better :)










  /** for loops + comprehension */
  for {
    i <- 1 to 10
    k <- List("a", "b", "c")
  } {
    println(s"Value: $k$i")
  }

  // Get 4 entries and collect the results. Urgh...
  db.getEntry(1L).flatMap(one =>
    db.getEntry(2L).flatMap(two =>
      db.getEntry(3L).flatMap(three =>
        db.getEntry(4L).map(four => one.param + two.param + three.param + four.param)
      )
    )
  )

  // equals

  for {
    one <- db.getEntry(1L)
    two <- db.getEntry(2L)
    three <- db.getEntry(3L)
    four <- db.getEntry(4L)
  } yield one.param + two.param + three.param + four.param










  /** Functions are values */
  val getAnEntry: Long => Option[Entry] = (id: Long) => db.getEntry(id)
  val entries2: List[Entry] = ids.flatMap(getAnEntry)










  /** So much more... */
  FancyMain // Hello world! Wait... why?

  // Scala.js, scala-native

}
