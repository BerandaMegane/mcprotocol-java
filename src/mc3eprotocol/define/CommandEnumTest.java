package mc3eprotocol.define;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CommandEnumTest {

    @Test
    public void testGetHexString() {
        assertEquals("0401", CommandEnum.BLOCK_READ.getHexString());
        assertEquals("1401", CommandEnum.BLOCK_WRITE.getHexString());
        assertEquals("0403", CommandEnum.RANDOM_READ.getHexString());
        assertEquals("1402", CommandEnum.RANDOM_WRITE.getHexString());
        assertEquals("0406", CommandEnum.MULTI_BLOCK_READ.getHexString());
        assertEquals("1406", CommandEnum.MULTI_BLOCK_WRITE.getHexString());
        assertEquals("0801", CommandEnum.MONITOR_REGISTER.getHexString());
        assertEquals("0802", CommandEnum.MONITOR_READ.getHexString());
        assertEquals("1001", CommandEnum.REMOTE_RUN.getHexString());
        assertEquals("1002", CommandEnum.REMOTE_STOP.getHexString());
        assertEquals("1003", CommandEnum.REMOTE_PAUSE.getHexString());
        assertEquals("1005", CommandEnum.REMOTE_LATCH_CLEAR.getHexString());
        assertEquals("1006", CommandEnum.REMOTE_RESET.getHexString());
        assertEquals("0101", CommandEnum.CPU_TYPE_READ.getHexString());
    }

    @Test
    public void testGetBytes() {
        assertArrayEquals(new byte[]{0x01, 0x04}, CommandEnum.BLOCK_READ.getBytes());
        assertArrayEquals(new byte[]{0x01, 0x14}, CommandEnum.BLOCK_WRITE.getBytes());
        assertArrayEquals(new byte[]{0x03, 0x04}, CommandEnum.RANDOM_READ.getBytes());
        assertArrayEquals(new byte[]{0x02, 0x14}, CommandEnum.RANDOM_WRITE.getBytes());
        assertArrayEquals(new byte[]{0x06, 0x04}, CommandEnum.MULTI_BLOCK_READ.getBytes());
        assertArrayEquals(new byte[]{0x06, 0x14}, CommandEnum.MULTI_BLOCK_WRITE.getBytes());
        assertArrayEquals(new byte[]{0x01, 0x08}, CommandEnum.MONITOR_REGISTER.getBytes());
        assertArrayEquals(new byte[]{0x02, 0x08}, CommandEnum.MONITOR_READ.getBytes());
        assertArrayEquals(new byte[]{0x01, 0x10}, CommandEnum.REMOTE_RUN.getBytes());
        assertArrayEquals(new byte[]{0x02, 0x10}, CommandEnum.REMOTE_STOP.getBytes());
        assertArrayEquals(new byte[]{0x03, 0x10}, CommandEnum.REMOTE_PAUSE.getBytes());
        assertArrayEquals(new byte[]{0x05, 0x10}, CommandEnum.REMOTE_LATCH_CLEAR.getBytes());
        assertArrayEquals(new byte[]{0x06, 0x10}, CommandEnum.REMOTE_RESET.getBytes());
        assertArrayEquals(new byte[]{0x01, 0x01}, CommandEnum.CPU_TYPE_READ.getBytes());
    }

    @Test
    public void testEquals() {
        assertTrue(CommandEnum.BLOCK_READ.equals(new byte[]{0x01, 0x04}));
        assertFalse(CommandEnum.BLOCK_READ.equals(new byte[]{0x01, 0x14}));
    }
}
