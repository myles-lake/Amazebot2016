package bots.Jolly_Cooperation;


import static amazebot2016.BrainInterface.MAZE_SIZE;
import amazebot2016.utils.Compass;
import java.awt.Point;

/**
 * @author Tyler Vandermeer
 * @author Myles Lake
 */
public class Direction {

	private boolean goalSouthWestCorner = false;
	private boolean goalSouthEastCorner = false;
	private boolean goalNorthWestCorner = false;
	private boolean goalNorthEastCorner = false;

	private boolean botSouthWestCorner = false;
	private boolean botSouthEastCorner = false;
	private boolean botNorthWestCorner = false;
	private boolean botNorthEastCorner = false;

	private Compass preferedDirection;

	private Point botPosition;
	private Point goalPosition;
	private final int MAZE_SIZE_HALF = MAZE_SIZE / 2;

	Direction(Point botPosition, Point goalPosition) {
		this.botPosition = botPosition;
		this.goalPosition = goalPosition;
	}

	public void Quadrants() {

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
	}

	public void BotQuadrant() {
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
	}

	public Compass PreferedDirection() {

		if (botSouthWestCorner == true && goalNorthWestCorner == true) {
			preferedDirection = Compass.NORTH;
		} else if (botSouthWestCorner == true && goalNorthEastCorner == true) {
			preferedDirection = Compass.NORTH;
		} else if (botSouthWestCorner == true && goalSouthEastCorner == true) {
			preferedDirection = Compass.EAST;
		} else if (botNorthWestCorner == true && goalNorthEastCorner == true) {
			preferedDirection = Compass.EAST;
		} else if (botNorthWestCorner == true && goalSouthEastCorner == true) {
			preferedDirection = Compass.SOUTH;
		} else if (botNorthWestCorner == true && goalSouthWestCorner == true) {
			preferedDirection = Compass.SOUTH;
		} else if (botNorthEastCorner == true && goalSouthEastCorner == true) {
			preferedDirection = Compass.SOUTH;
		} else if (botNorthEastCorner == true && goalSouthWestCorner == true) {
			preferedDirection = Compass.SOUTH;
		} else if (botNorthEastCorner == true && goalNorthWestCorner == true) {
			preferedDirection = Compass.WEST;
		} else if (botSouthEastCorner == true && goalSouthWestCorner == true) {
			preferedDirection = Compass.WEST;
		} else if (botSouthEastCorner == true && goalNorthWestCorner == true) {
			preferedDirection = Compass.NORTH;
		} else if (botSouthEastCorner == true && goalNorthEastCorner == true) {
			preferedDirection = Compass.NORTH;
		} else {
			preferedDirection = CalculateDistance();
		}
		return preferedDirection;
	}

	private Compass CalculateDistance() {
		Point eastOfBot = new Point(botPosition.x + 1, botPosition.y);
		Point northOfBot = new Point(botPosition.x, botPosition.y - 1);
		Point westOfBot = new Point(botPosition.x - 1, botPosition.y);
		Point southOfBot = new Point(botPosition.x, botPosition.y + 1);
		
		double eastOfBotDistanceToGoal;
		double northOfBotDistanceToGoal;
		double westOfBotDistanceToGoal;
		double southOfBotDistanceToGoal;

		eastOfBotDistanceToGoal = goalPosition.distance(eastOfBot);
		northOfBotDistanceToGoal = goalPosition.distance(northOfBot);
		westOfBotDistanceToGoal = goalPosition.distance(westOfBot);
		southOfBotDistanceToGoal = goalPosition.distance(southOfBot);

		if (eastOfBotDistanceToGoal < northOfBotDistanceToGoal && eastOfBotDistanceToGoal < westOfBotDistanceToGoal && eastOfBotDistanceToGoal < southOfBotDistanceToGoal) {
			preferedDirection = Compass.EAST;
		} else if (northOfBotDistanceToGoal < westOfBotDistanceToGoal && northOfBotDistanceToGoal < southOfBotDistanceToGoal && northOfBotDistanceToGoal < eastOfBotDistanceToGoal) {
			preferedDirection = Compass.NORTH;
		} else if (westOfBotDistanceToGoal < southOfBotDistanceToGoal && westOfBotDistanceToGoal < eastOfBotDistanceToGoal && westOfBotDistanceToGoal < northOfBotDistanceToGoal) {
			preferedDirection = Compass.WEST;
		} else {
			preferedDirection = Compass.SOUTH;
		}
		return preferedDirection;
	}
}
