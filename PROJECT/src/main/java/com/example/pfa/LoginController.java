package com.example.pfa;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class LoginController {
    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPass;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public void onLogin(ActionEvent event) throws IOException {
        String username = txtUser.getText();
        String password = txtPass.getText();

        if(username.equals("admin") && password.equals("admin")) {
            System.out.println("Hel");
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
