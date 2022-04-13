package Utilities;

import java.util.LinkedList;
import java.util.List;

public class DisjointSetNode<E> {
		private DisjointSetNode<E> parent; // reference to the next node in the list, or null if there isn't one
		private E data; // data carried by the node
		public int rank;
		private List<DisjointSetNode<E>> contents;
		private String compName;

		public DisjointSetNode(E data, DisjointSetNode<E> parent, int rank) {
				this.parent = parent;
				this.data=data;
				this.rank = rank;
				contents = new LinkedList<>();
				compName = "Unknown";
		}

		public int getRank() {
				return rank;
		}

		public void setRank(int rank) {
				this.rank = rank;
		}

		public E getData() {
				return data;
		}

		public void setData(E data) {
				this.data = data;
		}

		public DisjointSetNode<E> getParent() {
				return parent;
		}

		public void setParent(DisjointSetNode<E> parent) {
				this.parent = parent;
		}

		public List<DisjointSetNode<E>> getContents() {
				return contents;
		}

		public void setContents(List<DisjointSetNode<E>> contents) {
				this.contents = contents;
		}

		public String getCompName() {
				return compName;
		}

		public void setCompName(String compName) {
				this.compName = compName;
		}
}
