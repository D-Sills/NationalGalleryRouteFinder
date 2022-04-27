package Model;

import java.util.*;

public class CustomGraph {
		private static List<List<GraphNode<?>>> allPathsFinal = new ArrayList<>();

		public static List<List<GraphNode<?>>> getAllPathsFinal() {
				return allPathsFinal;
		}

		//New class to hold a CostedPath object i.e. a list of GraphNodeAL2 objects and a total cost attribute
		public static class CostedPath{
				public int pathCost=0;
				public List<GraphNode<?>> pathList=new ArrayList<>();

				@Override
				public String toString() {
						return "Route " + 1;
				}
		}
		//Retrieve cheapest path by expanding all paths recursively depth-first
		public static <T> List<List<GraphNode<?>>> findAllPathsDepthFirst(GraphNode<?> from, List<GraphNode<?>> encountered, T lookingfor) {
				List<List<GraphNode<?>>> result=null;
				List<List<GraphNode<?>>> temp2;
				if(from.getData().equals(lookingfor)) { //Found it
						List<GraphNode<?>> temp=new ArrayList<>(); //Create new single solution path list
						temp.add(from); //Add current node to the new single path list
						result=new ArrayList<>(); //Create new "list of lists" to store path permutations
						result.add(temp); //Add the new single path list to the path permutations list
						return result; //Return the path permutations list
				}
				if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
				encountered.add(from); //Add current node to encountered list
				for(GraphLink adjNode : from.getAdjList()){
						if(!encountered.contains(adjNode.getDestNode())) {
								temp2=findAllPathsDepthFirst(adjNode.getDestNode(),new ArrayList<>(encountered),lookingfor); //Use clone of encountered list
//for recursive call!
								if(temp2!=null) { //Result of the recursive call contains one or more paths to the solution node
										for(List<GraphNode<?>> x : temp2) //For each partial path list returned
												x.add(0,from); //Add the current node to the front of each path list
										if(result==null) result=temp2; //If this is the first set of solution paths found use it as the result
										else result.addAll(temp2); //Otherwise append them to the previously found paths
								}
						}
				}
				return result;
		}

		public static <T> CostedPath findCheapestPathDijkstra(GraphNode<?> startNode, T lookingfor){
				CostedPath cp=new CostedPath(); //Create result object for cheapest path
				List<GraphNode<?>> encountered=new ArrayList<>();
				List<GraphNode<?>>unencountered=new ArrayList<>(); //Create encountered/unencountered lists
				startNode.setNodeValue(0); //Set the starting node value to zero
				unencountered.add(startNode); //Add the start node as the only value in the unencountered list to start
				GraphNode<?> currentNode;
				do{ //Loop until unencountered list is empty
						currentNode=unencountered.remove(0); //Get the first unencountered node (sorted list, so will have lowest value)
						encountered.add(currentNode); //Record current node in encountered list
						if(currentNode.getData().equals(lookingfor)){ //Found goal - assemble path list back to start and return it
								cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
								cp.pathCost=currentNode.getNodeValue(); //The total cheapest path cost is the node value of the current/goal node
								while(currentNode!=startNode) { //While we're not back to the start node...
										boolean foundPrevPathNode=false; //Use a flag to identify when the previous path node is identified
										for(GraphNode<?> n : encountered) { //For each node in the encountered list...
												for(GraphLink e : n.getAdjList()) //For each edge from that node...
														if(e.getDestNode()==currentNode && currentNode.getNodeValue()-e.getCost()==n.getNodeValue()){ //If that edge links to the
//current node and the difference in node values is the cost of the edge -> found path node!
																cp.pathList.add(0,n); //Add the identified path node to the front of the result list
																currentNode=n; //Move the currentNode reference back to the identified path node
																foundPrevPathNode=true; //Set the flag to break the outer loop
																break; //We've found the correct previous path node and moved the currentNode reference
//back to it so break the inner loop
														}
												if(foundPrevPathNode) break; //We've identified the previous path node, so break the inner loop to continue
										}
								}
//Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
								for(GraphNode<?> n : encountered) n.setNodeValue(Integer.MAX_VALUE);
								for(GraphNode<?> n : unencountered) n.setNodeValue(Integer.MAX_VALUE);
								return cp; //The costed (cheapest) path has been assembled, so return it!
						}
//We're not at the goal node yet, so...
						for(GraphLink e : currentNode.getAdjList()) //For each edge/link from the current node...
								if(!encountered.contains(e.getDestNode())) { //If the node it leads to has not yet been encountered (i.e. processed)
										e.getDestNode().setNodeValue(Integer.min(e.getDestNode().getNodeValue(), currentNode.getNodeValue()+e.getCost())); //Update the node value at the end
										//of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
										unencountered.add(e.getDestNode());
								}
						unencountered.sort(Comparator.comparingInt(GraphNode::getNodeValue)); //Sort in ascending node value order
				}while(!unencountered.isEmpty());
				return null; //No path found, so return null
		}

