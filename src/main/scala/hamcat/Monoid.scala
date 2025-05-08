package hamcat

// 型クラス
// 関数を束ねたもの
trait Monoid[A]:
  def combine(v1: A, v2: A): A
  def empty: A

// `A + A`` でAに対応するmonoidの二項演算(combine)が実行されるようにする
extension [A: Monoid](v1: A)
  def |+|(v2: A): A =
    summon[Monoid[A]].combine(v1, v2)

// 型クラスのインスタンス Int
given Monoid[Int] with
  def combine(left: Int, right: Int): Int = left + right

  def empty: Int = 0

// 型クラスのインスタンス Option[A]
given [A: Monoid]: Monoid[Option[A]] with
  def combine(left: Option[A], right: Option[A]): Option[A] =
    (left, right) match
      case (Some(a), Some(b)) => Some(summon[Monoid[A]].combine(a, b))
      case (Some(a), None)    => Some(a)
      case (None, Some(b))    => Some(b)
      case (None, None)       => None

  def empty: Option[A] = None

object Monoid:
  def main(args: Array[String]): Unit =
    println(1 |+| 2) // 3

    val left1: Option[Int] = Some(1)
    val right1: Option[Int] = Some(2)
    println(left1 |+| right1) // Some(3)

    val left2: Option[Int] = Some(1)
    val right2: Option[Int] = None
    println(left2 |+| right2) // Some(1)

    val left3: Option[Int] = None
    val right3: Option[Int] = Some(2)
    println(left3 |+| right3) // Some(2)

    val left4: Option[Int] = None
    val right4: Option[Int] = None
    println(left4 |+| right4) // None
