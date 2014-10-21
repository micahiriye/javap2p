package com.cst311;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by micahiriye on 10/9/14.
 */
public class Server {

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8000);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                    }
                } catch (Exception e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };

        Thread serverThread = new Thread(serverTask); //don't set this as a daemon thread because at some point all processes will run in background...
        serverThread.start();
    }

    //Internal class used to handle all of the client requests
    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Got a client !");

            try {
                DataInputStream clientMessage = new DataInputStream( this.clientSocket.getInputStream() );
                boolean done = false;
                while(!done) {
                    //let's figure out what kind of message it is
                    byte messageType = clientMessage.readByte();

                    //handle the messageType accordingly
                    switch(messageType)
                    {
                        case 1:
                            System.out.println("Message 1: " + clientMessage.readUTF());
                            break;
                        case 2:
                            System.out.println("Message 2: " + clientMessage.readUTF());
                            break;
                        case 3:
                            System.out.println("Message 3: " + clientMessage.readUTF());
                            break;
                        default:
                            done = true;
                    }
                }

                clientMessage.close();
                this.clientSocket.close(); //Are these the same?
                System.out.println("Connection closed");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}