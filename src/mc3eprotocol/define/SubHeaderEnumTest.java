package mc3eprotocol.define;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SubHeaderEnumTest {

    @Test
    public void testGetHexString() {
        assertEquals("0050", SubHeaderEnum.REQUEST.getHexString());
        assertEquals("00D0", SubHeaderEnum.RESPONSE.getHexString());
    }

    @Test
    public void testGetBytes() {
        assertArrayEquals(new byte[]{0x50, 0x00}, SubHeaderEnum.REQUEST.getBytes());
        assertArrayEquals(new byte[]{(byte) 0xD0, 0x00}, SubHeaderEnum.RESPONSE.getBytes());
    }

    @Test
    public void testEquals() {
        assertTrue( SubHeaderEnum.REQUEST .equals(new byte[]{(byte) 0x50, 0x00}));
        assertTrue( SubHeaderEnum.RESPONSE.equals(new byte[]{(byte) 0xD0, 0x00}));
        assertFalse(SubHeaderEnum.REQUEST .equals(new byte[]{0x00, 0x50}));
        assertFalse(SubHeaderEnum.RESPONSE.equals(new byte[]{0x00, (byte) 0xD0}));
    }
}
