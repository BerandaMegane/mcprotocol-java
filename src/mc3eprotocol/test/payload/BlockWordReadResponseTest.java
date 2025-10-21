package mc3eprotocol.test.payload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.lib.define.DeviceSpec;
import mc3eprotocol.lib.payload.BlockReadRequest;
import mc3eprotocol.lib.payload.BlockWordReadResponse;

class BlockWordReadResponseTest {

    @Test
    void testParseValidResponse() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x00, 0x02, 0x00};
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        short[] expectedData = {1, 2};
        assertArrayEquals(expectedData, response.getReadData());
    }

    @Test
    void testParseInvalidResponse() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = true;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x00, 0x02, 0x00};

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockWordReadResponse(responseData, request);
        });
    }
}