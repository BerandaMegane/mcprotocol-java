package mc3eprotocol.define;

import mc3eprotocol.Utility;

/**
 * サブヘッダ sh080003ah p.42
 */
public enum SubHeaderEnum {
    REQUEST ("0050"),
    RESPONSE("00D0"),
    ;

    // フィールド
    private final String _hexString;
    private final byte[] _bytes;

    // コンストラクタ
    SubHeaderEnum(String hexCode) {
        this._hexString = hexCode;
        this._bytes = Utility.fromHexStringToBytes(_hexString, 2);
    }

    // ビルドメソッド
    public static SubHeaderEnum build(byte[] bytes) {
        for (SubHeaderEnum subHeader : values()) {
            if (subHeader.equals(bytes)) {
                return subHeader;
            }
        }
        throw new IllegalArgumentException("サブヘッダが正しくありません。");
    }

    public String getHexString() {
        return _hexString;
    }

    public byte[] getBytes() {
        return this._bytes;
    }

    public boolean equals(byte[] bytes) {
        return java.util.Arrays.equals(this._bytes, bytes);
    }
}
