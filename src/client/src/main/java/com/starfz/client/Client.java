package com.starfz.client;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {

    public static void main(String []args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 5200);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());


        Sender sender = new Sender(out);
        new  Thread(sender).start();

        Receiver receiver = new Receiver(in);
        new Thread(receiver).start();

//        BufferedReader cmd = new BufferedReader(new InputStreamReader(System.in));
//        String line = cmd.readLine();
//
//        while (!line.equals("exit")) {
//            System.out.println("input 'exit' to quit");
//        }
//
//        socket.close();
    }

}

class Sender implements Runnable {

    private DataOutputStream out;

    Sender(DataOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {

            try {
                LocalDateTime now = LocalDateTime.now();
                String msg = String.format("client report: %s", now.toString());

                out.write(msg.getBytes());
                Thread.sleep(3000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}


class Receiver implements Runnable{
    private DataInputStream in;

    Receiver(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("start read....");

            try {
                byte []buff = new byte[128];

                if (in.read(buff) != -1) {
                    System.out.println(new String(buff));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
