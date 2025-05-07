package chap1

def increment(x: Int): Int = x + 1

def getFirstCharacter(str: String): Char = {
  str.charAt(0)
}

def wordScore(words: String): Int = {
  words.length()
}

@main
def main(): Unit =
  println("this is chap1/main.scala")
  println(increment(100))
  println(getFirstCharacter("Hello"))
  println(wordScore("Hello"))

// scala標準のprintlnは副作用を伴うが、IO型ではない
// 関数型のライブラリ（ex. cats effect）でIO.printlnというIO型を返す関数を提供している
