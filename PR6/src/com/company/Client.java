package com.company;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket socket;
    private static BufferedReader inStream;
    private static BufferedWriter outStream;

    public static void main(String[] args) {
        try {
            try {
                System.out.println("Клиент: Запуск");

                socket = new Socket("localhost", 8080);
                inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Scanner scanner = new Scanner(System.in);

                //Отправка сообщения
                System.out.println("Введите выражение:");
                String s = scanner.nextLine();

                //"\n" необходим, поскольку оно является сигналом к тому, что из потока можно читать
                outStream.write(s + "\n");
                outStream.flush();
                System.out.println("Сообщение отправлено");

                //вывод
                String answerLine;
                StringBuilder serverAnswer = new StringBuilder();
                while ((answerLine = inStream.readLine()) != null)
                    serverAnswer.append(answerLine).append("\n");
                System.out.println("Получен ответ от сервера: " + serverAnswer);

            } finally {
                System.out.println("Клиент: Остановка");
                socket.close();
                inStream.close();
                outStream.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
