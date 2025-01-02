package mc3eprotocol;

import java.nio.ByteBuffer;

import mc3eprotocol.define.*;

/** 
 * デバイス指定子
 */
public class DeviceSpec {

    private DeviceCodeEnum deviceCode;
    private int deviceNumber;

    public DeviceSpec(DeviceCodeEnum deviceCode, int deviceNumber) {
        this.deviceCode = deviceCode;
        this.deviceNumber = deviceNumber;
    }

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
    
    public DeviceSpec(SeriesModelEnum _seriesModel, byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        // デバイス番号
        int deviceNumberLength = DeviceCodeEnum.getDeviceNumberLength(_seriesModel);
        byte[] deviceNumberBytes = new byte[deviceNumberLength];
        buffer.get(deviceNumberBytes);
        this.deviceNumber = Utility.fromBytesToInt(deviceNumberBytes);

        // デバイスコード
        int deviceCodeLength = DeviceCodeEnum.getDeviceCodeLength(_seriesModel);
        byte[] deviceCodeBytes = new byte[deviceCodeLength];
        buffer.get(deviceCodeBytes);
        this.deviceCode = DeviceCodeEnum.build(deviceCodeBytes, _seriesModel);
    }
    
    public DeviceCodeEnum getDeviceCode() {
        return deviceCode;
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public byte[] toBytes(SeriesModelEnum seriesModel) {
        byte[] deviceNumberBytes = Utility.fromIntToBytes(deviceNumber, DeviceCodeEnum.getDeviceNumberLength(seriesModel));
        return Utility.concatByteArrays(
            deviceNumberBytes,               // 先頭デバイス番号
            deviceCode.toBytes(seriesModel)  // デバイスコード
        );
    }

    @Override
    public String toString() {
        int base = deviceCode.getDeviceNumberBase();
        return deviceCode.getDeviceSymbol() + Integer.toString(deviceNumber, base);
    }
}
