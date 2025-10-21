package mc3eprotocol.test.payload;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import mc3eprotocol.lib.define.DeviceSpec;
import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.payload.BlockWriteRequest;

public class BlockWriteRequestTest {

    @Test
    public void testConstructorWithByteArray() {
        byte[] request = new byte[] {
            0x01, 0x14,  // コマンド
            0x00, 0x00,  // サブコマンド
            (byte)0xC8, 0x00, 0x00,  // デバイス番号 200
            (byte)0xA8,  // デバイスコード D
            0x01, 0x00,  // デバイス点数
            0x01, 0x00  // 書込みデータ
        };
        BlockWriteRequest blockWriteRequest = new BlockWriteRequest(request);

        assertNotNull(blockWriteRequest);
        assertEquals(CommandEnum.BLOCK_WRITE, blockWriteRequest.getCommand());
        assertEquals(SubCommandEnum.Q_WORD, blockWriteRequest.getSubCommand());
        assertArrayEquals(blockWriteRequest.getWriteBytes(), new byte[] {0x01, 0x00});
    }

    @Test
    public void testConstructorWithParameters() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 2;
        boolean isBitDevice = false;
        byte[] writeBytes = new byte[] {0x01, 0x02, 0x03, 0x04};

        BlockWriteRequest blockWriteRequest = new BlockWriteRequest(deviceSpec, (short)2, writeBytes, isBitDevice);

        assertNotNull(blockWriteRequest);
        assertEquals(deviceSpec, blockWriteRequest.getDeviceSpec());
        assertEquals(devicePoint, blockWriteRequest.getDevicePoint());
        assertArrayEquals(writeBytes, blockWriteRequest.getWriteBytes());
        assertEquals(isBitDevice, blockWriteRequest.isBitDevice());
        assertEquals(CommandEnum.BLOCK_WRITE, blockWriteRequest.getCommand());
        assertEquals(SubCommandEnum.Q_WORD, blockWriteRequest.getSubCommand());
    }

    @Test
    public void testToBytes() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        boolean isBitDevice = false;
        byte[] writeBytes = new byte[] {0x01, 0x02};

        BlockWriteRequest blockWriteRequest = new BlockWriteRequest(deviceSpec, (short)2, writeBytes, isBitDevice);
        byte[] bytes = blockWriteRequest.toByteArray();

        assertNotNull(bytes);
        assertEquals(12, bytes.length);
    }

}