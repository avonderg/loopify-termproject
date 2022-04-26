package edu.brown.cs.student.main.routefindermaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a graph of nodes.
 *
 * source: https://www.softwaretestinghelp.com/java-graph-tutorial/
 */
public class Graph {

  // A list of lists to represent an adjacency list
  private List<List<Node>> adjList = new ArrayList<>();

  /**
   * Getter method for the adjacency list of a graph.
   * @return adjList
   */
  public List<List<Node>> getAdjList() {
    return adjList;
  }

  // Constructor to construct a graph
  public Graph(List<Edge> edges) {
//    // find the maximum numbered vertex
//    int n = 0;
//    for (Edge e: edges) {
//      n = Integer.max(n, Integer.max(e.getSrc(), e.getDest()));
//    }
//
//    // allocate memory for the adjacency list
//    for (int i = 0; i <= n; i++) {
//      adjList.add(i, new ArrayList<>());
//    }
//
//    // add edges to the directed graph
//    for (Edge current: edges)
//    {
//      // allocate new node in adjacency list from src to dest
//      adjList.get(current.getSrc()).add(current.getDest());
//
//      // uncomment below line for undirected graph
//
//      // allocate new node in adjacency list from dest to src
//      // adjList.get(current.dest).add(current.src);
//    }
    // adjacency list memory allocation
    for (int i = 0; i < edges.size(); i++)
      adjList.add(i, new ArrayList<>());

    // add edges to the graph
    for (Edge e : edges) {
      // allocate new node in adjacency List from src to dest
      int index = adjList.indexOf(e.getSrc());
      adjList.get(index).add(new Node(e.getDest().getLatitude(), e.getDest().getLongitude()));
    }
  }
}
