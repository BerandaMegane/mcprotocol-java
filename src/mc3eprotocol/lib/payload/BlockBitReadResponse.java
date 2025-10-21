package mc3eprotocol.lib.payload;

/**
 * ビット単位一括読込み応答クラス
 * ビットデバイスの一括読込み要求に対する応答を処理するクラスです。
 * 受信したバイトデータをboolean配列に変換して管理します。
 */
public class BlockBitReadResponse extends AbstractResponse {
    /** 読込まれたビットデータ配列 */
    private boolean[] _readData;

    /**
     * コンストラクタ
     * 応答データを解析してビット読込み応答オブジェクトを作成します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する読込み要求オブジェクト
     */
    public BlockBitReadResponse(byte[] responseData, BlockReadRequest request) {
        parse(responseData, request);
    }

    /**
     * 応答データを解析してメンバ変数にセットします。
     * バイトデータの各ニブル（4ビット）をboolean値に変換します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する要求オブジェクト
     * @throws IllegalArgumentException ワードデバイスの要求が指定された場合
     */
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        if (! ((BlockReadRequest)request)._isBitDevice) {
            throw new IllegalArgumentException("ワードデバイスの読み込み要求に対しては、別のクラスを使ってください");
        }

        _readData = new boolean[responseData.length * 2];
        for (int i=0; i<responseData.length; i++) {
            _readData[i*2 + 0] = (responseData[i] & 0xF0) != 0 ? true : false;
            _readData[i*2 + 1] = (responseData[i] & 0x0F) != 0 ? true : false;
        }
    }

    /**
     * 読込まれたデバイス値を取得します。
     *
     * @return ビットデータのboolean配列
     */
    public boolean[] getReadData() {
        return _readData;
    }

    /**
     * ビットデータをバイト配列に変換します。
     * boolean配列の各ペアを1バイトに圧縮します。
     *
     * @return 変換されたバイト配列
     */
    @Override
    public byte[] toBytes() {
        byte[] retData = new byte[_readData.length / 2];
        for (int i=0; i<retData.length; i++) {
            byte temp0 = (byte)(_readData[i*2 + 0] ? 0x1 : 0x0);
            byte temp1 = (byte)(_readData[i*2 + 1] ? 0x1 : 0x0);
            retData[i] = (byte)(temp0 << 4 | temp1);
        }
        return retData;
    }
}
