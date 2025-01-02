package mc3eprotocol.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.*;

public class EchoTestResponse extends AbstractResponse {
    private byte[] _data;

    /**
     * 応答データを解析する
     * 終了コードより後ろを渡すこと
     * @param responseData
     * @return
     */
    public EchoTestResponse(byte[] responseData) {
        parse(responseData, null);
    }

    /**
     * 応答データを解析する
     * 終了コードより後ろを渡すこと
     * @param responseData
     * @param request
     * @return
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
     * デバイス値を取得する
     * @return
     */
    public byte[] getReadData() {
        return _data;
    }

    /**
     * 終了コードより後ろのバイト列に変換する
     * @return
     */
    @Override
    public byte[] toBytes() {
        return Utility.concatByteArrays(
            Utility.fromIntToBytes(_data.length, 2),
            _data
        );
    }
}
