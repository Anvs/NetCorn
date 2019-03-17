package com.anvs;

import java.io.*;
import java.net.*;

public class NCClient implements Closeable
{
    private Socket connection;
    private BufferedReader in;
    private BufferedWriter out;

    public NCClient(String host, int port) throws IOException {
        connection = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
    }

    @Override
    public void close() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (connection != null) connection.close();
    }

    public void sendMessage(String message) {
        try {
            out.write(message + '\n');
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getInput() {return in;}

    public BufferedWriter getOutout() {return out;}


}
