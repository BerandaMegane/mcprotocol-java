package mc3eprotocol.lib.define;

import mc3eprotocol.lib.util.Converter;

/**
 * アクセス経路クラス
 * MC3Eプロトコルのアクセス経路設定を管理します（sh080003ah p.48参照）。
 * ネットワーク構成に応じてPLCへの通信経路を指定します。
 */
public class AccessRoute {

    /** ネットワーク番号 */
    private byte[] networkNumberBytes;
    /** PLC局番号 */
    private byte[] plcStationNumberBytes;
    /** 要求先ユニットI/O番号 */
    private byte[] unitIoNumberBytes;
    /** 要求先ユニット局番号 */
    private byte[] unitStationNumberBytes;

    /**
     * 直接TCP/IP接続時のデフォルトアクセス経路
     */
    public static final AccessRoute DEFAULT = new AccessRoute(new byte[] {
        (byte)0x00,  // ネットワーク番号
        (byte)0xFF,  // PLC番号
        (byte)0xFF, 0x03,  // 要求先ユニットI/O番号
        (byte)0x00   // 要求先ユニット局番号
    });

    /**
     * コンストラクタ
     * 
     * @param byteArray アクセス経路の5バイトデータ
     * @throws IllegalArgumentException バイト数が5でない場合
     */
    public AccessRoute(byte[] byteArray) {
        if (byteArray.length != 5) {
            throw new IllegalArgumentException("アクセス経路のバイト数が正しくありません。正しくは 5 バイトです。");
        }
        networkNumberBytes     = new byte[] {byteArray[0]};
        plcStationNumberBytes  = new byte[] {byteArray[1]};
        unitIoNumberBytes      = new byte[] {byteArray[2], byteArray[3]};
        unitStationNumberBytes = new byte[] {byteArray[4]};
    }

    /**
     * アクセス経路をバイト配列に変換します。
     * 
     * @return アクセス経路のバイト配列
     */
    public byte[] toByteArray() {
        return Converter.concatByteArrays(
            networkNumberBytes,
            plcStationNumberBytes,
            unitIoNumberBytes,
            unitStationNumberBytes
        );
    }

    /**
     * アクセス経路の詳細情報を文字列形式で返します。
     * 
     * @return アクセス経路情報の文字列表現
     */
    @Override
    public String toString() {
        return Converter.hereDoc(s->s.trim(), System.lineSeparator(),
            "AccessRoute: {",
            "- networkNumber: "     + Converter.fromBytesToHexString(networkNumberBytes),
            "- plcStationNumber: "  + Converter.fromBytesToHexString(plcStationNumberBytes),
            "- unitIoNumber: "      + Converter.fromBytesToHexString(unitIoNumberBytes),
            "- unitStationNumber: " + Converter.fromBytesToHexString(unitStationNumberBytes),
            "}"
        );
    }

    /**
     * ネットワーク番号のバイトデータを取得します。
     * 
     * @return ネットワーク番号
     */
    public byte[] getNetworkNumberBytes() {
        return networkNumberBytes;
    }

    /**
     * PLC局番号のバイトデータを取得します。
     * 
     * @return PLC局番号
     */
    public byte[] getPlcStationNumberBytes() {
        return plcStationNumberBytes;
    }

    /**
     * 要求先ユニットI/O番号のバイトデータを取得します。
     * 
     * @return 要求先ユニットI/O番号
     */
    public byte[] getUnitIoNumberBytes() {
        return unitIoNumberBytes;
    }

    /**
     * 要求先ユニット局番号のバイトデータを取得します。
     * 
     * @return 要求先ユニット局番号
     */
    public byte[] getUnitStationNumberBytes() {
        return unitStationNumberBytes;
    }
}
