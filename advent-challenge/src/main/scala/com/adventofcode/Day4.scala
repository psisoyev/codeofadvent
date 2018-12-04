package com.adventofcode

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.io.Source

object Day4 extends App {
  implicit val LocalDateTimeOrdering: Ordering[LocalDateTime] = _ compareTo _

  trait Event { def timestamp: LocalDateTime }

  type Minute = Int
  type TimesGuardSlept = Int
  type GuardId = Int

  case class ShiftStarted(timestamp: LocalDateTime, id: GuardId) extends Event
  case class FallenAsleep(timestamp: LocalDateTime) extends Event
  case class WokenUp(timestamp: LocalDateTime) extends Event

  case class GuardState(currentGuard: GuardId, asleepFrom: Option[Minute], sleepingMap: Map[GuardId, List[Minute]])
  val EmptyState = GuardState(0, None, Map.empty)

  val input = Source.fromResource("day4.txt").getLines().toList

  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  val TimestampRegex = "\\[(.+)\\] (.+)".r
  val StartShiftRegex = """Guard #(\d+) begins shift""".r
  val FallsAsleep = "falls asleep"
  val WokeUp = "wakes up"

  val events: List[Event] = input.map {
    case TimestampRegex(timestamp, event) =>
      val time = LocalDateTime.from(formatter.parse(timestamp))
      event match {
        case StartShiftRegex(id) => ShiftStarted(time, id.toInt)
        case FallsAsleep => FallenAsleep(time)
        case WokeUp => WokenUp(time)
      }
  }.sortBy(_.timestamp)

  val state = events.scanLeft(EmptyState) {
    case (currentState, event) => event match {
      case ShiftStarted(_, id) => currentState.copy(currentGuard = id)
      case FallenAsleep(time) => currentState.copy(asleepFrom = Some(time.getMinute))
      case WokenUp(time) =>
        val guardId = currentState.currentGuard
        val guardMinutes = currentState.sleepingMap.getOrElse(guardId, List.empty)
        val fallenAsleepAt = currentState.asleepFrom.getOrElse(sys.error("Woke up while not sleeping!"))

        val updatedGuardMinutes = guardMinutes ++ (fallenAsleepAt until time.getMinute)
        val updatedMap = currentState.sleepingMap + (guardId -> updatedGuardMinutes)

        currentState.copy(sleepingMap = updatedMap)
    }
  }.last

  val sleepingMap = state.sleepingMap.map { case (id, minutes) =>
    id -> minutes.groupBy(identity).map { case (min, count) => min -> count.size }
  }

  val (guardId, (_, mostPopularMinute)) = sleepingMap.map { case (id, minuteMap) =>
    val (mostPopularMinute, _) = minuteMap.maxBy(_._2)
    id -> (minuteMap.values.sum, mostPopularMinute)
  }.maxBy { case (_, (minuteCount, _)) => minuteCount }

  val solution1 = guardId * mostPopularMinute
  println("SOLUTION 1: " + solution1)

  val (guardId2, (minute, _)) = sleepingMap.map { case (id, minuteMap) =>
    val (mostPopularMinute, count) = minuteMap.maxBy(_._2)
    id -> (mostPopularMinute, count)
  }.maxBy { case (_, (_, timesSlept)) => timesSlept }

  val solution2 = guardId2 * minute
  println("SOLUTION 2: " + solution2)
}
