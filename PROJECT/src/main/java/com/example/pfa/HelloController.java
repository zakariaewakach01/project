package com.example.pfa;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HelloController {
    @FXML
    private TextField portID;

    @FXML
    private TextField hostID;
    @FXML
    private ListView<String> listMsgs;
    PrintWriter pw;
    @FXML
    private TextField txtMessage;
    @FXML
    protected void connectToServer () throws Exception {
        String host = hostID.getText();
        int port = Integer.parseInt(portID.getText());

        Socket s = new Socket(host,port);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = s.getOutputStream();
        String Ip = s.getRemoteSocketAddress().toString();
        pw = new PrintWriter(os,true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true){
                    try {
                        String reponse  = br.readLine();
                        Platform.runLater(()->{
                            listMsgs.getItems().add(reponse);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    @FXML
    public void onSubmit(){
        String  message = txtMessage.getText();
        pw.println(message);
    }
}