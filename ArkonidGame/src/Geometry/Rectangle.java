//211913587 oded didi
package Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * The geometry.Rectangle class represents a rectangle defined by an upper-left point, width, and height.
 * It provides methods to get the dimensions, find intersection points with a line,
 * and generate the edges of the rectangle.
 */
public class Rectangle {
    private final Point upperLeft;
    private final double width;
    private final double height;

    /**
     * Constructs a new geometry.Rectangle with the specified upper-left point, width, and height.
     *
     * @param upperLeft The upper-left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return The width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return The height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return The upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Returns a list of intersection points between the rectangle and the specified line.
     * This method checks for intersections between the line and each of the rectangle's edges.
     *
     * @param line The line to check for intersections.
     * @return A list of intersection points (possibly empty).
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();
        Line[] lines = this.generateRecLines();
        addIntersectionPoint(points, line, lines[0]); // Top edge
        addIntersectionPoint(points, line, lines[1]); // Left edge
        addIntersectionPoint(points, line, lines[2]); // Right edge
        addIntersectionPoint(points, line, lines[3]); // Bottom edge
        return points;
    }

    /**
     * Adds an intersection point between a line and a rectangle recSides to the list of points.
     *
     * @param points The list to add the intersection point to.
     * @param line The line to check for intersections.
     * @param recSides The rectangle sides to check for intersections.
     */
    private void addIntersectionPoint(List<Point> points, Line line, Line recSides) {
        Point intersection = line.intersectionWith(recSides);
        if (intersection != null) {
            points.add(intersection);
        }
    }

    /**
     * Generates the four edges of the rectangle as lines.
     *
     * @return An array of four lines representing the edges of the rectangle.
     */
    public Line[] generateRecLines() {
        Line[] lines = new Line[4];
        // Top edge
        Line top = new Line(this.upperLeft, new Point(this.upperLeft.getX() + width, this.upperLeft.getY()));
        // Left edge
        Line left = new Line(this.upperLeft, new Point(this.upperLeft.getX(), this.upperLeft.getY() + height));
        // Right edge
        Line right = new Line(top.end(), new Point(this.upperLeft.getX() + width, this.upperLeft.getY() + height));
        // Bottom edge
        Line bottom = new Line(left.end(), new Point(this.upperLeft.getX() + width, this.upperLeft.getY() + height));
        lines[0] = top;
        lines[1] = left;
        lines[2] = right;
        lines[3] = bottom;
        return lines;
    }
}