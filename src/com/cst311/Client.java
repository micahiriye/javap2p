package com.cst311;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * Created by micahiriye on 10/9/14.
 */
public class Client {

    protected Socket serverConnection;

    public Client() {
        //TODO: make a way to automatically try to connect to a known list of servers (other peers)
        Scanner input = new Scanner(System.in);
        String initialServerAddress;
        int initialServerPort;

        System.out.println("Enter the initial peers IP address: ");
        initialServerAddress = input.nextLine();
        System.out.println("Enter the initial peers port #: ");
        initialServerPort = input.nextInt();

        try {
            this.serverConnection = new Socket(initialServerAddress, initialServerPort);
            System.out.println("connection has been established");

            DataOutputStream clientMessage = new DataOutputStream( this.serverConnection.getOutputStream() );
            //now let's start sending it data...
            clientMessage.writeByte(1);
            clientMessage.writeUTF("Hey");
            clientMessage.flush(); //send the message

            clientMessage.writeByte(2);
            clientMessage.writeUTF("what's");
            clientMessage.flush();

            clientMessage.writeByte(3);
            clientMessage.writeUTF("up?");
            clientMessage.flush();

            clientMessage.writeByte(-1); //right now any number besides 1,2, or 3 will close the connection...
            clientMessage.flush();

            clientMessage.close();
            this.serverConnection.close(); //do I have to close the connection from here?
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unfortunately we were unable to connection to " + initialServerAddress + ":" + initialServerPort);
        }
    }
}
