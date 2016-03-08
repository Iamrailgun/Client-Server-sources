import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 25500; // Порта сервера

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port); // Создание сервера
        } catch (IOException e){
            System.out.println("Порт: " + port + " Ошибка подключения!");
            System.exit(-1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept(); // Ожидание подключения клиента
            System.out.println("connecting" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        } catch (IOException e) {
            System.out.println("Порт: " + port + " Ошибка подключения!");
            System.exit(-1);
        }

        InputStream inClintStream = null;
        try {
            inClintStream = clientSocket.getInputStream(); // Получение входного потока
        } catch (IOException e) {
            System.out.println("Невозможно получить поток ввода!");
            System.exit(-1);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inClintStream)); // Буффер входного потока
        String readClientStream = null;
        try {
            while ((readClientStream = reader.readLine()) != null){ // Загрузка стоки из входного потока потока в строку
                System.out.println("Сообщение от клиента: " + readClientStream);
                System.out.flush();

                OutputStream outClintStream = null; // Поток вывода
                outClintStream = clientSocket.getOutputStream(); // Получение потока вывода
                DataOutputStream outDataClientStream = new DataOutputStream(outClintStream);  
                try {
                    outDataClientStream.writeUTF("server: " + readClientStream);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Невозможно прочесть сообщение!");
            System.exit(-1);
        }
    }
}