/**
 * Team Cat (emoji) AngryFlappyBird class
 * @author Lynn, Mina, and Claudia
 */

package angryflappybird;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;


/**
 * The main game (JavaFX Application)
 */
public class AngryFlappyBird extends Application {

    private Defines DEF = new Defines();
    
    /** Difficulty level (Easy, Medium or Hard) */
    private String difficultyLevel;

    // Time related attributes
    private long clickTime, startTime, elapsedTime;
    private AnimationTimer timer;
    /** *Time spent for the current background mode */
    private int backgroundTime;

    // Background related attributes
    /** *Image of background */
    private ImageView background;
    /** *Fish */
    private Fish blob;
    /** *List of current floors in the game*/
    private ArrayList<Sprite> floors;
    /** *List of current pipes in the game */
    private ArrayList<Sprite> pipes;
    /** *List of current pearls in the game */
    private ArrayList<Egg> eggs;
    /** *List of current turtles in the game */
    private ArrayList<Sprite> turtles;
    
    /** *Score displaying text */
    private Text scoreText;
    /** *Current score */
    private int score; 
    /** *Collision state of the fish */
    private boolean collision;
    
    /** *Lives displaying text */
    private Text livesText;
    /** *Current number of lives left */
    private int lives; 
    
    /** *Snooze state of the fish */
    private boolean isSnoozing;
    /** *Text displaying number of seconds left in snooze mode */
    private Text snoozeText;
    /** * Time (in nanoseconds) that the autopiloting mode starts */
    private double snoozeHitTime;
    
    /** * Game flags */
    private boolean CLICKED, GAME_START, GAME_OVER; 
    /** * END flag is to tell us when all 3 lives have been used */
    private boolean END;
    private boolean hitByTurtleOrPipe;
    private boolean hitByTurtle;
    
    /** * The left half of the GUI (the playground) */
    private Group gameScene; 
    /** * The right half of the GUI (the control panel) */
    private VBox gameControl; 
    private GraphicsContext gc;
    
    int currentBlobDegree;
    
   
    // the mandatory main method
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Set up the Stage layer
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // initialize scene graphs and UIs
        resetGameControl(); // resets the gameControl
        resetGameScene(true); // resets the gameScene

        HBox root = new HBox();
        HBox.setMargin(gameScene, new Insets(0, 0, 0, 15));
        root.getChildren().add(gameScene);
        root.getChildren().add(gameControl);

        // add scene graphs to scene
        Scene scene = new Scene(root, DEF.APP_WIDTH, DEF.APP_HEIGHT);

        // finalize and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle(DEF.STAGE_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Set up the control panel
     */
    private void resetGameControl() {
        DEF.startButton.setOnMouseClicked(this::mouseClickHandler);
        DEF.difficultySelection.setOnAction(this::difficultyStateChanged);
        gameControl = new VBox(20);
        gameControl.setPadding(new Insets(10, 10, 10, 10));
        gameControl.getChildren().addAll(DEF.startButton, DEF.difficultySelection, DEF.instructionRows);
    }

    /**
     * Handle events when the start button is clicked
     * @param e clicking event
     */
    private void mouseClickHandler(MouseEvent e) {
    	if (GAME_OVER) {
            resetGameScene(false);
        } else if (GAME_START) {
            clickTime = System.nanoTime();
        }
        GAME_START = true;
        CLICKED = true;
    }
    
