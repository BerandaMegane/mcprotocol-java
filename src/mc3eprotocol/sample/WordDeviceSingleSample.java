package mc3eprotocol.sample;

import mc3eprotocol.lib.Client;

public class WordDeviceSingleSample {
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
        
        System.out.println("D200 に 512 を書き込んで読み込む");
        client.setDevice2("D200", (short)512);
        client.printDevice2("D200");

        System.out.println("D200 に 256 を書き込んで読み込む");
        client.setDevice2("D200", (short)256);
        client.printDevice2("D200");
        
        client.disconnect();
    }
}
