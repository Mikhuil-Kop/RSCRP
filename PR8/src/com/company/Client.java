package com.company;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class Client implements Runnable {

    private Socket clientSocket;

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getPagesPath () {
        File file = new File("Result");
        String pagesPath = file.getAbsolutePath();
        pagesPath = pagesPath.substring(0, pagesPath.lastIndexOf('\\') + 1);
        return pagesPath + "src/public/";
    }

    public String getPage(String pageName) {
        String filePath = getPagesPath() + pageName;
        getPagesPath();
        String pageData = "";
        try {
            pageData = Files.readString(Paths.get(filePath));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return pageData;
    }

    @Override
    public void run() {
        BufferedReader inStream;
        PrintWriter headerPrintWriter;
        BufferedWriter outDataStream;
        try {
            inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String request = inStream.readLine().split(" ")[1].substring(1);
            if (!request.equals("") && !request.contains("?") && !request.contains("css") && !request.contains("js")){
                request = request.substring(0, request.length() - 1);
            }
            String currentPage = getPage("index.html");
            String restResponse = "";
            String contentType = "";
            boolean isRestUsed = false;

            if(!request.contains("api")){
                contentType = "Content-type: text/html; charset=utf-8";
                switch (request) {
                    case "about":
                        currentPage = getPage("about.html");
                        break;
                    case "task":
                        currentPage = getPage("task.html");
                        break;
                    case "table":
                        currentPage = getPage("table.html");
                        break;
                    default:
                        currentPage = getPage("index.html");
                        break;
                }
                if(request.contains(".css")) {
                    System.out.println(request);
                    request = request.substring(request.indexOf("css"));
                    System.out.println(request);
                    currentPage = getPage(request);
                    contentType = "Content-type: text/css";
                }
                else if(request.contains(".js")){
                    System.out.println(request);
                    request = request.substring(request.indexOf("js"));
                    System.out.println(request);
                    currentPage = getPage(request);
                    contentType = "Content-type: text/javascript";
                }
            }
            else{
                contentType = "Content-type: application/json; charset=utf-8";
                Rest rest = new Rest(request);
                restResponse = rest.getResponse();
                isRestUsed = true;
            }

            headerPrintWriter = new PrintWriter(clientSocket.getOutputStream());
            outDataStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            headerPrintWriter.println("HTTP/1.1 200 OK");
            headerPrintWriter.println("Server: Java HTTP Server : 1.0");
            headerPrintWriter.println("Date: " + new Date());
            headerPrintWriter.println(contentType);
            headerPrintWriter.println("Accept-Language: *");
            headerPrintWriter.println();
            headerPrintWriter.flush();

            if(!isRestUsed){
                outDataStream.write(currentPage);
                outDataStream.flush();
            }
            else{
                outDataStream.write(restResponse);
                outDataStream.flush();
            }

            System.out.println("Отправлен " + request);

            inStream.close();
            headerPrintWriter.close();
            outDataStream.close();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
