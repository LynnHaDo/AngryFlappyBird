/**
 * Team Cat (emoji) - Defines class
 * @author Lynn, Mina, and Claudia
 */

package angryflappybird;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class containing all the parameters of the game
 */
public class Defines {
    
    // dimension of the GUI application
    final int APP_HEIGHT = 600;
    final int APP_WIDTH = 600;
    final int SCENE_HEIGHT = 570;
    final int SCENE_WIDTH = 400;

    // coefficients related to the blob
    final int BLOB_WIDTH = 60;
    final int BLOB_HEIGHT = 40;
    final int BLOB_SNOOZE_WIDTH = 60;
    final int BLOB_SNOOZE_HEIGHT = 60;
    final int BLOB_POS_X = 70;
    final int BLOB_POS_Y = 200;
    final int BLOB_DROP_TIME = 300000000; // the elapsed time threshold before
                                          // the blob starts dropping
    final int BLOB_DROP_VEL = 300; // the blob drop velocity
    int BLOB_FLY_VEL = -40;
    final int BLOB_IMG_LEN = 4;
    final int BLOB_IMG_PERIOD = 5;
    
    /**
     * limit of degrees of the blob orientation
     * @author Mina Kocer-Bowman
     */
    final int BLOB_FALL_DEGREE_LIM = 40;
    final int BLOB_FLY_DEGREE_LIM = -20;
    
    // coefficients related to the pearl (egg)
    int PEARL_WIDTH = 70;
    int PEARL_HEIGHT = 32;
    final int PEARL_COUNT = 30;
    double PEARL_OCCUR_RATE = 0.35;
    
    // coefficients related to the turtle
    final int TURTLE_WIDTH = 120;
    final int TURTLE_HEIGHT = 99;
    final int TURLE_POS_X = 400;            
    final int TURLE_POS_Y = 20;             
    double TURLE_DROP_VEL = 0.07;  
    double TURTLE_OCCUR_RATE = 0.35;
    int TURTLE_COUNT = 20;

    // coefficients related to the floors
    final int FLOOR_WIDTH = 400;
    final int FLOOR_HEIGHT = 100;
    final int FLOOR_COUNT = 2;
    
    // coefficients related to the Pipes
    int PIPE_WIDTH = 70;
    final int UPPER_PIPE_HEIGHT = 210;
    final int LOWER_PIPE_HEIGHT = 210;
    final int PIPE_MxHEIGHT = 150;
    final int PIPE_MnHEIGHT = 120;
    final int PIPE_SPACING = 200;
    double PIPE_VEL = -0.3;
    final int PIPE_COUNT = 2;
    
    // coefficients related to the background
    final int BACKGROUND_TIME = 1000;
    
    // coefficients related to snooze 
    final int SNOOZE_TIME = 6;

    // coefficients related to time
    int SCENE_SHIFT_TIME = 5;
    final double SCENE_SHIFT_INCR = -0.4;
    final double NANOSEC_TO_SEC = 1.0 / 1000000000.0;
    final double TRANSITION_TIME = 0.1;
    final int TRANSITION_CYCLE = 2;

    // coefficients related to media display
    final String STAGE_TITLE = "Angry Flappy Bird";
    private final String IMAGE_DIR = "../resources/images/";
    final String[] IMAGE_FILES = { "background", "blob0", "blob1", "blob2",
            "blob3", "floor", "background_night", "blob_snooze", "upper_pipe",
            "lower_pipe", "normal_pearl", "special_pearl", "turtle"};

    final HashMap<String, ImageView> IMVIEW = new HashMap<String, ImageView>();
    final HashMap<String, Image> IMAGE = new HashMap<String, Image>();
    
    /**
     * related to sound
     * @author Mina Kocer-Bowman
     */
    private final String AUDIO_DIR = "../resources/sounds/";
    String fullpathBG = getClass().getResource(AUDIO_DIR+"background.mp3").toExternalForm();
    Media sound = new Media(fullpathBG);
    MediaPlayer backgroundMusic = new MediaPlayer(sound);
    String snoozeAudioPath = getClass().getResource(AUDIO_DIR+"snooze.mp3").toExternalForm();
    MediaPlayer snoozeMusic = new MediaPlayer(new Media(snoozeAudioPath));

    // nodes on the scene graph
    Button startButton;
    ComboBox<String> difficultySelection;
    VBox instructionRows;
    ArrayList<String> difficultyLevels = new ArrayList<>(Arrays.asList("Easy", "Medium", "Hard"));

