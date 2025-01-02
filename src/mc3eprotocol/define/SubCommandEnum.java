package mc3eprotocol.define;

import mc3eprotocol.Utility;

/**
 * サブコマンド sh080003ah p.61
 */
public enum SubCommandEnum {
    // MELSEC-Q/L シリーズ共通
    Q_WORD("0000"),
    Q_BIT ("0001"),
    
    // MELSEC iQ-R シリーズ共通
    IQ_R_WORD("0002"),
    IQ_R_BIT ("0003"),

    // モニタ条件指定あり
    MONITOR_CONDITION("0040"),

    // サブコマンド指定なし
    NONE("0000"),
    ;
    
    // フィールド
    private final String _hexString;
    private final byte[] _bytes;

    /**
     * コンストラクタ
     * @param hexString
     */
    SubCommandEnum(String hexString) {
        this._hexString = hexString;
        this._bytes = Utility.fromHexStringToBytes(_hexString, 2);
    }

    /**
     * ビルドメソッド
     * @param bytes
     * @return
     */
    public static SubCommandEnum build(byte[] bytes) {
        for (SubCommandEnum subCommand : SubCommandEnum.values()) {
            if (subCommand == NONE) {
                continue;
            }
            if (subCommand.equals(bytes)) {
                return subCommand;
            }
        }
        return NONE;
    }

    public String getHexString() {
        return _hexString;
    }

    public byte[] getBytes() {
        return _bytes;
    }

    public boolean equals(byte[] bytes) {
        return java.util.Arrays.equals(getBytes(), bytes);
    }
}

