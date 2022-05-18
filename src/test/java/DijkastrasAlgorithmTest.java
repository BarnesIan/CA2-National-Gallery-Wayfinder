import static org.junit.Assert.assertEquals;

import dijkstra.DijkstraAlgorithm;
import dijkstra.GraphLink;
import dijkstra.GraphNode;
import dijkstra.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DijkastrasAlgorithmTest {
    Vertex a;
    Vertex b;
    Vertex c;
    Vertex d;
    GraphNode<Vertex> aNode;
    GraphNode<Vertex> bNode;
    GraphNode<Vertex> cNode;
    GraphNode<Vertex> dNode;
    GraphLink aConB;
    GraphLink bConD;
    GraphLink aConC;
    GraphLink CConD;

    @Before
    public void setUp() {

        a = new Vertex("0", "TestRoomA", 10, 10);
        b = new Vertex("1", "TestRoomB",5,5);
        c = new Vertex("2", "TestRoomC", 20, 13);
        d = new Vertex("3", "TestRoomD",2,0);

        aNode = new GraphNode<>(a);
        bNode = new GraphNode<>(b);
        cNode = new GraphNode<>(c);
        dNode = new GraphNode<>(d);

        aConB = new GraphLink(bNode,calcDistance(a.getxCoord(),a.getyCoord(),b.getxCoord(),b.getyCoord()));
        bConD = new GraphLink(dNode,calcDistance(b.getxCoord(),b.getyCoord(),d.getxCoord(),d.getyCoord()));
        aConC = new GraphLink(cNode,calcDistance(a.getxCoord(),a.getyCoord(),c.getxCoord(),c.getyCoord()));
        CConD = new GraphLink(dNode,calcDistance(c.getxCoord(),c.getyCoord(),d.getxCoord(),d.getyCoord()));


        aNode.connectToNodeUndirected(bNode,calcDistance(a.getxCoord(),a.getyCoord(),b.getxCoord(),b.getyCoord()));
        bNode.connectToNodeUndirected(dNode,calcDistance(b.getxCoord(),b.getyCoord(),d.getxCoord(),d.getyCoord()));
        aNode.connectToNodeUndirected(cNode,calcDistance(a.getxCoord(),a.getyCoord(),c.getxCoord(),c.getyCoord()));
        cNode.connectToNodeUndirected(dNode,calcDistance(c.getxCoord(),c.getyCoord(),d.getxCoord(),d.getyCoord()));
    }

    @Test
    public void testDijkstraAlgorithm(){
        DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPathDijkstra(aNode, dNode.getData());
        //System.out.println(cpa.pathCost);
        assertEquals(12,cpa.pathCost);
    }

    private static int calcDistance(int node1X, int node1Y, int node2X, int node2Y) {
        double distance = Math.sqrt((node2Y - node1Y) * (node2Y - node1Y) + (node2X - node1X) * (node2X - node1X));
        return (int) distance;
    }
}
