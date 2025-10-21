package mc3eprotocol.test.define;
import org.junit.jupiter.api.Test;

import mc3eprotocol.lib.define.AccessRoute;
import mc3eprotocol.lib.util.Converter;

import static org.junit.jupiter.api.Assertions.*;

public class AccessRouteTest {

    @Test
    public void testAccessRouteWithBytes() {
        byte[] bytes = {0x00, (byte)0xff, (byte)0xff, 0x03, 0x00};

        AccessRoute route = new AccessRoute(bytes);

        assertArrayEquals(new byte[] {0x00}, route.getNetworkNumberBytes());
        assertArrayEquals(new byte[] {(byte)0xff}, route.getPlcStationNumberBytes());
        assertArrayEquals(new byte[] {(byte)0xff, 0x03}, route.getUnitIoNumberBytes());
        assertArrayEquals(new byte[] {0x00}, route.getUnitStationNumberBytes());
    }

    @Test
    public void testToBytes() {
        byte[] bytes = {0x00, (byte)0xff, (byte)0xff, 0x03, 0x00};

        AccessRoute route = new AccessRoute(bytes);

        byte[] expectedBytes = Converter.concatByteArrays(
            new byte[] {0x00},
            new byte[] {(byte)0xff},
            new byte[] {(byte)0xff, 0x03},
            new byte[] {0x00}
        );

        assertArrayEquals(expectedBytes, route.toByteArray());
    }

    @Test
    public void testInvalidByteArrayLength() {
        byte[] invalidBytes = {0x01, 0x02, 0x03, 0x04};

        assertThrows(IllegalArgumentException.class, () -> {
            new AccessRoute(invalidBytes);
        });
    }

    @Test
    public void testDefaultAccessRoute() {
        AccessRoute route = AccessRoute.DEFAULT;

        assertArrayEquals(new byte[]{(byte)0x00}, route.getNetworkNumberBytes());
        assertArrayEquals(new byte[]{(byte)0xFF}, route.getPlcStationNumberBytes());
        assertArrayEquals(new byte[]{(byte)0xFF, 0x03}, route.getUnitIoNumberBytes());
        assertArrayEquals(new byte[]{(byte)0x00}, route.getUnitStationNumberBytes());
    }

    public static void main(String[] args) {
        System.out.println("AccessRouteTest");
    }
}
