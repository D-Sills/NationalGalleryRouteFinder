package Model;

public class GraphLink {
		private GraphNode<?> destNode; //Could also store source node if required
		private int cost; //Other link attributes could be similarly stored

		public GraphLink(GraphNode<?> destNode,int cost) {
				this.destNode=destNode;
				this.cost=cost;
		}

		public GraphNode<?> getDestNode() {
				return destNode;
		}

		public void setDestNode(GraphNode<?> destNode) {
				this.destNode = destNode;
		}

		public int getCost() {
				return cost;
		}

		public void setCost(int cost) {
				this.cost = cost;
		}
}

