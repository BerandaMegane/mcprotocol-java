package mc3eprotocol.sample;

import mc3eprotocol.lib.Client;

public class ReturnEchoSample {
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
        
        // 折返しテスト
        client.echoTest("Hello, MC world!".getBytes());
        
        client.disconnect();
    }
}
