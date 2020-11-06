package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            try {
                serverSocket = new ServerSocket(8080);
                System.out.println("Сервер: Запуск");
                while (true) {
                    Client client = new Client(serverSocket.accept());
                    System.out.println("Сервер: Подключение к " + client);

                    Thread thread = new Thread(client);
                    thread.start();
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Сервер: Завершение");
    }
}

