package mc3eprotocol.payload;

public class BlockBitReadResponse extends AbstractResponse {
    private boolean[] _readData;

    /**
     * 応答データを解析する
     * 終了コードより後ろを渡すこと
     * @param responseData
     * @param request
     * @return
     */
    public BlockBitReadResponse(byte[] responseData, BlockReadRequest request) {
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
     * デバイス値を取得する
     * @return
     */
    public boolean[] getReadData() {
        return _readData;
    }

    /**
     * 終了コードより後ろのバイト列に変換する
     * @return
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
