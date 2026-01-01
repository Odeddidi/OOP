//211913587 oded didi
package Geometry;
/**
 * A class representing a point in a two-dimensional plane.
 */
public class Point {
    private final double x; // The x-coordinate of the point
    private final double y; // The y-coordinate of the point
    static final double EPSILON = 0.00001;

    /**
     * Constructs a new point with the given coordinates.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x =  x;
        this.y =  y;
    }

    /**
     * Calculates the distance between this point and another point.
     * @param other The other point to calculate the distance to.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
    }

    /**
     * Checks if this point is equal to another point.
     * @param other The other point to compare with.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return  Math.abs(this.getX() - other.getX()) < EPSILON && Math.abs(this.getY() - other.getY()) < EPSILON;
    }

    /**
     * Gets the x-coordinate of this point.
     * @return The x-coordinate of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of this point.
     * @return The y-coordinate of the point.
     */
    public double getY() {
        return this.y;
    }
    /**
     * Checks if a point is inside the bounds of a rectangle.
     * This method checks if the coordinates of this point are within the boundaries
     * of the rectangle represented by the specified collision.Collidable object. It uses threshold
     * comparisons to determine if the point lies within the bounds.
     *
     * @param rectangle The rectangle will be checked.
     * @return {@code true} if this point is inside the rectangle; {@code false} otherwise.
     */
    public boolean pointInsideRectangle(Rectangle rectangle) {
        // Get the coordinates of the point
        double a = this.getX(), b = this.getY();
        // Get the upper-left point of the rectangle
        Point upperLeft = rectangle.getUpperLeft();

        // Get the bottom-right point of the rectangle
        Point bottomRight = new Point(
                rectangle.getUpperLeft().getX() + rectangle.getWidth(),
                rectangle.getUpperLeft().getY() + rectangle.getHeight());
        // Check if the point is inside the rectangle using threshold comparison
        return Line.tresHold1(a, upperLeft.getX()) && Line.tresHold2(a, bottomRight.getX())
                && Line.tresHold1(b, upperLeft.getY()) && Line.tresHold2(b, bottomRight.getY());
    }
}