    /**
     * Handle events when the user selects difficulty level
     * @param evt event happening as user selects an option
     * @author Lynn
     */
    private void difficultyStateChanged(ActionEvent evt) {
       if (((ComboBox)evt.getSource()).getSelectionModel().getSelectedItem() != null) {
           difficultyLevel = ((ComboBox)evt.getSource()).getSelectionModel().getSelectedItem().toString();
       } else {
           difficultyLevel = "Easy";
       }
       
       if (difficultyLevel.equals("Medium")) {
           DEF.setMediumCoefficients();
           System.out.println("Reset");
       }
       else if (difficultyLevel.equals("Hard")) {
           DEF.setHardCoefficients();
       } 
       else {
           DEF.resetCoefficients();
       }
       
    }
    
    
    /**
     * Set up the game scene
     * @author Claudia, Lynn, and Mina
     * @param firstEntry whether the user is playing the first round
     */
    private void resetGameScene(boolean firstEntry) {
        currentBlobDegree = 0;
        
        // reset variables
        CLICKED = false;
        GAME_OVER = false;
        GAME_START = false;
        /**
         * Creation of arrayLists to hold Sprite objects
         * @author Claudia and Lynn
         */
        floors = new ArrayList<>();
        pipes = new ArrayList<>();
        turtles = new ArrayList<>();
        eggs = new ArrayList<>();
       
        //Checks if we have died completely before
        if(END) {
        	//makes us have 3 lives again - rebirth
        	lives = 3;
            score = 0; // restart score as well
        	END = false; //starting anew
        }
        
        if (firstEntry) {
        	lives = 3;
            // create two canvases
            Canvas canvas = new Canvas(DEF.SCENE_WIDTH, DEF.SCENE_HEIGHT);
            gc = canvas.getGraphicsContext2D();

            // create a background
            background = DEF.IMVIEW.get("background");
            
            /**
             * create score text
             * @author Claudia
             */
            scoreText = new Text(20, 45, "0");
            scoreText.setFont(Font.font("Brush Script MT",FontWeight.NORMAL,50));
            scoreText.setStroke(Color.BLACK);
            scoreText.setFill(Color.ALICEBLUE);
            
            /**
             * create lives left text
             * @author Claudia
             */
            livesText = new Text(210, 540, Integer.toString(lives) + " lives left");
            livesText.setFont(Font.font("Brush Script MT",FontWeight.BOLD,50));
            livesText.setStroke(Color.BLACK);
            livesText.setFill(Color.WHITE);
            
            //create snooze time text
            snoozeText = new Text(20, 95, "");
            snoozeText.setFont(Font.font("Brush Script MT",FontWeight.NORMAL,30));
            snoozeText.setFill(Color.YELLOW);
            
            // create the game scene
            gameScene = new Group();
            gameScene.getChildren().addAll(background, canvas, scoreText, livesText, snoozeText);
            
        } 
        
        // sets the score to be what is saved on the score variable
        scoreText.setText(Integer.toString(score));
        // create lives left text
        livesText.setText(livesToString() + " lives left");

        // initialize floor
        for (int i = 0; i < DEF.FLOOR_COUNT; i++) {

            int posX = i * DEF.FLOOR_WIDTH;
            int posY = DEF.SCENE_HEIGHT - DEF.FLOOR_HEIGHT;

            Floor floor = new Floor(posX, posY, DEF.IMAGE.get("floor"));
            floor.setVelocity(DEF.SCENE_SHIFT_INCR, 0);
            floor.render(gc);

            floors.add(floor);
        }

        // initialize blob
        blob = new Fish(DEF.BLOB_POS_X, DEF.BLOB_POS_Y,
                DEF.IMAGE.get("blob0"));
        blob.render(gc);
        
        // initialized pipes
        //createPipes();

        // initialize timer
        startTime = System.nanoTime();
        // start background timer
        backgroundTime = 0;
        timer = new MyTimer();
        timer.start();
    }
    
    /**
     * Turns the int variable lives into a String
     * @return a string
     * @author Claudia
     */
    public String livesToString() {
    	return Integer.toString(lives);
    }
    
    /**
     * Checks on the current live count
     * Should decrease by 1 every time there is a collision
     * Updates the lives of the fish - text
     * @author Claudia
     */
    private void checkLives() {
    	
    	if(lives <= 1) {
    		GAME_OVER = true; //once we die again it's game_over
    		END = true; //we are out of lives
    		lives = lives - 1; //lives = 0
    		livesText.setText(Integer.toString(lives) + " lives left");
    	}else {
    		lives = lives - 1;
    		livesText.setText(Integer.toString(lives) + " lives left");
    	}
 
    }
    
