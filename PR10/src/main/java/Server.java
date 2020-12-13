import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Сервер запущен на порту 8080!");
            while (true) {
                Client client = new Client(serverSocket.accept());

                Thread thread = new Thread(client);
                thread.start();
            }

        } catch (IOException e){
            System.err.println(e);
        }
    }
}
