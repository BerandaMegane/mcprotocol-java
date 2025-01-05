package mc3eprotocol.sample;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class ClientSample {
    public static void main(String[] args) {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }
        
        // ここに書く
        
        client.disconnect();
    }

    /**
     * ワードデバイス 1ワード読み書きサンプル
     */
    public static void wordDeviceSingleSample() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }
        
        System.out.println("D200 に 512 を書き込んで読み込むサンプル");
        client.setDevice2("D200", (short)512);
        client.printDevice2("D200");

        client.disconnect();
    }

    /**
     * ワードデバイス 複数ワード読取るサンプル
     */
    public static void wordDeviceMultipleReadSample() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("D200 から 4 ワード (D200 ~ D203) 読み取るサンプル");
            
            client.setDevice2("D200", (short)0x0001);
            client.setDevice2("D201", (short)0x0002);
            client.setDevice2("D202", (short)0x0003);
            client.setDevice2("D203", (short)0x0004);
            
            // 表示
            DeviceSpec deviceSpec = new DeviceSpec("D200");
            short[] readValues = client.readBlockWord(deviceSpec, (short)2);
            for (int i=0; i<readValues.length; i++) {
                short value = readValues[i];
                System.out.printf("%d: %d\n", i, value);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }    
        client.disconnect();
    }

    /**
     * ワードデバイス 複数ワード書込みサンプル
     */
    public static void wordDeviceMultipleWriteSample() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("D200, D201 に 3, 2 を書き込んで読み込むサンプル");
            client.writeBlockWord(new DeviceSpec("D200"), new short[]{0x0003, 0x0002});
        } catch(Exception e) {
            e.printStackTrace();
        }

        client.printDevice2("D200");
        client.printDevice2("D201");

        client.disconnect();
    }


    /**
     * ビットデバイス 1ビット読み書きサンプル
     */
    public static void bitDeviceSingleSample() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        System.out.println("M0, Y0 に 1 を書き込んで読み込むサンプル");
        client.setDevice2("M0", (short)1);
        client.setDevice2("Y0", (short)1);
        client.printDevice2("M0");
        client.printDevice2("Y0");
        
        client.disconnect();
    }

    /**
     * ビットデバイス 複数ビット読み取るサンプル
     */
    public static void bitDeviceMultipleReadSample() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("一括読込み（複数ビット）のテスト");

            client.setDevice2("M0", (short)0);
            client.setDevice2("M1", (short)0);
            client.setDevice2("M2", (short)1);
            client.setDevice2("M3", (short)0);
            client.setDevice2("M4", (short)1);
            client.setDevice2("M5", (short)1);
            client.setDevice2("M6", (short)0);
            client.setDevice2("M7", (short)0);

            // 表示
            System.out.println("M2 から表示してみる");
            DeviceSpec deviceSpec = new DeviceSpec("M2");
            boolean[] readValues = client.readBlockBit(deviceSpec, (short)4);
            for (int i=0; i<readValues.length; i++) {
                System.out.printf("%d: %d\n", i, (readValues[i] ? 1 : 0));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        client.disconnect();
    }

    
    /**
     * ビットデバイス 複数ビット書き込むサンプル
     */
    public static void bitDeviceMultipleWriteSample() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("一括書込み（複数ビット）のテスト");
            boolean[] writeData = new boolean[] {
                true,
                false,
                true,
                true
            };
            DeviceSpec deviceSpec = new DeviceSpec("M2");
            client.writeBlockBit(deviceSpec, writeData);

            // 読取り
            DeviceSpec readDeviceSpec = new DeviceSpec("M0");
            boolean[] readValues = client.readBlockBit(readDeviceSpec, (short)8);
            
            // 表示
            System.out.println("M0 から表示してみる");
            for (int i=0; i<readValues.length; i++) {
                System.out.printf("M%d: %d\n", i, (readValues[i] ? 1 : 0));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        client.disconnect();
    }

    /**
     * PLC 形名を表示するサンプル
     * Q03UDV CPU では動かなかった
     */
    public static void printPlcTypeName() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("PLC 形名を読み取るテスト");
            System.out.println("PLC TypeName: " + client.readPlcTypeName());
        } catch(Exception e) {
            e.printStackTrace();
        }

        client.disconnect();
    }

    /**
     * リモート STOP を送信するサンプル
     * Q03UDV CPU では動かなかった
     */
    public static void remoteStop() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("PLC リモート STOP テスト");
            client.remoteStop();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        client.disconnect();
    }

    /**
     * リモート STOP を送信するサンプル
     * Q03UDV CPU では動かなかった
     */
    public static void remoteRun() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            System.out.println("PLC リモート RUN テスト");
            client.remoteRun();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        client.disconnect();
    }

    /**
     * 折返しテストを行うサンプル
     * Q03UDV CPU では動かなかった
     */
    public static void returnEchoTest() {
        boolean debug = true;
        Client client = new Client("172.16.12.171", 1025, SeriesModelEnum.Q_SERIES, debug);
        client.connect();
        if (!client.isConnected()) {
            System.out.println("接続できませんでした");
            return;
        }

        try {
            // 折返しテスト
            client.echoTest("Hello".getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }

        client.disconnect();
    }
}
