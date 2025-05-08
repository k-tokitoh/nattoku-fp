package syntax

// case classはclassに加えて以下を自動的に提供してくれる
// - toString, equals, hashCodeメソッド
// - パターンマッチングのサポート
// - イミュータビリティ
// - copyメソッド
// - apply/unapplyメソッド（newなしでインスタンス化できる）

// 上記をscala2で書く。パラメータを受け取る()を複数並べる
def curry2(num: Int)(str: String): Boolean =
  (num + str.length()) > 5

// scala3で可能になったカリー化の記法
// シグネチャには型のみを記述して、引数は実装内で宣言する（inferしてくれる）
def curry3: Int => String => Boolean =
  num => str => (num + str.length()) > 5

// 複数パラメータ記法の、ジェネリクスありパターン
def curry2Generic[A](list: List[A])(str: String): (Boolean, A) =
  (str.length() > 5, list.head)

// 型のみ先行して記述する記法の、ジェネリスクありパターン
def curry3Generic[A]: List[A] => String => (Boolean, A) =
  list => str => (str.length() > 5, list.head)

// ================ 型引数もカリー化する

// インラインの関数定義
val pair1 = [A] => (a: A) => [B] => (b: B) => (a, b)

// 上記を、カリー化の構造は変えずにトラディショナルな書き方にすると...
def pair2[A](a: A)[B](b: B): (A, B) = (a, b)

// objectはclassではなくて、シングルトン
// scala3だと{}ではなく:とインデントでブロックを記述できる
object Curry:
  def main(args: Array[String]): Unit = {
    println(curry2(3)("hey")) // true
    println(curry3(3)("hey")) // true
    println(curry2Generic(List(10, 20))("hello")) // (false,10)
    println(curry3Generic(List(10, 20))("hello")) // (false,10)

    // 型引数はinferしてもらうこともできるし、明示的に指定することもできる
    println(pair1("a")(true)) // (a,true)
    println(pair1[String]("a")[Boolean](true)) // (a,true)

    println(pair2("a")(true)) // (a,true)
    println(pair2[String]("a")[Boolean](true)) // (a,true)
  }
