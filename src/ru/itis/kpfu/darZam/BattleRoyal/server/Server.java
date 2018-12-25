package ru.itis.kpfu.darZam.BattleRoyal.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Server implements Runnable {

    private static final int PORT = 8081;
    private Map<Socket, String> hashMap;
    private Socket socket;
    private DataInputStream in;
    private BufferedWriter outB;

    public Server(Socket socket) throws IOException {
        this.socket = socket;
        hashMap = new HashMap<>();
        InputStream sin = socket.getInputStream();
        outB = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
        in = new DataInputStream(sin);
    }

    public void run() {
        try {
            while (true) {
                String line = in.readUTF();
                System.out.println(line);
                hashMap.put(socket, "1");
                for (Server vr : Server.serverList) {
                    vr.send(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String msg) {
        try {
            outB.write(msg + "\n");
            outB.flush();
        } catch (IOException ignored) {}

    }


    public static LinkedList<Server> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server Started");
        int playerId = 0;
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    Server server1 = new Server(socket);
                    server1.send(String.valueOf(playerId));
                    playerId++;
                    serverList.add(server1);
                    new Thread(server1).start();
                } catch (IOException e) {
                    socket.close();
                    playerId--;
                }
            }
        } finally {
            server.close();
        }
    }

}
