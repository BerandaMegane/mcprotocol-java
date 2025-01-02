package mc3eprotocol.payload;

public interface IPayload {
    /**
     * バイト列に変換する
     * @return
     */
    public byte[] toBytes();
    
    /**
     * バイト列から解析してメンバ変数にセットする
     * @param bytes
     */
    public void parse(byte[] bytes);
}
