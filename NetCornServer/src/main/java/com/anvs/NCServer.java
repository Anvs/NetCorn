package com.anvs;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


/**
 * Implements simple server with multiple connections
 *
 */
public class NCServer implements Closeable
{
    private ServerSocket serverSocket;
    private Set<ThreadSocket> socketSet = new HashSet<>();
//    private Socket clientConnection;
//    private BufferedReader in;
//    private BufferedWriter out;

    @Override
    public void close() throws IOException {
//        if (in != null) in.close();
//        if (out != null) out.close();
//        if (clientConnection != null) clientConnection.close();
        for(ThreadSocket socket : socketSet) {
            socket.interrupt();
        }
        if (serverSocket != null) serverSocket.close();
    }

    public NCServer(int port) throws IOException {
            serverSocket = new ServerSocket(port);
    }

    public void waitForConnect() throws IOException {
        socketSet.add(new ThreadSocket(serverSocket.accept()));
//        in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
//        out = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
    }

//    public String getMessage() throws IOException {
//        return  in.readLine();
//    }
//
//    public BufferedReader getIn() {
//        return in;
//    }
//
//    public BufferedWriter getOut() {
//        return out;
//    }

}
