package mc3eprotocol.payload;

import mc3eprotocol.Utility;
import mc3eprotocol.define.*;

public abstract class AbstractRequest implements IPayload {
    protected CommandEnum _command;
    protected SubCommandEnum _subCommand;

    /**
     * 応答を解析する
     * 終了コードより後ろを渡すこと
     * @param response
     * @return
     */
    public abstract AbstractResponse parseResponse(byte[] response);
    
    /**
     * 監視タイマーより後ろのバイト列に変換する
     * @return
     */
    public abstract byte[] toBytes();

    public CommandEnum getCommand() {
        return _command;
    }

    public SubCommandEnum getSubCommand() {
        return _subCommand;
    }

    /**
     * バイト列から解析してメンバ変数にセットする
     * 監視タイマーより後ろを渡すこと
     * @param request
     */
    public abstract void parse(byte[] request);

    /**
     * リクエストの情報を表示する
     */
    public void printInfo() {
        System.out.println(Utility.hereDoc(s->s, System.lineSeparator(), 
        //   Request payload: 00019000000100000401
            "Request payload: " + Utility.fromBytesToHexStringBigEndian(toBytes()),
            "                 1   2   3",
            "                 1: コマンド: " + _command.toString(),
            "                 2: サブコマンド: " + _subCommand.toString(),
            "                 3: その他のデータ"
        ));
    }
}
