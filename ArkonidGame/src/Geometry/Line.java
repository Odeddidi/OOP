//211913587 oded didi
package Geometry;

import java.util.List;
/**
 * The geometry.Line class represents a line segment between two points.
 */
public class Line {
    private final Point start; // The starting point of the line
    private final Point end;   // The ending point of the line
    static final double EPSILON = 0.00001;
    /**
     * Constructs a line with the specified start and end points.
     * @param start The start point of the line.
     * @param end The end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line with the specified start and end coordinates.
     * @param x1 The x-coordinate of the start point.
     * @param y1 The y-coordinate of the start point.
     * @param x2 The x-coordinate of the end point.
     * @param y2 The y-coordinate of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     * @return The length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     * @return The middle point of the line.
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * Returns the start point of the line.
     * @return The start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     * @return The end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other The other line to check for intersection.
     * @return True if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (this.ifOverlapping(other)) {
            return true;
        }
        if (this.equals(other)) {
            return true;
        }
        return this.intersectionWith(other) != null;
    }

    /**
     * Returns true if both lines intersect with this line, false otherwise.
     * @param other1 The first line to check for intersection.
     * @param other2 The second line to check for intersection.
     * @return True if both lines intersect with this line, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * @param other The other line to check for intersection.
     * @return The intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (Math.abs(this.slope() - other.slope()) < EPSILON) {
            //case of intersection just at the edge
            if (this.edgeIntersect(other) != null) {
                return this.edgeIntersect(other);
            }
            return null;
        }
        double x1 = this.start.getX();
        double x2 = other.start.getX();
        double y1 = this.start.getY();
        double y2 = other.start.getY();
        double m1 = this.slope();
        double m2 = other.slope();
        double x;
        double y;
        Point checkPoint;
        //one of the lines is vertical
        if (m1 == Double.POSITIVE_INFINITY) {
            y = m2 * (x1 - x2) + y2;
            checkPoint = new Point(x1, y);
            if (isOnLine(checkPoint, this) && isOnLine(checkPoint, other)) {
                return checkPoint;
            }
        } else if (m2 == Double.POSITIVE_INFINITY) {
            y = m1 * (x2 - x1) + y1;
            checkPoint = new Point(x2, y);
            if (isOnLine(checkPoint, this) && isOnLine(checkPoint, other)) {
                return checkPoint;
            }
        } else {
            //check for the intersection point and if it is on the lines
            x = ((y2 - m2 * x2) - (y1 - m1 * x1)) / (m1 - m2);
            y = m2 * (x - x2) + y2;
            checkPoint = new Point(x, y);
            if (isOnLine(checkPoint, this) && isOnLine(checkPoint, other)) {
                return checkPoint;
            }
        }
        return null;
    }

    /**
     * Returns true if the lines are equal, false otherwise.
     * @param other The other line to check for equality.
     * @return True if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if (this.lineIsPoint() && other.lineIsPoint()) {
            if ((this.start.equals(other.start) && this.end.equals(other.end))
                    || (this.start.equals(other.end) && this.end.equals(other.start))) {
                return true;
            }
        }
        if (Math.abs(this.slope() - other.slope()) < EPSILON) {
            return (this.start.equals(other.start) && this.end.equals(other.end))
                    || (this.start.equals(other.end) && this.end.equals(other.start));
        }
        return false;
    }

    /**
     * Returns the incline (slope) of the line.
     * @return The incline (slope) of the line.
     */
    public double slope() {
        if (Math.abs(this.start.getX() - this.end.getX()) < EPSILON
                && Math.abs(this.start.getY() - this.end.getY()) >= EPSILON) {
            return Double.POSITIVE_INFINITY;
        }
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * Checks if a point is on a line segment.
     * @param p The point to check.
     * @param other The line segment to check against.
     * @return True if the point is on the line segment, false otherwise.
     */
    public boolean isOnLine(Point p, Line other) {
        if (other.start.getY() - EPSILON > other.end.getY()) {
            if (tresHold2(p.getY(), other.start.getY()) && tresHold1(p.getY(), other.end.getY())) {
                return true;
            }
        }
        if (other.start.getY() + EPSILON < other.end.getY()) {
            if (tresHold1(p.getY(), other.start.getY()) && tresHold2(p.getY(), other.end.getY())) {
                return true;
            }
        }
        if (Math.abs(other.start.getY() - other.end.getY()) < EPSILON) {
            if (other.start.getX() - EPSILON > other.end.getX()) {
                if (tresHold2(p.getX(), other.start.getX())  && tresHold1(p.getX(), other.end.getX())) {
                    return true;
                }
            }
            if (other.start.getX() + EPSILON < other.end.getX()) {
                return tresHold2(p.getX(), other.end.getX()) &&  tresHold1(p.getX(), other.start.getX());
            }
        }
        return false;
    }

    /**
     * Checks if two lines overlap.
     * @param other The other line to check for overlapping.
     * @return True if the lines overlap, false otherwise.
     */
    public boolean ifOverlapping(Line other) {
        if (this.lineIsPoint() && other.lineIsPoint()) {
            if (this.equals(other)) {
                return true;
            }
        }
        if (this.lineIsPoint() || other.lineIsPoint()) {
            if (isOnLine(this.start, other) || isOnLine(other.start, this)) {
                return true;
            }
        }
        if (Math.abs(this.slope() - other.slope()) < EPSILON) {
            return isOnLine(this.start, other) || isOnLine(this.end, other)
                    || isOnLine(other.start, this) || isOnLine(other.end, this);
        }
        return false;
    }

    /**
     * Handles edge cases where lines might touch at the ends.
     * @param other The other line to check for edge cases.
     * @return The intersection point if it exists, null otherwise.
     */
    public Point edgeIntersect(Line other) {
        if (this.start.equals(other.start)) {
            if (!isOnLine(this.end, other) || !isOnLine(other.end, this)) {
                return this.start;
            }
        }
        if (this.start.equals(other.end)) {
            if (!isOnLine(this.end, other) || !isOnLine(other.start, this)) {
                return this.end;
            }
        }
        if (this.end.equals(other.start)) {
            if (!isOnLine(this.start, other) || !isOnLine(other.end, this)) {
                return this.end;
            }
        }
        if (this.end.equals(other.end)) {
            if (!isOnLine(this.start, other) || !isOnLine(other.start, this)) {
                return this.end;
            }
        }
        return null;
    }
    /**
     * Checks if the line is actually a point.
     * A line is considered a point if its start and end points are the same.
     *
     * @return true if the start point equals the end point, false otherwise.
     */
    public boolean lineIsPoint() {
        return this.start.equals(this.end);
    }
    /**
     * Finds the closest intersection point to the start of the line with a given rectangle.
     *
     * @param rect The rectangle to check for intersection with.
     * @return The closest intersection point to the start of the line, or null if no intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Find all intersection points between the line and the rectangle
        List<Point> intersections = rect.intersectionPoints(this);

        // If there are no intersections, return null
        if (intersections.isEmpty()) {
            return null;
        }

        // Initialize variables to track the closest intersection point and its distance from the start of the line
        Point closestPoint = intersections.get(0);
        double closestDistance = this.start.distance(closestPoint);

        // Iterate over all intersection points and update the closest one
        for (Point intersection : intersections) {
            double distance = this.start.distance(intersection);
            if (distance < closestDistance) {
                closestPoint = intersection;
                closestDistance = distance;
            }
        }

        // Return the closest intersection point
        return closestPoint;
    }
    /**
     * Checks if x is equal to y within a small threshold.
     *
     * @param a The first value to compare.
     * @param b The second value to compare.
     * @return True if a is equal to b within a small threshold, false otherwise.
     */
    public static boolean tresHold(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     * Checks if a is less than or equal to b within a small threshold.
     *
     * @param a The first value to compare.
     * @param b The second value to compare.
     * @return True if a is less than or equal to b within a small threshold, false otherwise.
     */
    public static boolean tresHold1(double a, double b) {
        // If the absolute difference between a and b is less than a small threshold, or if a is greater than b,
        // return true. Otherwise, return false.
        return Math.abs(a - b) < EPSILON || a > b;
    }

    /**
     * Checks if a is greater than or equal to y within a small threshold.
     *
     * @param a The first value to compare.
     * @param b The second value to compare.
     * @return True if a is greater than or equal to b within a small threshold, false otherwise.
     */
    public static boolean tresHold2(double a, double b) {
        // If the absolute difference between a and y is less than a small threshold, or if a is less than y,
        // return true. Otherwise, return false.
        return Math.abs(a - b) < EPSILON || a < b;
    }
}