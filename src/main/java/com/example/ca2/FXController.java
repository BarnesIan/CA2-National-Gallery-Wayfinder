package com.example.ca2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Initializable {
    @FXML
    ImageView mainimage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainimage.setImage(new Image(FXController.class.getResource("map.jpg").toExternalForm()));
    }
}

