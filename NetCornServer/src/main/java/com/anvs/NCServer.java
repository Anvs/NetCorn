package com.anvs;

import java.io.*;
import java.net.ServerSocket;
import java.util.*;
import java.util.logging.Logger;


/**
 * Implements simple server with multiple connections
 *
 */
public class NCServer implements Closeable
{
//    private static boolean isSopped = false;
    private static Logger log = Logger.getLogger(NCServer.class.getName());

    private boolean isStopped;

    private ServerSocket serverSocket;
    private Thread listener;
    private volatile List<ThreadSocket> clientPool = new ArrayList<>();

    @Override
    public void close() throws IOException {
        listener.interrupt();
        for(ThreadSocket socket : clientPool) {
            socket.interrupt();
        }
        if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        log.info("Server stoped.");
    }

    public NCServer(int port) throws IOException {
        this.isStopped = false;
        serverSocket = new ServerSocket(port);
        listener = new Thread() {
            @Override
            public void run() {
                log.info("Listener started.");
                while (!interrupted()) {
                    try {
                        clientPool.add(new ThreadSocket(serverSocket.accept()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.severe("Critical error in client listener!");
                    }
                }
                log.info("Listener interrupted.");
            }
        };
        log.info("Server started.");
    }

    public void catchClients() {
        listener.start();
    }

    public void sendDirect(String message, String clientName) {

    }

    public void sendBroadcast( String message) {
        for (ThreadSocket client : clientPool) {
            try {
                client.send(message);
            } catch (IOException e) {
                log.severe("Error while sending the message to client." + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public synchronized void shrinkClientPool() {
        ThreadSocket item;
        for(Iterator<ThreadSocket> it = clientPool.iterator(); it.hasNext();) {
            item = it.next();
            if (item.isClosed()) {
                it.remove();
                log.info("Old client socket was remoled");
            }
        }
    }
}

