package mc3eprotocol.sample;

import mc3eprotocol.lib.Client;

public class RemoteStartStopSample {
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
            System.out.println("PLC リモート STOP テスト");
            client.remoteStop();
            
            Thread.sleep(1000);

            System.out.println("PLC リモート RUN テスト");
            client.remoteRun();

        } catch(Exception e) {
            e.printStackTrace();
        }
   
        client.disconnect();
    }
}
