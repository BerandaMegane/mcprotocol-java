package mc3eprotocol.payload;

import mc3eprotocol.*;

public class BlockWordReadResponse extends AbstractResponse {
    private short[] _readData;

    /**
     * 応答データを解析する
     * 終了コードより後ろを渡すこと
     * @param responseData
     * @param request
     * @return
     */
    public BlockWordReadResponse(byte[] responseData, BlockReadRequest request) {
        parse(responseData, request);
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
        if (((BlockReadRequest)request)._isBitDevice) {
            throw new IllegalArgumentException("ビットデバイスの読み込み要求に対しては、別のクラスを使ってください");
        }
        _readData = Utility.fromBytesToShortArray(responseData);
    }

    /**
     * デバイス値を取得する
     * @return
     */
    public short[] getReadData() {
        return _readData;
    }

    /**
     * 終了コードより後ろのバイト列に変換する
     * @return
     */
    @Override
    public byte[] toBytes() {
        return Utility.fromShortArrayToBytes(_readData);
    }
}