    /**
     * Updates the scoreText based on whether the blob(fish) has crossed a set of pipes successfully
     * If pipes have been crossed completely 1 point should be added to variable score 
     * The scoreText will be updated accordingly
     * @author claudia
     */
    private void checkScore() {
        if(!collision) {
            for(int i = 0; i < pipes.size() - 1; i += 2) {
                
                Sprite current = pipes.get(i);
                    if(current.getPositionX() == DEF.BLOB_POS_X) {
                        DEF.playSound("score.mp3"); // add sound effect when score
                        score += 1;
                        scoreText.setText(Integer.toString(score));
                    }           
                }
            }
        
    }
    
    /**
     * Finds a random height value for a pipe
     * @param maxH maximum height of pipe
     * @param minH minimum height of pipe
     * @return randHeight
     * @author claudia, lynn
     */
    public int RandomHeight(int maxH, int minH) {
        int randHeight = (int) (Math.random() * (maxH - minH + 1)) + minH;
        return randHeight;
    }
    
    /**
     * Creates lower and upper pipes to appear.
     * Creates eggs (pearls).
     * Creates turtles
     * @author Claudia and Lynn
     */
    public void createPipes() {
        Pipes lowerPipe = new Pipes(400, DEF.SCENE_HEIGHT - RandomHeight(DEF.PIPE_MxHEIGHT, DEF.PIPE_MnHEIGHT), DEF.IMAGE.get("lower_pipe"));
        Pipes upperPipe = new Pipes(400, RandomHeight(0, -50), DEF.IMAGE.get("upper_pipe"));  
        
        lowerPipe.setVelocity(DEF.PIPE_VEL, 0);
        upperPipe.setVelocity(DEF.PIPE_VEL, 0);
        
        pipes.add(upperPipe);
        pipes.add(lowerPipe);
        
        createEggs(lowerPipe);
        createTurtles();
    }
    
    /**
     * Changes the day and night backgrounds in the game based on backgroundTime
     * @author Claudia
     */
    public void updateBackground() {
        
    	//updates every time the function is called
        backgroundTime = backgroundTime + 1;
        
        // checks if it is time to change the background
        if(backgroundTime > DEF.BACKGROUND_TIME) {
            
            // checks which image is currently being shown
            if(background.getImage() == DEF.IMAGE.get("background_night")) {
                background.setImage(DEF.IMAGE.get("background"));
            } else {
                background.setImage(DEF.IMAGE.get("background_night"));
            }
            
            // turn back time to zero
            backgroundTime = 0;
        }
    }
    
        
    /**
     * Creates the turtles at random that are obstacles for the fish
     * They will fall at a certain velocity from the right of the screen
     * @author Claudia
     */
    public void createTurtles() {
        
        // we want to make them randomly -> returns a number between 0 - 1
        double randomNumber = Math.random();
        
        if(turtles.size() <= DEF.TURTLE_COUNT && randomNumber < DEF.TURTLE_OCCUR_RATE) {
            
            // have to update the positions x and y
            Turtle turtle = new Turtle(400, 20, DEF.IMAGE.get("turtle"));
            
            turtle.setVelocity(-DEF.TURLE_DROP_VEL, DEF.TURLE_DROP_VEL);
            
            turtles.add(turtle); 
            
            turtle.render(gc);
        }
    }
    
