package edu.brown.cs.student.main.routefindermaps;

/**
 * Class representing a vertex within a graph.
 */
public class Edge {
  private Node src;
  private Node dest;

  /**
   * Constructor for the Edge class.
   * @param src - source node
   * @param dest - destination node
   */
  public Edge(Node src, Node dest) {
    this.src = src;
    this.dest = dest;
  }

  /**
   * Getter method for the source node.
   * @return - source node
   */
  public Node getSrc() {
    return src;
  }

  /**
   * Getter method for the destination node.
   * @return - destination node
   */
  public Node getDest() {
    return dest;
  }
}

