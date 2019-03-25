package com.anvs;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class NetCornServer {
    public static Logger log = Logger.getLogger(NetCornServer.class.getName());

    private final static int PORT = 3456;
    public static void main(String[] args) {
        try (NCServer server = new NCServer(PORT)) {
            server.catchClients();
            while(!server.isStopped()) {
                Thread.sleep(1000);
                server.shrinkClientPool();
            }
        } catch (IOException e) {
            log.severe("Error while server stats." + Arrays.toString(e.getStackTrace()));
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("Server thread was interrupted.");
        }


    }
}


