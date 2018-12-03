package com.adventofcode

import scala.annotation.tailrec
import scala.io.Source

object Day1 extends App {
  val input = Source.fromResource("day1.txt").getLines().map(_.toLong).toList

  val solution1 = input.sum
  println(s"SOLUTION1: $solution1")

  val input2 = Stream.continually(input).flatten

  @tailrec
  def findDuplicate(acc: Long, results: List[Long], s: Stream[Long]): Long = {
    val result = acc + s.head

    if (results.contains(result)) result
    else findDuplicate(result, results :+ result, s.tail)
  }

  val solution2 = findDuplicate(0, List.empty, input2)
  println(s"SOLUTION2: $solution2")
}
