package mc3eprotocol.payload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class BlockReadRequestTest {

    @Test
    public void testConstructorWithParameters() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = false;

        BlockReadRequest request = new BlockReadRequest(seriesModel, deviceSpec, devicePoint, isBitDevice);

        assertEquals(isBitDevice, request._isBitDevice);
        assertEquals(CommandEnum.BLOCK_READ, request._command);
        assertEquals(SubCommandEnum.Q_WORD, request._subCommand);
    }

    @Test
    public void testConstructorWithByteArray() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        byte[] requestBytes = new byte[]{
            0x01, 0x04,  // コマンド
            0x00, 0x00,  // サブコマンド
            (byte)0xC8, 0x00, 0x00,  // デバイス番号 200
            (byte)0xA8,  // デバイスコード D
            0x01, 0x00  // デバイス点数
        };
        BlockReadRequest request = new BlockReadRequest(seriesModel, requestBytes);

        assertEquals(CommandEnum.BLOCK_READ, request._command);
        assertEquals(SubCommandEnum.Q_WORD, request._subCommand);
        assertEquals(DeviceCodeEnum.DataRegister, request._deviceSpec.getDeviceCode());
    }

    @Test
    public void testToBytes() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = false;

        BlockReadRequest request = new BlockReadRequest(seriesModel, deviceSpec, devicePoint, isBitDevice);
        byte[] bytes = request.toBytes();

        assertArrayEquals(new byte[]{
                0x01, 0x04,  // コマンド
                0x00, 0x00,  // サブコマンド
                (byte)0xC8, 0x00, 0x00,  // デバイス番号
                (byte)0xA8,  // デバイスコード
                0x01, 0x00  // デバイス点数
            }, bytes
        );
    }


    @Test
    public void testParseResponseUnsupportedOperationException() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("M200");
        short devicePoint = 1;
        boolean isBitDevice = true;

        BlockReadRequest request = new BlockReadRequest(seriesModel, deviceSpec, devicePoint, isBitDevice);
        byte[] responseBytes = new byte[]{0x00, 0x00, 0x00, 0x01, 0x02, 0x03, 0x04};

        assertThrows(UnsupportedOperationException.class, () -> {
            request.parseResponse(responseBytes);
        });
    }
}