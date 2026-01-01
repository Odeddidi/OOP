//211913587 oded didi
package Collision;
import Geometry.Point;

/**
 * The {@code collision.CollisionInfo} class provides information about a collision event
 * in the game. It contains the point where the collision occurs and the
 * {@link Collidable} object involved in the collision.
 */
public class CollisionInfo {
    private final Point collisionPoint; // The point where the collision occurs
    private final Collidable collidable; // The collidable object involved in the collision

    /**
     * Constructs a new {@code collision.CollisionInfo} with the specified collision point
     * and collidable object.
     *
     * @param collisionPoint the point at which the collision occurs
     * @param collidable the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collidable) {
        this.collisionPoint = collisionPoint;
        this.collidable = collidable;
    }

    /**
     * Returns the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}