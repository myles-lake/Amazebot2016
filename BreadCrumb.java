
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
    
    public Point getBeen(){
        return Been;
    }
	
	 public int getBeenX(){
        return Been.x;
    }
	 
	  public int getBeenY(){
        return Been.y;
    }
    
    public boolean getOptions(){
        return options;
    }
	
	public int getAge(){
        return objectAge;
    }
    
	public static int getCount(){
		return count;
	}
}
