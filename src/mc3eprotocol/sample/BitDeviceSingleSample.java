package mc3eprotocol.sample;

import mc3eprotocol.lib.Client;

public class BitDeviceSingleSample {
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
        
        System.out.println("M0, Y0 に 1 を書き込んで読み込むサンプル");
        client.setDevice2("M0", (short)1);
        client.setDevice2("Y0", (short)1);
        client.printDevice2("M0");
        client.printDevice2("Y0");
        
        client.disconnect();
    }
}
