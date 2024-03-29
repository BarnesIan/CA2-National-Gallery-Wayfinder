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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public List<GraphLink> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<GraphLink> nodeList) {
        this.nodeList = nodeList;
    }
}
