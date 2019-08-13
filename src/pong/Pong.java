package pong;


import java.util.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Pong extends Application {
    //constant values
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_HEIGHT = 100;   
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_RADIUS = 15;
    
    //variables
    private int ballXSpeed = 1;
    private int ballYSpeed = 1;
    private double ballXPos = WIDTH/2;
    private double ballYPos = HEIGHT/2;
    private int scoreOfPlayer1 = 0;
    private int scoreOfPlayer2 = 0;
    private boolean gameStarted;
    private double playerOneXPos = 0;
    private double playerTwoXPos = WIDTH - PLAYER_WIDTH;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    
    public void start(Stage stage) throws Exception {
        stage.setTitle("PONG");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);

        //controlling by mouse
        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        t1.play();
    }

    private void run(GraphicsContext gc) {
        //colour of the background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        //colour of the text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(30));

        if(gameStarted) {
            //movement
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
        
            //computer
            if(ballXPos < WIDTH - WIDTH/3) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPos = (ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2) ? playerTwoYPos += 1: playerTwoYPos -1;
            }
            
            //ball
            gc.fillOval(ballXPos, ballYPos, BALL_RADIUS, BALL_RADIUS);

            } else {
                //Start the game text
                gc.setStroke(Color.WHITE);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.strokeText("Start Game", WIDTH/2, HEIGHT/2);

                //reset the start position of the ball
                ballXPos = WIDTH / 2;
                ballYPos = HEIGHT / 2;

                //reset speed and direction
                ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
                ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;

            }

    //stay in the Frame
    if(ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *= -1;    
    
    //computer gets a point
    if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
        scoreOfPlayer2++;
        gameStarted = false;
    }

        //Human gets a point
        if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {
                scoreOfPlayer1++;
                gameStarted = false;
        }
                  
        //increase speed of the ball
        if((ballXPos + BALL_RADIUS > playerTwoXPos) && (ballYPos >= playerTwoYPos) && (ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
                ballYSpeed += 1 * Math.signum(ballYSpeed);
                ballXSpeed += 1 * Math.signum(ballXSpeed);
                ballXSpeed *= -1;
                ballYSpeed *= -1;
        }

            //draw score
            gc.fillText(scoreOfPlayer1 + "\n\n" + scoreOfPlayer2, WIDTH / 2, 100);
            //DRAW PLAYER 1 AND 2
            gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
            gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        }
    public static void main(String[] args) {
        launch(args);
    }
}