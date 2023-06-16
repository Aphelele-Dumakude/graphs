package graphs;

import java.util.List;

public interface Graph<V> {
    /**
     *
     * @return the vertices in the graph
     */
    int getSize();

    /**
     *
     * @return the object for the specified vertex index
     */
    List<V> getVertices();

    /**
     *
     * @param index
     * @return the object for the specified vertex index
     */
    V getVertex(int index);

    /**
     *
     * @param v
     * @return the index for the specified vertex index
     */
    int getIndex(V v);

    /**
     *
     * @param index
     * @return the neighbors of vertex with the specified index
     */
    List<Integer> getNeighbors(int index);

    /**
     *
     * @param v
     * @return the degree for a specified vertex
     */
    int getDegree(int v);

    /**
     * Print the edges
     */
    void printEdges();

    /**
     * Clear the graph
     */
    void clear();

    /**
     *
     * @param vertex
     * @return true if this vertex can be added to the graph
     */
    boolean addVertex(V vertex);

    /**
     *
     * @param u
     * @param v
     * @return true if this edge can be added
     */
    boolean addEdge(int u, int v);


}
