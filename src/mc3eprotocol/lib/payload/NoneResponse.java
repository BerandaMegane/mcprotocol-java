package mc3eprotocol.lib.payload;

/**
 * 空応答クラス
 * データを含まない応答（正常終了応答等）を処理するクラスです。
 * 書込み要求やリモート制御要求などの応答で使用されます。
 */
public class NoneResponse extends AbstractResponse {

    /**
     * コンストラクタ
     * 空の応答データを処理して応答オブジェクトを作成します。
     * 
     * @param response 応答データ（空であることが期待される）
     */
    public NoneResponse(byte[] response) {
        parse(response, null);
    }
    
    /**
     * 応答データを解析してメンバ変数にセットします。
     * データが存在しないことを確認します。
     * 
     * @param responseData 応答データ（空であることが期待される）
     * @param request 対応する要求オブジェクト（使用されません）
     * @throws IllegalArgumentException 応答データが存在する場合
     */
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        if (responseData.length != 0) {
            throw new IllegalArgumentException("応答データが存在します");
        }
    }

    /**
     * 応答データをバイト配列に変換します。
     * 
     * @return 空のバイト配列
     */
    @Override
    public byte[] toBytes() {
        return new byte[]{};
    }
}
