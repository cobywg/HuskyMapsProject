package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;
import minpq.DoubleMapMinPQ;
import minpq.ExtrinsicMinPQ;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        edgeTo.put(start, null);
        distTo.put(start, 0.0);
        Set<V> visited = new HashSet<>();
        List<V> result = new ArrayList<>();
        dfsPostOrder(graph, start, visited, result);
        for (int i = result.size()-1; i >= 0; i--) {
            V from = result.get(i);
            for (Edge<V> e : graph.neighbors(from)) {
                V to = e.to;
                double prevDistance = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                double newDistance = distTo.get(from) + e.weight;
                if (newDistance < prevDistance) {
                    edgeTo.put(to, e);
                    distTo.put(to, newDistance);
                }
            }
        }
//        ExtrinsicMinPQ<V> perimeter = new DoubleMapMinPQ<>();
//        Set<V> visited = new HashSet<>();
//        perimeter.add(start, 0.0);
//        List<V> result = new ArrayList<>();
//        while(!perimeter.isEmpty()) {
//            V from = perimeter.removeMin();
//            visited.add(from);
//            dfsPostOrder(graph, from, visited, result, perimeter);
//        }

    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        if (visited.contains(start)) {
            return;
        }
        visited.add(start);
        for (Edge<V> e : graph.neighbors(start)) {
            V to = e.to;
            dfsPostOrder(graph, to, visited, result);
        }
        result.add(start);
//        if(!graph.neighbors(start).isEmpty()) {
//            for(Edge<V> edge: graph.neighbors(start)) {
//                V to = edge.to;
//                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
//                double newDist = distTo.get(start) + edge.weight;
//                if (newDist > oldDist) {
//                    edgeTo.put(to, edge);
//                    distTo.put(to, newDist);
//                    result.add(to);
//                }
//                    if(perimeter.contains(to)) {
//                        perimeter.changePriority(to, newDist);
//                    } else {
//                        perimeter.add(to, newDist);
//                    }
//
//            }
//        }

    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
