import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Client implements Runnable {

    private Socket clientSocket;

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getPage(String pageName) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File("/Users/mikekopotov/Desktop/РКСП/PR10/src/main/java/public"));

        Map<String, Object> root = new HashMap<>();

        if(pageName.equals("table.ftl")) root.put("robots", Robot.getAllArr());
        root.put("head", cfg.getTemplate("head.ftl").toString());
        root.put("header", cfg.getTemplate("header.ftl").toString());

        Template pageTemp = cfg.getTemplate(pageName);

        StringWriter stringWriter = new StringWriter();
        pageTemp.process(root, stringWriter);

        return stringWriter.toString();
    }

    @Override
    public void run() {
        //Инициализация
        BufferedReader inStream;
        PrintWriter headerPrintWriter;
        BufferedWriter outDataStream;
        try {
            inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String str = inStream.readLine();
            System.out.println("-: " + str);
            String request = str.split(" ")[1].substring(1);
            System.out.println("Запрос: " + request);
            //обрезать запрос типа '8080/about/'
            if (!request.equals("") && !request.contains("?") && !request.contains("css") && !request.contains("js"))
                request = request.substring(0, request.length() - 1);

            String currentPage = "";
            String contentType = "";
            String restResponse = "";
            boolean isRestUsed = false;

            //Форматирование запроса
            if(!request.contains("api")){
                if(request.contains(".css")) {
                    request = request.substring(request.indexOf("css"));
                    currentPage = getPage(request);
                    contentType = "Content-type: text/css";
                }
                else if(request.contains(".js")){
                    request = request.substring(request.indexOf("js"));
                    currentPage = getPage(request);
                    contentType = "Content-type: text/javascript";
                }
                else {
                    switch (request) {
                        case "about":
                            currentPage = getPage("about.ftl");
                            break;
                        case "task":
                            currentPage = getPage("task.ftl");
                            break;
                        case "table":
                            currentPage = getPage("table.ftl");
                            break;
                        default:
                            currentPage = getPage("index.ftl");
                            break;
                    }
                    contentType = "Content-type: text/html; charset=utf-8";
                }
            }
            else{
                contentType = "Content-type: application/json; charset=utf-8";
                Functions rest = new Functions(request);
                restResponse = rest.getResponse();
                isRestUsed = true;
            }

            //передача клиенту
            headerPrintWriter = new PrintWriter(clientSocket.getOutputStream());
            outDataStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            headerPrintWriter.println("HTTP/1.1 200 OK");
            headerPrintWriter.println("Server: Java HTTP Server : 1.0");
            headerPrintWriter.println("Date: " + new Date());
            headerPrintWriter.println(contentType);
            headerPrintWriter.println("Accept-Language: *");
            headerPrintWriter.println();
            headerPrintWriter.flush();

            System.out.println("Итоговый запрос: " + request);

            if(!isRestUsed){
                System.out.println("Отправлен: файл");
                outDataStream.write(currentPage);
                outDataStream.flush();
            }
            else{
                System.out.println("Отправлено: " + restResponse);
                outDataStream.write(restResponse);
                outDataStream.flush();
            }

            inStream.close();
            headerPrintWriter.close();
            outDataStream.close();
            Thread.currentThread().interrupt();

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
