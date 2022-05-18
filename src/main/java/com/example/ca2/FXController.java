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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

public class FXController implements Initializable {
    @FXML
    ImageView mainimage;

    @FXML
    Button showRoutes, restart;
    @FXML Label label;
    @FXML
    ComboBox startingLocation, endLocation, avoid, avoidRooms;

    @FXML
    Pane mapPane;

    private DijkstraAlgorithm da;
        List<GraphNode<Vertex>> nodes = new ArrayList<>();
    GraphNode<String>[] strings = new GraphNode[100];
    List<GraphNode<Vertex>> avoidNodes = new ArrayList<>();

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
                vertices = line.split(",");
                String roomNum = vertices[0];
                String name = vertices[1];
                int x = Integer.valueOf(vertices[2]);
                int y = Integer.valueOf(vertices[3]);
                System.out.println("x: " + x + " y: " + y + " name : " + name);
//                nodes.add(new GraphNode<>(new Vertex(roomNum,name, x, y)));
                nodes.add(new GraphNode<>(new Vertex(roomNum, name, x, y)));
                Rectangle rect = new Rectangle(x,y,10,10);
                mapPane.getChildren().add(rect);
                rect.setTranslateX(mainimage.getTranslateX());
                rect.setTranslateY(mainimage.getTranslateY());
                rect.setFill(Color.TRANSPARENT);
                rect.setOpacity(0.3);
                Tooltip rec = new Tooltip("Room Name: " + name + "\n" +" Number :" + roomNum + "\n");
                rec.setGraphic(new ImageView(getImageFromURL(vertices[4])));
                Tooltip.install(rect, rec);
                rec.setWidth(500);
                rec.setHeight(1400);
            }
            for(int i = 0; i < nodes.size(); i ++){
                for(int j = 0; j < nodes.size(); j ++){
                    br2 = new BufferedReader(new FileReader("src/main/resources/com/example/ca2/Edges.txt"));
                    line = "";
                    while ((line = br2.readLine()) != null) {
                        String[] edges = line.split(",");
                        int con1 = Integer.parseInt(edges[0]);
                        int con2 = Integer.parseInt(edges[1]);
                        if(nodes.get(i).data.getRoomNum().matches(String.valueOf(con1))) {
                            if (nodes.get(j).data.getRoomNum().matches(String.valueOf(con2))) {
                                nodes.get(i).connectToNodeUndirected(nodes.get(j),calcDistance(nodes.get(i).getData().getxCoord(),nodes.get(i).getData().getyCoord(),nodes.get(j).getData().getxCoord(),nodes.get(j).getData().getyCoord()));
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupComboBox() {
        for (int i = 0; i < 65; i++) {
            var string = nodes.get(i).getData().getName() + " (" + nodes.get(i).getData().getRoomNum() + ")";
            startingLocation.getItems().add(string);
            endLocation.getItems().add(string);
            avoid.getItems().add(string);
        }
    }

    public void close() {
        System.exit(0);
    }

    public void addToAvoidList() {
        avoidRooms.getItems().add(nodes.get(avoid.getSelectionModel().getSelectedIndex()).getData().getRoomNum());
        avoidNodes.add(nodes.get(avoid.getSelectionModel().getSelectedIndex()));
        avoid.getItems().remove(avoid.getSelectionModel().getSelectedIndex());
        avoid.getSelectionModel().clearSelection();
    }

    public void removeFromAvoidList() {
        avoid.getItems().add(avoidNodes.get(avoidRooms.getSelectionModel().getSelectedIndex()).getData().getRoomNum());
        avoidRooms.getItems().remove(avoidRooms.getSelectionModel().getSelectedIndex());
        avoidNodes.remove(avoidRooms.getSelectionModel().getSelectedIndex());
        avoidRooms.getSelectionModel().clearSelection();
    }

    public void restart(ActionEvent event) {
        mapPane.getChildren().removeIf((e -> e.getClass() != mainimage.getClass()));
        nodes.removeAll(nodes);
        loadData();
        System.out.println(nodes.size());
    }

    public void showRoutes(ActionEvent event) {
        int startPosition = startingLocation.getSelectionModel().getSelectedIndex();
        int endPosition = endLocation.getSelectionModel().getSelectedIndex();
     //   System.out.println(nodes.get(startPosition) + ""+ nodes.get(endPosition).getData());

        DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPathDijkstra(nodes.get(startPosition), nodes.get(endPosition).getData(), avoidNodes);

        for (GraphNode<Vertex> n : cpa.pathList) {
            System.out.println(n.getData().getRoomNum());
            mapPane.getChildren().addAll(drawNodes(n.getData().getxCoord(), n.getData().getyCoord(), Color.PINK, n.getData().getName(),n.getData().getRoomNum(), n.getImage()));


        }
        int j = 0;
        int k = j+1;
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
        int startPosition = startingLocation.getSelectionModel().getSelectedIndex();
        int endPosition = endLocation.getSelectionModel().getSelectedIndex();


                DijkstraAlgorithm.CostedPath cp = DijkstraAlgorithm.searchGraphDepthFirstCheapestPath(nodes.get(startPosition), null, 0, nodes.get(endPosition));

                for (GraphNode<Vertex> n : cp.pathList) {
                    mapPane.getChildren().addAll(drawNodes(n.getData().getxCoord(), n.getData().getyCoord(), Color.PINK, n.getData().getName(),n.getData().getRoomNum(), n.getImage()));

                }
                    int j = 0;
                    int k = j + 1;
                    for (int l = 0; l < cp.pathList.size(); l++) {
                            if (k < cp.pathList.size()) {
                                int x1 = cp.pathList.get(j).getData().getxCoord();
                                int y1 = cp.pathList.get(j).getData().getyCoord();
                                int x2 = cp.pathList.get(k).getData().getxCoord();
                                int y2 = cp.pathList.get(k).getData().getyCoord();
                                mapPane.getChildren().add(connectNodes(x1, y1, x2, y2, Color.BLUE));
                            }
                            j++;
                            k++;
                        }
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

    public Rectangle drawNodes(int x, int y, Color color,String name, String roomNum, Image image) {
        Rectangle rec = new Rectangle(x, y, 10, 10);
        rec.setStroke(Color.TRANSPARENT);
        rec.setFill(color);
        rec.setOpacity(0.3);
        Tooltip rect = new Tooltip("Room Name: " + name + "\n" +" Number :" + roomNum + "\n");
        Tooltip.install(rec, rect);
        rect.setGraphic(new ImageView(image));
        return rec;
    }

    public Image getImageFromURL(String url) throws IOException {
        Image image = new Image(url);
        return image;
    }

}


