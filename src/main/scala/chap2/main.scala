package chap2

@main
def main(): Unit =
  println("this is chap2/main.scala")
  println(getDiscountRate(List("book", "pen")))
  println(getDiscountRate(List("pen", "pen")))
  println(getDiscountRate(List.empty))

def getDiscountRate(items: List[String]): Int = {
  if (items.contains("book")) 5
  else 0
}
