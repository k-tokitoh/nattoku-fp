package chap2

class MainTest extends munit.FunSuite {
  test("getDiscountRate - 本が含まれる場合は割引率5%") {
    assertEquals(getDiscountRate(List("book", "pen")), 5)
  }

  test("getDiscountRate - 本が含まれない場合は割引率0%") {
    assertEquals(getDiscountRate(List("pen")), 0)
  }

  test("getDiscountRate - 空のリストの場合は割引率0%") {
    assertEquals(getDiscountRate(List.empty), 0)
  }
}