    /**
     * Create pearls (eggs) that is a mix of both normal (bonus point) and 
     * special (snooze) pearls 
     * @param lowerPipe lower pipe to put pearls on
     * @author Lynn
     */
    public void createEggs(Sprite lowerPipe) {
        
        double randomNumber = Math.random(); // generate a random float from 0 to 1
        double pipeXPos = lowerPipe.getPositionX();
        double pipeYPos = lowerPipe.getPositionY();
        
        // Generate normal eggs
        if (eggs.size() <= DEF.PEARL_COUNT && randomNumber < DEF.PEARL_OCCUR_RATE && randomNumber >= 0.2) {
            Egg normalEgg = new Egg(pipeXPos, pipeYPos - DEF.PEARL_HEIGHT, DEF.IMAGE.get("normal_pearl"));
            normalEgg.setWidth(DEF.PEARL_WIDTH);
            normalEgg.setHeight(DEF.PEARL_HEIGHT);
            normalEgg.setVelocity(DEF.PIPE_VEL, 0);
            eggs.add(normalEgg);
            normalEgg.render(gc);
        }
        // Generate special eggs
        else if (eggs.size() <= DEF.PEARL_COUNT && randomNumber < DEF.PEARL_OCCUR_RATE && randomNumber < 0.2) {
            Egg specialEgg = new Egg(pipeXPos, pipeYPos - DEF.PEARL_HEIGHT, DEF.IMAGE.get("special_pearl"));
            specialEgg.setVelocity(DEF.PIPE_VEL, 0);
            specialEgg.setNormalEgg(false);
            specialEgg.setWidth(DEF.PEARL_WIDTH);
            specialEgg.setHeight(DEF.PEARL_HEIGHT);
            eggs.add(specialEgg);
            specialEgg.render(gc);
        }
        
    }
    

    /**
     * Handle events and animations as time passes
     * @author Claudia, Lynn, Mina
     */
    class MyTimer extends AnimationTimer {

        int counter = 0;

        @Override
        public void handle(long now) {
            // time keeping
            elapsedTime = now - startTime;
            startTime = now;

            // clear current scene
            gc.clearRect(0, 0, DEF.SCENE_WIDTH, DEF.SCENE_HEIGHT);

            if (GAME_START) {
                DEF.difficultySelection.getSelectionModel().select(DEF.difficultyLevels.indexOf(difficultyLevel));
                DEF.difficultySelection.setDisable(true); // disable difficulty selection
                
                // step1: update floor & background
                moveFloor();
                updateBackground();
                
                // choose sound to play
                if (!isSnoozing) {
                    DEF.backgroundMusic.play(); // start music
                    DEF.backgroundMusic.setVolume(0.1);
                    DEF.snoozeMusic.stop();
                } else {
                    DEF.snoozeMusic.play();
                }
                
                // play background music again if it has ended
                DEF.backgroundMusic.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        DEF.backgroundMusic.seek(Duration.ZERO);
                        DEF.backgroundMusic.play();
                    }
                });
               

                // step2: update blob
                moveBlob(); 
                
                // step3: update pipes
                movePipes();
                checkPipes();
                
                // step4: update turtles & eggs
                checkTurtles();
                checkEggs();
      
                // step5: check collision
                if (!hitByTurtleOrPipe) {
                    checkCollision();
                }

                // step6: check if we are snoozing
                if (isSnoozing) { 
                    snooze();
                    setSnoozeMode();
                    realignFish();
                }
                
