package com.example.ca2;

import dijkstra.DijkstraAlgorithm;
import dijkstra.GraphNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class FXController implements Initializable {
    @FXML
    ImageView mainimage;
    @FXML
    Button showRoutes,restart;
    @FXML
    ComboBox<Vertex> start, destination;

    private GraphNode graphNode;
    private DijkstraAlgorithm da;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainimage.setImage(new Image(FXController.class.getResource("map.jpg").toExternalForm()));
        graphNode = new GraphNode(300);
        da = new DijkstraAlgorithm();

    }

    public void loadData(){
        String line = "";
        BufferedReader br = null;
        Random rand = new Random();

            try {
                br = new BufferedReader(new FileReader("../resources/Vertices.txt"));
                while ((line = br.readLine()) != null){
                    String[] vertices = line.split(",");
                    int roomNum = Integer.valueOf(vertices[0]);
                    String name = vertices[1];
                    int x = Integer.valueOf(vertices[2]);
                    int y = Integer.valueOf(vertices[3]);
                    new GraphNode<Vertex>(new Vertex(roomNum,name,x,y),??);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void close() {
        System.exit(0);
    }

    public void restart(ActionEvent event) {
    }

    public void showRoutes(ActionEvent event) {
    int start =
    }
}

