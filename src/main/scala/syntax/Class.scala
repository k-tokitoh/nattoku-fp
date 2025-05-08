package syntax

class Person1(name: String)

class Person2(val name: String)

class Person3 private (val name: String)

// コンパニオンオブジェクトを利用したスマートコンストラクタパターン
object Person3 {
  def apply(number: Int): Person3 = new Person3(s"number: $number")
}

// case classだとコンストラクタ引数が自動的にpublic valになる
case class Person4(name: String)

object Class {
  def main(args: Array[String]): Unit = {
    val val1 = 1
    // val1 = 2  // valはimmutableなので再代入できない
    var var1 = 1
    var1 = 2 // varはmutableなので再代入できる

    val person1 = Person1("John1")
    // println(person1.name)  // val, varがつかないコンストラクタ引数はclassの外から参照できない
    // println(person1.age)   // val, varがつかないコンストラクタ引数はclassの外から参照できない

    val person2 = Person2("John2")
    println(person2.name) // val, varがついているコンストラクタ引数はclassの外から参照できる

    val person3 = Person3(3)
    println(person3.name)

    val person4 = Person4("John4")
    println(person4.name)
  }
}
