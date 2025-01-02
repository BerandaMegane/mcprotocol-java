package mc3eprotocol.payload;

public abstract class AbstractResponse {

    /**
     * 応答データを解析する
     * 終了コードより後ろを渡すこと
     * @param responseData
     * @param request
     * @return
     */
    protected abstract void parse(byte[] responseData, AbstractRequest request);

    /**
     * 終了コードより後ろのバイト列に変換する
     * @return
     */
    public abstract byte[] toBytes();
}
