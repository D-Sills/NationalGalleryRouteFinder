import Model.CustomGraph;
import Model.GraphNode;
import Model.Room;
import application.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static Model.CustomGraph.findCheapestPathDijkstra;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomGraphTest {
		GraphNode<String> a;
		GraphNode<String> b;
		GraphNode<String> c;
		GraphNode<String> d;
		GraphNode<String> e;
		GraphNode<String> f;
		GraphNode<String> g;
		GraphNode<String> h;

		@BeforeEach
		void setUp() {
				a=new GraphNode<>("Cherry");
				b=new GraphNode<>("Apple");
				c=new GraphNode<>("Plum");
				d=new GraphNode<>("Mango");
				e=new GraphNode<>("Kiwi");
				f=new GraphNode<>("Coconut");
				g=new GraphNode<>("Pear");
				h=new GraphNode<>("Orange");

				a.connectToNodeUndirected(b, 5);
				a.connectToNodeUndirected(c, 9);
				b.connectToNodeUndirected(c, 2);
				b.connectToNodeUndirected(d, 6);
				c.connectToNodeUndirected(e, 5);
				d.connectToNodeUndirected(h, 8);
				d.connectToNodeUndirected(g, 9);
				e.connectToNodeUndirected(g, 3);
				e.connectToNodeUndirected(f, 7);
				f.connectToNodeUndirected(g, 6);
				g.connectToNodeUndirected(h, 2);
		}

		@Test
		void testDjikstra()
		{
				var cpa = findCheapestPathDijkstra(a, null, h.getData());
				assertEquals(17, cpa.pathCost);
		}
}