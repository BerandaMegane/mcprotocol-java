package mc3eprotocol.sample;

import mc3eprotocol.lib.Client;
import mc3eprotocol.lib.define.DeviceSpec;

public class BitDeviceBatchSample {
    public static void main(String[] args) {
        boolean debug = true;
        Client client = new Client("172.16.212.172", 1025, debug);
        client.connect();
        if (client.isConnected()) {
            System.out.println("接続できました");
        } else {
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
}
