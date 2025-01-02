package mc3eprotocol.frame;

import java.nio.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import mc3eprotocol.define.*;

public class ParserTest {

    @Test
    public void testParseFrame() {

        byte[] bytes = new byte[] { 
            // サブヘッダ
            (byte)0xD0, 0x00, 
            // アクセス経路
            0x00, (byte)0xFF, (byte)0xFF, 0x03, 0x00, 
            // データ長
            0x04, 0x00, 
            // 監視タイマー/終了コード
            0x00, 0x00, 
            // データ
            0x01, 0x02 
        };

        Frame3E frame = Parser.parseFrame(bytes);
        Parser.printInfo(frame.toBytes());

        assertEquals(SubHeaderEnum.RESPONSE, frame._subHeader);
        assertNotNull(frame._accessRoute);
        assertEquals(4, frame._uncheckedDataLength);
        assertEquals(0, frame._monitorOrFinishCode);
        assertArrayEquals(new byte[] { 0x01, 0x02 }, frame._payload);
    }

    @Test
    public void testParseSubHeader() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] { 0x50, 0x00 });
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        SubHeaderEnum subHeader = Parser.parseSubHeader(buffer);

        assertEquals(SubHeaderEnum.REQUEST, subHeader);
    }

    @Test
    public void testFetchData() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] { 0x01, 0x02 });
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        byte[] data = Parser.fetchData(buffer);

        assertArrayEquals(new byte[] { 0x01, 0x02 }, data);
    }

    @Test
    public void testPrintInfo() {
        byte[] bytes = new byte[] { 
            // サブヘッダ
            (byte)0xD0, 0x00, 
            // アクセス経路
            0x00, (byte)0xFF, (byte)0xFF, 0x03, 0x00, 
            // データ長
            0x02, 0x00, 
            // 監視タイマー/終了コード
            0x00, 0x00, 
            // データ
            0x01, 0x02 
        };

        Parser.printInfo(bytes);
    }

    public static void main(String[] args) {
        ParserTest test = new ParserTest();
        test.testPrintInfo();
    }
}