    // constructor
    Defines() {        
        // initialize images
        for (int i = 0; i < IMAGE_FILES.length; i++) {
            Image img;
            if (i == 5) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), FLOOR_WIDTH,
                        FLOOR_HEIGHT, false, false);
            } else if (i == 1 || i == 2 || i == 3 || i == 4) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), BLOB_WIDTH,
                        BLOB_HEIGHT, false, false);
            } else if (i == 6) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), SCENE_WIDTH,
                        SCENE_HEIGHT, false, false);
            } else if (i == 7) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), BLOB_SNOOZE_WIDTH,
                        BLOB_SNOOZE_HEIGHT, false, false);
            } else if (i == 8) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), PIPE_WIDTH,
                        UPPER_PIPE_HEIGHT, false, false);
            } else if (i == 9) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), PIPE_WIDTH,
                        LOWER_PIPE_HEIGHT, false, false);
            } else if (i == 10 || i == 11) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), PEARL_WIDTH,
                        PEARL_HEIGHT, false, false);
            } else if (i == 12) {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), TURTLE_WIDTH,
                        TURTLE_HEIGHT, false, false);
            } else {
                img = new Image(pathFile(IMAGE_DIR, IMAGE_FILES[i], "png"), SCENE_WIDTH,
                        SCENE_HEIGHT, false, false);
            }
            IMAGE.put(IMAGE_FILES[i], img);
        }

        // initialize image views
        for (int i = 0; i < IMAGE_FILES.length; i++) {
            ImageView imgView = new ImageView(IMAGE.get(IMAGE_FILES[i]));
            IMVIEW.put(IMAGE_FILES[i], imgView);
        }

        // initialize scene nodes
        startButton = new Button("Start Game");
        difficultySelection = new ComboBox<String>();
        difficultySelection.getItems().addAll(difficultyLevels);
        difficultySelection.getSelectionModel().selectFirst(); 
        instructionRows = new VBox(10);
        
        VBox row1 = new VBox(10);
        Label label1 = new Label("Bonus points");
        row1.getChildren().addAll(IMVIEW.get(IMAGE_FILES[10]), label1);
        
        VBox row2 = new VBox(10);
        Label label2 = new Label("Let you snooze");
        row2.getChildren().addAll(IMVIEW.get(IMAGE_FILES[11]), label2);
        
        VBox row3 = new VBox(10);
        Label label3 = new Label("Avoid turtles");
        row3.getChildren().addAll(new ImageView(this.resizeImage(IMAGE_FILES[12], PEARL_WIDTH, PEARL_WIDTH * TURTLE_HEIGHT/TURTLE_WIDTH)), label3);
        instructionRows.getChildren().addAll(row1, row2, row3);
    }
    
    
    /** 
     * Plays the sound of the file name - only for short sounds
     * @author Mina Kocer-Bowman
     */
    public void playSound(String fileName) {
        String fullpath = getClass().getResource(AUDIO_DIR+fileName).toExternalForm();
        AudioClip mediaPlayer = new AudioClip(fullpath);
        mediaPlayer.play();
    }
    
    
    /**
     * Concatenate folder, filepath and extension to get full directory
     * @param dir folder name
     * @param filepath name of file
     * @param ext extension
     * @return full directory
     * @author Lynn
     */
    public String pathFile(String dir, String filepath, String ext) {
        String fullpath = getClass().getResource(dir + filepath + "." + ext)
                .toExternalForm();
        return fullpath;
    }

    /**
     * Resize an image to given width and height
     * @param filepath name of image
     * @param width new width
     * @param height new height
     * @return new Image object
     */
    public Image resizeImage(String filepath, int width, int height) {
        IMAGE.put(filepath,
                new Image(pathFile(IMAGE_DIR, filepath, "png"), width, height, false, false));
        ImageView imgView = new ImageView(IMAGE.get(filepath));
        IMVIEW.put(filepath, imgView);
        return IMAGE.get(filepath);
    }
    
    /**
     * Resize objects when difficulty levels change
     * @author Lynn
     */
    private void resizeObjects() {
        // Resize pipes
        this.resizeImage("lower_pipe", this.PIPE_WIDTH, this.LOWER_PIPE_HEIGHT);
        this.resizeImage("upper_pipe", this.PIPE_WIDTH, this.UPPER_PIPE_HEIGHT);
        // Resize pearls
        this.resizeImage("normal_pearl", this.PEARL_WIDTH, this.PEARL_HEIGHT);
        this.resizeImage("special_pearl", this.PEARL_WIDTH, this.PEARL_HEIGHT);
    }
    
    /**
     * Reset the coefficients for Easy level
     * @author Lynn
     */
    public void resetCoefficients() {
        this.BLOB_FLY_VEL = -40;
        this.PEARL_OCCUR_RATE = 0.35;
        this.TURLE_DROP_VEL = 0.07;
        this.TURTLE_OCCUR_RATE = 0.35;
        this.TURTLE_COUNT = 20;
        this.PIPE_VEL = -0.3;
        this.PIPE_WIDTH = 70;
        this.PEARL_WIDTH = 70;
        this.PEARL_HEIGHT = 32;
        this.resizeObjects();
    }

    
    /**
     * Set the coefficients for Medium level
     * @author Lynn
     */
    public void setMediumCoefficients() {
        this.BLOB_FLY_VEL = -45;
        this.PEARL_OCCUR_RATE = 0.25;
        this.TURLE_DROP_VEL = 0.1;
        this.TURTLE_OCCUR_RATE = 0.45;
        this.TURTLE_COUNT = 30;
        this.PIPE_VEL = -0.4;
        this.PIPE_WIDTH = 80;
        this.PEARL_WIDTH = 75;
        this.PEARL_HEIGHT = 34;
        this.resizeObjects();
    }
    
    /**
     * Set the coefficients for Hard level
     * @author Lynn
     */
    public void setHardCoefficients() {
        this.BLOB_FLY_VEL = -47;
        this.PEARL_OCCUR_RATE = 0.25;
        this.TURLE_DROP_VEL = 0.15;
        this.TURTLE_OCCUR_RATE = 0.45;
        this.TURTLE_COUNT = 30;
        this.PIPE_VEL = -0.5;
        this.PIPE_WIDTH = 90;
        this.PEARL_WIDTH = 80;
        this.PEARL_HEIGHT = 36;
        this.resizeObjects();
    }
  

}
