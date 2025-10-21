package mc3eprotocol.sample;

import mc3eprotocol.lib.Client;
import mc3eprotocol.lib.define.DeviceSpec;

public class WordDeviceBatchSample {
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
            System.out.println("D200 から 4 ワード (D200 ~ D203) 読み取るサンプル");    
            client.writeBlockWord(new DeviceSpec("D200"), new short[]{
                0x0001, 0x0002, 0x0003, 0x0004
            });
                    
            // 表示
            DeviceSpec deviceSpec = new DeviceSpec("D200");
            short[] readValues = client.readBlockWord(deviceSpec, (short)4);
            for (int i=0; i<readValues.length; i++) {
                short value = readValues[i];
                System.out.printf("%d: %d\n", i, value);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
 
        client.disconnect();
    }
}
