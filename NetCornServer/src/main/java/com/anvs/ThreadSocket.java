package com.anvs;

import java.io.*;
import java.net.Socket;

public class ThreadSocket extends Thread {
    final static char EOL = '\n';
    private Socket clientConnection;
    private BufferedReader in;
    private BufferedWriter out;

    public ThreadSocket(Socket clientConnection) throws IOException, IllegalArgumentException {

        super();
        this.clientConnection = clientConnection;
        in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
        start();
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void send(String message) throws IOException {
        out.write(message + EOL);
        out.flush();
    }
}
