package mc3eprotocol.lib.payload;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * 抽象要求クラス
 * MC3Eプロトコルの要求ペイロードの基底クラスです。
 * 全ての要求クラスはこのクラスを継承して実装します。
 */
public abstract class AbstractRequest implements IPayload {
    /** コマンド種別 */
    protected CommandEnum _command;
    /** サブコマンド種別 */
    protected SubCommandEnum _subCommand;

    /**
     * 応答を解析します。
     * 終了コードより後ろのデータを渡して応答オブジェクトを生成します。
     *
     * @param response 応答データ（終了コードより後ろ）
     * @return 解析された応答オブジェクト
     */
    public abstract AbstractResponse parseResponse(byte[] response);
    
    /**
     * 監視タイマーより後ろのバイト列に変換します。
     *
     * @return 要求データのバイト配列
     */
    public abstract byte[] toByteArray();

    /**
     * コマンド種別を取得します。
     *
     * @return コマンド種別
     */
    public CommandEnum getCommand() {
        return _command;
    }

    /**
     * サブコマンド種別を取得します。
     *
     * @return サブコマンド種別
     */
    public SubCommandEnum getSubCommand() {
        return _subCommand;
    }

    /**
     * バイト列から解析してメンバ変数にセットします。
     * 監視タイマーより後ろのデータを渡して要求オブジェクトを復元します。
     *
     * @param request 要求データ（監視タイマーより後ろ）
     */
    public abstract void parse(byte[] request);

    /**
     * リクエストの情報をコンソールに表示します。
     * デバッグ用途で要求の詳細情報を確認するために使用します。
     */
    public void printInfo() {
        System.out.println(Converter.hereDoc(s->s, System.lineSeparator(), 
        //   Request payload: 00019000000100000401
            "Request payload: " + Converter.fromBytesToHexStringBigEndian(toByteArray()),
            "                 1   2   3",
            "                 1: コマンド: " + _command.toString(),
            "                 2: サブコマンド: " + _subCommand.toString(),
            "                 3: その他のデータ"
        ));
    }
}
