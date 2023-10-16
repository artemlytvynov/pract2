package sumdu.edu.ua.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ChatServer implements Runnable{
    private Map<Integer, Socket> mapClient = new TreeMap<Integer, Socket>();

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(8887);
            System.out.println("Сервер запущено. Чекаємо на клієнтів.");
            int numberClient = 1;
            Socket client = null;

            while (true) {
                client = server.accept();
                Thread clientThread = new Thread(new ClientThread(client, this, numberClient));
                clientThread.setDaemon(true);
                clientThread.start();
                mapClient.put(numberClient, client);
                numberClient++;
            }
        } catch (IOException e) {
            System.err.println("Помилка створення сокета: " + e.getMessage());
        }
    }

    public void sendMessageForAllClient(int numberClient, String clientMessage) {
        Iterator<Map.Entry<Integer, Socket>> iterator = mapClient.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<Integer, Socket> entry = iterator.next();
            int clientNumb = entry.getKey();
            Socket clientSocket = entry.getValue();

            if (clientNumb != numberClient){
                try{
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Клієнт №" + numberClient + ": " + clientMessage);
                }
                catch (IOException e){
                    System.err.println("Помилка виводу");
                    e.printStackTrace();
                    iterator.remove();
                }
            }
        }
    }
}