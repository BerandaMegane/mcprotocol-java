package mc3eprotocol.test.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.lib.util.Bytes;

class BytesTest {

    @Test
    public void testConstructorWithByteArray() {
        byte[] arr = {0x12, 0x34};
        Bytes bytes = new Bytes(arr);
        assertArrayEquals(arr, bytes.getByteArray());
        assertEquals("3412", bytes.getHexString());
    }

    @Test
    public void testConstructorWithHexString() {
        Bytes bytes = new Bytes("3412");
        assertArrayEquals(new byte[]{0x12, 0x34}, bytes.getByteArray());
        assertEquals("3412", bytes.getHexString());
    }

    @Test
    public void testGetLength() {
        Bytes bytes = new Bytes(new byte[]{0x01, 0x02, 0x03});
        assertEquals(3, bytes.getLength());
    }

    @Test
    public void testEqualsByteArray() {
        Bytes bytes = new Bytes(new byte[]{0x01, 0x02});
        assertTrue(bytes.equals(new byte[]{0x01, 0x02}));
        assertFalse(bytes.equals(new byte[]{0x01, 0x03}));
    }

    @Test
    public void testEqualsBytes() {
        Bytes a = new Bytes(new byte[]{0x01, 0x02});
        Bytes b = new Bytes(new byte[]{0x01, 0x02});
        Bytes c = new Bytes(new byte[]{0x02, 0x01});
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
    }

    @Test
    public void testStaticEqualsBytes() {
        Bytes a = new Bytes(new byte[]{0x01, 0x02});
        Bytes b = new Bytes(new byte[]{0x01, 0x02});
        Bytes c = new Bytes(new byte[]{0x02, 0x01});
        assertTrue(Bytes.equals(a, b));
        assertFalse(Bytes.equals(a, c));
    }

    @Test
    public void testStaticEqualsByteArray() {
        assertTrue(Bytes.equals(new byte[]{0x01, 0x02}, new byte[]{0x01, 0x02}));
        assertFalse(Bytes.equals(new byte[]{0x01, 0x02}, new byte[]{0x02, 0x01}));
    }
}
