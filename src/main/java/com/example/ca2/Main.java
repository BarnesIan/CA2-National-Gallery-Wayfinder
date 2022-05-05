package com.example.ca2;

import dijkstra.DijkstraAlgorithm;
import dijkstra.GraphNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("routeFinder.FXML"));
        stage.setTitle("National Gallery Route Finder");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {

  /*      GraphNode<String> a = new GraphNode<>("Silver");
        GraphNode<String> b = new GraphNode<>("Bronze");
        GraphNode<String> c = new GraphNode<>("Lead");
        GraphNode<String> d = new GraphNode<>("Tin");
        GraphNode<String> e = new GraphNode<>("Copper");
        GraphNode<String> f = new GraphNode<>("Brass");
        GraphNode<String> g = new GraphNode<>("Iron");
        GraphNode<String> h = new GraphNode<>("Gold");

        a.connectToNodeUndirected(b, 5);
        a.connectToNodeUndirected(c, 9);
        b.connectToNodeUndirected(c, 2);
        b.connectToNodeUndirected(d, 6);
        c.connectToNodeUndirected(e, 5);
        d.connectToNodeUndirected(h, 8);
        d.connectToNodeUndirected(g, 9);
        e.connectToNodeUndirected(g, 3);
        e.connectToNodeUndirected(f, 6);
        f.connectToNodeUndirected(g, 6);
        g.connectToNodeUndirected(h, 2);

        DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPathDijkstra(a, "Gold");

        for (GraphNode<?> n : cpa.pathList)
            System.out.println(n.data);
        System.out.println("\n The total path cost is: " + cpa.pathCost);*/

        launch();
    }
}