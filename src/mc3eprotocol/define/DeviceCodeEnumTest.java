package mc3eprotocol.define;

import org.junit.jupiter.api.Test;
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
    public void testGetQ_LSeriesHexString() throws Exception {
        assertEquals("91", DeviceCodeEnum.SpecialRelay.toQ_SeriesHexString());
        assertEquals("A9", DeviceCodeEnum.SpecialRegister.toQ_SeriesHexString());
        assertEquals("9C", DeviceCodeEnum.Input.toQ_SeriesHexString());
        assertEquals("9D", DeviceCodeEnum.Output.toQ_SeriesHexString());
    }

    @Test
    public void testGetIQ_RSeriesHexString() throws Exception {
        assertEquals("0091", DeviceCodeEnum.SpecialRelay.toIQSeriesHexString());
        assertEquals("00A9", DeviceCodeEnum.SpecialRegister.toIQSeriesHexString());
        assertEquals("009C", DeviceCodeEnum.Input.toIQSeriesHexString());
        assertEquals("009D", DeviceCodeEnum.Output.toIQSeriesHexString());
    }

    @Test
    public void testGetQ_LSeriesBytes() throws Exception {
        assertArrayEquals(new byte[]{(byte) 0x91}, DeviceCodeEnum.SpecialRelay.toQSeriesBytes());
        assertArrayEquals(new byte[]{(byte) 0xA9}, DeviceCodeEnum.SpecialRegister.toQSeriesBytes());
    }

    @Test
    public void testGetIQ_RSeriesBytes() throws Exception {
        assertArrayEquals(new byte[]{(byte) 0x91, 0x00}, DeviceCodeEnum.SpecialRelay.toIQSeriesBytes());
        assertArrayEquals(new byte[]{(byte) 0xA9, 0x00}, DeviceCodeEnum.SpecialRegister.toIQSeriesBytes());
    }

    @Test
    public void testGetBytes() throws Exception {
        assertArrayEquals(new byte[]{(byte) 0x91}, DeviceCodeEnum.SpecialRelay.toBytes(SeriesModelEnum.Q_SERIES));
        assertArrayEquals(new byte[]{(byte) 0x91, 0x00}, DeviceCodeEnum.SpecialRelay.toBytes(SeriesModelEnum.IQ_SERIES));
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