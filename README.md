```
$ sbt "runMain chap1.main"
```

ビルドツールとしては以下の ⭕️ を利用している。

- ⭕️ sbt
  - scala のビルドツールとしてはデファクト
- ⭕️ bloop
  - vscode で LSP(language server protocol) に準拠した LS(language server) である metals から、BSP(build server protocol) を通じて bloop というビルドツールを実行している
  - 高速にインクリメンタルビルドする点が特徴
  - 設定としては sbt の設定ファイルである build.sbt を参照する
- ❌️ scala-cli
  - `scala run`コマンドで利用されるビルドツール
  - `scala-build/`を生成する
  - 単一ファイル実行小規模な開発に特化したツールであり、今回の手習いというユースケースには適合する
  - しかしたくさん混在するとややこしいので利用しないこととした
