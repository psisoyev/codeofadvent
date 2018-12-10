package com.adventofcode

import scala.io.Source

object Day2 extends App {
  val input = Source.fromResource("day2.txt").getLines().toList
  //  val input = List("bababc", "abbcde")
  implicit class BooleanOps(b: Boolean) {
    def toInt: Int = if (b) 1 else 0
  }

  val solution1 = {
    val (twos, threes) = input.map { s =>
      val grouped = s.groupBy(identity).values
      val twos = grouped.exists(_.length == 2)
      val threes = grouped.exists(_.length == 3)
      (twos.toInt, threes.toInt)
    }.fold((0, 0)) {
      case ((twosAcc, threesAcc), (tw, th)) => (twosAcc + tw, threesAcc + th)
    }

    twos * threes
  }

  println("SOLUTION 1: " + solution1)

  val result = for {
    i <- input
    j <- input
  } yield compare(i, j)

  def compare(s1: String, s2: String): Either[Unit, String] = {
    val commonPart = s1.intersect(s2)
    val diff = s1.length - commonPart.length

    if (diff == 1) Right(commonPart)
    else Left("That's not what we are looking for")
  }

  val solution2 =
    result
      .collect { case Right(s) => s }
      .toSet

  println("SOLUTION 2: " + solution2)
}
