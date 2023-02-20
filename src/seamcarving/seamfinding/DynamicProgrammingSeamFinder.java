package seamcarving.seamfinding;

import seamcarving.Picture;
import seamcarving.SeamCarver;
import seamcarving.energy.EnergyFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        double[][] dpTable = new double[picture.width()][picture.height()];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < picture.height(); i++) {
            dpTable[0][i] = f.apply(picture, 0, i);
        }

        double min_start = Double.POSITIVE_INFINITY;
        int min_y_position = 0;

        for (int x = 1; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                double initial = f.apply(picture, x, y);
                double total = findNeighbors(dpTable, x, y, initial);
                dpTable[x][y] = total;
                if (x == picture.width() - 1 && total < min_start) {
                    min_start = total;
                    min_y_position = y;
                }
            }
        }

        pathFinder(dpTable, picture.width() - 1, min_y_position, result);
        Collections.reverse(result);
        return result;
    }

    private double findNeighbors(double[][] graph, int x, int y, double initial) {
        double min = graph[x - 1][y]; // sets immediate left neighbor as min
        if (y > 0) { // for upper left neighbor
            double up = graph[x - 1][y - 1];
            if (up < min) {
                min = up;
            }
        }
        if (y < graph[0].length-1) { // for bottom left neighbor
            double down = graph[x - 1][y + 1];
            if (down < min) {
                min = down;
            }
        }
        min += initial; // initial = pixel's energy alone, min = the smallest energy from the chosen left neighbor
        return min; // returns the energies added up to become the total energy of the current pixel
    }
    private void pathFinder(double[][] graph, int x, int y, List<Integer> path) {
        path.add(y);
        if (x == 0) {
            return;
        }
        double min = graph[x - 1][y]; // sets immediate left neighbor as min
        int min_y = y;
        if (y > 0) { // for upper left neighbor
            double up = graph[x - 1][y - 1];
            if (up < min) {
                min = up;
                min_y = y-1;
            }
        }
        if (y < graph[0].length-1) { // for bottom left neighbor
            double down = graph[x - 1][y + 1];
            if (down < min) {
                min_y = y+1;
            }
        }
        pathFinder(graph, x-1, min_y, path);
    }
}
