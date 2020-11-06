package com.company;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.regex.Pattern;

public class Client implements Runnable {

    private Socket socket;
    private BufferedReader inStream;
    private BufferedWriter outStream;
    private PrintWriter outHeader;

    public Client(Socket clientSocket) {
        this.socket = clientSocket;
    }//новая строка
    //бывшее main
    public void run() {
        try {
            try {
                //Инициализация
                inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                outHeader = new PrintWriter(socket.getOutputStream());

                //Получение запроса
                String request = inStream.readLine().split(" ")[1].substring(1);
                System.out.println(this + ": Получен запрос: " + request);

                //Заголовки ответа
                outHeader.println("HTTP/1.1 200 OK");
                outHeader.println("Server: Java HTTP Server : 1.0");
                outHeader.println("Date: " + new Date());
                outHeader.println("Content-type: text/html; charset=utf-8");
                outHeader.println("Accept-Language: *");
                outHeader.println();
                outHeader.flush();

                System.out.println(this + ": Подготовка ответа.");

                //Отображение базового текста
                outStream.write("<h1>Работу выполнил: Копотов Михаил Алексеевич. ИКБО-12-18</h1>" +
                        "<h2>Номер индивидуального задания: 13</h2>" +
                        "<h2>Текст индивидуального задания: Калькулятор. Формула должна поступать в виде в виде текста (пример: «4/2»)</h2>");

                //Отображение ответа
                try {
                    var answer = calculate(request);
                    outStream.write("<h3 style='color: blue'>Ответ: " + answer + "</h3>");
                }catch (Exception e){
                    outStream.write("<h3 style='color: red'>Ошибка: " + e + "</h3>");
                }

                outStream.flush();

                System.out.println(this + ": Ответ отправлен.");

            } finally {
                System.out.println(this + ": Остановка");
                socket.close();
                inStream.close();
                outStream.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    static float calculate(String task) throws Exception {
        char sign;
        String aString = "", bString = "";

        int i = 0;
        while (Character.isDigit(task.charAt(i)))
            aString += task.charAt(i++);

        sign = task.charAt(i++);

        while (i < task.length())
            bString += task.charAt(i++);

        float a = Float.parseFloat(aString);
        float b = Float.parseFloat(bString);

        switch (sign) {
            case '+':
                return (a + b);
            case '-':
                return (a - b);
            case '*':
                return (a * b);
            case '/':
                return (a / b);
            default:
                throw new Exception();
        }
    }
}
