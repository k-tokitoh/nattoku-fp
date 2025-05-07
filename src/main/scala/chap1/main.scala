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
