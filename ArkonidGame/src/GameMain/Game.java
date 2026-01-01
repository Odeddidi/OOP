// 211913587 oded didi
package GameMain;
import Hit.BallRemover;
import Hit.BlockRemover;
import Hit.Counter;
import Hit.HitListener;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import Collision.Block;
import Collision.Collidable;
import Collision.Paddle;
import Geometry.Point;
import Geometry.Rectangle;
import Sprites.Ball;
import Sprites.ScoreIndicator;
import Sprites.Sprite;
import Sprites.SpriteCollection;
import Sprites.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The GameMain.Game class represents the main game logic.
 * It manages the sprites, the game environment, and runs the game loop.
 */
public class Game {
    private final SpriteCollection sprites; // Collection of all sprites in the game
    private final GameEnvironment environment; // Environment managing collidable objects
    private final Counter ramainingBlocks;
    private final Counter ramainingBalls;
    private final Counter counter;
    static final int WIDTH = 800; // Width of the game screen
    static final int HEIGHT = 600; // Height of the game screen
    static final int BALL_SPEED = 5; // X velocity of the first ball
    static final int FIRST_UPPER_X = 740; // X position of the first block
    static final int UPPER_Y = 200; // Y position of the first block
    static final int BLOCK_WIDTH = 50; // Width of a block
    static final int BLOCK_HEIGHT = 20; // Height of a block
    static final int FRAME_BLOCK_SIZE = 10; // Size of the frame blocks
    static final int FIRST_ROW_BLOCK = 12;
    static final int PADDLE_WIDTH = 80;
    static final int PADDLE_HEIGHT = 20;
    static final int BALLS_CENTER_X = 430;
    static final int BALLS_CENTER_Y = 80;
    static final int PADDLE_SPEED = 5;
    static final int PADDLE_CONST_Y = 570;
    static final int NUM_OF_BALLS = 3;

    /**
     * Constructs a new GameMain.Game.
     * Initializes the sprite collection and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.ramainingBlocks = new Counter();
        this.ramainingBalls = new Counter();
        this.counter = new Counter();
    }

    /**
     * Adds a collidable to the game environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the sprite collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * Remove a collidable to the game environment.
     *
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**
     * Remove a sprite to the sprite collection.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initializes a new game.
     * Creates the blocks, balls, and paddle and adds them to the game.
     */
    public void initialize() {
        List<Block> blocks = getBlocks();
        HitListener blockRemover = new BlockRemover(this, ramainingBlocks);
        ramainingBlocks.increase(blocks.size());
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.counter);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.counter);
        scoreIndicator.addToGame(this);
        for (Block block : blocks) {
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
            block.addToGame(this);
        }
        // Create frame blocks
        Block block1 = new Block(Color.gray, new Point(0, 20), FRAME_BLOCK_SIZE, HEIGHT);
        Block block2 = new Block(Color.green, new Point(0, HEIGHT), WIDTH, FRAME_BLOCK_SIZE);
        Block block3 = new Block(Color.gray, new Point(0, 20), WIDTH - FRAME_BLOCK_SIZE, FRAME_BLOCK_SIZE);
        Block block4 = new Block(Color.gray, new Point(WIDTH - FRAME_BLOCK_SIZE, 20), FRAME_BLOCK_SIZE, HEIGHT);

        block1.addToGame(this);
        block2.addToGame(this);
        block3.addToGame(this);
        block4.addToGame(this);

        //Create balls
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            initializeGameBalls();
        }
        BallRemover ballRemover = new BallRemover(this, ramainingBalls);
        ramainingBalls.increase(NUM_OF_BALLS);
        block2.addHitListener(ballRemover);
    }

    /**
     * Creates a list of blocks arranged in rows with different colors.
     *
     * @return a list of blocks
     */
    private static List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        // Add blocks in sequence according to colors and rows
        for (int i = 0; i < FIRST_ROW_BLOCK; i++) {
            Block block = new Block(Color.gray,
                    new Point(FIRST_UPPER_X - BLOCK_WIDTH * i, UPPER_Y),
                    BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(block);
        }
        for (int i = 0; i < FIRST_ROW_BLOCK - 1; i++) {
            Block block = new Block(Color.red,
                    new Point(FIRST_UPPER_X - BLOCK_WIDTH * i, UPPER_Y + BLOCK_HEIGHT),
                    BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(block);
        }
        for (int i = 0; i < FIRST_ROW_BLOCK - 2; i++) {
            Block block = new Block(Color.yellow,
                    new Point(FIRST_UPPER_X - BLOCK_WIDTH * i, UPPER_Y + 2 * BLOCK_HEIGHT),
                    BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(block);
        }
        for (int i = 0; i < FIRST_ROW_BLOCK - 3; i++) {
            Block block = new Block(Color.blue,
                    new Point(FIRST_UPPER_X - BLOCK_WIDTH * i, UPPER_Y + 3 * BLOCK_HEIGHT),
                    BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(block);
        }
        for (int i = 0; i < FIRST_ROW_BLOCK - 4; i++) {
            Block block = new Block(Color.white,
                    new Point(FIRST_UPPER_X - BLOCK_WIDTH * i, UPPER_Y + 4 * BLOCK_HEIGHT),
                    BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(block);
        }
        return blocks;
    }

    /**
     * Runs the game by starting the animation loop.
     */
    public void run() {
        GUI gui = new GUI("Arkonid", WIDTH, HEIGHT);
        initializeGamePaddle(gui);
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.green.darker());
            d.fillRectangle(0, 20, WIDTH, HEIGHT);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (ramainingBlocks.getValue() == 0) {
                counter.increase(100);
                gui.close();
            }
            if (ramainingBalls.getValue() == 0) {
                gui.close();
            }
        }
    }

    /**
     * Initializes the paddle and adds it to the game.
     *
     * @param gui the GUI object to get the keyboard sensor
     */
    public void initializeGamePaddle(GUI gui) {
        Paddle currPaddle = new Paddle(new Rectangle(new Point(400, PADDLE_CONST_Y),
                PADDLE_WIDTH, PADDLE_HEIGHT), Color.orange, PADDLE_SPEED, gui);
        currPaddle.setGameEnvironment(this.environment);
        currPaddle.addToGame(this);
    }

    /**
     * Initializes the balls in the game.
     * Creates balls with predefined positions, velocities, and colors,
     * sets their game environment, adds them to the game, and adds them to the game environment.
     */
    public void initializeGameBalls() {
        Random random = new Random();
        // Create balls
        Ball ball1 = new Ball(new Point(BALLS_CENTER_X, BALLS_CENTER_Y), 4, Color.WHITE);
        Velocity v = Velocity.fromAngleAndSpeed(random.nextDouble() * 360, BALL_SPEED);
        ball1.setVelocity(v);
        ball1.setGameEnvironment(this.environment);
        this.environment.addBall(ball1);
        ball1.addToGame(this);
    }
}