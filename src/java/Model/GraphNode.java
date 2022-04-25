package Model;

import java.util.LinkedList;
import java.util.List;

public class GraphNode<T> {
		private T data; // data carried by the node
		private int nodeValue=Integer.MAX_VALUE;
		private List<GraphLink> adjList=new LinkedList<>();

		public GraphNode(T data) {
				this.data=data;
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

		public List<GraphLink> getAdjList() {
				return adjList;
		}

		public void setAdjList(List<GraphLink> adjList) {
				this.adjList = adjList;
		}

		public void connectToNodeDirected(GraphNode<T> destNode, int cost) {
				adjList.add(new GraphLink(destNode,cost));
		}
		public void connectToNodeUndirected(GraphNode<T> destNode,int cost) {
				adjList.add(new GraphLink(destNode,cost));
				destNode.adjList.add(new GraphLink(this,cost));
		}



}