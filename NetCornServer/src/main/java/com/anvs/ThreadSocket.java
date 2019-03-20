package com.anvs;

import java.io.*;
import java.net.Socket;

public class ThreadSocket extends Thread {
    private final static char EOL = '\n';
    private Socket clientConnection;
    private BufferedReader in;
    private BufferedWriter out;

    public ThreadSocket(Socket clientConnection) throws IOException {
        super();
        this.clientConnection = clientConnection;
        in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String m;
        while (true) {
            try {
                if (((m = receive()) == null)) break;
                System.out.println(m);
                send("Сообщение принято!");
            } catch (IOException e) {
                e.printStackTrace();
                interrupt();
            }
        }
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void send(String message) throws IOException {
        out.write(message + EOL);
        out.flush();
    }

    @Override
    public void interrupt() {
        try {
            send("Server cut the connection...");
            clientConnection.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.interrupt();
        }

    }
}
