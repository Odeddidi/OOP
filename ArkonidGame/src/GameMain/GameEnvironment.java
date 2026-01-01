//211913587 oded didi
package GameMain;
import Collision.Collidable;
import Collision.CollisionInfo;
import Geometry.Line;
import Geometry.Point;
import Sprites.Ball;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameMain.GameEnvironment class manages the collidable objects in the game.
 * It stores a list of collidable objects and provides methods to add new
 * collidables and determine the closest collision point of a moving object
 * based on its trajectory.
 */
public class GameEnvironment {
    private final List<Collidable> list;  // List of collidable objects
    private final List<Ball> balls; // List of balls to check for collisions

    /**
     * Constructs a GameMain.GameEnvironment with specified width and height.
     *
     */
    public GameEnvironment() {
        this.list = new ArrayList<>();
        this.balls = new ArrayList<>();
    }

    /**
     * Adds a collidable object to the environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.list.add(c);
    }
    /**
     * Remove a collidable object to the environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.list.remove(c);
    }
    /**
     * Adds a ball to the list of balls in the environment.
     *
     * @param b the ball to add to the game
     */
    public void addBall(Ball b) {
        this.balls.add(b);
    }
    /**
     * Returns a list of balls currently in the environment.
     *
     * @return a copy of the list of balls in the environment.
     */
    public List<Ball> getGameBalls() {
        return new ArrayList<>(this.balls);
    }
    /**
     * Finds the closest collision point for the given trajectory.
     * This method checks all collidable objects to determine which one,
     * if any, the trajectory will collide with first.
     *
     * @param trajectory the line representing the moving object's trajectory
     * @return the closest collision info or null if no collision is detected
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        Point closestPoint = null;
        double closestDistance = 0; // This will be initialized with the first valid collision point distance

        for (Collidable c : list) {
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (collisionPoint != null) {
                double distance = trajectory.start().distance(collisionPoint);

                // Initialize the closest point and distance with the first valid collision
                if (closestPoint == null) {
                    closestPoint = collisionPoint;
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(collisionPoint, c);
                } else if (distance < closestDistance) {
                    closestPoint = collisionPoint;
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(collisionPoint, c);
                }
            }
        }
        return closestCollision;
    }

    /**
     * Returns the list of collidable objects in the environment.
     *
     * @return the list of collidable objects
     */
    public List<Collidable> getCollidableList() {
        return this.list;
    }
}