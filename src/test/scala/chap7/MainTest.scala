package chap7

import model._

val artists = List(
  Artist(
    "Metallica",
    Genre.HeavyMetal,
    Location("USA"),
    Career.RetiredCareer(List(new Period(1981, 2011))) // total 30
  ),
  Artist(
    "Queen",
    Genre.Pop,
    Location("UK"),
    Career.ActiveCareer(List(new Period(1970, 1980)), 2020) // total 15
  )
)

class MainTest extends munit.FunSuite {
  test("Period - 期間の順序が正しい場合") {
    assertEquals(
      Period(1939, 1945),
      Some(new Period(1939, 1945))
    )
  }

  test("Period - 期間の順序が正しくない場合") {
    assertEquals(
      Period(1945, 1939),
      None
    )
  }

  test("periodsInOrder - 複数の期間の順序が正しい場合") {
    assertEquals(
      periodsInOrder(List(new Period(1940, 1945), new Period(1950, 1955))),
      true
    )
  }

  test("periodsInOrder - 複数の期間の順序が正しくない場合") {
    assertEquals(
      periodsInOrder(List(new Period(1940, 1950), new Period(1945, 1955))),
      false
    )
  }

  test("ActiveCareer - 現在の活動期間の開始が、閉じた最後の期間の後である場合") {
    assertEquals(
      ActiveCareer(List(new Period(1940, 1950), new Period(1955, 1960)), 1965),
      Some(
        new Career.ActiveCareer(
          List(new Period(1940, 1950), new Period(1955, 1960)),
          1965
        )
      )
    )
  }

  test("ActiveCareer - 現在の活動期間の開始が、閉じた最後の期間より前である場合") {
    assertEquals(
      ActiveCareer(List(new Period(1940, 1950), new Period(1950, 1960)), 1955),
      None
    )
  }

  test("byGenre - ジャンルが一致するArtistを抽出する") {
    assertEquals(
      byGenre(Genre.HeavyMetal)(artists).map(_.name),
      List("Metallica")
    )
  }

  test("byOrigin - 出身地が一致するArtistを抽出する") {
    assertEquals(
      byOrigin(Location("USA"))(artists).map(_.name),
      List("Metallica")
    )
  }

  test("byCareerTotal - キャリアの総年数が指定された年数以上のArtistを抽出する - 1件該当する場合") {
    assertEquals(
      byCareerTotal(20)(artists).map(_.name),
      List("Metallica")
    )
  }

  test("byCareerTotal - キャリアの総年数が指定された年数以上のArtistを抽出する - 2件該当する場合") {
    assertEquals(
      byCareerTotal(10)(artists).map(_.name),
      List("Metallica", "Queen")
    )
  }

  test("byCareerActive - キャリアがアクティブなArtistを抽出する") {
    assertEquals(
      byCareerActive(true)(artists).map(_.name),
      List("Queen")
    )
  }
}
