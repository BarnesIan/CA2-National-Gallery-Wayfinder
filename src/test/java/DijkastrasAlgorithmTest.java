import static org.junit.Assert.assertEquals;

import dijkstra.DijkstraAlgorithm;
import dijkstra.GraphLink;
import dijkstra.GraphNode;
import dijkstra.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DijkastrasAlgorithmTest {
    List<GraphNode<Vertex>> avoidNodes = new ArrayList<>();
    List<GraphNode<Vertex>> nodes = new ArrayList<>();
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
        DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPathDijkstra(aNode, dNode.getData(),null);
        System.out.println(cpa.pathCost);
        assertEquals(12,cpa.pathCost);
    }

    @Test
    public void testDijkstraAlgorithmWithAvoidance(){
        avoidNodes.add(bNode);
        DijkstraAlgorithm.CostedPath cpa = DijkstraAlgorithm.findCheapestPathDijkstra(aNode, dNode.getData(),avoidNodes);
        System.out.println(cpa.pathCost);
        assertEquals(32,cpa.pathCost);
    }
    @Test
    public void testAddNode(){
        //   System.out.println(nodes.size());
        GraphNode newNode = new GraphNode<>(a);
        nodes.add(newNode);
        assertEquals(1,nodes.size());

    }
    @Test
    public void testDeleteNode(){
        GraphNode newNode = new GraphNode<>(a);
        GraphNode newNode1 = new GraphNode<>(b);
        nodes.add(newNode);
        nodes.add(newNode1);
        nodes.remove(newNode);
        assertEquals(1,nodes.size());
    }

    private static int calcDistance(int node1X, int node1Y, int node2X, int node2Y) {
        double distance = Math.sqrt((node2Y - node1Y) * (node2Y - node1Y) + (node2X - node1X) * (node2X - node1X));
        return (int) distance;
    }
}
