package mc3eprotocol.lib.define;

import mc3eprotocol.lib.util.Bytes;

/**
 * コマンドコード列挙型
 * MC3Eプロトコルのコマンドコードを定義します（sh080003ah p.61参照）。
 * デバイスアクセスやユニット制御などの各種コマンドを提供します。
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
    REMOTE_RUN  ("1001"),
    /**
     * リモートSTOP
     */
    REMOTE_STOP ("1002"),
    /**
     * リモートPAUSE
     */
    REMOTE_PAUSE("1003"),
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
    CPU_TYPE_READ("0101"),
    /**
     * 折返しテスト
     */
    ECHO_TEST("0619"),
    ;

    /** コマンドのバイトデータ */
    private final Bytes _bytes;

    /**
     * コンストラクタ
     * 
     * @param hexString 16進文字列形式のコマンドコード
     */
    CommandEnum(String hexString) {
        this._bytes = new Bytes(hexString);
    }

    /**
     * バイト配列からコマンドを構築します。
     * 
     * @param byteArray コマンドコードのバイト配列
     * @return 対応するコマンド列挙値
     * @throws IllegalArgumentException 指定されたコードのコマンドが存在しない場合
     */
    public static CommandEnum build(byte[] byteArray) {
        for (CommandEnum command : CommandEnum.values()) {
            if (command.equals(byteArray)) {
                return command;
            }
        }
        throw new IllegalArgumentException("指定されたコードのコマンドは存在しません");
    }

    /**
     * コマンドのバイトデータを取得します。
     * 
     * @return コマンドのBytesオブジェクト
     */
    public Bytes getBytes() {
        return _bytes;
    }

    /**
     * バイト配列とコマンドが一致するかを判定します。
     * 
     * @param bytes 比較対象のバイト配列
     * @return 一致する場合はtrue、それ以外はfalse
     */
    public boolean equals(byte[] bytes) {
        return java.util.Arrays.equals(_bytes.getByteArray(), bytes);
    }
}
