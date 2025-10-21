package mc3eprotocol.test.define;

import org.junit.jupiter.api.Test;

import mc3eprotocol.lib.define.DataTypeEnum;
import mc3eprotocol.lib.define.DeviceCodeEnum;

import static org.junit.jupiter.api.Assertions.*;


public class DeviceCodeEnumTest {

    @Test
    public void testGetDataType() {
        assertEquals(DataTypeEnum.BIT, DeviceCodeEnum.SpecialRelay.getDataType());
        assertEquals(DataTypeEnum.WORD, DeviceCodeEnum.SpecialRegister.getDataType());
        assertEquals(DataTypeEnum.BIT, DeviceCodeEnum.Input.getDataType());
        assertEquals(DataTypeEnum.BIT, DeviceCodeEnum.Output.getDataType());
    }

    @Test
    public void testGetCode() {
        assertEquals("SM", DeviceCodeEnum.SpecialRelay.getDeviceSymbol());
        assertEquals("SD", DeviceCodeEnum.SpecialRegister.getDeviceSymbol());
        assertEquals("X", DeviceCodeEnum.Input.getDeviceSymbol());
        assertEquals("Y", DeviceCodeEnum.Output.getDeviceSymbol());
    }

    @Test
    public void testGetHexString() throws Exception {
        assertEquals("91", DeviceCodeEnum.SpecialRelay.getBytes().getHexString());
        assertEquals("A9", DeviceCodeEnum.SpecialRegister.getBytes().getHexString());
        assertEquals("9C", DeviceCodeEnum.Input.getBytes().getHexString());
        assertEquals("9D", DeviceCodeEnum.Output.getBytes().getHexString());
    }


    @Test
    public void testGetByteArray() throws Exception {
        assertArrayEquals(new byte[]{(byte) 0x91}, DeviceCodeEnum.SpecialRelay.getBytes().getByteArray());
        assertArrayEquals(new byte[]{(byte) 0xA9}, DeviceCodeEnum.SpecialRegister.getBytes().getByteArray());
    }

    @Test
    public void testGetDeviceNumberBase() throws Exception {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values() ) {
            
            int expected = switch (deviceCode) {
                case Input:
                case Output:
                case LinkRelay:
                case LinkRegister:
                case LinkSpecialRelay:
                case LinkSpecialRegister:
                case DirectAccessInput:
                case DirectAccessOutput:
                case FileRegisterSerialNumberAccessMethod:
                case ExtendedLinkRegister:
                    yield 16;
                default:
                    yield 10;
            };
            assertEquals(expected, deviceCode.getDeviceNumberBase());
        }
    }
}