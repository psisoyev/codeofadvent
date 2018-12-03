package com.adventofcode

import scala.io.Source

object Day3 extends App {
  case class Claim(id: Int, fromLeft: Int, fromTop: Int, width: Int, height: Int)
  case class Point(id: Int, x: Int, y: Int)
  val ClaimRegex = """^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""".r

    val input = Source.fromResource("day3.txt").getLines().toList
//  val input = List("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")

  val claims = input.map {
    case ClaimRegex(id, fromLeft, fromTop, width, height) => Claim(id.toInt, fromLeft.toInt, fromTop.toInt, width.toInt, height.toInt)
  }

  val points = for {
    claim <- claims
    i <- claim.fromLeft until (claim.width + claim.fromLeft)
    j <- (claim.height + claim.fromTop) until claim.fromTop by -1
  } yield Point(claim.id, i, j)

  val solution1 = points
    .groupBy(p => (p.x, p.y))
    .values
    .count(_.size > 1)

  println("SOLUTION 1: " + solution1)

  val overlappingIds = points
    .groupBy(p => (p.x, p.y))
    .values
    .collect { case x if x.size > 1 => x.map(_.id) }
    .flatten
    .toList

  val solution2 = points
    .filterNot(p => overlappingIds.contains(p.id))
    .map(_.id)
    .toSet

  println("SOLUTION 2: " + solution2)
}
