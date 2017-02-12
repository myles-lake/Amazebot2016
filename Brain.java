// AMazeBot 2016 Jolly Cooperation
// 
package bots.Jolly_Cooperation;

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
    public void run() {
        /*
            MYLES
            TYLER
         */
        
        // Get goal location    
        Point goalPosition = getGoalCorner();

        // Get bot position, should be called every move, unless we start keeping
        // track of where we are so that it doesn't need to be called
        Point botPosition = getPosition();

        // Set an array and the values for the positions in the maze, set the
        // values for each check and the starting position to open
        // First [] is cols, second [] is rows
        int[][] map = new int[MAZE_SIZE + 2][MAZE_SIZE + 2];
        final int UNKNOWN = 0;
        final int OPEN = 1;
        final int WALL = 2;
        final int BEEN = 3;
        final int GOAL = 4;

        final int MAZE_SIZE_HALF = MAZE_SIZE / 2;

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

        // Determine which quarter the goal is in
        boolean goalSouthWestCorner = false;
        boolean goalSouthEastCorner = false;
        boolean goalNorthWestCorner = false;
        boolean goalNorthEastCorner = false;

        //Find which corner of the map the goal is in and set it to true
        if (goalPosition.x <= MAZE_SIZE_HALF) {
            if (goalPosition.y <= MAZE_SIZE_HALF) {
                goalNorthWestCorner = true;
            } else {
                goalSouthWestCorner = true;
            }
        } else if (goalPosition.y <= MAZE_SIZE_HALF) {
            goalNorthEastCorner = true;
        } else {
            goalSouthEastCorner = true;
        }

        // while true
        while (true) {
            int ageNorth = 2000000000;
            int ageSouth = 2000000000;
            int ageEast = 2000000000;
            int ageWest = 2000000000;

            // Set variables for positions surrounding current bot position, inside 
            Point eastOfBot = new Point(botPosition.x + 1, botPosition.y);
            Point northOfBot = new Point(botPosition.x, botPosition.y - 1);
            Point westOfBot = new Point(botPosition.x - 1, botPosition.y);
            Point southOfBot = new Point(botPosition.x, botPosition.y + 1);

            // Determine which quarter the bot is in
            boolean botSouthWestCorner = false;
            boolean botSouthEastCorner = false;
            boolean botNorthWestCorner = false;
            boolean botNorthEastCorner = false;

            double eastOfBotDistanceToGoal;
            double northOfBotDistanceToGoal;
            double westOfBotDistanceToGoal;
            double southOfBotDistanceToGoal;

            //Find which corner of the map the bot is in and set it to true
            if (botPosition.x <= MAZE_SIZE_HALF) {
                if (botPosition.y <= MAZE_SIZE_HALF) {
                    botNorthWestCorner = true;
                } else {
                    botSouthWestCorner = true;
                }
            } else if (botPosition.y <= MAZE_SIZE_HALF) {
                botNorthEastCorner = true;
            } else {
                botSouthEastCorner = true;
            }

            //Check where the goal is in comparison the the bot in order to make movements
            //more efficient
            Compass preferedDirection;
            Compass secondaryDirection = Compass.EAST;

            if (botSouthWestCorner == true && goalNorthWestCorner == true) {
                preferedDirection = Compass.NORTH;
                secondaryDirection = Compass.EAST;
            } else if (botSouthWestCorner == true && goalNorthEastCorner == true) {
                preferedDirection = Compass.NORTH;
                secondaryDirection = Compass.EAST;
            } else if (botSouthWestCorner == true && goalSouthEastCorner == true) {
                preferedDirection = Compass.EAST;
                secondaryDirection = Compass.NORTH;
            } else if (botNorthWestCorner == true && goalNorthEastCorner == true) {
                preferedDirection = Compass.EAST;
                secondaryDirection = Compass.SOUTH;
            } else if (botNorthWestCorner == true && goalSouthEastCorner == true) {
                preferedDirection = Compass.SOUTH;
                secondaryDirection = Compass.EAST;
            } else if (botNorthWestCorner == true && goalSouthWestCorner == true) {
                preferedDirection = Compass.SOUTH;
                secondaryDirection = Compass.EAST;
            } else if (botNorthEastCorner == true && goalSouthEastCorner == true) {
                preferedDirection = Compass.SOUTH;
                secondaryDirection = Compass.WEST;
            } else if (botNorthEastCorner == true && goalSouthWestCorner == true) {
                preferedDirection = Compass.SOUTH;
                secondaryDirection = Compass.WEST;
            } else if (botNorthEastCorner == true && goalNorthWestCorner == true) {
                preferedDirection = Compass.WEST;
                secondaryDirection = Compass.SOUTH;
            } else if (botSouthEastCorner == true && goalSouthWestCorner == true) {
                preferedDirection = Compass.WEST;
                secondaryDirection = Compass.NORTH;
            } else if (botSouthEastCorner == true && goalNorthWestCorner == true) {
                preferedDirection = Compass.NORTH;
                secondaryDirection = Compass.WEST;
            } else if (botSouthEastCorner == true && goalNorthEastCorner == true) {
                preferedDirection = Compass.NORTH;
                secondaryDirection = Compass.WEST;
            } else {

                // Set variables for how far the goal is from the positions surrounding 
                // the bot
                eastOfBotDistanceToGoal = goalPosition.distance(eastOfBot);
                northOfBotDistanceToGoal = goalPosition.distance(northOfBot);
                westOfBotDistanceToGoal = goalPosition.distance(westOfBot);
                southOfBotDistanceToGoal = goalPosition.distance(southOfBot);

                // Move in the direction that's closest to the goal
                if (eastOfBotDistanceToGoal < northOfBotDistanceToGoal && eastOfBotDistanceToGoal < westOfBotDistanceToGoal && eastOfBotDistanceToGoal < southOfBotDistanceToGoal) {
                    preferedDirection = Compass.EAST;
                } else if (northOfBotDistanceToGoal < westOfBotDistanceToGoal && northOfBotDistanceToGoal < southOfBotDistanceToGoal && northOfBotDistanceToGoal < eastOfBotDistanceToGoal) {
                    preferedDirection = Compass.NORTH;
                } else if (westOfBotDistanceToGoal < southOfBotDistanceToGoal && westOfBotDistanceToGoal < eastOfBotDistanceToGoal && westOfBotDistanceToGoal < northOfBotDistanceToGoal) {
                    preferedDirection = Compass.WEST;
                } else {
                    preferedDirection = Compass.SOUTH;
                }
            }

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
            if (checkSurroundings(botPosition, map, preferedDirection)) {
                direction = preferedDirection;
                //Check if the secondary direction is open
            } else if (checkSurroundings(botPosition, map, secondaryDirection)) {
                direction = secondaryDirection;
                //check surroundings for and open direction
            } else {
                if (map[botPosition.y + 1][botPosition.x] == OPEN) {
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
                        if (checkSurroundings(botPosition, map, preferedDirection)) {
                            direction = preferedDirection;
                            break;
                            //Check if the secondary direction is open
                        } else if (checkSurroundings(botPosition, map, secondaryDirection)) {
                            direction = secondaryDirection;
                            break;
                            //check surroundings for and open direction
                        } else {
                            if (map[botPosition.y + 1][botPosition.x] == OPEN) {
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
            }
            
            // Try to move in our preferred direction...
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


//            for (int i = 0; i < map.length; i++) {
//                for (int j = 0; j < map[i].length; j++) {
//                    System.out.printf("%d", map[i][j]);
//                }
//                System.out.println("");
//            }
//            System.out.println("");
        }
    }

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
