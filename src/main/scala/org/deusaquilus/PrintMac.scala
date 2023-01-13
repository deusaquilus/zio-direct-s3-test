package org.deusaquilus

import scala.quoted._

object PrintMac {

  inline def apply(inline any: Any): Unit = ${ printMacImpl('any, '{ false }) }
  inline def detail(inline any: Any): Unit = ${ printMacImpl('any, '{ true }) }
  inline def passthrough[T](inline any: T): T = ${ printMacImpl('any, '{ false }) }

  def printMacImpl[T: Type](anyRaw: Expr[T], showDetailRaw: Expr[Boolean])(using Quotes): Expr[T] = {
    import quotes.reflect._
    val showDetail = Expr.unapply(showDetailRaw).getOrElse { report.errorAndAbort("showDetail must be a constant value true/false") }

    val any = anyRaw.asTerm.underlyingArgument.asExprOf[T]
    val deser = any.asTerm

    println("================= Tree =================")
    println(Printer.TreeAnsiCode.show(any.asTerm))

    if (showDetail) {
      println("================= Detail =================")
      println(Printer.TreeStructure.show(any.asTerm))
    }

    Trees.exists(anyRaw.asTerm, Symbol.spliceOwner) {
      case id: Ident =>
        val idTree = id.tpe.typeSymbol.tree

        println(
          "========= All Annotations ============\n" +
            // id.symbol.annotations.map(_.show)
            id.tpe.typeSymbol.annotations.map(_.show)
        )

        println(s"================= Tree of `${id.show}` =================")
        println(Printer.TreeAnsiCode.show(idTree))

        if (showDetail) {
          println(s"================= Detail of `${id.show}` =================")
          println(Printer.TreeStructure.show(idTree))
        }

        true
    }

    any
  }
}
