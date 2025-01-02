package mc3eprotocol;

import org.junit.jupiter.api.Test;

import mc3eprotocol.define.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceSpecTest {

    @Test
    public void testConstructorWithDeviceCodeEnum() {
        DeviceCodeEnum deviceCode = DeviceCodeEnum.Input;
        int deviceNumber = 100;
        DeviceSpec deviceSpec = new DeviceSpec(deviceCode, deviceNumber);

        assertEquals(deviceCode, deviceSpec.getDeviceCode());
        assertEquals(deviceNumber, deviceSpec.getDeviceNumber());
    }

    @Test
    public void testConstructorWithStringValid() {
        DeviceSpec deviceSpec = new DeviceSpec("X100");

        assertEquals(DeviceCodeEnum.Input, deviceSpec.getDeviceCode());
        assertEquals(0x100, deviceSpec.getDeviceNumber());
    }

    @Test
    public void testConstructorWithStringInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DeviceSpec("X-100");
        });
    }

    @Test
    public void testConstructorWithStringInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DeviceSpec("U100");
        });
    }
}