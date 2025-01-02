package mc3eprotocol.frame;

import java.nio.*;
import java.util.HexFormat;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

/**
 * 3Eフレーム パーサー sh080003ah p.41
 */
public class Parser {

    public Parser() {
    }

    public static Frame3E parseFrame(byte[] bytes) {
        // オブジェクト生成
        Frame3E frame = new Frame3E();

        // バッファに貯める
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // サブヘッダ
        frame._subHeader = parseSubHeader(buffer);
        // アクセス経路
        frame._accessRoute = parseAccessRoute(buffer);
        // データ長
        frame._uncheckedDataLength = buffer.getShort();
        // 監視タイマー/終了コード
        frame._monitorOrFinishCode = buffer.getShort();
        // データ
        byte[] data = new byte[frame._uncheckedDataLength - 2];  // 終了コードは除く
        buffer.get(data);
        frame._payload = data;

        return frame;
    }

    public static SubHeaderEnum parseSubHeader(ByteBuffer buffer) {
        // サブヘッダ
        byte[] bytes = new byte[2];
        buffer.get(bytes);
        return SubHeaderEnum.build(bytes);
    }

    public static AccessRoute parseAccessRoute(ByteBuffer buffer) {
        // アクセス経路
        byte[] bytes = new byte[5];
        buffer.get(bytes);

        // いまのところノーチェック
        return new AccessRoute(bytes);
    }

    public static byte[] fetchData(ByteBuffer buffer) {
        // 応答データ
        byte[] data = new byte[buffer.remaining()];  // 終了コードは除く
        buffer.get(data);
        return data;
    }

    public static void printInfo(byte[] bytes) {
        Frame3E frame = parseFrame(bytes);

        System.out.println(
            Utility.hereDoc(s->s, System.lineSeparator(), 
                // "               d00000ffff030002000000000000000000000000",  // 正常応答
                // "               d00000ffff03000b00550000ffff030001140000",  // 異常応答
                "Frame: " + HexFormat.of().formatHex(bytes),
                "       1   2         3   4   5",
                "       1: サブヘッダ: " + frame._subHeader,
                "       2: アクセス経路",
                "       3: データ長 (4以降のバイト数）: " + frame._uncheckedDataLength + "",
                "       4: 監視タイマ/終了コード: " + Utility.fromBytesToHexString(Utility.fromIntToBytes(frame._monitorOrFinishCode, 2)),
                "       5: その他のデータ" 
            )
        );
    }
}
