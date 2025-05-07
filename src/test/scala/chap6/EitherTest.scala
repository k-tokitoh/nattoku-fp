package chap6

class EitherTest extends munit.FunSuite {
  test("parseShowEither - 全てOKな場合TvShowが返る") {
    assertEquals(
      parseShowEither("The Wire (2002-2008)"),
      Right(TvShow("The Wire", 2002, 2008))
    )
  }

  test("parseShowEither - タイトルがない場合はNoneが返る") {
    assertEquals(parseShowEither("(2002-2008)"), Left("Title is empty"))
  }

  test("parseShowEither - 開始年がない場合はデフォルト値になる") {
    assertEquals(
      parseShowEither("The Wire (- 2008)"),
      Right(TvShow("The Wire", 999, 2008))
    )
  }

  test("parseShowEither - 終了年がない場合はNoneが返る") {
    assertEquals(
      parseShowEither("The Wire (2002-)"),
      Left("End is empty")
    )
  }

  test("parseShowEither - 終了年が数値変換できない場合はNoneが返る") {
    assertEquals(parseShowEither("The Wire (2002-200a8)"), Left("Not a number"))
  }

  val list = List(
    "The Wire (2002-2008)",
    "The Wire (2002-)",
    "The Wire (2002-200a8)"
  )

  test("parseShowsAll - 失敗も成功も全部返る") {
    assertEquals(
      parseShowsAll(list),
      List(
        Right(TvShow("The Wire", 2002, 2008)),
        Left("End is empty"),
        Left("Not a number")
      )
    )
  }

  test("parseShowsAllShowsOrFirstLeftString - 失敗した場合は最初の失敗した情報だけが返る") {
    assertEquals(
      parseShowsAllShowsOrFirstLeftString(list),
      Left("End is empty")
    )
  }

  test("parseShowsAllShowsOrAllLeftStrings - 失敗した場合は全ての失敗した情報が返る") {
    assertEquals(
      parseShowsAllShowsOrAllLeftStrings(list),
      Left(List("End is empty", "Not a number"))
    )
  }
}
