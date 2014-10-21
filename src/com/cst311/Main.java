package com.cst311;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
        //now that the client has been created
        Client client = new Client(); //we can have multiple but i haven't setup multithreading for the client yet
    }
}
