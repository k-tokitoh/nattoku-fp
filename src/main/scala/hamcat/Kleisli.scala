package hamcat

// 型クラス
// monoidの性質のうち、二項演算のみ（結合律はインターフェースでは保証されない）
trait SemiGroup[A]:
  def combine(a1: A, a2: A): A

// 型クラスのインスタンス
// monoidの性質を全て備える（単位元を追加。ただし単位元であることはインターフェースでは保証されない）
trait Monoid2[A] extends SemiGroup[A]:
  def empty: A

extension [A: SemiGroup as sg](left: A)
  def |+|(right: A): A =
    sg.combine(left, right)

// LはlogのL
// Lに基づいてSemigroupが決定される。ここで明示的には利用しないため、変数名はあてずに匿名的なcontextとして存在させる
class Writer[L: SemiGroup, A](val run: (L, A)):
  def flatMap[B](f: A => Writer[L, B]): Writer[L, B] =
    val (log, a) = run // 自身のログと値を取り出す
    val (log2, b) = f(a).run // 自身の値aに対して、受け取った関数fを適用して結果bを得る

    // 二項演算で結合したログと、関数fの結果bから構成されるWriterを返す
    Writer((log |+| log2, b))
    // ログはmonoidなので結合律をみたす、値はふつうの関数適用なので結合律をみたす
    // 結果としてWriter#flatMapという射の合成は、結合律をみたす

  def map[B](f: A => B): Writer[L, B] =
    Writer(run._1, f(run._2)) // 値に関数を適用するだけ

// 型クラスのインスタンス
given Monoid2[String] with
  def combine(a1: String, a2: String): String = a1 + a2
  def empty: String = ""

object Writer:
  // この戻り値が恒等射
  def pure[L: Monoid2 as m, A](a: A): Writer[L, A] = Writer((m.empty, a))

object Kleisli:
  def main(args: Array[String]): Unit =
    val w = Writer(("hello", 1)).flatMap(a1 =>
      Writer((" world", a1 + 100)).flatMap(a2 => Writer(("!", a2 * 2)))
    )
    println(w.run) // (hello world!,202)

    // forで書くと以下
    val w2 = for
      a1 <- Writer(("hello", 1))
      a2 <- Writer((" world", a1 + 100))
      a3 <- Writer(("!", a2 * 2))
    yield a3
    println(w2.run) // (hello world!,202)
