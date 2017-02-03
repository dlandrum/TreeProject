/*
 * Author: Don Landrum
 * Date Created: January 11, 2017
 * Date Last Modified: February 3, 2017
 * 
 * Notes:
 * This file contains a tree that will store nodes, assign edges, add weights, 
 * and traverse between any two nodes. It contains several methods.
 * addVertices populates the variable "nodes" with nodes.
 * addEdges creates an edge between two specified nodes.
 * addValue uses findPath and DFS to help it add an integer "aValue" to the 
 *     value of each appropriate node in the tree.
 * DFS recursively calls itself to perform a depth-first search, adding an 
 *     input "aValue" to each unmarked node that it encounters.
 * max uses findPath to locate the highest value amongst all the nodes on the
 *     path between two given nodes.
 * findPath uses the helper method findPathHelper to find the path between two 
 *     given nodes.
 * findPathHelper recursively calls itself and uses ArrayLists to locate the 
 *     path between two given nodes. Ultimately, it returns its findings to its
 *     parent method in the form of an ArrayList.
 */

import java.util.ArrayList;
import java.util.HashSet;
public class Tree {
	class Node {
		//"edges" contains the names of a Node's neighbors, not their locations.
		ArrayList<Integer> edges;
		int name;
		int value;
		public Node(int aName) {
			this.edges = new ArrayList<Integer>();
			this.name = aName;
			this.value = 0;
		}
	}
	Node[] nodes;
	public Tree(int n) {
		//This constructor gives a size to "nodes" and adds vertices to it.
		this.nodes = new Node[n];
		addVertices(n);
	}
	private void addVertices(int n) { 
		//Node 1 (named 1) is at index 0, and so on.
		for (int i = 0; i < n; ++i) {
			Node aNode = new Node(i+1);
			nodes[i] = aNode;
		}
	}
	public void addEdge(int x, int y) {
		//The node named x (at x-1) is adjacent to the node named y (at y-1).
		//"edges" stores names, not locations, so we add y and x respectively.
		nodes[x-1].edges.add(y); 
		nodes[y-1].edges.add(x);
	}
	public void addValue(int t, int aValue) {
	/*
	 * This first line calls the findPath method to determine the path to 1.
	 * The reason for this is to determine which nodes are "below" t.
	 * Since names and edges are assigned arbitrarily, it is not obvious
	 * whether a given neighbor is "above" or "below" t, so we find the path
	 * from t to 1 and mark each node in that path to avoid adding to them.
	 * By default, all neighbors not in this path are "below" t, so we add
	 * "aValue" to them.
	 */
		ArrayList<Integer> path = findPath(t, 1); 
		ArrayList<Integer> marked = new ArrayList<Integer>();
		for (Integer s : path) {
			//This if statement is necessary because we do want to add to "t".
			if (s != t) {
				marked.add((s));
			}
		}
		//This line calls the DFS method to actually augment the values.
		DFS(nodes[t-1], marked, aValue);
	}
	private void DFS(Node aNode, ArrayList<Integer> aMarked, int aValue) {
		//This method is called recursively by itself. It uses depth-first
		//search to add aValue to all nodes not yet marked.
		if (aMarked.contains(aNode.name)) {
			return;
		}
		aNode.value += aValue; 
		aMarked.add(aNode.name); 
		//This for loop keeps recursively calling the method on its neighbors.
		for (int i : aNode.edges) {
			DFS(nodes[i-1], aMarked, aValue);
		}
	}
	public int max(int a, int b) {
		//This method calls the findPath method and then compares the value
		//of each node in that path, returning the greatest one.
		ArrayList<Integer> path = findPath(a,b); 
		//We assume that the value of the last node in the path is largest.
		int max = nodes[path.get(path.size()-1)-1].value;
		for (int i = 0; i < path.size()-1; ++i) { 
			//"val" is the value of the ith node
			int val = nodes[path.get(i)-1].value;
			if (val > max) {
				max = val;
			}
		}
		return max;
	}
	private ArrayList<Integer> findPath(int start, int end) {
		//This method uses a helper function to find the path from one node to
		//another. It is worth noting that the start and end inputs are names, 
		//not locations.
		HashSet<Integer> marked = new HashSet<Integer>();
		ArrayList<Integer> empty = new ArrayList<Integer>();
		ArrayList<Integer> s = findPathHelper(start, end, marked, empty);
		return s;
	}
	private ArrayList<Integer> findPathHelper(int start, int end, 
			HashSet<Integer> aMarked, ArrayList<Integer> path) {
		//This helper method does the work of finding the path and returning it
		//in an ArrayList. 
		aMarked.add(start);
		if (start == end) {
			path.add(end);
			return path;
		}
		ArrayList<Integer> a = new ArrayList<Integer>();
		//For each unmarked neighbor of the node named "start", we recursively 
		//call this helper method again using that neighbor as "start".
		for (int i : nodes[start-1].edges) {
			if (!(aMarked.contains(i))) {
				ArrayList<Integer> l = findPathHelper(i, end, aMarked, path);
				//By placing an if statement here, I gain the ability to insert
				//a break statement. Since I know that only one ArrayList "l" 
				//will contain the desired data, by breaking out of the for 
				//loop once that ArrayList is found, I am saving myself some 
				//extra iterations.
				if (l.contains(end)) {
					//This for loop adds the contents of "l" to "a". 
					//Interestingly enough, a for loop must be used as opposed 
					//to a for each loop, as the latter will throw a 
					//concurrent modification exception.
					for (int j = 0; j < l.size(); ++j) {
						a.add(l.get(j));
					}
					break;
				}
			}
		}
		for (Integer s : a) {
			//Much like the one above, this if statement allows me to cut time
			//out of my program by ending this for loop once a suitable match
			//is found and added to path.
			if (s != null) {
				path.add(s);
				break;
			}
		}
		//If we have found a valid path, we add "start" to that path.
		if (!(path.isEmpty())) {
			path.add(start);
		}
		return path;
	}
}