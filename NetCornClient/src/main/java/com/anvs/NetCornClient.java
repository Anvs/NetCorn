package com.anvs;

import java.io.IOException;
import java.util.Scanner;

public class NetCornClient {
    private final static String HOST = "127.0.0.1";
    private final static int PORT = 3456;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try (NCClient client = new NCClient(HOST, PORT)) {
            System.out.println("Connected to host");
            String newLine;
            while (true) {
                System.out.print(">> ");
                newLine = in.nextLine();
                if (newLine.equals("exit")) break;

                client.sendMessage(newLine);
                String response = client.getInput().readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error with connection!");
        }
//        System.out.println("");

    }
}
