package mc3eprotocol.lib.util;

/**
 * バイト配列と16進数文字列の相互変換を提供するユーティリティクラスです。
 */
public class Bytes {
    /** 16進数文字列 */
    private final String _hexString;
    /** バイト配列 */
    private final byte[] _byteArray;
    
    /**
     * バイト配列からBytesオブジェクトを生成します。
     * @param byteArray バイト配列
     */
    public Bytes(byte[] byteArray) {
        _byteArray = byteArray;
        _hexString = Converter.fromBytesToHexString(byteArray);
    }

    /**
     * 16進数文字列からBytesオブジェクトを生成します。
     * @param hexString 16進数文字列
     * @throws IllegalArgumentException 文字列長が偶数でない場合、または2文字未満の場合
     */
    public Bytes(String hexString) {
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("16 進数文字列の長さは偶数でなければなりません。");
        }
        if (hexString.length() < 2) {
            throw new IllegalArgumentException("16 進数文字列の長さは2文字以上でなければなりません。");
        }

        _hexString = hexString;
        _byteArray = Converter.fromHexStringToBytes(hexString, hexString.length() / 2);
    }

    /**
     * 16進数文字列を取得します。
     * @return 16進数文字列
     */
    public String getHexString() {
        return _hexString;
    }

    /**
     * バイト配列を取得します。
     * @return バイト配列
     */
    public byte[] getByteArray() {
        return _byteArray;
    }

    /**
     * バイト配列の長さを取得します。
     * @return バイト配列の長さ
     */
    public int getLength() {
        return _byteArray.length;
    }

    /**
     * 指定したバイト配列と内容が等しいか判定します。
     * @param bytes 比較対象のバイト配列
     * @return 内容が等しい場合はtrue
     */
    public boolean equals(byte[] bytes) {
        return java.util.Arrays.equals(getByteArray(), bytes);
    }

    /**
     * 指定したBytesオブジェクトと内容が等しいか判定します。
     * @param bytes 比較対象のBytesオブジェクト
     * @return 内容が等しい場合はtrue
     */
    public boolean equals(Bytes bytes) {
        return this.equals(bytes.getByteArray());
    }

    /**
     * 2つのBytesオブジェクトが等しいか判定します。
     * @param a 比較対象1
     * @param b 比較対象2
     * @return 内容が等しい場合はtrue
     */
    public static boolean equals(Bytes a, Bytes b) {
        return a.equals(b);
    }

    /**
     * 2つのバイト配列が等しいか判定します。
     * @param a 比較対象1
     * @param b 比較対象2
     * @return 内容が等しい場合はtrue
     */
    public static boolean equals(byte[] a, byte[] b) {
        return java.util.Arrays.equals(a, b);
    }
}
