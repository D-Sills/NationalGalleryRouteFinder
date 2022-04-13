package Utilities;
/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

import java.util.*;

/**
 * A disjoint set is a generic data structure that represents a collection of
 * sets that are assumed to be disjoint (no object exists in more than
 * one set).
 * <p>
 * This disjoint set implementation represents the disjoint set as a forest,
 * where the nodes of each tree all belong to the same set. This implementation
 * uses path compression in the findSet implementation to flatten each tree
 * to a constant depth.  A rank is maintained for each tree that is used when
 * performing union operations to ensure the tree remains balanced.
 * <p>
 * Ref: Cormen, Leiserson, and Rivest <it>Introduction to Algorithms,
 * McGraw-Hill, 1990. The disjoint set forest implementation in section 22.3.
 * </p>
 * @since 3.2
 */
public class DisjointSet<E> {
		public List<DisjointSetNode<E>> parents = new ArrayList<>();

		/**
		 * A node in the disjoint set forest.  Each tree in the forest is
		 * a disjoint set, where the root of the tree is the set representative.
		 */
		private class Node {
				/** The node rank used for union by rank optimization */
				int rank;
				/** The parent of this node in the tree. */
				Node parent;

				/** The element belonging to this node */
				E element;

				Node(E element, Node parent, int rank) {
						this.parent = parent;
						this.rank = rank;
						this.element = element;
				}
		}

		/**
		 * Map of Object -> Node, where each key is an object in the
		 * disjoint set, and the Node represents its position and rank
		 * within the set.
		 */
		private final HashMap<E,DisjointSetNode<E>> objectsToNodes = new HashMap<>();

		/**
		 * Returns the set token for the given object, or null if the
		 * object does not belong to any set.  All object
		 * in the same set have an identical set token.
		 * @param o The object to return the set token for
		 * @return The set token, or <code>null
		 */
		public E findSet(E o) {
				DisjointSetNode<E> node = objectsToNodes.get(o);
				DisjointSetNode<E> res = findSet(node);
				return res.getData();
		}

		public DisjointSetNode<E> findSet(DisjointSetNode<E> node) {
				if (node == null)
						return null;
				if (node != node.getParent())
						node.setParent(findSet(node.getParent()));
				return node.getParent();
		}
		/**
		 * Adds a new set to the group of disjoint sets for the given object.
		 * It is assumed that the object does not yet belong to any set.
		 * @param o The object to add to the set
		 */
		public void makeSet(E o) {
				DisjointSetNode<E> n = new DisjointSetNode<>(o, null, 0);
				n.setParent(n);
				n.getContents().add(n);
				objectsToNodes.put(o, n);
		}


		/**
		 * Copies all objects in the disjoint set to the provided list
		 * @param list The list to copy objects into
		 */
		public void toListE(List<E> list) {
				list.addAll(objectsToNodes.keySet());
		}
		/**
		 * Copies all objects in the disjoint set to the provided list
		 * @param list The list to copy objects into
		 */
		public void toListNode(List<DisjointSetNode<E>> list) {
				list.addAll(objectsToNodes.values());
		}
		// Finds the representative of the set that x
		// is an element of
		public DisjointSetNode<E> find(E x)
		{
				DisjointSetNode<E> node = objectsToNodes.get(x);
				while(node.getParent()!=null) {
						if(node.getParent().getParent()!=null) node.setParent(node.getParent().getParent());//Compress path
						node.setParent(node.getParent());
				}
				return node;
		}

		/**
		 * Removes all elements belonging to the set of the given object.
		 * @param o The object to remove
		 */
		public void removeSet(E o) {
				E set = findSet(o);
				if (set == null)
						return;
				//remove the set representative last, otherwise findSet will fail
				objectsToNodes.keySet().removeIf(next -> next != set && findSet(next) == set);
				objectsToNodes.remove(set);
		}

		public DisjointSetNode<E> getNode (E x) {
				return objectsToNodes.get(x);
		}

		// Unites the set that includes x and the set
		// that includes y
		public void union(E x, E y)
		{
				E setX = findSet(x);
				E setY = findSet(y);
				if (setX == null || setY == null || setX == setY)
						return;

				DisjointSetNode<E> xRoot = objectsToNodes.get(setX);
				DisjointSetNode<E> yRoot = objectsToNodes.get(setY);
				//List<DisjointSetNode<E>> old = new ArrayList<>();
				//old.addAll(xRoot.getContents());
				//old.addAll(yRoot.getContents());
				//System.out.println(old);
				//System.out.println(old);
				//xRoot.getContents().clear();
				//yRoot.getContents().clear();
				// If x's rank is less than y's rank
				// Then move x under y  so that depth of tree
				// remains less
				if (xRoot.getRank() < yRoot.getRank()) {
						xRoot.setParent(yRoot);
						yRoot.getContents().addAll(xRoot.getContents());
						xRoot.getContents().clear();
						xRoot.getContents().add(xRoot);
						System.out.println(yRoot.getContents().size());
				}
						// Else if y's rank is less than x's rank
						// Then move y under x so that depth of tree
						// remains less
				else if(yRoot.getRank()< xRoot.getRank()) {
						yRoot.setParent(xRoot);
						xRoot.getContents().addAll(yRoot.getContents());
						yRoot.getContents().clear();
						yRoot.getContents().add(yRoot);
						System.out.println(xRoot.getContents().size());
				}
				else  // Else if their ranks are the same
				{
						// Then move y under x (doesn't matter
						// which one goes where)
						yRoot.setParent(xRoot);
						xRoot.getContents().addAll(yRoot.getContents());
						yRoot.getContents().clear();
						yRoot.getContents().add(yRoot);
						System.out.println(xRoot.getContents().size());
						// And increment the result tree's
						// rank by 1
						xRoot.rank++;
				}
		}

		public void populateParents() {
				parents.clear();
				for (DisjointSetNode<E> node: objectsToNodes.values()) {
						if (node.getParent() == node && node.getContents().size() >= 2 ){
								parents.add(node);
						}
				}
				parents.sort(Comparator.comparingInt(o -> o.getContents().size()));
				Collections.reverse(parents);
		}
}