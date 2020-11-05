package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static BufferedReader inStream;
    private static BufferedWriter outStream;

    public static void main(String[] args) {
        try {
            try {
                serverSocket = new ServerSocket(8080);
                System.out.println("Сервер: Запуск");
                clientSocket = serverSocket.accept();
                try {
                    inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    //Обработка входящего сообщения
                    String task = inStream.readLine();
                    System.out.println("От клиента получена строка: " + task);

                    String result;
                    try {
                        result = Float.toString(calculate(task));
                    } catch (Exception e) {
                        result = e.getMessage();
                    }
                    System.out.println("Вычислен и отправлен результат: " + result);

                    outStream.write(result);
                    outStream.flush();

                } finally {
                    clientSocket.close();
                    inStream.close();
                    outStream.close();
                }
            }finally {
                System.out.println("Сервер: Остановка");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    static float calculate(String task) throws Exception {
        var strings = task.split(" ");
        float a = Float.parseFloat(strings[0]);
        float b = Float.parseFloat(strings[2]);

        switch (strings[1]) {
            case "+":
                return (a + b);
            case "-":
                return (a - b);
            case "*":
                return (a * b);
            case "/":
                return (a / b);
            default:
                throw new Exception();
        }
    }
}

