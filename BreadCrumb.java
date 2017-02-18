package bots.Jolly_Cooperation;

import java.awt.Point;

/**
 * @author Tyler Vandermeer
 * @author Myles Lake
 */
public class BreadCrumb {

    Point Been;
    boolean options;
    static int count;
    int objectAge;

    /**
     * Sets default for BreadCrumb
     *
     * @param botPosition
     * @param map
     */
    BreadCrumb(Point botPosition, int[][] map) {
        Been = new Point(botPosition);
        int optionCounter = 0;
        count++;
        objectAge = count;
        if (map[botPosition.y][botPosition.x - 1] == 1) {
            //you can move west
            optionCounter++;
        }
        if (map[botPosition.y][botPosition.x + 1] == 1) {
            //you can move east
            optionCounter++;
        }
        if (map[botPosition.y + 1][botPosition.x] == 1) {
            //you can move north
            optionCounter++;
        }
        if (map[botPosition.y - 1][botPosition.x] == 1) {
            //you can move south
            optionCounter++;
        }
        if (optionCounter > 1) {
            options = true;
        }
    }

    /**
     * Shows if bot has been in this location before
     *
     * @return Been
     */
    public Point getBeen() {
        return Been;
    }

    /**
     * Returns the x-coordinate of where the bot has been
     *
     * @return Been.x
     */
    public int getBeenX() {
        return Been.x;
    }

    /**
     * Returns the y-coordinate of where the bot has been
     *
     * @return Been.y
     */
    public int getBeenY() {
        return Been.y;
    }

    /**
     * Returns which directions the bot can go
     *
     * @return options
     */
    public boolean getOptions() {
        return options;
    }

    /**
     * Returns how long ago the bot was at this location
     *
     * @return objectAge
     */
    public int getAge() {
        return objectAge;
    }

    /**
     * Returns the BreadCrumb number
     *
     * @return count
     */
    public static int getCount() {
        return count;
    }
}
