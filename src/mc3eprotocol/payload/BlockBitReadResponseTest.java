package mc3eprotocol.payload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

class BlockBitReadResponseTest {

    @Test
    void testParseValidResponse() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("X00");
        short devicePoint = 6;
        boolean isBitDevice = true;
        BlockReadRequest request = new BlockReadRequest(seriesModel, deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x10, 0x11, 0x01};
        BlockBitReadResponse response = new BlockBitReadResponse(responseData, request);

        boolean[] expectedData = {true, false, true, true, false, true};
        assertArrayEquals(expectedData, response.getReadData());
    }

    @Test
    void testParseInvalidResponse() {
        SeriesModelEnum seriesModel = SeriesModelEnum.Q_SERIES;
        DeviceSpec deviceSpec = new DeviceSpec("X00");
        short devicePoint = 6;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(seriesModel, deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x01, 0x01};

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockBitReadResponse(responseData, request);
        });
    }
}