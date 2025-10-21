package mc3eprotocol.lib.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 各種型変換を行うユーティリティクラスです。
 */
public class Converter {

    /**
     * 複数のbyte配列を連結します。
     * @param arrays 連結するbyte配列群
     * @return 連結されたbyte配列
     */
    public static byte[] concatByteArrays(byte[]... arrays) {
        if (arrays.length <= 1) {
            return arrays[0];
        }
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        for (byte[] array : arrays) {
            buffer.put(array);
        }
        return buffer.array();
    }

    /**
     * 16進数文字列をbyte配列に変換します。
     * @param hexString 16進数文字列
     * @param length 生成するbyte配列の長さ
     * @return 変換されたbyte配列
     */
    public static byte[] fromHexStringToBytes(String hexString, int length) {
        int value = Integer.parseInt(hexString, 16);
        return fromIntToByteArray(value, length);
    }

    /**
     * int値を指定した長さのbyte配列（リトルエンディアン）に変換します。
     * @param value int値
     * @param length 生成するbyte配列の長さ
     * @return 変換されたbyte配列
     */
    public static byte[] fromIntToByteArray(int value, int length) {
        byte[] bytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            if (i < bytes.length) {
                result[i] = bytes[i];
            } else {
                result[i] = 0;
            }
        }
        return result;
    }

    /**
     * byte配列（リトルエンディアン）をint値に変換します。
     * @param bytes 変換するbyte配列
     * @return 変換されたint値
     * @throws IllegalArgumentException 配列長が1～4以外の場合
     */
    public static int fromBytesToInt(byte[] bytes) {
        switch (bytes.length) {
            case 1:
                return bytes[0];
            case 2:
                return (bytes[1] & 0xff) << 8 | (bytes[0] & 0xff);
            case 3:
                return (bytes[2] & 0xff) << 16 | (bytes[1] & 0xff) << 8 | (bytes[0] & 0xff);
            case 4:
                return (bytes[3] & 0xff) << 24 | (bytes[2] & 0xff) << 16 | (bytes[1] & 0xff) << 8 | (bytes[0] & 0xff);
            default:
                throw new IllegalArgumentException("Invalid length: " + bytes.length);
        }
    }
    
    /**
     * byte配列（リトルエンディアン）を16進数文字列に変換します。
     * @param bytes 変換するbyte配列
     * @return 16進数文字列
     */
    public static String fromBytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length-1; i >= 0; i--) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    /**
     * byte配列（ビッグエンディアン）を16進数文字列に変換します。
     * @param bytes 変換するbyte配列
     * @return 16進数文字列
     */
    public static String fromBytesToHexStringBigEndian(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    /**
     * byte配列（リトルエンディアン）をshort配列に変換します。
     * @param bytes 変換するbyte配列
     * @return 変換されたshort配列
     * @throws IllegalArgumentException 配列長が偶数でない場合
     */
    public static short[] fromBytesToShortArray(byte[] bytes) {
        if (bytes.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid length: " + bytes.length);
        }
        short[] result = new short[bytes.length / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = (short) ((bytes[i * 2 + 1] & 0xff) << 8 | (bytes[i * 2] & 0xff));
        }
        return result;
    }

    /**
     * short配列（リトルエンディアン）をbyte配列に変換します。
     * @param values short配列
     * @return 変換されたbyte配列
     */
    public static byte[] fromShortArrayToBytes(short[] values) {
        ByteBuffer buffer = ByteBuffer.allocate(values.length * 2).order(ByteOrder.LITTLE_ENDIAN);
        for (short value : values) {
            buffer.putShort(value);
        }
        return buffer.array();
    }

    /**
     * byte配列を2進数文字列に変換します。
     * @param bytes 変換するbyte配列
     * @param isLittleEndian リトルエンディアンの場合true
     * @return 2進数文字列
     */
    public static String fromBytesToBinaryString(byte[] bytes, boolean isLittleEndian) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (isLittleEndian) {
                for (int j = 0; j < 8; j++) {
                    sb.append((bytes[i] >> j) & 0x01);
                }
            } else {
                for (int j = 7; j >= 0; j--) {
                    sb.append((bytes[i] >> j) & 0x01);
                }
            }
        }
        return sb.toString();
    }

    /**
     * ヒアドキュメントを生成します。
     * @param trimmer 行ごとのトリム関数
     * @param eol 行末文字列
     * @param strs 行データ
     * @return 結合された文字列
     */
    public static String hereDoc(Function<String, String> trimmer, String eol, String... strs) {
		StringBuilder sb = new StringBuilder();
		for (String s : strs) {
			sb.append(trimmer == null ? s : trimmer.apply(s)); 
			sb.append(eol == null ? "" : eol);
		}
		return sb.toString();
	}

    /**
     * 動作確認用メインメソッドです。
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        int value = 0x12345678;  // 305419896 
        int length = 4;
        byte[] expected1 = {0x78, 0x56, 0x34, 0x12};

        byte[] result1 = Converter.fromIntToByteArray(value, length);
        System.out.println(Arrays.equals(expected1, result1));
    }
}
