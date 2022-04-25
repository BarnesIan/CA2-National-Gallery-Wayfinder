package dijkstra;

public class GraphLink {
    public GraphNode<?> destNode; // Could also store source node if required
    public int cost;

    public GraphLink(GraphNode<?> destNode, int cost) {
        this.destNode = destNode;
        this.cost = cost;
    }
}
