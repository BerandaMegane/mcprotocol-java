package mc3eprotocol.payload;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class BlockWriteRequestTest {

    @Test
    public void testConstructorWithByteArray() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        byte[] request = new byte[] {
            0x01, 0x14,  // コマンド
            0x00, 0x00,  // サブコマンド
            (byte)0xC8, 0x00, 0x00,  // デバイス番号 200
            (byte)0xA8,  // デバイスコード D
            0x01, 0x00,  // デバイス点数
            0x01, 0x00  // 書込みデータ
        };
        BlockWriteRequest blockWriteRequest = new BlockWriteRequest(seriesModel, request);

        assertNotNull(blockWriteRequest);
        assertEquals(CommandEnum.BLOCK_WRITE, blockWriteRequest._command);
        assertEquals(SubCommandEnum.Q_WORD, blockWriteRequest._subCommand);
        assertArrayEquals(blockWriteRequest._writeBytes, new byte[] {0x01, 0x00});
    }

    @Test
    public void testConstructorWithParameters() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 2;
        boolean isBitDevice = false;
        byte[] writeBytes = new byte[] {0x01, 0x02, 0x03, 0x04};

        BlockWriteRequest blockWriteRequest = new BlockWriteRequest(seriesModel, deviceSpec, (short)2, writeBytes, isBitDevice);

        assertNotNull(blockWriteRequest);
        assertEquals(seriesModel, blockWriteRequest._seriesModel);
        assertEquals(deviceSpec, blockWriteRequest._deviceSpec);
        assertEquals(devicePoint, blockWriteRequest._devicePoint);
        assertArrayEquals(writeBytes, blockWriteRequest._writeBytes);
        assertEquals(isBitDevice, blockWriteRequest._isBitDevice);
        assertEquals(CommandEnum.BLOCK_WRITE, blockWriteRequest._command);
        assertEquals(SubCommandEnum.Q_WORD, blockWriteRequest._subCommand);
    }

    @Test
    public void testToBytes() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        boolean isBitDevice = false;
        byte[] writeBytes = new byte[] {0x01, 0x02};

        BlockWriteRequest blockWriteRequest = new BlockWriteRequest(seriesModel, deviceSpec, (short)2, writeBytes, isBitDevice);
        byte[] bytes = blockWriteRequest.toBytes();

        assertNotNull(bytes);
        assertEquals(12, bytes.length);
    }

}