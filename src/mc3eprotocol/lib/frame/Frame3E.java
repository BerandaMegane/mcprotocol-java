package mc3eprotocol.lib.frame;

import java.util.HexFormat;

import mc3eprotocol.lib.define.AccessRoute;
import mc3eprotocol.lib.define.SubHeaderEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * MC3Eプロトコルフレームクラス
 * 要求伝文・応答伝文を表現するクラスです（sh080003ah p.60以降参照）。
 * MC3Eフレームのヘッダー情報とペイロードデータを管理します。
 */
public class Frame3E {
    /** サブヘッダ情報 */
    public SubHeaderEnum _subHeader;
    /** アクセス経路設定 */
    public AccessRoute _accessRoute;
    /** データ長（監視タイマ/終了コード + データ） */
    public short _uncheckedDataLength;
    /** 監視タイマ値（要求時）または終了コード（応答時） */
    public short _monitorOrFinishCode;
    /** ペイロードデータ */
    public byte[] _payloadByteArray;

    /**
     * デフォルトコンストラクタ
     */
    public Frame3E() {}
    
    /**
     * パラメータ付きコンストラクタ
     * 
     * @param subHeader サブヘッダ情報
     * @param accessRoute アクセス経路設定
     * @param monitorOrFinishCode 監視タイマ値または終了コード
     * @param payloadByteArray ペイロードデータ
     */
    public Frame3E(
        SubHeaderEnum subHeader,
        AccessRoute accessRoute,
        short monitorOrFinishCode,
        byte[] payloadByteArray
    ) {
        this._subHeader = subHeader;
        this._accessRoute = accessRoute;
        this._monitorOrFinishCode = monitorOrFinishCode;
        this._payloadByteArray = payloadByteArray;
    }

    /**
     * フレームをバイト配列に変換します。
     * MC3Eプロトコル仕様に従って各要素を結合してバイト配列を生成します。
     * 
     * @return フレーム全体のバイト配列
     * @throws RuntimeException ペイロードデータがnullの場合
     */
    public byte[] toBytes() {
        if (_payloadByteArray == null) {
            throw new RuntimeException("Data is null.");
        }
        // 要求データ長 = 監視タイマー(2 byte) + 要求データ
        short dataLength = (short)(2 + _payloadByteArray.length);

        return Converter.concatByteArrays(
            _subHeader.getBytes().getByteArray(),       // サブヘッダ
            _accessRoute.toByteArray(),     // アクセス経路
            Converter.fromIntToByteArray(dataLength, 2),  // データ長
            Converter.fromIntToByteArray(_monitorOrFinishCode, 2),  // 監視タイマ/終了コード
            _payloadByteArray
        );
    }

    /**
     * 応答フレームの終了コードが正常かどうかを判定します。
     * 
     * @return 終了コードが0x0000（正常）の場合はtrue、それ以外はfalse
     */
    public boolean isNormalFinish() {
        return _monitorOrFinishCode == 0x0000;
    }

    /**
     * データ長フィールドの値が実際のペイロードサイズと一致するかを検証します。
     * 
     * @return データ長が正しい場合はtrue、不正な場合はfalse
     */
    public boolean isDataLengthValid() {
        return _payloadByteArray.length + 2 == _uncheckedDataLength;
    }

    /**
     * フレーム情報を文字列形式で返します。
     * デバッグ用途でフレームの内容を確認するために使用します。
     * 
     * @return フレーム情報の文字列表現
     */
    @Override
    public String toString() {
        // 要求データ長 = 監視タイマー(2 byte) + 要求データ
        short dataLength = (short)(2 + _payloadByteArray.length);

        return Converter.hereDoc(s->s.trim(), System.lineSeparator(),
            "Frame3E {",
            "- SubHeader: " + _subHeader,
            _accessRoute.toString(),
            "- DataLength: " + dataLength,
            "- MonitorOrFinishCode: " + _monitorOrFinishCode,
            "- Payload: " + HexFormat.of().formatHex(_payloadByteArray),
            "}"
        );
    }
}
