package chap5

class MainTest extends munit.FunSuite {
  test("validate - 全て有効な場合はEventが返る") {
    assertEquals(
      validate("world war 2", 1939, 1945),
      Some(Event("world war 2", 1939, 1945))
    )
  }

  test("validate - 名前が空の場合はNoneが返る") {
    assertEquals(validate("", 1939, 1945), None)
  }

  test("validate - 終了年が2025年より後の場合はNoneが返る") {
    assertEquals(validate("world war 2", 1939, 2026), None)
  }

  test("validate - 開始年が終了年より後の場合はNoneが返る") {
    assertEquals(validate("world war 2", 1945, 1939), None)
  }
}
