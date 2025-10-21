package mc3eprotocol.lib.payload;

import mc3eprotocol.lib.util.Converter;

/**
 * ワード単位一括読込み応答クラス
 * ワードデバイスの一括読込み要求に対する応答を処理するクラスです。
 * 受信したバイトデータをshort配列に変換して管理します。
 */
public class BlockWordReadResponse extends AbstractResponse {
    /** 読込まれたワードデータ配列 */
    private short[] _readData;

    /**
     * コンストラクタ
     * 応答データを解析してワード読込み応答オブジェクトを作成します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する読込み要求オブジェクト
     */
    public BlockWordReadResponse(byte[] responseData, BlockReadRequest request) {
        parse(responseData, request);
    }

    /**
     * 応答データを解析してメンバ変数にセットします。
     * バイトデータをshort配列に変換します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する要求オブジェクト
     * @throws IllegalArgumentException ビットデバイスの要求が指定された場合
     */
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        if (((BlockReadRequest)request)._isBitDevice) {
            throw new IllegalArgumentException("ビットデバイスの読み込み要求に対しては、別のクラスを使ってください");
        }
        _readData = Converter.fromBytesToShortArray(responseData);
    }

    /**
     * 読込まれたデバイス値を取得します。
     *
     * @return ワードデータのshort配列
     */
    public short[] getReadData() {
        return _readData;
    }

    /**
     * ワードデータをバイト配列に変換します。
     *
     * @return 変換されたバイト配列
     */
    @Override
    public byte[] toBytes() {
        return Converter.fromShortArrayToBytes(_readData);
    }
}
