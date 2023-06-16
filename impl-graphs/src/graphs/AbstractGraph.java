package graphs;

import java.util.*;

public abstract class AbstractGraph<V> implements Graph<V>{

    protected List<V> vertices = new ArrayList<>();
    protected List<List<Edge>> neighbors = new ArrayList<>();
    protected AbstractGraph() {
    }
    protected AbstractGraph(V[] vertices, int[][] edges) {
        for (V vertex : vertices) {
            addVertex(vertex);
        }
        createAdjacencyLists(edges);
    }
    protected AbstractGraph(List<V> vertices, List<Edge> edges) {
        for (V vertex : vertices) {
            addVertex(vertex);
        }
        createAdjacencyLists(edges);
    }
    protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            addVertex((V) (Integer.valueOf(i)));
        }
        createAdjacencyLists(edges);
    }

    public AbstractGraph(int[][] edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            addVertex((V) (Integer.valueOf(i)));
        }
        createAdjacencyLists(edges);
    }

    private void createAdjacencyLists(int[][] edges) {
        for (int[] edge : edges) {
            addEdge(edge[0], edge[1]);
        }
    }
    private void createAdjacencyLists(List<Edge> edges) {
        for (Edge edge : edges) {
            addEdge(edge.u, edge.v);
        }
    }

    /**
     * @return the vertices in the graph
     */
    @Override
    public int getSize() {
        return vertices.size();
    }

    /**
     * @return the object for the specified vertex index
     */
    @Override
    public List<V> getVertices() {
        return vertices;
    }
    /**
     *
     * @param index
     * @return the object for the specified vertex index
     */
    @Override
    public V getVertex(int index) {
        return vertices.get(index);
    }

    /**
     * @param v
     * @return the index for the specified vertex index
     */
    @Override
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    /**
     * @param index
     * @return the neighbors of vertex with the specified index
     */
    @Override
    public List<Integer> getNeighbors(int index) {
        List<Integer> list= new ArrayList<>();
        for (Edge edge: neighbors.get(index)) {
            list.add(edge.v);
        }
        return list;
    }

    /**
     * @param v
     * @return the degree for a specified vertex
     */
    @Override
    public int getDegree(int v) {
        return neighbors.get(v).size();
    }

    /**
     * Print the edges
     */
    @Override
    public void printEdges() {
        for (int u = 0; u < neighbors.size(); u++) {
            System.out.print(getVertex(u) + " (" + u + "): ");
            for (Edge e: neighbors.get(u)) {
                System.out.print("(" + getVertex(e.u) + ", " + getVertex(e.v) + ") ");
            }
            System.out.println();
        }
    }

    /**
     * Clear the graph
     */
    @Override
    public void clear() {
        vertices.clear();
        neighbors.clear();
    }

    /**
     *
     * @param vertex
     * @return true if this vertex can be added to the graph
     */
    @Override
    public boolean addVertex(V vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        neighbors.add(new ArrayList<>());
        return true;
    }

    /**
     *
     * @param edge
     * @return true if an edge can be added
     */
    protected boolean addEdge(Edge edge) {
        if (edge.u < 0 || edge.u > getSize()) {
            throw new IllegalArgumentException("The provided Edge index is out of bounds: " + edge.u);
        }
        if (edge.v < 0 || edge.v > getSize()) {
            throw new IllegalArgumentException("The provided Edge index is out of bounds: " + edge.v);
        }
        if (!neighbors.get(edge.u).contains(edge)) {
            return neighbors.get(edge.u).add(edge);
        }
        return false;
    }

    /**
     *
     * @param u
     * @param v
     * @return true if this edge can be added
     */
    @Override
    public boolean addEdge(int u, int v) {
      return addEdge(new Edge(u, v));
    }
    public static class Edge {
        public int u;
        public int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
        @Override
        public boolean equals(Object obj) {
            return u == ((Edge)obj).u && v == ((Edge)obj).v;
        }
    }

    /**
     *
     * @param v
     * @return the tree object from the breadth-first traversal
     */
    public Tree bfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();
        boolean[] isVisited = new boolean[vertices.size()];
        queue.offer(v);
        isVisited[v] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            searchOrder.add(u);
            for (Edge e: neighbors.get(u)) {
                if (!isVisited[u]) {
                    queue.offer(e.v);
                    parent[e.v] = u;
                    isVisited[e.v] = true;
                }
            }
        }
        return new Tree(v, parent, searchOrder);
    }
    /**
     *
     * @param v
     * @return the tree object from the depth-first traversal
     */
    public Tree dfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        Arrays.fill(parent, -1);
        Stack<Integer> stack = new Stack<>();
        boolean[] isVisited = new boolean[vertices.size()];
        stack.push(v);
        isVisited[v] = true;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            searchOrder.add(u);
            for (Edge e: neighbors.get(u)) {
                if (!isVisited[u]) {
                    stack.push(e.v);
                    parent[e.v] = u;
                    isVisited[e.v] = true;
                }
            }
        }
        return new Tree(v, parent, searchOrder);
    }
    public class Tree {
        // The root of the tree
        private final int root;
        // Keeps track of the parent of each vertex
        private final int[] parent;
        // Store the search order
        private final List<Integer> searchOrder;

        public Tree(int root, int[] parent, List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }

        /**
         * @return the root of the tree
         */
        public int getRoot() {
            return root;
        }

        /**
         * @param v
         * @return the parent of vertex v
         */
        public int getParent(int v) {
            return parent[v];
        }

        /**
         * @return the list representing search order
         */
        public List<Integer> getSearchOrder() {
            return searchOrder;
        }

        /**
         * @return the number of vertices found
         */
        public int getNumberOfVerticesFound() {
            return searchOrder.size();
        }

        /**
         * @param index
         * @return the path of vertices from a vertex to the root
         */
        public List<V> getPath(int index) {
            ArrayList<V> path = new ArrayList<>();
            while (index != -1) {
                path.add(vertices.get(index));
                index = parent[index];
            }
            return path;
        }

        /**
         * Print a path from the root to vertex
         *
         * @param index
         */
        public void printPath(int index) {
            List<V> path = getPath(index);
            System.out.print("A path from " + vertices.get(root) + " to " + vertices.get(index) + ":");
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i) + " ");
            }
        }

        /**
         * Print the whole tree
         */
        public void printTree() {

            System.out.println("Root is: " + vertices.get(root));
            System.out.print("Edges: ");
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] != -1) {
                    // Display an edge
                    System.out.print("(" + vertices.get(parent[i]) + ", " + vertices.get(i) + ") ");
                }
            }
            System.out.println();
        }
    }
}
