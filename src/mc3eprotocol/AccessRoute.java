package mc3eprotocol;

/**
 * アクセス経路プリセット sh080003ah p.48
 */
public class AccessRoute {

    // フィールド
    private byte[] networkNumberBytes;
    private byte[] plcStationNumberBytes;
    private byte[] unitIoNumberBytes;
    private byte[] unitStationNumberBytes;

    /**
     * 直接 TCP/IP 接続するときのデフォルト
     */
    public static final AccessRoute DEFAULT = new AccessRoute(new byte[] {
        (byte)0x00,  // ネットワーク番号
        (byte)0xFF,  // PLC番号
        (byte)0xFF, 0x03,  // 要求先ユニットI/O番号
        (byte)0x00   // 要求先ユニット局番号
    });

    /**
     * コンストラクタ
     * @param bytes: byte[] 型で直接指定
     */
    public AccessRoute(byte[] bytes) {
        if (bytes.length != 5) {
            throw new IllegalArgumentException("アクセス経路のバイト数が正しくありません。正しくは 5 バイトです。");
        }
        networkNumberBytes = new byte[] {bytes[0]};
        plcStationNumberBytes = new byte[] {bytes[1]};
        unitIoNumberBytes = new byte[] {bytes[2], bytes[3]};
        unitStationNumberBytes = new byte[] {bytes[4]};
    }

    /**
     * byte[] に変換
     * @return
     */
    public byte[] toBytes() {
        return Utility.concatByteArrays(
            networkNumberBytes,
            plcStationNumberBytes,
            unitIoNumberBytes,
            unitStationNumberBytes
        );
    }

    @Override
    public String toString() {
        return Utility.hereDoc(s->s.trim(), System.lineSeparator(),
            "AccessRoute: {",
            "- networkNumber: " + Utility.fromBytesToHexString(networkNumberBytes),
            "- plcStationNumber: " + Utility.fromBytesToHexString(plcStationNumberBytes),
            "- unitIoNumber: " + Utility.fromBytesToHexString(unitIoNumberBytes),
            "- unitStationNumber: " + Utility.fromBytesToHexString(unitStationNumberBytes),
            "}"
        );
    }

    public byte[] getNetworkNumberBytes() {
        return networkNumberBytes;
    }

    public byte[] getPlcStationNumberBytes() {
        return plcStationNumberBytes;
    }

    public byte[] getUnitIoNumberBytes() {
        return unitIoNumberBytes;
    }

    public byte[] getUnitStationNumberBytes() {
        return unitStationNumberBytes;
    }
}
