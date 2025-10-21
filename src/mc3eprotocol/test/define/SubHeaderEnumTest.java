package mc3eprotocol.test.define;

import org.junit.jupiter.api.Test;

import mc3eprotocol.lib.define.SubHeaderEnum;

import static org.junit.jupiter.api.Assertions.*;

public class SubHeaderEnumTest {

    @Test
    public void testGetHexString() {
        assertEquals("0050", SubHeaderEnum.REQUEST.getBytes().getHexString());
        assertEquals("00D0", SubHeaderEnum.RESPONSE.getBytes().getHexString());
    }

    @Test
    public void testGetBytes() {
        assertArrayEquals(new byte[]{0x50, 0x00}, SubHeaderEnum.REQUEST.getBytes().getByteArray());
        assertArrayEquals(new byte[]{(byte) 0xD0, 0x00}, SubHeaderEnum.RESPONSE.getBytes().getByteArray());
    }

    @Test
    public void testEquals() {
        assertTrue( SubHeaderEnum.REQUEST .equals(new byte[]{(byte) 0x50, 0x00}));
        assertTrue( SubHeaderEnum.RESPONSE.equals(new byte[]{(byte) 0xD0, 0x00}));
        assertFalse(SubHeaderEnum.REQUEST .equals(new byte[]{0x00, 0x50}));
        assertFalse(SubHeaderEnum.RESPONSE.equals(new byte[]{0x00, (byte) 0xD0}));
    }
}
