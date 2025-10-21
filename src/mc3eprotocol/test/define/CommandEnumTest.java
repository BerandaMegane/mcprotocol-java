package mc3eprotocol.test.define;

import org.junit.jupiter.api.Test;

import mc3eprotocol.lib.define.CommandEnum;

import static org.junit.jupiter.api.Assertions.*;


public class CommandEnumTest {

    @Test
    public void testGetHexString() {
        assertEquals("0401", CommandEnum.BLOCK_READ.getBytes().getHexString());
        assertEquals("1401", CommandEnum.BLOCK_WRITE.getBytes().getHexString());
        assertEquals("0403", CommandEnum.RANDOM_READ.getBytes().getHexString());
        assertEquals("1402", CommandEnum.RANDOM_WRITE.getBytes().getHexString());
        assertEquals("0406", CommandEnum.MULTI_BLOCK_READ.getBytes().getHexString());
        assertEquals("1406", CommandEnum.MULTI_BLOCK_WRITE.getBytes().getHexString());
        assertEquals("0801", CommandEnum.MONITOR_REGISTER.getBytes().getHexString());
        assertEquals("0802", CommandEnum.MONITOR_READ.getBytes().getHexString());
        assertEquals("1001", CommandEnum.REMOTE_RUN.getBytes().getHexString());
        assertEquals("1002", CommandEnum.REMOTE_STOP.getBytes().getHexString());
        assertEquals("1003", CommandEnum.REMOTE_PAUSE.getBytes().getHexString());
        assertEquals("1005", CommandEnum.REMOTE_LATCH_CLEAR.getBytes().getHexString());
        assertEquals("1006", CommandEnum.REMOTE_RESET.getBytes().getHexString());
        assertEquals("0101", CommandEnum.CPU_TYPE_READ.getBytes().getHexString());
    }

    @Test
    public void testGetBytes() {
        assertArrayEquals(new byte[]{0x01, 0x04}, CommandEnum.BLOCK_READ.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x01, 0x14}, CommandEnum.BLOCK_WRITE.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x03, 0x04}, CommandEnum.RANDOM_READ.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x02, 0x14}, CommandEnum.RANDOM_WRITE.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x06, 0x04}, CommandEnum.MULTI_BLOCK_READ.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x06, 0x14}, CommandEnum.MULTI_BLOCK_WRITE.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x01, 0x08}, CommandEnum.MONITOR_REGISTER.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x02, 0x08}, CommandEnum.MONITOR_READ.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x01, 0x10}, CommandEnum.REMOTE_RUN.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x02, 0x10}, CommandEnum.REMOTE_STOP.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x03, 0x10}, CommandEnum.REMOTE_PAUSE.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x05, 0x10}, CommandEnum.REMOTE_LATCH_CLEAR.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x06, 0x10}, CommandEnum.REMOTE_RESET.getBytes().getByteArray());
        assertArrayEquals(new byte[]{0x01, 0x01}, CommandEnum.CPU_TYPE_READ.getBytes().getByteArray());
    }

    @Test
    public void testEquals() {
        assertTrue(CommandEnum.BLOCK_READ.equals(new byte[]{0x01, 0x04}));
        assertFalse(CommandEnum.BLOCK_READ.equals(new byte[]{0x01, 0x14}));
    }
}
