package mc3eprotocol;
import mc3eprotocol.define.*;

public class ClientSample {
    public static void main(String[] args) {
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();
            
            System.out.println("D200 に 512 を書き込んで読み込むテスト");
            client.setDeviceValue2("D200", (short)512);
            client.printDeviceValue2("D200");

            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeAndPrint() {
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();
            
            System.out.println("D200 に 512 を書き込んで読み込むテスト");
            client.setDeviceValue2("D200", (short)512);
            client.printDeviceValue2("D200");

            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeAndPrint2() {
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();

            System.out.println("D200, D201 に 3, 2 を書き込んで読み込むテスト");
            client.setBlockWordWrite(new DeviceSpec("D200"), new short[]{0x0003, 0x0002});
            client.printDeviceValue2("D200");
            client.printDeviceValue2("D201");

            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void readAndPrint3() {
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();

            System.out.println("一括読込み（複数ワード）のテスト");
            DeviceSpec deviceSpec = new DeviceSpec("D200");
            short[] writeValues = new short[]{0x1003, 0x0002};
            client.setBlockWordWrite(deviceSpec, writeValues);

            short[] readValues = client.getBlockWordRead(deviceSpec, (short)2);
            for (short value : readValues) {
                System.out.println(value);
            }
            
            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }    
    }

    public static void readAndPrint4() {
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();

            System.out.println("一括読込み（複数ビット）のテスト");
            DeviceSpec deviceSpec = new DeviceSpec("M512");
            short[] writeValues = new short[]{0x4321, (short)0x8765};
            client.setBlockWordWrite(deviceSpec, writeValues);

            short[] readValues = client.getBlockWordRead(deviceSpec, (short)1);
            System.out.println(Utility.fromBytesToBinaryString(Utility.fromShortArrayToBytes(readValues), false));
            
            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void printPlcTypeName() {
        // Q03UDV CPU では動かない
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();

            System.out.println("PLC 形名を読み取るテスト");
            System.out.println("PLC TypeName: " + client.readPlcTypeName());
            
            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void remoteStop() {
        // Q03UDV CPU では動かない
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();

            System.out.println("PLC リモート STOP テスト");
            client.remoteStop();
            
            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void remoteRun() {
        // Q03UDV CPU では動かない
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);
        try {
            client.connect();

            System.out.println("PLC リモート RUN テスト");
            client.remoteRun();
            
            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void returnEchoTest() {
        // 環境に合わせて書き換える
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES);

        try {
            client.connect();
            
            // 折返しテスト
            client.echoTest("Hello".getBytes());

            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
