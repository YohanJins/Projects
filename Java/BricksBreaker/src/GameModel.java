// Import necessary libraries for handling shapes and collections
import java.awt.Rectangle;
import java.util.ArrayList;

public class GameModel {
    // Declare class variables
    private int score;
    private int timer;
    private int stage;
    private ArrayList<Rectangle> bricks;
    private Rectangle paddle;
    private Rectangle ball;
    private int ballSpeedX;
    private int ballSpeedY;

    // Constructor for the GameModel class
    public GameModel() {
        this.score = 0;
        this.timer = 1000;  // Set timer to 60 seconds
        this.stage = 1;
        this.bricks = new ArrayList<>();
        this.paddle = new Rectangle(275, 350, 100, 10);
        this.ball = new Rectangle(295, 340, 10, 10);
        this.ballSpeedX = 3;
        this.ballSpeedY = -3;
        initializeBricks(); // Initialize the bricks for the game
    }

    // Method to initialize bricks based on the current stage
    public void initializeBricks() {
        bricks.clear(); // Clear any existing bricks
        int[] stageBricks = {5, 10, 20}; // Define the number of bricks for each stage
        int brickWidth = 600 / stageBricks[stage - 1]; // Calculate the width of each brick
        for (int i = 0; i < stageBricks[stage - 1]; i++) {
            bricks.add(new Rectangle(i * brickWidth, 50, brickWidth - 2, 10)); // Add bricks to the list
        }
    }

    // Method to move to the next stage
    public void nextStage() {
        if (stage < 3) {
            stage++;
            timer += 1000; // Add 60 seconds to the timer
            initializeBricks(); // Re-initialize bricks for the new stage
            resetGame(); // Reset the game state
            setGameState(GameState.PLAYING); // Set the game state to playing
        }
    }

    // Method to reset the game state
    private void resetGame() {
        ball.x = 295;
        ball.y = 340;
        ballSpeedX = 3;
        ballSpeedY = -3;
    }

    // Method to move the ball and handle collisions
    public void moveBall() {
        ball.x += ballSpeedX;
        ball.y += ballSpeedY;

        // Check for ball collision with the left and right walls
        if (ball.x <= 0 || ball.x >= 590 - ball.width) {
            ballSpeedX = -ballSpeedX;
        }

        // Check for ball collision with the top and bottom walls
        if (ball.y <= 0 || ball.y >= 390 - ball.height) {
            ballSpeedY = -ballSpeedY;
        }

        // Check for ball collision with the paddle
        if (ball.intersects(paddle)) {
            ballSpeedY = -ballSpeedY;
        }

        // Check for ball collision with bricks
        for (int i = 0; i < bricks.size(); i++) {
            if (ball.intersects(bricks.get(i))) {
                bricks.remove(i); // Remove the brick that the ball collided with
                ballSpeedY = -ballSpeedY; // Reverse the ball's vertical direction
                score += 10; // Increase the score
            }
        }
    }

    // Enum to represent the different game states
    public enum GameState {
        PLAYING, GAME_OVER, CLEAR
    }

    private GameState gameState = GameState.PLAYING; // Initialize the game state to playing

    // Getter and setter methods for the class variables
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public ArrayList<Rectangle> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Rectangle> bricks) {
        this.bricks = bricks;
    }

    public Rectangle getPaddle() {
        return paddle;
    }

    public void setPaddle(Rectangle paddle) {
        this.paddle = paddle;
    }

    public Rectangle getBall() {
        return ball;
    }

    public void setBall(Rectangle ball) {
        this.ball = ball;
    }

    public int getBallSpeedX() {
        return ballSpeedX;
    }

    public void setBallSpeedX(int ballSpeedX) {
        this.ballSpeedX = ballSpeedX;
    }

    public int getBallSpeedY() {
        return ballSpeedY;
    }

    public void setBallSpeedY(int ballSpeedY) {
        this.ballSpeedY = ballSpeedY;
    }
}
