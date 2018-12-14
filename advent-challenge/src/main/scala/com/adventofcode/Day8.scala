package com.adventofcode

import scala.io.Source

object Day8 extends App {

  val input = Source.fromResource("day8.txt").getLines().mkString.split(" ").map(_.toInt).toList

  def check(s: Seq[Int]): Int = {
    def _check(s: Seq[Int]): (Seq[Int], Int) = s match {
      case childrenCount :: metadataCount :: tail =>
        val init = (tail, Seq[Int]())

        val (right, values) = (0 until childrenCount).foldLeft(init) { case ((left, acc), _) =>
          val (leftover, value) = _check(left)
          (leftover.toList, acc :+ value)
        }

        val (metadata, other) = right.splitAt(metadataCount)
        (other, values.sum + metadata.sum)
    }

    _check(s)._2
  }

  val solution1 = check(input)
  println(s"SOLUTION1 : $solution1")

  def check2(s: Seq[Int]): Int = {
    def _check(s: Seq[Int]): (Seq[Int], Int) = s match {
      case childrenCount :: metadataCount :: tail =>
        val init = (tail, Seq[Int]())

        val (right, values) = (0 until childrenCount).foldLeft(init) { case ((left, acc), _) =>
          val (leftover, value) = _check(left)
          (leftover.toList, acc :+ value)
        }

        val (metadata, other) = right.splitAt(metadataCount)

        val value = if (childrenCount > 0)
          metadata.filter(_ <= values.size).map(_ - 1).map(values).sum
        else
          values.sum + metadata.sum

        (other, value)
    }

    _check(s)._2
  }

  val solution2 = check2(input)
  println(s"SOLUTION2 : $solution2")
}
