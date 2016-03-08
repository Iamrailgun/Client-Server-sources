import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        String host = "localhost"; // IP адрес серврп
        int port = 25500; // Порт сервера
        String data;

        Socket socket = null;
        try {
            socket = new Socket(host, port); // Создание клментв
        } catch (UnknownHostException e){
            System.out.println("Неизвестный хост: " + host);
            System.exit(-1);
        } catch (IOException e){
            System.out.println("Ошибка ввода/вывода при создании сокета " + host + ":" + port);
            System.exit(-1);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Запись в буффер вывода потока
        OutputStream out = null; // Поток вывода
        try {
            out = socket.getOutputStream();
        } catch (IOException e){
            System.out.println("Невозможно получить поток вывода!");
            System.exit(-1);
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        String in = null;
        try {
            while ((in = reader.readLine()) != null){
                writer.write(in + "\n");
                writer.flush();
                try {
                    InputStream iStream = socket.getInputStream(); // Входной поток
                    DataInputStream inStream = new DataInputStream(iStream);
                    data = inStream.readUTF();
                    System.out.println(data);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи сообщения!");
            System.exit(-1);
        }
    }
}