		public static CostedPath findInterestingPathDijkstra(GraphNode<?> startNode, List<GraphNode<?>> ignore, List<GraphNode<?>> lookingfor){
				CostedPath cp=new CostedPath(); //Create result object for cheapest path

				int i =0;
				for (GraphNode<?> graphNode : lookingfor) {
						List<GraphNode<?>> unencountered = new ArrayList<>();
						List<GraphNode<?>> encountered = new ArrayList<>(ignore);
						i++;
						//Create encountered/unencountered lists
						startNode.setNodeValue(0); //Set the starting node value to zero
						unencountered.add(0, startNode); //Add the start node as the only value in the unencountered list to start
						do { //Loop until unencountered list is empty
								//Get the first unencountered node (sorted list, so will have lowest value)
								GraphNode<?> currentNode = unencountered.remove(0);
								encountered.add(currentNode); //Record current node in encountered list
								//cp.pathList.add(currentNode);
								if (currentNode.getData().equals(graphNode.getData())) {
										GraphNode<?> tmp = currentNode;
										//Found goal - assemble path list back to start and return it
										cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
										 //The total cheapest path cost is the node value of the current/goal node


												cp.pathCost = currentNode.getNodeValue();
												while (currentNode != startNode) { //While we're not back to the start node...
														boolean foundPrevPathNode = false; //Use a flag to identify when the previous path node is identified
														for (GraphNode<?> n : encountered) { //For each node in the encountered list...
																for (GraphLink e : n.getAdjList()) //For each edge from that node...
																		if (e.getDestNode() == currentNode && currentNode.getNodeValue() - e.getCost() == n.getNodeValue()) { //If that edge links to the
																				//current node and the difference in node values is the cost of the edge -> found path node!
																				cp.pathList.add(0,n); //Add the identified path node to the front of the result list
																				currentNode = n; //Move the currentNode reference back to the identified path node
																				foundPrevPathNode = true; //Set the flag to break the outer loop

																				break;
																				//We've found the correct previous path node and moved the currentNode reference
																				//back to it so break the inner loop
																		}
																if (foundPrevPathNode) {
																		break;//We've identified the previous path node, so break the inner loop to continue
																}
														}
												}

//Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
										for (GraphNode<?> n : encountered) n.setNodeValue(Integer.MAX_VALUE);
										for (GraphNode<?> n : unencountered) n.setNodeValue(Integer.MAX_VALUE);
										startNode = tmp;
										break;//The costed (cheapest) path has been assembled, so return it!
								}
//We're not at the goal node yet, so...
								for (GraphLink e : currentNode.getAdjList()) //For each edge/link from the current node...
										if (!encountered.contains(e.getDestNode())) { //If the node it leads to has not yet been encountered (i.e. processed)
												e.getDestNode().setNodeValue(Integer.min(e.getDestNode().getNodeValue(), currentNode.getNodeValue() + e.getCost())); //Update the node value at the end
												//of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
												unencountered.add(e.getDestNode());

										}
								unencountered.sort(Comparator.comparingInt(GraphNode::getNodeValue)); //Sort in ascending node value order
						} while (!unencountered.isEmpty());

				}
				return cp;
		}

		//Interface method to allow just the starting node and the goal node data to match to be specified
		public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(GraphNodeAL<?> startNode, T lookingfor){
				List<List<GraphNodeAL<?>>> agenda=new ArrayList<>(); //Agenda comprised of path lists here!
				List<GraphNodeAL<?>> firstAgendaPath=new ArrayList<>();
				List<GraphNodeAL<?>> resultPath;
				firstAgendaPath.add(startNode);
				agenda.add(firstAgendaPath);
				resultPath=findPathBreadthFirst(agenda,null,lookingfor); //Get single BFS path (will be shortest)
				Collections.reverse(Objects.requireNonNull(resultPath)); //Reverse path (currently has the goal node as the first item)
				return resultPath;
		}
		//Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
		public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(List<List<GraphNodeAL<?>>> agenda,
		                                                            List<GraphNodeAL<?>> encountered, T lookingfor){
				if(agenda.isEmpty()) return Collections.emptyList(); //Search failed
				List<GraphNodeAL<?>> nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
				GraphNodeAL<?> currentNode=nextPath.get(0); //The first item in the next path is the current node
				if(currentNode.data.equals(lookingfor)) return nextPath; //If that's the goal, we've found our path (so return it)
				if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty)encountered list
				encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
				for(GraphNodeAL<?> adjNode : currentNode.adjList) //For each adjacent node
						if(!encountered.contains(adjNode)) { //If it hasn't already been encountered
								List<GraphNodeAL<?>> newPath=new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
								newPath.add(0,adjNode); //And add the adjacent node to the front of the new copy
								agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
						}
				return findPathBreadthFirst(agenda,encountered,lookingfor); //Tail call
		}



}
