package sumdu.edu.ua.client;

import java.io.InputStream;

public class MainClient {
    public static void main(String[] args) {
        ConnectInputMessage connectWithServer = new ConnectInputMessage();
        Thread tConnectInputMessage = new Thread(connectWithServer);
        tConnectInputMessage.start();

        ReceiveMessageFromServer receiverMessage = new ReceiveMessageFromServer(connectWithServer.getInputStreamServer());
        Thread tReceiveMessage = new Thread(receiverMessage);
        tReceiveMessage.start();
    }
}