package mc3eprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.lib.util.Converter;

/**
 * 折り返しテスト応答クラス
 * 折り返しテスト要求に対する応答を処理するクラスです。
 * 送信されたデータがそのまま返されることを確認します。
 */
public class EchoTestResponse extends AbstractResponse {
    /** 折り返されたデータ */
    private byte[] _data;

    /**
     * コンストラクタ
     * 応答データを解析して折り返しテスト応答オブジェクトを作成します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     */
    public EchoTestResponse(byte[] responseData) {
        parse(responseData, null);
    }

    /**
     * 応答データを解析してメンバ変数にセットします。
     * 折り返しデータ数とデータ本体を解析します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する要求オブジェクト（使用されません）
     */
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        // バッファ
        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] data;

        // 折返しデータ数
        data = new byte[2];
        short uncheckedLength = buffer.getShort();

        // 折返しデータ
        data = new byte[uncheckedLength];
        buffer.get(data);
        _data = data;
    }

    /**
     * 折り返されたデータを取得します。
     *
     * @return 折り返しデータ
     */
    public byte[] getReadData() {
        return _data;
    }

    /**
     * 応答データをバイト配列に変換します。
     *
     * @return 応答データのバイト配列
     */
    @Override
    public byte[] toBytes() {
        return Converter.concatByteArrays(
            Converter.fromIntToByteArray(_data.length, 2),
            _data
        );
    }
}
