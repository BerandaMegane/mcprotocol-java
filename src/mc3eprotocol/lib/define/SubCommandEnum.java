package mc3eprotocol.lib.define;

import mc3eprotocol.lib.util.Bytes;

/**
 * サブコマンド列挙型
 * MC3Eプロトコルのサブコマンドを定義します（sh080003ah p.61参照）。
 * 対象PLCシリーズやデータ種別によって異なるサブコマンドを使用します。
 */
public enum SubCommandEnum {
    /** MELSEC-Q/Lシリーズ ワードデバイス用 */
    Q_WORD("0000"),
    /** MELSEC-Q/Lシリーズ ビットデバイス用 */
    Q_BIT ("0001"),
    
    /** MELSEC iQ-Rシリーズ ワードデバイス用 */
    IQ_R_WORD("0002"),
    /** MELSEC iQ-Rシリーズ ビットデバイス用 */
    IQ_R_BIT ("0003"),

    /** モニタ条件指定あり */
    MONITOR_CONDITION("0040"),

    /** サブコマンド指定なし */
    NONE("0000"),
    ;
    
    /** サブコマンドのバイトデータ */
    private final Bytes _bytes;

    /**
     * コンストラクタ
     * 
     * @param hexString 16進文字列形式のサブコマンド値
     */
    SubCommandEnum(String hexString) {
        this._bytes = new Bytes(hexString);
    }

    /**
     * バイト配列からサブコマンドを構築します。
     * 該当するサブコマンドがない場合はNONEを返します。
     * 
     * @param bytes サブコマンドのバイト配列
     * @return 対応するサブコマンド列挙値（見つからない場合はNONE）
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

    /**
     * サブコマンドのバイトデータを取得します。
     * 
     * @return サブコマンドのBytesオブジェクト
     */
    public Bytes getBytes() {
        return _bytes;
    }

    /**
     * バイト配列とサブコマンドが一致するかを判定します。
     * 
     * @param bytes 比較対象のバイト配列
     * @return 一致する場合はtrue、それ以外はfalse
     */
    public boolean equals(byte[] bytes) {
        return java.util.Arrays.equals(_bytes.getByteArray(), bytes);
    }
}

