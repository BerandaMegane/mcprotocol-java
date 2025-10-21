# mcprotocol-java

## はじめに

Java で PLC と MC プロトコルで通信するクライアントプログラムを作りました。  
対応する規格は MC プロトコル (3E フレーム) の一部のコマンドです。

## 動作環境

* PLC
  * 三菱電機 Q03UDV CPU
* パソコン
  * Windows 11 Pro
  * Oracle OpenJDK 25

### パソコンへの Java インストール

プログラムは Java で作られているため、Java を実行するソフト（JDK）のインストールが必要です。  
例えば、次のサイトから Java をダウンロード・インストールしてください。

https://www.oracle.com/jp/java/technologies/downloads/#jdk25-windows

## 動作確認手順

[src/mc3eprotocol/sample] ディレクトリにサンプルプログラムが格納されています。  
動作確認の際は、プログラム中の IP アドレスおよびポート番号を実際の機器のものに変更してから、プログラムを実行してください。

### PLC の IP アドレス設定

Q03UDV の場合は、次の記事を参考に、PLC に IP アドレスなどを設定します。他の機種を利用している場合は、別途情報収集をお願いします。

Qiita - [@satosisotas Works2でSLMP通信を試すための内蔵Ethernet設定](https://qiita.com/satosisotas/items/38f64c872d161b612071)

やっておかなければならない設定は次の通りです。
* IP アドレス
* ポート番号 (TCP)
* RUN中書込みを許可する
* CPU のリセットをかけておく


### 動作確認手順

動作確認の環境に git がインストールされていれば、次のようにプログラムをダウンロードします。  
git がなくても、このリポジトリを ZIP ファイルでダウンロードして、好きな場所で解凍（展開）します。  
また、JAR ファイルも提供しています。

```powershell
# GitHub からダウンロード
> git clone https://github.com/work-motonaga/mcprotocol-java.git
# ディレクトリ移動
> cd mcprotocol-java
```

ここでは、[ClientSample.java](src/mc3eprotocol/sample/ClientSample.java) (接続・切断のみ) を実行しています。

```powershell
# コンパイル
> javac -d bin -sourcepath src src/mc3eprotocol/sample/ClientSample.java
# 実行
> java -cp bin mc3eprotocol/sample/ClientSample
```
