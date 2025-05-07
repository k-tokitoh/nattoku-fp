package chap6

class OptionTest extends munit.FunSuite {
  test("parseShow - 全てOKな場合TvShowが返る") {
    assertEquals(
      parseShow("The Wire (2002-2008)"),
      Some(TvShow("The Wire", 2002, 2008))
    )
  }

  test("parseShow - タイトルがない場合はNoneが返る") {
    assertEquals(parseShow("(2002-2008)"), None)
  }

  test("parseShow - 開始年がない場合はデフォルト値になる") {
    assertEquals(
      parseShow("The Wire (- 2008)"),
      Some(TvShow("The Wire", 999, 2008))
    )
  }

  test("parseShow - 終了年がない場合はNoneが返る") {
    assertEquals(parseShow("The Wire (2002-)"), None)
  }

  test("parseShow - 終了年が数値変換できない場合はNoneが返る") {
    assertEquals(parseShow("The Wire (2002-200a8)"), None)
  }

  test("parseShowsOnlySucceeded - 全て成功した場合はTvShowのリストが返る") {
    assertEquals(
      parseShowsOnlySucceeded(
        List("The Wire (2002-x)", "Mad Men (2007-2015)")
      ),
      List(TvShow("Mad Men", 2007, 2015))
    )
  }

  test("parseShowsAllOrNothing - 1つでも失敗した場合はNoneが返る") {
    assertEquals(
      parseShowsAllOrNothing(
        List("The Wire (2002-x)", "Mad Men (2007-2015)")
      ),
      None
    )
  }
}
