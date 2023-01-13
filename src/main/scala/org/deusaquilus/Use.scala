package org.deusaquilus

import scala.annotation.StaticAnnotation

case class Annot(value: String) extends StaticAnnotation

object Use {

  // @Annot("FooMethod")
  // def foo(a: String, b: String) = a + b

  @Annot("MyModule")
  trait MyModule {
    def someMethod: String
  }
  val moduleInst: MyModule = ???

  def main(args: Array[String]): Unit = { // // //

    PrintMac.detail {
      moduleInst
    }
  }
}
