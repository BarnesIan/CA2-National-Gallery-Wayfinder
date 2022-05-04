package com.example.ca2;

import dijkstra.DijkstraAlgorithm;
import dijkstra.GraphLink;
import dijkstra.GraphNode;
import dijkstra.Vertex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class FXController implements Initializable {
    @FXML
    ImageView mainimage;

    @FXML
    Button showRoutes, restart;
    @FXML Label label;
    @FXML
    ComboBox startingLocation, endLocation;

    @FXML
    Pane mapPane;

    private DijkstraAlgorithm da;
    //    List<GraphNode<Vertex>> nodes = new ArrayList<>();
    GraphNode<Vertex>[] nodes = new GraphNode[76];


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainimage.setImage(new Image(FXController.class.getResource("map.jpg").toExternalForm()));
        da = new DijkstraAlgorithm();
        loadData();

       setupComboBox();

    }
    
    public void clickedImage(MouseEvent event) {
        PixelReader preader = mainimage.getImage().getPixelReader();
        Image inputImage = mainimage.getImage();

        mainimage.setOnMouseClicked(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            label.setText(String.format("xCoord = "+ x  + "ycoord = " + y));
    });
    
    }

    public void loadData() {
        String line = "";
        BufferedReader br = null;
        BufferedReader br2 = null;
        Random rand = new Random();
        String[] vertices = new String[0];
        int counter = -1;

        try {
            br = new BufferedReader(new FileReader("src/main/resources/com/example/ca2/Vertices.txt"));
            while ((line = br.readLine()) != null) {
                counter++;
                vertices = line.split(",");
                String roomNum = vertices[0];
                String name = vertices[1];
                int x = Integer.valueOf(vertices[2]);
                int y = Integer.valueOf(vertices[3]);
                System.out.println("x: " + x + " y: " + y + " name : " + name);
//                nodes.add(new GraphNode<>(new Vertex(roomNum,name, x, y)));
                nodes[counter] = new GraphNode<Vertex>(new Vertex(roomNum, name, x, y));

            }

            br2 = new BufferedReader(new FileReader("src/main/resources/com/example/ca2/Edges.txt"));
            line = "";
            while ((line = br2.readLine()) != null) {
                String[] edges = line.split(",");
                int con1 = Integer.parseInt(edges[0]);
                int con2 = Integer.parseInt(edges[1]);

                if (con2 < 65 && con1 < 65) {
                    nodes[con1].connectToNodeUndirected(nodes[con2], calcDistance(nodes[con1].getData().getxCoord(), nodes[con1].getData().getyCoord(), nodes[con2].getData().getxCoord(), nodes[con2].getData().getyCoord()));
                }


//                for (GraphNode<Vertex> v : nodes) {
//                    if (v != null) {
//                        if (v.getData().getRoomNum().equals(con1)) {
//                            vertex1 = v.getNodeValue();
//                            found1 = true;
//                        } else if (v.getData().getRoomNum().equals(con2)) {
//                            vertex2 = v.getNodeValue();
//                            found2 = true;
//                        }
//                    }
//                }
//                if (vertex1 != -1 || vertex2 != -1) {
//                    if (found1 && found2) {
//                        System.out.println("cock");
////                        GraphLink graphLink = new GraphLink(nodes[vertex2], calcDistance(nodes[vertex1].getData().getxCoord(), nodes[vertex1].getData().getyCoord(), nodes[vertex2].getData().getxCoord(), nodes[vertex2].getData().getyCoord()));
//
//                        nodes[vertex1].connectToNodeUndirected(nodes[vertex2], calcDistance(nodes[vertex1].getData().getxCoord(), nodes[vertex1].getData().getyCoord(), nodes[vertex2].getData().getxCoord(), nodes[vertex2].getData().getyCoord()));
//                    }
//                }

//                    if (vertex1 != -1 || vertex2 != -1) {
//                        if (found1 && found2) {
//                            GraphLink link = new GraphLink("",
//                                    calcDistance(graphNode.nodeList[vertex1].getData().getX(), nodeArray[vertex1].getData().getY(),
//                                            nodeArray[vertex2].getData().getX(), nodeArray[vertex2].getData().getY()),
//                                    difficulty, danger);
//
//                            nodeArray[vertex1].connectUndirected(nodeArray[vertex2], link);
//                        }
//                    }
            }

//            System.out.println("size: " + nodes[30].nodeList.size() + " Name: " + nodes[30].data.getName() + " room num: " + nodes[30].data.getRoomNum());

//            DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPath(nodes[10], nodes[0].getData().getRoomNum());
//
//            for (GraphNode<?> n : cpa.pathList)
//                System.out.println(n.data);
//            System.out.println("\n The total path cost is: " + cpa.pathCost);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupComboBox() {
        for (int i = 0; i < 65; i++) {
            startingLocation.getItems().add(nodes[i].getData().getRoomNum());
            endLocation.getItems().add(nodes[i].getData().getRoomNum());
        }
    }

    public void close() {
        System.exit(0);
    }

    public void restart(ActionEvent event) {
        ((AnchorPane) mainimage.getParent()).getChildren().removeIf(e -> e.getClass() != mainimage.getClass());
    }

    public void showRoutes(ActionEvent event) {
        int startPosition = startingLocation.getSelectionModel().getSelectedIndex();
        int endPosition = endLocation.getSelectionModel().getSelectedIndex();

//        for (int i = 0; i < 60; i++) {
//            for (int j = 0; j < nodes[i].nodeList.size(); j++) {
//                System.out.println(nodes[i].nodeList.get(j).cost);
//            }
//        }

        DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPathDijkstra(nodes[startPosition], nodes[endPosition].getData());
//        System.out.println(cpa.pathList.size());

        for (GraphNode<Vertex> n : cpa.pathList) {
            mapPane.getChildren().addAll(drawNodes(n.getData().getxCoord(), n.getData().getyCoord(), Color.PINK));

            System.out.println(n.getData().getRoomNum());
        }
        int j = 0;
        int k = j + 1;
        for (int i = 0; i < cpa.pathList.size(); i++) {
            if (k < cpa.pathList.size()) {
                int x1 = cpa.pathList.get(j).getData().getxCoord();
                int y1 = cpa.pathList.get(j).getData().getyCoord();
                int x2 = cpa.pathList.get(k).getData().getxCoord();
                int y2 = cpa.pathList.get(k).getData().getyCoord();
                mapPane.getChildren().add(connectNodes(x1,y1,x2,y2,Color.BLUE));
            }
            j++;
            k++;
        }

        System.out.println("\n The total path cost is: " + cpa.pathCost);
    }

    public void showAllRoutes() {
//        int startPosition = startingLocation.getSelectionModel().getSelectedIndex();
//        int endPosition = endLocation.getSelectionModel().getSelectedIndex();
//
//        for (int i = 0; i < nodes.length; i++) {
//            for(int j = )
//        }
    }

    // The disatnce formula for calculating the distance between two points
    private static int calcDistance(int node1X, int node1Y, int node2X, int node2Y) {
        double distance = Math.sqrt((node2Y - node1Y) * (node2Y - node1Y) + (node2X - node1X) * (node2X - node1X));
        return (int) distance;
    }

    public Line connectNodes(int x1, int y1, int x2, int y2, Color color) {
        Line line = new Line(x1 , y1 , x2 , y2 );
        line.setStroke(color);
        line.setStrokeWidth(5);
        line.setOpacity(0.5);
        return line;
    }

    public Rectangle drawNodes(int x, int y, Color color) {
        Rectangle rec = new Rectangle(x, y, 10, 10);
        rec.setStroke(Color.TRANSPARENT);
        rec.setFill(color);
        rec.setOpacity(0.3);
        return rec;
    }

}


