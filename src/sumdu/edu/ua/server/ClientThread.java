package sumdu.edu.ua.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import sumdu.edu.ua.server.ChatServer;

public class ClientThread implements Runnable{
    Socket clientSocket;
    ChatServer chatServer;
    int numberClient;

    public ClientThread(Socket clientSocket, ChatServer chatServer, int number) {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
        this.numberClient = number;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            try {
                System.out.println("Клієнт №" + numberClient + " під'єднаний.");
                out.println("Клієнт №" + numberClient);
                String clientMessage = null;

                while (true) {
                    clientMessage = in.readLine();
                    if (!"exit".equals(clientMessage) && clientMessage != null) {
                        System.out.println("Клієнт №" + numberClient + ": " + clientMessage);
                        chatServer.sendMessageForAllClient(numberClient, clientMessage);
                    } else {
                        break;
                    }
                }
            }
            finally {
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Клієнт №" + numberClient + "від'єднався");
            }
        } catch (IOException e) {
            System.err.println("Помилка клієнтського потоку: " + e.getMessage());
        }
    }
}