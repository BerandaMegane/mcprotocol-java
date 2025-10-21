package mc3eprotocol.test.frame;

import org.junit.jupiter.api.Test;

import mc3eprotocol.lib.define.AccessRoute;
import mc3eprotocol.lib.define.SubHeaderEnum;
import mc3eprotocol.lib.frame.Frame3E;

import static org.junit.jupiter.api.Assertions.*;

class Frame3ETest {

    @Test
    void testToBytes() {
        SubHeaderEnum subHeader = SubHeaderEnum.REQUEST; // Replace with actual value
        AccessRoute accessRoute = AccessRoute.DEFAULT; // Initialize with actual values
        short monitorOrFinishCode = 0x1234;
        byte[] data = {0x01, 0x02, 0x03, 0x04};

        Frame3E frame = new Frame3E(subHeader, accessRoute, monitorOrFinishCode, data);
        byte[] bytes = frame.toBytes();

        assertNotNull(bytes);
        assertEquals(2 + data.length + subHeader.getBytes().getByteArray().length + accessRoute.toByteArray().length + 2, bytes.length);
    }

    @Test
    void testIsNormalFinish() {
        Frame3E frame = new Frame3E();
        frame._monitorOrFinishCode = 0x0000;
        assertTrue(frame.isNormalFinish());

        frame._monitorOrFinishCode = 0x1234;
        assertFalse(frame.isNormalFinish());
    }
}