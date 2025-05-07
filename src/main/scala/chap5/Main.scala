package chap5

case class Event(name: String, start: Int, end: Int)

// def validate(name: String, start: String, end: Int): Option[Event] = {
//   Some(Event(name, start, end))
// }

def validate(name: String, start: Int, end: Int): Option[Event] = {
  for {
    nonEmptyName <- isNamed(name)
    pastEnd <- isPast(end)
    (orderedStart, orderedEnd) <- startsBeforeEnd(start, end)
  } yield Event(nonEmptyName, orderedStart, orderedEnd)
}

def isNamed(name: String): Option[String] = {
  if (name.isEmpty) None
  else Some(name)
}

def isPast(end: Int): Option[Int] = {
  if (end > 2025) None
  else Some(end)
}

def startsBeforeEnd(start: Int, end: Int): Option[(Int, Int)] = {
  if (start > end) None
  else Some((start, end))
}

@main
def main(): Unit = {
  println(
    for {
      alphabet <- List("a", "b")
      foo = "awesome" // 変数の宣言も可能
      // listの1つ1つの要素に対して右辺が評価される、つまりSet(1,2,3)がつくられる
      // そのうえでflatMapが適用されて、Setというコンテナが解体されるため、(a,1),(a,2),(b,1),(b,2)がflatに並ぶ
      number <- Set(1, 2)
    } yield s"$alphabet$number$foo" // yieldされた値が、1行目の右辺のコンテナに入る
  )

  println(
    for {
      number <- Set(1, 2)
      alphabet <- List("a", "b")
    } yield s"$alphabet$number" // yieldされた値が、1行目の右辺のコンテナに入る
  )

  println(validate("応仁の乱", 1467, 1477))
}
