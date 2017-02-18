package bots.Jolly_Cooperation;

import amazebot2016.*;
import static amazebot2016.BrainInterface.MAZE_SIZE;
import amazebot2016.utils.Compass;
import java.awt.Point;

/**
 * @author Tyler Vandermeer
 * @author Myles Lake
 */
public class Mapping {

    /**
     * Checks to see what directions the bot can move in
     *
     * @param botPosition
     * @param map
     * @param direction
     * @return true or false
     */
    public static boolean checkSurroundings(Point botPosition, int[][] map, Compass direction) {
        if (map[botPosition.y][botPosition.x - 1] == 1 && direction == Compass.WEST) {
            //you can move west
            return true;
        } else if (map[botPosition.y][botPosition.x + 1] == 1 && direction == Compass.EAST) {
            //you can move east
            return true;
        } else if (map[botPosition.y - 1][botPosition.x] == 1 && direction == Compass.NORTH) {
            //you can move north
            return true;
        } else if (map[botPosition.y + 1][botPosition.x] == 1 && direction == Compass.SOUTH) {
            //you can move south
            return true;
        }
        return false;
    }
}
