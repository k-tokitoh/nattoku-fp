package chap6

// やること
// 文字列をparseする（小さな処理を組み合わせて構成する）
// orElseでのfallback
// - 失敗した情報を伝えない(option)
//   - 失敗した要素だけ除く
//   - 全部返さない
// - 失敗した情報を伝える(either) <- このファイルではこっちをやる
//   - 成功した情報も失敗した情報も伝える
//   - 1つ以上失敗した場合は、失敗した最初の情報だけ伝える（後続の処理を中断する）
//   - 1つ以上失敗した場合は、失敗した全ての情報を伝える

// `title(start-end)`という形式

def parseShowsAll(
    rawShows: List[String]
): List[Either[String, TvShow]] = {
  rawShows.map(parseShowEither)
}

def parseShowsAllShowsOrFirstLeftString(
    rawShows: List[String]
): Either[String, List[TvShow]] = {
  // 1つのEitherに畳み込む
  val initial: Either[String, List[TvShow]] = Right(List.empty[TvShow])
  rawShows.foldLeft(initial)((acc, rawShow) =>
    for {
      shows <- acc // accがRightであり
      show <- parseShowEither(rawShow) // iterateした値もRightだったら
    } yield shows :+ show // 累積のshowsにshowを追加したものを返す
  )
}

def parseShowsAllShowsOrAllLeftStrings(
    rawShows: List[String]
): Either[List[String], List[TvShow]] = {
  // いったんEitherのリストにmappingする
  val eithers = rawShows.map(parseShowEither)

  // 失敗した場合があれば、失敗した情報を全て返す
  val leftStrings = eithers.filter(_.isLeft).map(_.left.get)
  if (leftStrings.length > 0) return Left(leftStrings)

  // 成功した場合は、成功した情報を全て返す
  val shows = eithers.filter(_.isRight).map(_.right.get)
  Right(shows)
}

def parseShowEither(str: String): Either[String, TvShow] = {
  for {
    title <- extractTitleEither(str)
    start <- extractStartEither(str).orElse(Right(999))
    end <- extractEndEither(str)
  } yield TvShow(title, start, end)
}

def extractTitleEither(str: String): Either[String, String] = {
  val bracketOpen = str.indexOf("(")
  if (bracketOpen == -1) return Left("No opening bracket")
  val rawTitle = str.substring(0, bracketOpen).trim()
  if (rawTitle.isEmpty) return Left("Title is empty")
  Right(rawTitle)
}

def extractStartEither(str: String): Either[String, Int] = {
  val bracketOpen = str.indexOf("(")
  if (bracketOpen == -1) return Left("No opening bracket")
  val dash = str.indexOf("-")
  if (dash == -1 || dash <= bracketOpen) return Left("No dash")

  val rawStart = str.substring(bracketOpen + 1, dash).trim()
  if (rawStart.isEmpty()) return Left("Start is empty")
  rawStart.toIntOption.toRight("Not a number")
}

def extractEndEither(str: String): Either[String, Int] = {
  val dash = str.indexOf("-")
  if (dash == -1) return Left("No dash")
  val bracketClose = str.indexOf(")")
  if (bracketClose == -1 || bracketClose <= dash)
    return Left("No closing bracket")

  val rawEnd = str.substring(dash + 1, bracketClose).trim()
  if (rawEnd.isEmpty()) return Left("End is empty")
  rawEnd.toIntOption.toRight("Not a number")
}

@main
def either(): Unit = {}
