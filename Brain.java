// AMazeBot 2016 Jolly Cooperation
// 
package bots.Jolly_Cooperation;

/**
 * @author Tyler Vandermeer
 * @author Myles Lake
 */
import amazebot2016.*;
import amazebot2016.utils.Compass;
import java.awt.Point;
import java.util.ArrayList;

public class Brain extends BotBrain {

    @Override
    public String getName() {
        return "Jolly Cooperation";
    }

    @Override
    /**
     * Runs the main program
     */
    public void run() {
        // Get goal location    
        Point goalPosition = getGoalCorner();
        Point botPosition = getPosition();
        int[][] map = new int[MAZE_SIZE + 2][MAZE_SIZE + 2];
        final int UNKNOWN = 0;
        final int OPEN = 1;
        final int WALL = 2;
        final int BEEN = 3;
        Point start = getStartPosition();
        map[start.y + 1][start.x + 1] = OPEN;
        goalPosition.x++;
        goalPosition.y++;
        for (int i = 0; i < map.length; i++) {
            map[0][i] = WALL;
            map[i][0] = WALL;
            map[map.length - 1][i] = WALL;
            map[i][map.length - 1] = WALL;
        }
        botPosition.x++;
        botPosition.y++;
        Compass direction = Compass.NORTH;
        ArrayList<BreadCrumb> breadCrumb = new ArrayList<BreadCrumb>();
        Direction myDirection = new Direction(botPosition, goalPosition);
        myDirection.Quadrants();
        while (true) {
            myDirection.BotQuadrant();
            Compass preferedDirection = myDirection.PreferedDirection();
            //Mapping out the map
            //Check south
            if (map[botPosition.y + 1][botPosition.x] == UNKNOWN) {
                if (look(Compass.SOUTH)) {
                    map[botPosition.y + 1][botPosition.x] = OPEN;
                } else {
                    map[botPosition.y + 1][botPosition.x] = WALL;
                }
            }
            //Check North
            if (map[botPosition.y - 1][botPosition.x] == UNKNOWN) {
                if (look(Compass.NORTH)) {
                    map[botPosition.y - 1][botPosition.x] = OPEN;
                } else {
                    map[botPosition.y - 1][botPosition.x] = WALL;
                }
            }
            //Check East
            if (map[botPosition.y][botPosition.x + 1] == UNKNOWN) {
                if (look(Compass.EAST)) {
                    map[botPosition.y][botPosition.x + 1] = OPEN;
                } else {
                    map[botPosition.y][botPosition.x + 1] = WALL;
                }
            }
            //Check West
            if (map[botPosition.y][botPosition.x - 1] == UNKNOWN) {
                if (look(Compass.WEST)) {
                    map[botPosition.y][botPosition.x - 1] = OPEN;
                } else {
                    map[botPosition.y][botPosition.x - 1] = WALL;
                }
            }
            //Check if the preferd direction is open
            if (Mapping.checkSurroundings(botPosition, map, preferedDirection)) {
                direction = preferedDirection;
                //Check if the secondary direction is open
            } else if (map[botPosition.y + 1][botPosition.x] == OPEN) {
                direction = Compass.SOUTH;
            } else if (map[botPosition.y - 1][botPosition.x] == OPEN) {
                direction = Compass.NORTH;
            } else if (map[botPosition.y][botPosition.x + 1] == OPEN) {
                direction = Compass.EAST;
            } else if (map[botPosition.y][botPosition.x - 1] == OPEN) {
                direction = Compass.WEST;
                //if no open directions backtrack
            } else {
                int count = breadCrumb.size() - 1;
                Point backTrack;
                while (map[botPosition.y][botPosition.x] == BEEN && count >= 0) {
                    backTrack = breadCrumb.get(count).getBeen();
                    //check if there is more than one option at bots current position
                    if (Mapping.checkSurroundings(botPosition, map, preferedDirection)) {
                        direction = preferedDirection;
                        break;
                        //check surroundings for and open direction
                    } else if (map[botPosition.y + 1][botPosition.x] == OPEN) {
                        direction = Compass.SOUTH;
                        break;
                    } else if (map[botPosition.y - 1][botPosition.x] == OPEN) {
                        direction = Compass.NORTH;
                        break;
                    } else if (map[botPosition.y][botPosition.x + 1] == OPEN) {
                        direction = Compass.EAST;
                        break;
                    } else if (map[botPosition.y][botPosition.x - 1] == OPEN) {
                        direction = Compass.WEST;
                        break;
                    }
                    //back track
                    if (backTrack.x < botPosition.x) {
                        move(Compass.WEST);
                        botPosition.x--;
                        breadCrumb.remove(count);
                        count--;
                    } else if (backTrack.x > botPosition.x) {
                        move(Compass.EAST);
                        botPosition.x++;
                        breadCrumb.remove(count);
                        count--;
                    } else if (backTrack.y > botPosition.y) {
                        move(Compass.SOUTH);
                        botPosition.y++;
                        breadCrumb.remove(count);
                        count--;
                    } else if (backTrack.y < botPosition.y) {
                        move(Compass.NORTH);
                        botPosition.y--;
                        breadCrumb.remove(count);
                        count--;
                    } else {
                        count--;
                    }
                }
            }
            // Try to move in our preferred direction
            boolean success = move(direction);
            //if move is succsessful update bots position and add a breadcrumb
            if (direction.equals(Compass.EAST) && success) {
                breadCrumb.add(new BreadCrumb(botPosition, map));
                botPosition.x++;
                map[botPosition.y][botPosition.x] = BEEN;
            } else if (direction.equals(Compass.WEST) && success) {
                breadCrumb.add(new BreadCrumb(botPosition, map));
                botPosition.x--;
                map[botPosition.y][botPosition.x] = BEEN;
            } else if (direction.equals(Compass.NORTH) && success) {
                breadCrumb.add(new BreadCrumb(botPosition, map));
                botPosition.y--;
                map[botPosition.y][botPosition.x] = BEEN;
            } else if (direction.equals(Compass.SOUTH) && success) {
                breadCrumb.add(new BreadCrumb(botPosition, map));
                botPosition.y++;
                map[botPosition.y][botPosition.x] = BEEN;
            }
        }
    }
}
