package com.adventofcode

import scala.io.Source

object Day7 extends App {
  val StepRegex = """Step ([A-Z]) must be finished before step ([A-Z]) can begin.""".r

  type Letter = String
  type WorkerId = Int
  type Seconds = Int
  type Dependencies = List[Letter]
  type StepsLeft = Map[Letter, List[Letter]]

  case class Step(name: Letter, dependsOn: Letter)
  case class Work(timeLeft: Seconds, letter: Letter)

  val input = Source.fromResource("day7.txt").getLines().map {
    case StepRegex(after, name) => Step(name, after)
  }.toList

  val independentSteps = {
    val dependencies = input.map(_.dependsOn).distinct
    val stepNames = input.map(_.name)

    dependencies.union(stepNames).distinct.diff(stepNames).sorted
  }

  def runSteps(independentSteps: List[Letter], stepsLeft: StepsLeft, executionOrder: Dependencies): List[Letter] = {
    independentSteps match {
      case Nil     => executionOrder
      case x :: xs =>
        val (independent, dependent) = findNextSteps(x, stepsLeft)

        val newDependencies = xs ++ independent.keys
        runSteps(newDependencies.sorted, dependent, executionOrder :+ x)
    }
  }

  val initialSteps = input.groupBy(_.name).map {
    case (name, steps) => name -> steps.map(_.dependsOn).distinct
  }

  val solution1 = runSteps(independentSteps, initialSteps, List.empty)
  println(s"SOLUTION 1: ${solution1.mkString}")


  val timePerLetter = ('A' to 'Z').toList.zipWithIndex.map { case (l, s) => l -> (s + 1) }
  val workers = 2

  def runStepsPar(
    currentSecond: Seconds,
    currentState: Map[WorkerId, Option[Work]],
    independentSteps: List[Letter],
    stepsLeft: Map[Letter, Dependencies]): List[Letter] = {

    val finishedSteps = currentState.map {
      case (id, Some(w)) => id -> Some(w.copy(w.timeLeft - 1))
      case x => x
    }.collect { case (id, Some(Work(0, l))) => id -> l }

    val zeroState = (Map[WorkerId, Work](), Map[Letter, Dependencies]())
    finishedSteps.values.fold(zeroState) {
      case (x, y) => x
    }


    ???
  }

  private def findNextSteps(l: Letter, stepsLeft: Map[Letter, List[Letter]]): (Map[Letter, List[Letter]], Map[Letter, List[Letter]]) = {
    val updatedStepsLeft = stepsLeft.collect { case (name, dependsOn) => name -> dependsOn.filterNot(_ == l) }
    val (independent, dependent) = updatedStepsLeft.partition { case (_, dependsOn) => dependsOn.isEmpty }
    (independent, dependent)
  }

  val initState = (1 to workers).map(_ -> None).toMap
  runStepsPar(0, initState, independentSteps, initialSteps)
}
