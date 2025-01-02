package mc3eprotocol.payload;

public class PlcTypeNameResponse extends AbstractResponse {

    private String _PlcTypeName;

    /**
     * 応答データを解析する
     * 終了コードより後ろを渡すこと
     * @param responseData
     * @param request
     * @return
     */
    public PlcTypeNameResponse(byte[] responseData, PlcTypeNameRequest request) {
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
        _PlcTypeName = new String(responseData);
    }

    /**
     * デバイス値を取得する
     * @return
     */
    public String getPlcTypeName() {
        return _PlcTypeName;
    }

    /**
     * 終了コードより後ろのバイト列に変換する
     * @return
     */
    @Override
    public byte[] toBytes() {
        return _PlcTypeName.getBytes();
    }
}
