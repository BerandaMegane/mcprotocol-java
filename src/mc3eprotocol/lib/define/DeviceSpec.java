package mc3eprotocol.lib.define;

import java.nio.ByteBuffer;

import mc3eprotocol.lib.util.Converter;

/**
 * デバイス指定子クラス
 * PLCデバイスの種別と番号を管理するクラスです。
 * 文字列表現からの解析やバイト配列への変換機能を提供します。
 */
public class DeviceSpec {

    /** デバイスコード */
    private DeviceCodeEnum deviceCode;
    /** デバイス番号 */
    private int deviceNumber;

    /**
     * デバイスコードと番号を指定するコンストラクタ
     * 
     * @param deviceCode デバイスコード
     * @param deviceNumber デバイス番号
     */
    public DeviceSpec(DeviceCodeEnum deviceCode, int deviceNumber) {
        this.deviceCode = deviceCode;
        this.deviceNumber = deviceNumber;
    }

    /**
     * 文字列からデバイス指定子を解析するコンストラクタ
     * 
     * @param deviceSpecString デバイス指定文字列（例："D100", "M0"）
     * @throws IllegalArgumentException フォーマットが不正な場合
     */
    public DeviceSpec(String deviceSpecString) {
        // 文字列を正規化
        deviceSpecString = deviceSpecString.toUpperCase();
        // アルファベットと数字以外の文字が含まれていたら例外を投げる
        if (!deviceSpecString.matches("^[A-Z0-9]*$")) {
            throw new IllegalArgumentException("デバイス指定子のフォーマットが不正です。");
        }

        try {
            // 試しに2文字切り出す
            String deviceCodeString = deviceSpecString.substring(0, 2);
            if (DeviceCodeEnum.containsCodeString(deviceCodeString)) {
                this.deviceCode = DeviceCodeEnum.build(deviceCodeString);
                this.deviceNumber = Integer.parseInt(deviceSpecString.substring(2), this.deviceCode.getDeviceNumberBase());
                return;
            } else {
                // 1文字目のみ
                deviceCodeString = deviceSpecString.substring(0, 1);
                this.deviceCode = DeviceCodeEnum.build(deviceCodeString);
                this.deviceNumber = Integer.parseInt(deviceSpecString.substring(1), this.deviceCode.getDeviceNumberBase());
                return;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("デバイス指定子のフォーマットが不正です。");
        }
    }
    
    /**
     * バイト配列からデバイス指定子を復元するコンストラクタ
     * 
     * @param data デバイス指定のバイトデータ
     */
    public DeviceSpec(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        // デバイス番号
        int deviceNumberLength = DeviceCodeEnum.getDeviceNumberLength();
        byte[] deviceNumberBytes = new byte[deviceNumberLength];
        buffer.get(deviceNumberBytes);
        this.deviceNumber = Converter.fromBytesToInt(deviceNumberBytes);

        // デバイスコード
        int deviceCodeLength = DeviceCodeEnum.getDeviceCodeLength();
        byte[] deviceCodeBytes = new byte[deviceCodeLength];
        buffer.get(deviceCodeBytes);
        this.deviceCode = DeviceCodeEnum.build(deviceCodeBytes);
    }
    
    /**
     * デバイスコードを取得します。
     * 
     * @return デバイスコード
     */
    public DeviceCodeEnum getDeviceCode() {
        return deviceCode;
    }

    /**
     * デバイス番号を取得します。
     * 
     * @return デバイス番号
     */
    public int getDeviceNumber() {
        return deviceNumber;
    }

    /**
     * デバイス指定をバイト配列に変換します。
     * MC3Eプロトコル仕様に従った形式で変換されます。
     * 
     * @return デバイス指定のバイト配列
     */
    public byte[] toByteArray() {
        byte[] deviceNumberBytes = Converter.fromIntToByteArray(deviceNumber, DeviceCodeEnum.getDeviceNumberLength());
        return Converter.concatByteArrays(
            deviceNumberBytes,               // 先頭デバイス番号
            deviceCode.getBytes().getByteArray()  // デバイスコード
        );
    }

    /**
     * デバイス指定を文字列形式で返します。
     * デバイス番号は適切な進数（10進数または16進数）で表示されます。
     * 
     * @return デバイス指定の文字列表現
     */
    @Override
    public String toString() {
        int base = deviceCode.getDeviceNumberBase();
        return deviceCode.getDeviceSymbol() + Integer.toString(deviceNumber, base);
    }
}
