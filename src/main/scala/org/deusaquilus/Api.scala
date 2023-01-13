package org.deusaquilus

object Api {
  type Monad[T]
  extension [T](m: Monad[T]) {
    def map[R](f: T => R): Monad[R] = m.asInstanceOf[List[T]].map(f).asInstanceOf[Monad[R]]
  }

  def main(args: Array[String]): Unit = {
    val v = List(1, 2, 3)
    println(v.asInstanceOf[Monad[Int]].map(_ + 1))
  }

  /*
  
   */
}
