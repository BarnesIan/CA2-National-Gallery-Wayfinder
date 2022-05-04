package com.example.ca2;

import dijkstra.DijkstraAlgorithm;
import dijkstra.GraphLink;
import dijkstra.GraphNode;
import dijkstra.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    Button showRoutes,restart;
    @FXML
    ComboBox<Vertex> start, destination;

    private GraphNode graphNode;
    private DijkstraAlgorithm da;
//    List<GraphNode<Vertex>> nodes = new ArrayList<>();
    GraphNode<Vertex>[] nodes = new GraphNode[300];



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainimage.setImage(new Image(FXController.class.getResource("map.jpg").toExternalForm()));
        graphNode = new GraphNode(300);
        da = new DijkstraAlgorithm();
        loadData();

    }

    public void loadData(){
        String line = "";
        BufferedReader br = null;
        BufferedReader br2 = null;
        Random rand = new Random();
        String[] vertices = new String[0];
        int counter = -1;

        try {
            br = new BufferedReader(new FileReader("src/main/resources/com/example/ca2/Vertices.txt"));
            while ((line = br.readLine()) != null){
                counter++;
                vertices = line.split(",");
                String roomNum = vertices[0];
                String name = vertices[1];
                int x = Integer.valueOf(vertices[2]);
                int y = Integer.valueOf(vertices[3]);
                System.out.println("x: " + x + " y: " + y + " name : " + name);
//                nodes.add(new GraphNode<>(new Vertex(roomNum,name, x, y)));
                nodes[counter] = new GraphNode<Vertex>(new Vertex(roomNum,name, x, y));
            }

            br2 = new BufferedReader(new FileReader("src/main/resources/com/example/ca2/Edges.txt"));
            line = "";
            while ((line = br2.readLine()) != null) {
                String[] edges = line.split(",");

//                    int danger = rand.nextInt(1000);
//                    int difficulty = rand.nextInt(1000);
                int con1 = Integer.parseInt(edges[0]);
                String con2 = edges[1];
//                    GraphNode<Vertex>[] nodeArray = graphNode.nodeList;

                int vertex1 = -1;
                int vertex2 = -1;
                boolean found1 = false;
                boolean found2 = false;
                System.out.println(con1 + " connected to " + con2);

                for (GraphNode<Vertex> v : nodes) {
                    if (v != null) {
                        if (v.getData().getRoomNum().equals(con1)) {
                            vertex1 = v.getNodeValue();
                            found1 = true;
                        } else if (v.getData().getRoomNum().equals(con2)) {
                            vertex2 = v.getNodeValue();
                            found2 = true;
                        }
                    }
                }
                if (vertex1 != -1 || vertex2 != -1) {
                    if (found1 && found2) {
                        GraphLink graphLink = new GraphLink(nodes[vertex2], 3);

                        nodes[vertex1].connectToNodeUndirected(nodes[vertex2],graphLink.cost);

                    }
                }


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

            System.out.println(nodes[0].nodeList.size());

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
    public void close() {
        System.exit(0);
    }

    public void restart(ActionEvent event) {
    }

    public void showRoutes(ActionEvent event) {
//    int start =
    }
    // The disatnce formula for calculating the distance between two points
    private static int calcDistance(int node1X, int nod1Y, int node2X, int node2Y) {
        double distance = Math.sqrt((node2Y - nod1Y) * (node2Y - nod1Y) + (node2X - node1X) * (node2X - node1X));
        return (int) distance;
    }
}

