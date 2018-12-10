package com.adventofcode

import scala.io.Source

object Day6 extends App {
  val CoordinateRegex = "(\\d+), (\\d+)".r
  type X = Int
  type Y = Int
  case class Coordinate(x: X, y: Y) {
    def distance(to: Coordinate): Int = Math.abs(x - to.x) + Math.abs(y - to.y)
  }

  val input = Source.fromResource("day6.txt").getLines().map {
    case CoordinateRegex(x, y) => Coordinate(x.toInt, y.toInt)
  }.toSeq

  val (minX, maxX, minY, maxY) = (input.map(_.x).min, input.map(_.x).max, input.map(_.y).min, input.map(_.y).max)

  def computeAreas(rangeX: Range, rangeY: Range): Seq[(Coordinate, Int)] = {
    def area(x: X, y: Y): Option[Coordinate] = {
      val p = Coordinate(x, y)
      val min = input.map(p.distance).min

      input.filter(p.distance(_) == min) match {
        case x :: Nil => Some(x)
        case _        => None
      }
    }

    val areas = for {
      y <- rangeX.toList
      x <- rangeY.toList
      area <- area(x, y).toList
    } yield area

    areas.groupBy(identity).mapValues(_.size).toSeq
  }

  def solution1: Int = {
    computeAreas(minX to maxX, minY to maxY)
      .zip(computeAreas((minX - 1) to (maxX + 1), (minY - 1) to (maxY + 1)))
      .filter(t => t._1._2 == t._2._2).map(_._1).minBy(-_._2)._2
  }
  println("SOLUTION 1: " + solution1)

  val region = for {
    y <- minY to maxY
    x <- minX to maxX
    p = Coordinate(x, y)
    if input.map(p.distance).sum < 10000
  } yield 1

  def solution2: Int = region.sum
  println("SOLUTION 2: " + solution2)
}
