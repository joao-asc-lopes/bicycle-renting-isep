/*
 * A collection of graph algorithms.
 */
package lapr.project.utils.graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author DEI-ESINF
 */

public final class GraphAlgorithms {

    /**
     * Private constructor to hide the implicit one
     */
    private GraphAlgorithms(){

    }

    /**
     * Performs breadth-first search of a Graph starting in a Vertex
     *
     * @param g    Graph instance
     * @param vert information of the Vertex that will be the source of the search
     * @return qbfs a queue with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }
        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qAux = new LinkedList<>();
        qbfs.add(vert);
        qAux.add(vert);
        while (!qAux.isEmpty()) {
            vert = qAux.removeFirst();
            for (V vAdj : g.adjVertices(vert)) {
                if (!qbfs.contains(vAdj)) {
                    qbfs.add(vAdj);
                    qAux.add(vAdj);
                }
            }
        }
        return qbfs;
    }

    /**
     * Performs depth-first search starting in a Vertex
     *
     * @param g       Graph instance
     * @param vOrig   Vertex of graph g that will be the source of the search
     * @param visited set of discovered vertices
     * @param qdfs    queue with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        qdfs.add(vOrig);
        visited[g.getKey(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig)) {
            if (!visited[g.getKey(vAdj)]) {
                DepthFirstSearch(g, vAdj, visited, qdfs);
            }
        }
    }

    /**
     * @param g    Graph instance
     * @param vert information of the Vertex that will be the source of the search
     * @return qdfs a queue with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }
        LinkedList<V> qdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        DepthFirstSearch(g, vert, visited, qdfs);
        return qdfs;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        visited[g.getKey(vOrig)] = true;
        path.add(vOrig);
        for (V vAdj : g.adjVertices(vOrig)) {
            if (vAdj.equals(vDest)) {
                path.add(vDest);
                paths.add(revPath(path));
                path.pop();
            } else {
                if (!visited[g.getKey(vAdj)]) {
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }
        visited[g.getKey(vOrig)] = false;
        path.pop();
    }

    /**
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from voInf to vdInf
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return paths;
        }
        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> path = new LinkedList<>();
        allPaths(g, vOrig, vDest, visited, path, paths);
        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of discovered vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices,
                                                    boolean[] visited, int[] pathKeys, double[] dist) {

        int vOrigKey = g.getKey(vOrig);
        dist[vOrigKey] = 0;
        while (vOrigKey != -1) {
            vOrig = vertices[vOrigKey];
            visited[vOrigKey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                Edge<V, E> edge = g.getEdge(vOrig, vAdj);
                int vAdjKey = g.getKey(vAdj);
                if (!visited[vAdjKey] && dist[vAdjKey] > dist[vOrigKey] + edge.getWeight()) {
                    dist[vAdjKey] = dist[vOrigKey] + edge.getWeight();
                    pathKeys[vAdjKey] = vOrigKey;
                }
            }
            vOrigKey = getVertMinDist(dist, visited);
        }
    }

    /**
     * @param dist    list of distances
     * @param visited list of visited vertices
     * @return Index of the vertex with minimum distance.
     */
    public static int getVertMinDist(double[] dist, boolean[] visited) {
        double min = Double.MAX_VALUE;
        int minVertIdx = -1;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                if (min > dist[i]) {
                    min = dist[i];
                    minVertIdx = i;
                }
            }
        }
        return minVertIdx;
    }

    /**
     * Extracts from pathKeys the minimum path between vOrig and vDest
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    protected static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path) {
        path.addFirst(vDest);
        if (!vOrig.equals(vDest)) {
            int vDestKey = g.getKey(vDest);
            vDest = verts[pathKeys[vDestKey]];
            getPath(g, vOrig, vDest, verts, pathKeys, path);
        }
    }

    /**
     * shortest-path between vOrig and vDest
     */
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {
        shortPath.clear();
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return 0;
        }

        if (vOrig.equals(vDest)) {
            shortPath.add(vDest);
            return 1;
        }

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] verts = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }
        shortestPathLength(g, vOrig, verts, visited, pathKeys, dist);
        if (pathKeys[g.getKey(vDest)] == -1) {
            return 0;
        }
        getPath(g, vOrig, vDest, verts, pathKeys, shortPath);
        return dist[g.getKey(vDest)];
    }

    /**
     * shortest-path between voInf and all other
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Double> dists) {
        dists.clear();
        paths.clear();
        if (!g.validVertex(vOrig)) {
            return false;
        }

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);


        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();

            if (Double.compare(dist[i], Double.MAX_VALUE) != 0)

                getPath(g, vOrig, vertices[i], vertices, pathKeys, shortPath);
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }

    /**
     * Reverses the path
     *
     * @param path stack with path
     */

    private static <V> LinkedList<V> revPath(LinkedList<V> path) {


        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty())
            pathrev.push(pathcopy.pop());

        return pathrev;
    }
}