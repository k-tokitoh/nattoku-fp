[![](https://images-na.ssl-images-amazon.com/images/P/4798179809.09.MZZZZZZZ)](https://www.amazon.co.jp/%E3%81%AA%E3%81%A3%E3%81%A8%E3%81%8F%EF%BC%81%E9%96%A2%E6%95%B0%E5%9E%8B%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%9F%E3%83%B3%E3%82%B0-Micha%C5%82-P%C5%82achta/dp/4798179809)

# 準備

(mac の場合の一例)

```
// 無償のjava実装であるopenjdk
$ brew install openjdk
// macではデフォルトで`/usr/bin/java`のpathが登録されてたりするので、上記のformulaは自動でpathの登録をしない（keg-only）
// なので必要に応じて手動でpathをとおす

// scalaのツールチェーン
$ brew install coursier
```

# 実行

```
$ sbt "runMain chap1.main"
```

## ビルドツール

以下の ⭕️ を利用している。

- ⭕️ sbt
  - scala のビルドツールとしてはデファクト
- ⭕️ bloop
  - vscode で LSP(language server protocol) に準拠した LS(language server) である metals から、BSP(build server protocol) を通じて bloop というビルドツールを実行している
  - 高速にインクリメンタルビルドする点が特徴
  - 設定としては sbt の設定ファイルである build.sbt を参照する
- ❌️ scala-cli
  - `scala run`コマンドで利用されるビルドツール
  - `scala-build/`を生成する
  - 単一ファイル実行や小規模な開発に特化したツールであり、今回の手習いというユースケースには適合する
  - しかしたくさん混在するとややこしいので利用しないこととした

# テスト

## 全体

```
$ sbt test
```

## 一部

```
$ sbt "testOnly chap2.MainTest"
```
