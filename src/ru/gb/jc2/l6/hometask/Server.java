package ru.gb.jc2.l6.hometask;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            socket = server.accept();
            System.out.println("Клиент подключился ");


            // входящий поток сообщений:
            Scanner in = new Scanner(socket.getInputStream());
            // исходящий поток сообщений:
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    while (true) {
                        String fromServerString = reader.readLine();
                        out.println("server: " + fromServerString);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


            while (true) {
                String str = in.nextLine();

                if(str.equals("/end")){
                    System.out.println("Клиент отключился");
                    break;
                }

                System.out.println("Клиент: " + str);
//                out.println("echo: " + str);

//                String fromServerString = reader.readLine();
//                out.println("server: " + fromServerString);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

