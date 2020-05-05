package org.ticdev.toolboxj.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import org.ticdev.toolboxj.collections.impl.IndexedVertexGraph;
import org.ticdev.toolboxj.functions.ReduceFunction;
import org.ticdev.toolboxj.functions.UnaryFunction;

/**
 *
 * Graph supporting methods.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class GraphSupport {

    /**
     * Computes the shortest path between two vertices in a graph, returning the
     * path in the path argument.
     *
     * @param <VERTEX> the type of the vertex
     * @param <EDGE> the type of the edge
     * @param <WEIGHT> the type of the weight of the edge
     * @param graph the graph
     * @param start the starting node
     * @param destination the destination node
     * @param edgeToWeight converts the edge to its weight (distance)
     * @param additiveWeightReducer addition operator for edges
     * @param weightComparator comparator for the edge weight
     * @param zeroValue the zero value for the addition operation. The distance
     * from a node to itself is zero.
     * @param infinityValue the value to use as infinite, a substitute for no
     * path between two vertices
     * @param path the list that will contain the nodes for the shortes path
     * @return the distance, possible infinite if there's no path between the
     * start and destination vertices
     * @throws Exception if an exception occurs
     */
    public static <VERTEX, EDGE, WEIGHT> WEIGHT shortesPath(
            Graph<VERTEX, EDGE> graph,
            VERTEX start,
            VERTEX destination,
            UnaryFunction<EDGE, WEIGHT> edgeToWeight,
            ReduceFunction<WEIGHT> additiveWeightReducer,
            Comparator<WEIGHT> weightComparator,
            final WEIGHT zeroValue,
            final WEIGHT infinityValue,
            Deque<VERTEX> path) throws Exception {

        /*
         * indexed access to the vertices of the graph
         */
        IndexedVertexGraph<VERTEX, EDGE> ivg = new IndexedVertexGraph<>(graph);

        int vertexCount = ivg.vertexCount();

        /*
         * distances between the start node and all the other nodes
         */
        final ArrayList<WEIGHT> distances = new ArrayList<>(vertexCount);

        /*
         * the index of the previous node
         */
        final int[] previous = new int[vertexCount];

        /*
         * a priority queue that contains the nodes in the order of their
         * distances from the source - we're using this to avoid resorting
         * elements
         */
        final PriorityQueue<Integer> all = new PriorityQueue<>(vertexCount,
                (i1, i2) -> weightComparator.compare(distances.get(i1),
                        distances.get(i2)));

        for (int i = 0; i < vertexCount; i++) {
            /*
             * initialize distances to infinity
             */
            distances.add(infinityValue);
            /*
             * previous is undefined for all the nodes
             */
            previous[i] = -1;
        }

        int startNode = ivg.indexOf(start);

        /*
         * the distance of the start vertex to itself is 0
         */
        distances.set(startNode, zeroValue);

        for (int i = 0; i < vertexCount; i++) {
            all.add(i);
        }

        /*
         * as long as we have more elements to visit
         */
        ArrayList<WEIGHT> args = new ArrayList<>(2);
        args.add(null);
        args.add(null);
        while (!all.isEmpty()) {
            int current = all.poll();
            if (weightComparator.compare(distances.get(current), infinityValue) == 0) {
                /*
                 * the remaining nodes are not reachable
                 */
                break;
            }
            for (int nodeIndex = 0; nodeIndex < vertexCount; nodeIndex++) {
                if (nodeIndex == startNode) {
                    continue;
                }
                EDGE edge = ivg.oneEdge(ivg.atIndex(current), ivg.atIndex(
                        nodeIndex));
                if (edge != null) {
                    args.set(0, distances.get(current));
                    args.set(1, edgeToWeight.apply(edge));
                    WEIGHT possibleDistance = additiveWeightReducer.reduce(
                            args);
                    if (weightComparator.compare(possibleDistance, distances.
                            get(nodeIndex)) < 0) {
                        distances.set(nodeIndex, possibleDistance);
                        previous[nodeIndex] = current;
                        all.remove(nodeIndex);
                        all.add(nodeIndex);
                    }
                }
            }
        }

        /*
         * we should have a normal distance here for the end node - we can
         * recreate the path by finding the previous node starting with the last
         * node
         */
        int current = ivg.indexOf(destination);
        if (weightComparator.compare(distances.get(current), infinityValue) == 0) {
            return infinityValue;
        }
        WEIGHT returnValue = distances.get(current);
        while (current != -1) {
            path.push(ivg.atIndex(current));
            current = previous[current];
        }
        return returnValue;

    }

}
