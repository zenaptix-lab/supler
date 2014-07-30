package org.supler.demo

import org.supler.Supler
import Supler._

object PersonForm {
  val carMakesAndModels = Map(
    "Ford" -> List("Ka", "Focus", "Mondeo", "Transit"),
    "Toyota" -> List("Aygo", "Yaris", "Corolla", "Auris", "Verso", "Avensis", "Rav4"),
    "KIA" -> List("Picanto", "Venga", "cee'd", "sport c'eed", "Carens", "Sportage"),
    "Lada" -> List("Niva")
  )
  val carForm = form[Car](f => List(
    f.field(_.make).use(dataProvider(_ => carMakesAndModels.keys.toList)),
    f.field(_.model).use(dataProvider(car => carMakesAndModels(car.make))),
    f.field(_.year)
  ))

  val personForm = form[Person](f => List(
    f.field(_.firstName).label("First name"),
    f.field(_.lastName).label("Last name"),
    f.field(_.age).label("Age"),
    f.field(_.address1).label("Address 1"),
    f.field(_.address2).label("Address 2"),
    f.field(_.gender).label("Gender").use(dataProvider(_ => List("Male", "Female"))),
    f.table(_.cars, carForm).label("Cars")
  ))
}

case class Person(
  firstName: String,
  lastName: String,
  age: Int,
  address1: Option[String],
  address2: Option[String],
  gender: String,
  cars: List[Car])

case class Car(
  make: String,
  model: String,
  year: Int
)