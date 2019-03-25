package com.anvs;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadSocket extends Thread {

    private static Logger log = Logger.getLogger(ThreadSocket.class.getName());
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
        log.info(this.getName() + " started.");
    }

    @Override
    public void run() {
        String m;
        while (true) {
            try {
                if (((m = receive()) == null)) break;
                System.out.println(m);
                send("Server: OK");
            } catch (IOException e) {
                e.printStackTrace();
                interrupt();
            }
        }
        interrupt();
        log.info("Connection is lost.");
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void send(String message) throws IOException {
        if (!clientConnection.isClosed()) {
            out.write(message + EOL);
            out.flush();
        }
    }

    @Override
    public void interrupt() {
        try {
            send("Server cuts off the connection...");
            clientConnection.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.interrupt();
            log.info("Sokcet is interrupted.");
        }
    }

    public boolean isClosed() {
        return clientConnection.isClosed();
    }
}
