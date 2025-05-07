package chap7

enum Genre {
  case HeavyMetal, Pop, HardRock
}

object model {
  opaque type Location = String
  object Location {
    def apply(s: String): Location = s
  }
}

import model._

case class Artist(
    name: String,
    genre: Genre,
    origin: Location,
    career: Career
)

case class Period(start: Int, end: Int)

// スマートコンストラクタ
object Period {
  def apply(start: Int, end: Int): Option[Period] = {
    // 開始 < 終了 が成り立つか
    if (start < end) Some(new Period(start, end)) else None
  }
}

enum Career {
  case ActiveCareer(past: List[Period], currentSince: Int) // TODO: 時系列の整合性を担保する
  case RetiredCareer(periods: List[Period]) // TODO: 時系列の整合性を担保する
}

// スマートコンストラクタ
def ActiveCareer(
    past: List[Period],
    currentSince: Int
): Option[Career.ActiveCareer] = {
  if (periodsInOrder(past) && past.last.end < currentSince)
    Some(Career.ActiveCareer(past, currentSince))
  else None
}

def RetiredCareer(periods: List[Period]): Option[Career.RetiredCareer] = {
  if (periodsInOrder(periods)) Some(Career.RetiredCareer(periods)) else None
}

def periodsInOrder(periods: List[Period]): Boolean = {
  // 前の期間の終了 < 次の期間の開始 が成り立つか
  periods
    .sliding(2)
    .toList
    .forall(sliding => sliding.head.end < sliding.last.start)
}

// Conditionを、元のArtistのリストと判定に必要な情報を受け取って、filterしたArtistのリストを返す関数のインターフェースとする
// それぞれの条件を、そのインターフェースを備えた関数とする

// 条件を満たすArtistを返す関数のインターフェース
trait Condition {
  def apply(artists: List[Artist]): List[Artist]
}

def byGenre(genre: Genre): Condition = { (artists: List[Artist]) =>
  artists.filter(artist => artist.genre == genre)
}

def byOrigin(origin: Location): Condition = { (artists: List[Artist]) =>
  artists.filter(artist => artist.origin == origin)
}

val currentYear = 2025

def periodYears(period: Period): Int = {
  period.end - period.start
}

def careerYears(career: Career): Int = {
  career match {
    case Career.ActiveCareer(past, currentSince) =>
      past.foldLeft(0)((acc, period) => acc + periodYears(period)) +
        (currentYear - currentSince)
    case Career.RetiredCareer(periods) =>
      periods.foldLeft(0)((acc, period) => acc + periodYears(period))
  }
}

def byCareerTotal(years: Int): Condition = { (artists: List[Artist]) =>
  artists.filter(artist => careerYears(artist.career) >= years)
}

def byCareerActive(isActive: Boolean): Condition = { (artists: List[Artist]) =>
  artists.filter(artist => {
    artist.career match {
      case Career.ActiveCareer(_, _) => isActive
      case Career.RetiredCareer(_)   => !isActive
    }
  })
}

def searchArtists(
    artists: List[Artist],
    conditions: List[Condition]
): List[Artist] = {
  conditions.foldLeft(artists)((artists, condition) => condition(artists))
}

@main
def main(): Unit = {
  println(List(1, 2, 3, 4, 5).sliding(2).toSet)
}
