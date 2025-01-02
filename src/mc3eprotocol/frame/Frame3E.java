package mc3eprotocol.frame;

import java.util.HexFormat;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

/**
 * 要求伝文・応答伝文 sh080003ah p.60 以降
 */
public class Frame3E {
    public SubHeaderEnum _subHeader;  // サブヘッダ
    public AccessRoute _accessRoute;  // アクセス経路
    public short _uncheckedDataLength;  // データ長 = 監視タイマ/終了コード + データ  
    public short _monitorOrFinishCode;  // 監視タイマ/終了コード
    public byte[] _payload;  // データ

    public Frame3E() {}
    public Frame3E(
        SubHeaderEnum subHeader,
        AccessRoute accessRoute,
        short monitorOrFinishCode,
        byte[] data
    ) {
        this._subHeader = subHeader;
        this._accessRoute = accessRoute;
        this._monitorOrFinishCode = monitorOrFinishCode;
        this._payload = data;
    }

    public byte[] toBytes() {
        if (_payload == null) {
            throw new RuntimeException("Data is null.");
        }
        // 要求データ長 = 監視タイマー(2 byte) + 要求データ
        short dataLength = (short)(2 + _payload.length);

        return Utility.concatByteArrays(
            _subHeader.getBytes(),       // サブヘッダ
            _accessRoute.toBytes(),     // アクセス経路
            Utility.fromIntToBytes(dataLength, 2),  // データ長
            Utility.fromIntToBytes(_monitorOrFinishCode, 2),  // 監視タイマ/終了コード
            _payload
        );
    }

    public boolean isNormalFinish() {
        return _monitorOrFinishCode == 0x0000;
    }

    public boolean isDataLengthValid() {
        return _payload.length + 2 == _uncheckedDataLength;
    }

    @Override
    public String toString() {
        // 要求データ長 = 監視タイマー(2 byte) + 要求データ
        short dataLength = (short)(2 + _payload.length);

        return Utility.hereDoc(s->s.trim(), System.lineSeparator(),
            "Frame3E {",
            "- SubHeader: " + _subHeader,
            _accessRoute.toString(),
            "- DataLength: " + dataLength,
            "- MonitorOrFinishCode: " + _monitorOrFinishCode,
            "- Payload: " + HexFormat.of().formatHex(_payload),
            "}"
        );
    }
}
