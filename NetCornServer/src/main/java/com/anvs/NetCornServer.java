package com.anvs;

import java.io.IOException;

public class NetCornServer {
    final static int PORT = 3456;
    public static void main(String[] args) {
        try (NCServer server = new NCServer(PORT)) {
            System.out.println("Server started...");
            while (true) {
                server.waitForConnect();
            }
//            String m;
//            while ((m = server.getMessage()) != null) {
//                System.out.println(m);
//                server.getOut().write("Сообщение принято!" + '\n');
//                server.getOut().flush();
//            }
//            System.out.println("Client was disconnected.");
//            System.out.println("Server stopped!");
        } catch (IOException e) {
            System.err.println("Sonething went wrong...");
        }
    }
}


