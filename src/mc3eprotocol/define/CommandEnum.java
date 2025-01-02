package mc3eprotocol.define;

import mc3eprotocol.Utility;

/**
 * コマンドコード sh080003ah p.61
 */
public enum CommandEnum {
    // *** デバイスアクセス ***
    /**
     * 一括読出し（ブロック読出し）
     */
    BLOCK_READ ("0401"),
    /**
     * 一括書込み（ブロック書込み）
     */
    BLOCK_WRITE("1401"),

    /**
     * ランダム読出し
     */
    RANDOM_READ ("0403"),
    /**
     * ランダム書込み
     */
    RANDOM_WRITE("1402"),

    /**
     * 複数ブロック一括読出し
     */
    MULTI_BLOCK_READ ("0406"),
    /**
     * 複数ブロック一括書込み
     */
    MULTI_BLOCK_WRITE("1406"),
    
    /**
     * モニタデータ登録
     */
    MONITOR_REGISTER("0801"),
    /**
     * モニタデータ読出し
     */
    MONITOR_READ    ("0802"),

    // *** ユニット制御 ***
    /**
     * リモートRUN
     */
    REMOTE_RUN        ("1001"),
    /**
     * リモートSTOP
     */
    REMOTE_STOP       ("1002"),
    /**
     * リモートPAUSE
     */
    REMOTE_PAUSE      ("1003"),
    /**
     * リモートラッチクリア
     */
    REMOTE_LATCH_CLEAR("1005"),
    /**
     * リモートリセット
     */
    REMOTE_RESET      ("1006"),
    /**
     * CPU形名読出し
     */
    CPU_TYPE_READ     ("0101"),
    /**
     * 折返しテスト
     */
    ECHO_TEST         ("0619"),
    ;

    // フィールド
    private final String _hexString;
    private final byte[] _bytes;

    /**
     * コンストラクタ
     * @param hexString
     */
    CommandEnum(String hexString) {
        this._hexString = hexString;
        this._bytes = Utility.fromHexStringToBytes(_hexString, 2);
    }

    public static CommandEnum build(byte[] bytes) {
        for (CommandEnum command : CommandEnum.values()) {
            if (command.equals(bytes)) {
                return command;
            }
        }
        throw new IllegalArgumentException("指定されたコードのコマンドは存在しません");
    }

    public String getHexString() {
        return _hexString;
    }

    public byte[] getBytes() {
        return _bytes;
    }

    public boolean equals(byte[] bytes) {
        return java.util.Arrays.equals(this._bytes, bytes);
    }
}
