package com.adventofcode

import scala.io.Source

object Day5 extends App {
  val input = Source.fromResource("day5.txt").getLines().mkString
  //  val input = "dabAcCaCBAcCcaDA"

  val combos = ('a' to 'z').map { letter =>
    s"($letter${letter.toUpper}|${letter.toUpper}$letter)"
  }

  def processReactions(s: String): String = {
    //    input
    //      .sliding(2)
    //      .toList
    //      .find(combos.contains)
    //      .fold(input)(pair => processReactions(input.replace(pair, "")))
    val result = combos.fold(s) { case (acc, c) => acc.replaceAll(c, "") }

    if (result == s) s
    else processReactions(result)
  }

  val solution1 = processReactions(input).length
  println(s"SOLUTION 1: $solution1")

  val letters = ('a' to 'z').map { letter => s"[$letter${letter.toUpper}]" }

  val solution2 =
    letters
      .map(c => input.replaceAll(c, ""))
      .map(i => processReactions(i).length).min

  println(s"SOLUTION 2: $solution2")
}
