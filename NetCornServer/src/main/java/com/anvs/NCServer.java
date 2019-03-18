package com.anvs;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Implements simple server with multiple connections
 *
 */
public class NCServer implements Closeable
{
    private ServerSocket serverSocket;
    private Socket clientConnection;
    private BufferedReader in;
    private BufferedWriter out;

    @Override
    public void close() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (clientConnection != null) clientConnection.close();
        if (serverSocket != null) serverSocket.close();
    }

    public NCServer(int port) throws IOException {
            serverSocket = new ServerSocket(port);
    }

    public void waitForConnect() throws IOException {
        clientConnection = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
    }

    public String getMessage() throws IOException {
        return  in.readLine();
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

}
