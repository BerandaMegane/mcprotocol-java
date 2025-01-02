package mc3eprotocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.function.Function;

public class Utility {
    /**
     * byte[] の連結
     * @param arrays
     * @return
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
     * hexString -> byte[]
     * @param hexString
     * @param length
     * @return
     */
    public static byte[] fromHexStringToBytes(String hexString, int length) {
        int value = Integer.parseInt(hexString, 16);
        return fromIntToBytes(value, length);
    }

    /**
     * int -> byte[]
     * @param value
     * @param length
     * @return
     */
    public static byte[] fromIntToBytes(int value, int length) {
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
     * byte[] -> int
     * @param bytes
     * @return
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
     * byte[] -> hexString (little endian)
     * @param bytes
     * @return
     */
    public static String fromBytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length-1; i >= 0; i--) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    /**
     * byte[] -> HexString (big endian)
     * @param bytes
     * @return
     */
    public static String fromBytesToHexStringBigEndian(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    /**
     * byte[] -> short[] (little endian)
     * @param bytes
     * @return
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
     * short[] -> byte[] (little endian)
     * @param short[]
     * @return
     */
    public static byte[] fromShortArrayToBytes(short[] values) {
        ByteBuffer buffer = ByteBuffer.allocate(values.length * 2).order(ByteOrder.LITTLE_ENDIAN);
        for (short value : values) {
            buffer.putShort(value);
        }
        return buffer.array();
    }

    /**
     * byte[] -> 2進数文字列
     * @param byte[]
     * @param isLittleEndian
     * @return
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

    // ヒアドキュメント
    public static String hereDoc(Function<String, String> trimmer, String eol, String... strs) {
		StringBuilder sb = new StringBuilder();
		for (String s : strs) {
			sb.append(trimmer == null ? s : trimmer.apply(s)); 
			sb.append(eol == null ? "" : eol);
		}
		return sb.toString();
	}

    public static void main(String[] args) {
        int value = 0x12345678;  // 305419896 
        int length = 4;
        byte[] expected1 = {0x78, 0x56, 0x34, 0x12};

        byte[] result1 = Utility.fromIntToBytes(value, length);
        System.out.println(Arrays.equals(expected1, result1));
    }

}