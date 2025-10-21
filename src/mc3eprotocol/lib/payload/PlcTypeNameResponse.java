package mc3eprotocol.lib.payload;

/**
 * PLC形名読取り応答クラス
 * CPU形名読出し要求に対する応答を処理するクラスです。
 * PLCの機種名情報を文字列として管理します。
 */
public class PlcTypeNameResponse extends AbstractResponse {

    /** PLC形名文字列 */
    private String _PlcTypeName;

    /**
     * コンストラクタ
     * 応答データを解析してPLC形名応答オブジェクトを作成します。
     * 
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する形名読取り要求オブジェクト
     */
    public PlcTypeNameResponse(byte[] responseData, PlcTypeNameRequest request) {
        parse(responseData, request);
    }

    /**
     * 応答データを解析してメンバ変数にセットします。
     * バイトデータを文字列に変換してPLC形名を取得します。
     * 
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する要求オブジェクト
     */
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        _PlcTypeName = new String(responseData);
    }

    /**
     * PLC形名を取得します。
     * 
     * @return PLC形名文字列
     */
    public String getPlcTypeName() {
        return _PlcTypeName;
    }

    /**
     * PLC形名をバイト配列に変換します。
     * 
     * @return PLC形名のバイト配列
     */
    @Override
    public byte[] toBytes() {
        return _PlcTypeName.getBytes();
    }
}
