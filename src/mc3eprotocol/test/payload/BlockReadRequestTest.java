package mc3eprotocol.test.payload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.lib.define.DeviceSpec;
import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.DeviceCodeEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.payload.BlockReadRequest;

public class BlockReadRequestTest {

    @Test
    public void testConstructorWithParameters() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = false;

        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);

        assertEquals(isBitDevice, request._isBitDevice);
        assertEquals(CommandEnum.BLOCK_READ, request.getCommand());
        assertEquals(SubCommandEnum.Q_WORD, request.getSubCommand());
    }

    @Test
    public void testConstructorWithByteArray() {
        byte[] requestBytes = new byte[]{
            0x01, 0x04,  // コマンド
            0x00, 0x00,  // サブコマンド
            (byte)0xC8, 0x00, 0x00,  // デバイス番号 200
            (byte)0xA8,  // デバイスコード D
            0x01, 0x00  // デバイス点数
        };
        BlockReadRequest request = new BlockReadRequest(requestBytes);

        assertEquals(CommandEnum.BLOCK_READ, request.getCommand());
        assertEquals(SubCommandEnum.Q_WORD, request.getSubCommand());
        assertEquals(DeviceCodeEnum.DataRegister, request.getDeviceSpec().getDeviceCode());
    }

    @Test
    public void testToBytes() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = false;

        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        byte[] bytes = request.toByteArray();

        assertArrayEquals(new byte[]{
                0x01, 0x04,  // コマンド
                0x00, 0x00,  // サブコマンド
                (byte)0xC8, 0x00, 0x00,  // デバイス番号
                (byte)0xA8,  // デバイスコード
                0x01, 0x00  // デバイス点数
            }, bytes
        );
    }
}