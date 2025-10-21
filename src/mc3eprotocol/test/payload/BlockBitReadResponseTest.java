package mc3eprotocol.test.payload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.lib.define.DeviceSpec;
import mc3eprotocol.lib.payload.BlockBitReadResponse;
import mc3eprotocol.lib.payload.BlockReadRequest;

class BlockBitReadResponseTest {

    @Test
    void testParseValidResponse() {
        DeviceSpec deviceSpec = new DeviceSpec("X00");
        short devicePoint = 6;
        boolean isBitDevice = true;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x10, 0x11, 0x01};
        BlockBitReadResponse response = new BlockBitReadResponse(responseData, request);

        boolean[] expectedData = {true, false, true, true, false, true};
        assertArrayEquals(expectedData, response.getReadData());
    }

    @Test
    void testParseInvalidResponse() {
        DeviceSpec deviceSpec = new DeviceSpec("X00");
        short devicePoint = 6;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x01, 0x01};

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockBitReadResponse(responseData, request);
        });
    }
}