package dijkstra;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
    public T data;
    public int nodeValue = Integer.MAX_VALUE;

    public List<GraphLink> nodeList = new ArrayList<>();

    public GraphNode(T data) {
        this.data = data;
    }

    public void connectToNodeDirected(GraphNode<T> destNode, int cost) {
        nodeList.add(new GraphLink(destNode, cost));
    }

    public void connectToNodeUndirected(GraphNode<T> destNode, int cost) {
        nodeList.add(new GraphLink(destNode, cost));
        destNode.nodeList.add(new GraphLink(this, cost));
    }
}
