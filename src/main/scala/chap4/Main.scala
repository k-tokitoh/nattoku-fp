package chap4

def score(word: String): Int = {
  word.length()
}

def sortedWords(words: List[String]): List[String] = {
  words.sortBy(score)
}

// カリー化された関数を定義しやすい、こんな書き方ができる！
// https://docs.scala-lang.org/ja/tour/multiple-parameter-lists.html
def wordsLargerThan(length: Int)(words: List[String]): List[String] = {
  // 実装の内部では、どの段階で渡される引数なのかを意識しなくていい
  words.filter(word => word.length() > length)
}

def totalScore1(words: List[String]): Int = {
  words.map(score).sum
}

def totalScore2(words: List[String]): Int = {
  words.foldLeft(0)((acc, word) => acc + score(word))
}

@main
def main(): Unit = {
  val words = List("scala", "is", "not", "java")
  val sorted = words.sortBy(score)
  val reversed = sorted.reverse
  val mapped = words.map(score)
  val sw = sortedWords(words)
  println(words)
  println(sorted)
  println(reversed)
  println(mapped)
  println(sw)

  val wordsLargerThan3 = wordsLargerThan(3)(words)
  println(wordsLargerThan3)

  val wordsLargerThan4 = wordsLargerThan(4)(words)
  println(wordsLargerThan4)

  val wordsLargerThan5 = wordsLargerThan(5)(words)
  println(wordsLargerThan5)

  println(totalScore1(words))
  println(totalScore2(words))
}