                // step7: update score
                checkScore();
            }
        }

        /**
         * Update the floor position and render to screen
         */
        private void moveFloor() {

            for (int i = 0; i < DEF.FLOOR_COUNT; i++) {
                if (floors.get(i).getPositionX() <= -DEF.FLOOR_WIDTH) {
                    double nextX = floors.get((i + 1) % DEF.FLOOR_COUNT)
                            .getPositionX() + DEF.FLOOR_WIDTH;
                    double nextY = DEF.SCENE_HEIGHT - DEF.FLOOR_HEIGHT;
                    floors.get(i).setPositionXY(nextX, nextY);
                }
                floors.get(i).render(gc);
                floors.get(i).update(DEF.SCENE_SHIFT_TIME);
            }
        }

        /**
         * Update the blob position based on clicking events/snoozing
         */
        private void moveBlob() {

            long diffTime = System.nanoTime() - clickTime;

            // blob flies upward with animation
            if (CLICKED && diffTime <= DEF.BLOB_DROP_TIME && !isSnoozing) {
                int imageIndex = Math.floorDiv(counter++, DEF.BLOB_IMG_PERIOD);
                imageIndex = Math.floorMod(imageIndex, DEF.BLOB_IMG_LEN);
                blob.setImage(
                        DEF.IMAGE.get("blob" + String.valueOf(imageIndex)));
                
                /**
                 * orient fish upwards
                 * @author Mina Kocer-Bowman
                 */
                blob.setImage(rotate("blob0",-currentBlobDegree, gc)); // reset rotation
                if (currentBlobDegree >= DEF.BLOB_FLY_DEGREE_LIM) {
                    blob.setImage(rotate("blob" + String.valueOf(imageIndex), currentBlobDegree, gc));
                    if (currentBlobDegree != DEF.BLOB_FLY_DEGREE_LIM) {
                        currentBlobDegree -= 5;
                    }
                }
                
                blob.setVelocity(0, DEF.BLOB_FLY_VEL);
            }
            // blob drops after a period of time without button click
            else if (!(CLICKED && diffTime <= DEF.BLOB_DROP_TIME) && !isSnoozing){

                /**
                 * orient fish downwards
                 * @author Mina Kocer-Bowman
                 */
                blob.setImage(rotate("blob0",-currentBlobDegree, gc)); // reset rotation
                if (currentBlobDegree <= DEF.BLOB_FALL_DEGREE_LIM) {
                    blob.setImage(rotate("blob0", currentBlobDegree, gc));
                    if (currentBlobDegree != DEF.BLOB_FALL_DEGREE_LIM) {
                        currentBlobDegree += 5;
                    }
                }
               
                blob.setVelocity(0, DEF.BLOB_DROP_VEL);
                CLICKED = false;
            } 
            else { //move forward on snooze mode
                blob.setVelocity(0, 0);
            }

            // render blob on GUI
            blob.update(elapsedTime * DEF.NANOSEC_TO_SEC);
            blob.render(gc);
        }

        /**
         * Update pipes by moving them from the right to left at a costant speed
         * Create turtles and eggs on the way
         * @author Claudia and Lynn
         */
        private void movePipes() {
            for (Sprite pipe : pipes) {
                pipe.render(gc);
                pipe.update(DEF.SCENE_SHIFT_TIME);
            }
            moveTurtles();
            moveEggs();
        }
        
        /**
         * Creates new pipes whenever the first set of pipes cross a certain point of the screen
         * Deletes set of pipes from the pipes arraylist if they have
         * @author Claudia
         */
        private void checkPipes() {
            if(pipes.size() != 0) {
                int latestPipes = pipes.size() - 1;
                if (pipes.get(latestPipes).getPositionX() == DEF.SCENE_WIDTH / 2 + 20) {
                    createPipes();
                }else if (pipes.get(0).getPositionX() <= -DEF.PIPE_WIDTH) {
                    pipes.remove(0);
                    pipes.remove(0);
                }   
            } else {
                createPipes();
            }
        }
        
        /**
         * Checks if should make new Turtles based on the position of the first Turtles on the ArrayList
         * If turtles have passed a certain point on the screen
         * 		new turtles will be created
         * 		old turtles will be deleted
         * @author Claudia
         */
        private void checkTurtles() {
            if(turtles.size() != 0) {
                int latestTurtle = turtles.size() - 1;
                if (turtles.get(latestTurtle).getPositionX() == DEF.SCENE_WIDTH / 2 + 20) {
                    createTurtles();
                }else if (turtles.get(0).getPositionX() <= -DEF.TURTLE_WIDTH) {
                    turtles.remove(0);
                    
                }   
                
            }
        }
        
        /**
         * Create eggs as blob moves further to the right
         * Delete eggs that have been crossed
         * @author Lynn
         */
        private void checkEggs() {
            if (eggs.size() != 0 && pipes.size() != 0) {
                int latestEggIndex = eggs.size() - 1;
                if (eggs.get(latestEggIndex).getPositionX() == DEF.SCENE_WIDTH/2 + 20) {
                    createEggs(pipes.get(pipes.size() - 1));
                } else if (eggs.get(0).getPositionX() <= -DEF.PEARL_WIDTH) {
                    eggs.remove(0);
                }
            }
        }
        
        /**
         * Move turtles (pigs) around the screen at a certain time
         * Renders and updates each Turtle on the turtle ArrayList
         * @author claudia
         */
        public void moveTurtles() {
            for (Sprite turtle : turtles) {

                turtle.render(gc);
                turtle.update(7);
                
            }
            
        }
        
        /**
         * Move eggs (pearls) along with the pipes
         * Renders and updates the pearls 
         * @author Lynn Do
         */
        public void moveEggs() {
            for (Egg egg : eggs) {

                egg.render(gc);
                egg.update(DEF.SCENE_SHIFT_TIME);
                
            }
            
        }
        
        /**
         *  Makes sure that the fish is not going through pipes during snooze mode. 
         *  Realigns the fish above the pipe
         * @author Mina Kocer-Bowman
         */
        public void realignFish() {
            for (Sprite pipe : pipes) {
                if (blob.intersectsSprite(pipe)) {
                    blob.setPositionXY(DEF.BLOB_POS_X, blob.getPositionY() - 30);
                    DEF.playSound("score.mp3");
                }
            }

        }
        
        /**
         * Check if any turtles hit the pearls
         * @return index of the pearl that the turtle hits
         * @author Lynn Do
         */
        public int checkEggsTurtlesCollision() {
            if (eggs.size() != 0 && turtles.size() != 0) {
                for (int i = 0; i < eggs.size(); i++) {
                    for (Sprite turtle : turtles) {
                        if (turtle.intersectsSprite(eggs.get(i))) {
                            return i;
                        }
                    }
                }
            }
            return -1;
        }
        
        /**
         * Check if the blob hits any pearls (eggs)
         * @return index of the pearl that the blob hits
         * @author Lynn Do
         */
        public int checkEggsCollision() {
            if (eggs.size() != 0) {
                for (int i = 0; i < eggs.size(); i++) {
                    if (blob.intersectsSprite(eggs.get(i)) && eggs.get(i).isNormalEgg()) {
                        score += 1;
                        scoreText.setText(Integer.toString(score));
                        DEF.playSound("collect_pearl.mp3");
                        return i;
                    }
                    else if (blob.intersectsSprite(eggs.get(i)) && !eggs.get(i).isNormalEgg()) {
                        isSnoozing = true;
                        snoozeHitTime = System.nanoTime();
                        snoozeText.setText("6 seconds to go");
                        return i;
                    }
                }
            }
            return -1;
        }
        
        /**
         * Round time to 2 decimals
         * @param time in nanoseconds
         * @return time with 2 decimals
         * @author Lynn Do
         */
        private double roundDecimalByTwo(double time) {
            BigDecimal bd = new BigDecimal(Double.toString(time));
            bd = bd.setScale(2, RoundingMode.HALF_DOWN);
            return bd.doubleValue();
        }
        
        /**
         * removes all the turtles and eggs during snooze mode
         *  @author Mina Kocer-Bowman
         */
        public void setSnoozeMode() {
            turtles.clear();
            eggs.clear();
        }
        
        /** 
         * Activates snooze mode when the special pearl is picked up
         * Display remaining time on the top left of the game scene
         * @author Mina Kocer-Bowman and Lynn Do
         */
        public void snooze() {
            blob.setImage(DEF.IMAGE.get("blob_snooze"));
            DEF.startButton.setDisable(true); // prevent user from moving the fish while on snooze
            double secondsPassed = roundDecimalByTwo((System.nanoTime() - snoozeHitTime) * DEF.NANOSEC_TO_SEC);
            if (secondsPassed % 1 == 0) { // if pass an integer number of seconds
                snoozeText.setText(Double.toString(DEF.SNOOZE_TIME - secondsPassed).split("\\.")[0] + " seconds to go");
            }
            // deactivate snooze
            if (secondsPassed > DEF.SNOOZE_TIME) {
                CLICKED = true;
                isSnoozing = false;
                DEF.startButton.setDisable(false);  
                clickTime = (long) (System.nanoTime() + secondsPassed);
                snoozeText.setText("");
                moveBlob();
            }
        }
    

        
        /**
         * Check if the fish collides with any obstacles (pipes/turtles/floors) 
         * turn this off if in snooze mode, and have the fish bounce back if it hits a pipe or turtle
         * and end the game
         * @author Claudia, Mina Kocer-Bowman, Lynn
         */
        public void checkCollision() {

            if (!isSnoozing) {
                // check for collision for these objects if snooze mode is off
                for (Sprite floor : floors) {
                    GAME_OVER = GAME_OVER || (blob.intersectsSprite(floor) && blob.getPositionY() >= 430);
                    collision = true;
                }
                
                // check collision for turtles
                for (Sprite turtle : turtles) {
                    if (blob.intersectsSprite(turtle)){
                        hitByTurtle = true;
                        bounceBack();
                    }
                    GAME_OVER = GAME_OVER || blob.intersectsSprite(turtle);
                    collision = true;
                }
                
                // check collision for pipes
                for (Sprite pipe : pipes) {
                    if (blob.intersectsSprite(pipe)){
                        hitByTurtle = false;
                        bounceBack();
                    }
                    GAME_OVER = GAME_OVER || blob.intersectsSprite(pipe);
                    collision = true;
                }
            }
            
            // check collision for eggs
            int hitEggIndex = checkEggsCollision();
            if (hitEggIndex != -1) {
                eggs.remove(hitEggIndex);
            }
            
            // check collision of turtles and eggs
            int turtleHitIndex = checkEggsTurtlesCollision();
            if (turtleHitIndex != -1) {
                eggs.remove(turtleHitIndex);
                if (score > 0) {
                    score = score - 1;
                    scoreText.setText(Integer.toString(score));
                }
            }
          
            // end the game when blob hit stuff
            if (GAME_OVER) {
                showHitEffect();
                DEF.backgroundMusic.stop(); // stops the BG music
                DEF.playSound("hit.mp3");
            	checkLives(); // updates live count and text
                blob.setCollision(true);
                stopMovement(floors);
                stopMovement(turtles);
                stopMovement(pipes);
                for (Egg egg : eggs) {
                    egg.setVelocity(0, 0);
                }
                DEF.difficultySelection.setDisable(false);
                DEF.difficultySelection.getSelectionModel().select(DEF.difficultyLevels.indexOf(difficultyLevel));
                isSnoozing = false;
                snoozeText.setText("");
                blob.setImage(rotate("blob0",-currentBlobDegree, gc)); // reset rotation
                timer.stop();
            }
            
            // updates variable 
            collision = blob.getCollision();
        }
        
        /**
         * Stop all elements in a list of sprites
         * @param sprites list of objects
         * @author Lynn
         */
        private void stopMovement(ArrayList<Sprite> sprites) {
            for (Sprite sprite : sprites) {
                sprite.setVelocity(0, 0);
            }
        }
        
        /**
         * fish bounces back animation
         * @author Mina Kocer-Bowman
         */
        public void bounceBack() {
            hitByTurtleOrPipe = true;
            int heightThreshold = 400;
            if (hitByTurtle) {
                blob.setVelocity(-3, 20);
            } else {
                blob.setVelocity(-10, 15);
            }
            while (blob.getPositionY() <= heightThreshold) {
                blob.render(gc);
                blob.update(10);
            }
            hitByTurtleOrPipe = false;
        }
        
        /**
         * rotates an image
         * @param imageName the name of the image
         * @param degree the degree of rotation
         * @param gc the graphics context
         * @return rotated image
         * @author Mina Kocer-Bowman
         */
        public Image rotate(String imageName, int degree, GraphicsContext gc) {
            ImageView iv = DEF.IMVIEW.get(imageName);
            iv.setRotate(degree);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage = iv.snapshot(params, null);
            return rotatedImage;
        }

        /**
         * Display effect when the blob hits obstacles
         */
        private void showHitEffect() {
            ParallelTransition parallelTransition = new ParallelTransition();
            FadeTransition fadeTransition = new FadeTransition(
                    Duration.seconds(DEF.TRANSITION_TIME), gameScene);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(DEF.TRANSITION_CYCLE);
            fadeTransition.setAutoReverse(true);
            parallelTransition.getChildren().add(fadeTransition);
            parallelTransition.play();
        }

    } // End of MyTimer class

} // End of AngryFlappyBird Class