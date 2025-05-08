package syntax

// scala3からはgiven/using
def hello1()(using name: String): String = {
  s"Hello, $name"
}

// scala2まではimplicit
def hello2()(implicit name: String): String = {
  s"Hello, $name"
}

given hoge: String = "John"

trait Additive[A] {
  def plus(a: A, b: A): A
  def zero: A
}

// 型クラスのインスタンスを定義。不思議な書き方だ。
given StringAdditive1: Additive[String] = new Additive[String] {
  def plus(a: String, b: String): String = a + "&" + b
  def zero: String = ""
}

// こういう匿名的な書き方もできるこれも不思議な書き方だ。
given Additive[Int] = new Additive[Int] {
  def plus(a: Int, b: Int): Int = a + b + 10_000
  def zero: Int = 0
}

// こういう書き方もできる。これもさらに不思議な書き方だ。
given Additive[Double] with {
  def plus(a: Double, b: Double): Double = a + b + 0.001
  def zero: Double = 0
}

def sum1[A](list: List[A])(using additive: Additive[A]): A = {
  list.foldLeft(additive.zero)((acc, el) => additive.plus(acc, el))
}

// こういう書き方もできる。Context Boundsというらしい。
def sum2[A: Additive](list: List[A]): A = {
  val additive = summon[Additive[A]]
  list.foldLeft(additive.zero)((acc, el) => additive.plus(acc, el))
}

// summonはscala3からで、scala2ではimplicitly
def sum3[A: Additive](list: List[A]): A = {
  val additive = implicitly[Additive[A]]
  list.foldLeft(additive.zero)((acc, el) => additive.plus(acc, el))
}

// asで名前をつけることもできるらしい
def sum4[A: Additive as additive](list: List[A]): A = {
  list.foldLeft(additive.zero)((acc, el) => additive.plus(acc, el))
}

// ================================================================

class Weather(val name: String)

def parent() =
  given Weather = new Weather("sunny")
  child()

def child()(using Weather) =
// def child() =  // これではエラー。関数の呼び出しで、利用するcontextは毎回usingで明示的に引き回される必要がある
  grandChild()

def grandChild()(using Weather) =
  println(s"It's ${summon[Weather].name} today.")

object Using {
  def main(args: Array[String]): Unit = {

    println(hello1()) // Hello, John
    println(hello2()) // Hello, John

    println(sum1(List(1, 2, 3))) // 30_006
    println(sum1(List("a", "b", "c"))) // "&a&b&c"
    println(sum1(List(1.0, 2.0, 3.0))) // 6.003

    println(sum2(List(1, 2, 3))) // 30_006
    println(sum3(List(1, 2, 3))) // 30_006
    println(sum4(List(1, 2, 3))) // 30_006

    parent() // It's sunny today.
  }
}
