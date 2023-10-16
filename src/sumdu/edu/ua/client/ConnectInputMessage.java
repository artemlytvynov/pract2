package sumdu.edu.ua.client;

import java.io.*;
import java.net.Socket;

public class ConnectInputMessage implements Runnable{
    private Socket serverConnect;
    private InputStream inputStreamServer;

    public ConnectInputMessage(){
        try{
            serverConnect = new Socket("localhost", 8887);
            inputStreamServer = serverConnect.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStreamServer));
        String serverMessage;

        while (true){
            try {
                serverMessage = in.readLine();
                if(serverMessage != null){
                    System.out.println(serverMessage + '\n');
                    break;
                }
            } catch (IOException e) {
                System.err.println("Неможливо зчитати: " + e.getMessage());
                break;
            }
        }

        PrintWriter out = null;
        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
        String userMessage = null;

        while (true) {
            System.out.println("Ввeдіть повідомлення: ");
            try {
                userMessage = inputUser.readLine();
                out = new PrintWriter(serverConnect.getOutputStream(), true);
                out.println(userMessage);
            } catch (IOException e) {
                System.err.println("Неможливий input/output: " + e.getMessage());
            }
        }
    }

    public InputStream getInputStreamServer() {
        return inputStreamServer;
    }
}