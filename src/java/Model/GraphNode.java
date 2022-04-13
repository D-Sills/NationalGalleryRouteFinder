package Model;

public class GraphNode<T> {
		private T data; // data carried by the node
		public AdjacencyMatrix mat;
		public int nodeId;

		public GraphNode(T data,AdjacencyMatrix mat) {
				this.data = data;
				this.mat = mat;
				mat.nodes[nodeId = mat.nodeCount++] = this;
		}

				public T getData() {
				return data;
		}

		public void connectToNodeDirected(GraphNode<T> destNode) {
				mat.amat[nodeId][destNode.nodeId]=true;
		}
		public void connectToNodeUndirected(GraphNode<T> destNode) {
				mat.amat[nodeId][destNode.nodeId]=mat.amat[destNode.nodeId][nodeId]=true;
		}


}