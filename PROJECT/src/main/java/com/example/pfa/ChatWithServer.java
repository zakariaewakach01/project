package com.example.pfa;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Random;

public class ChatWithServer extends Thread{

    private int ClientNbre;
    private List<Communication> connectedClient = new ArrayList<Communication>();

    public static void main(String[] args) {
        new ChatWithServer().start();
    }

    @Override
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);

            System.out.println("Server is starting...");
            while(true){
                Socket s = ss.accept();
                ++ClientNbre;

                Communication newCommunication = new Communication(s,ClientNbre);
                connectedClient.add(newCommunication);
                newCommunication.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public class Communication extends Thread{
        private Socket s;
        private int ClientNumber;

        Communication(Socket s, int ClientNumber){
            this.s=s;
            this.ClientNumber=ClientNumber;
        }
        @Override
        public void run(){
            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("Le Client : "+ClientNumber+"  est connecter sur l'adresse ip : "+Ip);
                PrintWriter pw = new PrintWriter(os,true);
                pw.println("Vous etes le client numero : "+ClientNumber);
                pw.println("Envoyez le message ... :D");
                while (true){
                    String UserRequest = br.readLine();
                    String cons = "=>";
                    if(UserRequest.contains(cons)){
                        String[] usermessage = UserRequest.split("=>");
                        if(usermessage.length == 2){
                            String message = usermessage[1];
                            int clientNb = Integer.parseInt(usermessage[0]);
                            Send(message,s,clientNb);
                        }
                    }else {
                        Send(UserRequest,s,-1);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        void Send(String UserRequest,Socket socket,int clientNbr) throws IOException {
            for(Communication client : connectedClient){
                if(client.s != socket){
                    if(client.ClientNumber==clientNbr || clientNbr==-1){
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(),true);
                        pw.println(UserRequest);
                    }
                }
            }
        }
    }
}
