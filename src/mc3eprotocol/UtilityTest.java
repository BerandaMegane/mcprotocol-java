package mc3eprotocol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    public void testConcatByteArrays() {
        // 複数のバイト配列を連結するテスト
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {4, 5, 6};
        byte[] array3 = {7, 8, 9};
        byte[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        byte[] result = Utility.concatByteArrays(array1, array2, array3);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testConcatByteArraysSingleArray() {
        // 単一のバイト配列を連結するテスト
        byte[] array1 = {1, 2, 3};
        byte[] expected = {1, 2, 3};

        byte[] result = Utility.concatByteArrays(array1);
        assertArrayEquals(expected, result);
    }

    // リトルエンディアン byte[] に変換 4 bytes
    @Test
    public void testToLittleEndianBytesInt_len4_0() {
        int value = 305419896; // 0x12345678
        int length = 4;
        byte[] expected = {0x78, 0x56, 0x34, 0x12};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToLittleEndianBytesInt_len4_1() {
        int value = 0x123456; // 1193046
        int length = 4;
        byte[] expected = {0x56, 0x34, 0x12, 0x00};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToLittleEndianBytesInt_len4_2() {
        int value = 0x1234; // 4660
        int length = 4;
        byte[] expected = {0x34, 0x12, 0x00, 0x00};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToLittleEndianBytesInt_len4_3() {
        int value = 0x12; // 18
        int length = 4;
        byte[] expected = {0x12, 0x00, 0x00, 0x00};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToLittleEndianBytesInt_len4_4() {
        int value = 0;
        int length = 4;
        byte[] expected = {0, 0, 0, 0};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    // リトルエンディアン byte[] に変換 3 bytes
    @Test
    public void testToLittleEndianBytesInt_len3_0() {
        int value = 0x123456; // 1193046
        int length = 3;
        byte[] expected = {0x56, 0x34, 0x12};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToLittleEndianBytesInt_len3_1() {
        int value = 0x1234; // 4660
        int length = 3;
        byte[] expected = {0x34, 0x12, 0x00};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }
    
    @Test
    public void testToLittleEndianBytesInt_len3_2() {
        int value = 0x12; // 18
        int length = 3;
        byte[] expected = {0x12, 0x00, 0x00};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testToLittleEndianBytesInt_len3_3() {
        int value = 0;
        int length = 3;
        byte[] expected = {0, 0, 0};

        byte[] result = Utility.fromIntToBytes(value, length);
        assertArrayEquals(expected, result);
    }
}
