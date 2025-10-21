package mc3eprotocol.lib.payload;

/**
 * 抽象応答クラス
 * MC3Eプロトコルの応答ペイロードの基底クラスです。
 * 全ての応答クラスはこのクラスを継承して実装します。
 */
public abstract class AbstractResponse {

    /**
     * 応答データを解析してメンバ変数にセットします。
     * 終了コードより後ろのデータを渡して応答オブジェクトを構築します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する要求オブジェクト
     */
    protected abstract void parse(byte[] responseData, AbstractRequest request);

    /**
     * 終了コードより後ろのバイト列に変換します。
     *
     * @return 応答データのバイト配列
     */
    public abstract byte[] toBytes();
}
