package mc3eprotocol.lib.payload;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * 異常応答クラス
 * PLCからの異常応答（エラー）を処理するクラスです。
 * エラー情報の解析と表示機能を提供します。
 */
public class AbnormalResponse extends AbstractResponse {

    /** 異常応答のペイロードデータ */
    public byte[] _payload;

    /**
     * コンストラクタ
     * 異常応答データを解析して異常応答オブジェクトを作成します。
     * 
     * @param response 異常応答データ
     */
    public AbnormalResponse(byte[] response) {
        parse(response, null);
    }
    
    /**
     * 異常応答データを解析してメンバ変数にセットします。
     * 
     * @param responseData 応答データ
     * @param request 対応する要求オブジェクト（使用されません）
     */
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        _payload = responseData;
    }

    /**
     * 異常応答をバイト配列に変換します。
     * 
     * @return 空のバイト配列
     */
    @Override
    public byte[] toBytes() {
        return new byte[]{};
    }

    /**
     * エラー情報をコンソールに表示します。
     * コマンドとサブコマンドを解析してエラーの詳細を出力します。
     */
    public void printErrorInfo() {
        CommandEnum command = CommandEnum.build(new byte[]{_payload[5], _payload[6]});
        SubCommandEnum subCommand = SubCommandEnum.build(new byte[]{_payload[7], _payload[8]});

        System.out.println(Converter.hereDoc(s->s, System.lineSeparator(), 
            "Error: " + Converter.fromBytesToHexStringBigEndian(_payload),
            "       1         2   3",
            "       1: アクセス経路",
            "       2: コマンド: " + command + "(" + command.getBytes().getHexString() + ")",
            "       3: サブコマンド: " + subCommand + "(" + subCommand.getBytes().getHexString() + ")"
        ));
    }
}
