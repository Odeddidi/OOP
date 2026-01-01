//211913587 oded didi
package Collision;
import GameMain.Game;
import GameMain.GameEnvironment;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Sprites.Ball;
import Sprites.Sprite;
import Sprites.Velocity;
import java.awt.Color;

/**
 * The collision.Paddle class represents the player's paddle in the game.
 * It handles the paddle's position, movement, and collision logic.
 * The paddle can move left and right based on user input from the keyboard.
 */
public  class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboard; // Keyboard sensor for detecting user input
    private Rectangle rectangle; // geometry.Rectangle representing the paddle's shape and position
    private final Color color; // Color of the paddle
    private final int speed; // Speed of the paddle's movement
    private GameEnvironment gameEnvironment;
    static final int RIGHT_B_START = 790; // Width of the game screen
    static final int LEFT_B_WIDTH = 10;

    /**
     * Constructs a collision.Paddle with a specified rectangle, color, speed, and GUI.
     *
     * @param rectangle the rectangle representing the paddle's shape and position
     * @param color     the color of the paddle
     * @param speed     the speed of the paddle's movement
     * @param gui       the GUI object to get the keyboard sensor
     */
    public Paddle(Rectangle rectangle, Color color, int speed, GUI gui) {
        this.rectangle = rectangle;
        this.color = color;
        this.speed = speed;
        this.keyboard = gui.getKeyboardSensor();
    }
    /**
     * Sets the game environment for the paddle.
     *
     * @param gameEnvironment The game environment to set.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }
    /**
     * Moves the paddle to the specified x-coordinate while keeping the y-coordinate constant.
     * The method ensures that the paddle does not move into any balls by checking for potential collisions.
     *
     * @param x the new x-coordinate for the upper-left corner of the paddle
     */
    public void movePaddle(double x) {
        double y = this.rectangle.getUpperLeft().getY();
        Point p1 = new Point(x, y);
        Rectangle newRec = new Rectangle(p1, rectangle.getWidth(), rectangle.getHeight());
        if (this.gameEnvironment != null) {
            for (Ball ball : this.gameEnvironment.getGameBalls()) {
                if (ball.getCenter().pointInsideRectangle(newRec)) {
                    return;
                }
            }
        }
        this.rectangle = newRec;
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        double x = this.rectangle.getUpperLeft().getX() - this.speed;
       this.movePaddle(x);
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        double x = this.rectangle.getUpperLeft().getX() + this.speed;
        this.movePaddle(x);
    }

    /**
     * Updates the paddle's state based on user input.
     * Moves the paddle left or right if the corresponding key is pressed.
     * Wraps the paddle around the screen edges if it moves outside the screen.
     */
    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
        // Wrap paddle around the screen
        if (this.rectangle.getUpperLeft().getX() < LEFT_B_WIDTH) {
            this.rectangle = new Rectangle(new Point(RIGHT_B_START - this.rectangle.getWidth(),
                    this.rectangle.getUpperLeft().getY()),  rectangle.getWidth(), rectangle.getHeight());
        }
        if (this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth()  > RIGHT_B_START) {
            this.rectangle = new Rectangle(
                    new Point(LEFT_B_WIDTH, this.rectangle.getUpperLeft().getY()),
                    rectangle.getWidth(), rectangle.getHeight());
        }
    }

    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
    }

    /**
     * Returns the rectangle representing the paddle's collision shape.
     *
     * @return the collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Adjusts the ball's velocity based on where it hits the paddle.
     * The paddle is divided into 5 regions that change the ball's angle and speed.
     * Handles edge collisions by reversing the ball's velocity components.
     *
     * @param collisionPoint the point where the ball collides with the paddle
     * @param currentVelocity the current velocity of the ball
     * @return the new velocity of the ball after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double distance = this.rectangle.getWidth() / 5;
        double start = this.rectangle.getUpperLeft().getX();
        double y = this.rectangle.getUpperLeft().getY();

        // Define the regions of the paddle
        Line l1 = new Line(new Point(start, y), new Point(start + distance, y));
        Line l2 = new Line(new Point(start + distance, y), new Point(start + 2 * distance, y));
        Line l3 = new Line(new Point(start + 2 * distance, y), new Point(start + 3 * distance, y));
        Line l4 = new Line(new Point(start + 3 * distance, y), new Point(start + 4 * distance, y));
        Line l5 = new Line(new Point(start + 4 * distance, y), new Point(start + 5 * distance, y));
        double speed = currentVelocity.speed();

        // Check collision with each region and adjust velocity accordingly
        if (l1.isOnLine(collisionPoint, l1)) {
            currentVelocity = Velocity.fromAngleAndSpeed(300, speed);
            return currentVelocity;
        }
        if (l2.isOnLine(collisionPoint, l2)) {
            currentVelocity = Velocity.fromAngleAndSpeed(330, speed);
            return currentVelocity;
        }
        if (l3.isOnLine(collisionPoint, l3)) {
            return new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
        }
        if (l4.isOnLine(collisionPoint, l4)) {
            currentVelocity = Velocity.fromAngleAndSpeed(30, speed);
            return currentVelocity;
        }
        if (l5.isOnLine(collisionPoint, l5)) {
            currentVelocity = Velocity.fromAngleAndSpeed(60, speed);
            return currentVelocity;
        }

        // If collision point doesn't match any region, reverse both components of the velocity
        return new Velocity(-1 * currentVelocity.getDx(), -1 * currentVelocity.getDy());
    }

    /**
     * Adds the paddle to the game as both a sprite and a collidable.
     *
     * @param g the game to add the paddle to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
