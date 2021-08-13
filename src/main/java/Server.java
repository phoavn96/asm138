import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);
        try{
            serverSocket = new ServerSocket(8088);
            clientSocket = serverSocket.accept();
            out =   new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while (true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }

                }
            });
            sender.start();
            Thread receive = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try{
                        msg = in.readLine();
                        while (msg!=null){
                            System.out.println("Client: "+msg);
                            msg = in.readLine();
                        }
                        System.out.println("client disconnect");
                        out.close();
                        clientSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
