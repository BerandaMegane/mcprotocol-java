# mcprotocol-java

## はじめに

Java で PLC と MC プロトコルで通信するクライアントプログラムを作りました。  
対応する規格は MC プロトコル (3E フレーム) の一部のコマンドです。

通信ライブラリとして作成することを意識したので、将来的には PLC の応答をエミュレートするソフトウェアにも発展させることができるかもしれません。

## 動作環境

* PLC
  * 三菱電機 Q03UDV CPU
* パソコン
  * Windows 11 Pro
  * Oracle OpenJDK 21

## 手順

`src` フォルダの中の [ClientSample.java](src/ClientSample.java) がサンプルプログラムです。  
動作確認の際は、プログラム中の IP アドレスおよびポート番号を、実際の機器のものに変更してから、サンプルプログラムを実行します。

### PLC の IP アドレス設定

Q03UDV の場合は、次の記事を参考に、PLC に IP アドレスなどを設定します。他の機種を利用している場合は、別途情報収集をお願いします。

Qiita - [@satosisotas Works2でSLMP通信を試すための内蔵Ethernet設定](https://qiita.com/satosisotas/items/38f64c872d161b612071)

やっておかなければならない設定は次の通りです。
* IP アドレス
* ポート番号 (TCP)
* RUN中書込みを許可する
* CPU のリセットをかけておく

### パソコンへの Java インストール

プログラムは Java で作られているため、Java を実行するソフト（JRE）のインストールが必要です。  
例えば、次のサイトから Java をダウンロード・インストールしてください。

https://www.oracle.com/jp/java/technologies/downloads/#jdk21-windows

### 動作確認手順

動作確認の環境に git がインストールされていれば、次のようにプログラムをダウンロードします。  
git がなくても、このリポジトリを ZIP ファイルでダウンロードして、好きな場所で解凍（展開）します。  
また、JAR ファイルも提供しています。

```powershell
# GitHub からダウンロード
> git clone https://github.com/BerandaMegane/mcprotocol-java.git
# ディレクトリ移動
> cd mcprotocol-java
```

[src/ClientSample.java](src/ClientSample.java) を開き、IP アドレスやポート番号を書き換えます。

```powershell
# コンパイル
> javac -d bin -sourcepath src src/ClientSample.java
# 実行
> java -cp bin src/ClientSample.java

D200 に 512 を書き込んで読み込むテスト
D200: 512
```
