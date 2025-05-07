package chap6

// やること
// 文字列をparseする（小さな処理を組み合わせて構成する）
// orElseでのfallback
// - 失敗した情報を伝えない(option) <- このファイルではこっちをやる
//   - 失敗した要素だけ除く
//   - 全部返さない
// - 失敗した情報を伝える(either)
//   - 成功した情報も失敗した情報も伝える
//   - 失敗した全ての情報を伝える
//   - 失敗した最初の情報だけ伝える（後続の処理を中断する）

// `title(start-end)`という形式

case class TvShow(title: String, start: Int, end: Int)

def parseShowsOnlySucceeded(rawShows: List[String]): List[TvShow] = {
// mapで各要素がOptionになり、flattenでOptionというコンテナが外されて、Someの値のみが残る
  rawShows.flatMap(parseShow)
}

def parseShowsAllOrNothing(rawShows: List[String]): Option[List[TvShow]] = {
// Optionの値に畳み込む。1つでも失敗したらNoneを返し、全部成功したらList[TvShow]を返す
  val initial = Some(List.empty[TvShow])
  rawShows.foldLeft(initial)(
    (showsOption: Option[List[TvShow]], rawShow: String) =>
      for {
        shows <- showsOption // 累積のshowOptionも存在して
        show <- parseShow(rawShow) // かつ新しいshowもparseに成功したら
      } yield shows :+ show // 累積のshowOptionに新しいshowを追加したものを返す
  )
}

def parseShow(str: String): Option[TvShow] = {
  for {
    title <- extractTitle(str)
    start <- extractStart(str).orElse(Some(999))
    end <- extractEnd(str)
  } yield TvShow(title, start, end)
}

def extractTitle(str: String): Option[String] = {
  val bracketOpen = str.indexOf("(")
  if (bracketOpen == -1) return None
  val rawTitle = str.substring(0, bracketOpen).trim()
  if (rawTitle.isEmpty) return None
  Some(rawTitle)
}

def extractStart(str: String): Option[Int] = {
  val bracketOpen = str.indexOf("(")
  if (bracketOpen == -1) return None
  val dash = str.indexOf("-")
  if (dash == -1 || dash <= bracketOpen) return None

  val rawStart = str.substring(bracketOpen + 1, dash).trim()
  if (rawStart.isEmpty()) return None
  rawStart.toIntOption
}

def extractEnd(str: String): Option[Int] = {
  val dash = str.indexOf("-")
  if (dash == -1) return None
  val bracketClose = str.indexOf(")")
  if (bracketClose == -1 || bracketClose <= dash) return None

  val rawEnd = str.substring(dash + 1, bracketClose).trim()
  if (rawEnd.isEmpty()) return None
  rawEnd.toIntOption
}

@main
def option(): Unit = {
  // val shows = List("The Wire (2002-2008)", "The Wire (2002-)")
  // val showsOnlySucceeded = parseShowsOnlySucceeded(shows)
  // println(showsOnlySucceeded)
}
