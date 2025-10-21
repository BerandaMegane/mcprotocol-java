package mc3eprotocol.lib.payload;

/**
 * ペイロードインターフェース
 * MC3Eプロトコルのペイロード処理に必要な基本機能を定義します。
 * 要求・応答データのバイト配列変換と解析機能を提供します。
 */
public interface IPayload {
    /**
     * オブジェクトをバイト配列に変換します。
     * 
     * @return 変換されたバイト配列
     */
    public byte[] toByteArray();
    
    /**
     * バイト配列からオブジェクトを解析してメンバ変数にセットします。
     * 
     * @param bytes 解析対象のバイト配列
     */
    public void parse(byte[] bytes);
}
