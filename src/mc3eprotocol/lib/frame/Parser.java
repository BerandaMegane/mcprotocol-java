package mc3eprotocol.lib.frame;

import java.nio.*;
import java.util.HexFormat;

import mc3eprotocol.lib.define.AccessRoute;
import mc3eprotocol.lib.define.SubHeaderEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * MC3Eフレーム解析クラス
 * 3Eフレームの解析を行うパーサークラスです（sh080003ah p.41参照）。
 * バイト配列からMC3Eフレーム構造を解析し、Frame3Eオブジェクトに変換します。
 */
public class Parser {

    /**
     * デフォルトコンストラクタ
     */
    public Parser() {
    }

    /**
     * バイト配列をFrame3Eオブジェクトに解析します。
     * MC3Eプロトコル仕様に従ってフレーム要素を順次解析します。
     *
     * @param bytes 解析対象のバイト配列
     * @return 解析されたFrame3Eオブジェクト
     */
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
        frame._payloadByteArray = data;

        return frame;
    }

    /**
     * バイトバッファからサブヘッダを解析します。
     *
     * @param buffer 解析対象のバイトバッファ
     * @return 解析されたサブヘッダ
     */
    public static SubHeaderEnum parseSubHeader(ByteBuffer buffer) {
        // サブヘッダ
        byte[] byteArray = new byte[2];
        buffer.get(byteArray);
        return SubHeaderEnum.build(byteArray);
    }

    /**
     * バイトバッファからアクセス経路を解析します。
     * 現在のところバリデーションは行っていません。
     *
     * @param buffer 解析対象のバイトバッファ
     * @return 解析されたアクセス経路
     */
    public static AccessRoute parseAccessRoute(ByteBuffer buffer) {
        // アクセス経路
        byte[] bytes = new byte[5];
        buffer.get(bytes);

        // いまのところノーチェック
        return new AccessRoute(bytes);
    }

    /**
     * バイトバッファから残りのデータを取得します。
     *
     * @param buffer 解析対象のバイトバッファ
     * @return 取得されたデータ
     */
    public static byte[] fetchData(ByteBuffer buffer) {
        // 応答データ
        byte[] data = new byte[buffer.remaining()];  // 終了コードは除く
        buffer.get(data);
        return data;
    }

    /**
     * フレーム情報をコンソールに出力します。
     * デバッグ用途でフレームの詳細情報を確認するために使用します。
     * 要求フレームと応答フレームで監視タイマー/終了コードの表示を切り替えます。
     *
     * @param bytes 出力対象のフレームバイト配列
     */
    public static void printFrameInfo(byte[] bytes) {
        Frame3E frame = parseFrame(bytes);

        String monitorOrFinish;
        if (frame._subHeader == SubHeaderEnum.REQUEST) {
            monitorOrFinish = "監視タイマ: " + Converter.fromBytesToHexString(Converter.fromIntToByteArray(frame._monitorOrFinishCode, 2));
        } else if (frame._subHeader == SubHeaderEnum.RESPONSE) {
            monitorOrFinish = "終了コード: " + Converter.fromBytesToHexString(Converter.fromIntToByteArray(frame._monitorOrFinishCode, 2));
        } else {
            monitorOrFinish = "監視タイマ/終了コード: " + Converter.fromBytesToHexString(Converter.fromIntToByteArray(frame._monitorOrFinishCode, 2));
        }

        System.out.println(
            Converter.hereDoc(s->s, System.lineSeparator(),
                // "               d00000ffff030002000000000000000000000000",  // 正常応答
                // "               d00000ffff03000b00550000ffff030001140000",  // 異常応答
                "Frame: " + HexFormat.of().formatHex(bytes),
                "       1   2         3   4   5",
                "       1: サブヘッダ: " + frame._subHeader,
                "       2: アクセス経路",
                "       3: データ長 (4以降のバイト数）: " + frame._uncheckedDataLength + "",
                "       4: " + monitorOrFinish,
                "       5: その他のデータ"
            )
        );
    }
}
