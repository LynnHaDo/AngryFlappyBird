/**
 * Team Cat - JUnit Test for Defines.java
 * @author claudia
 */
package angryflappybird;

import javafx.application.Platform;
import javafx.scene.image.Image;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * JUnit Test Class to test the Defines Class from the angryflappybird package
 */
class DefinesTest {
	
	/**
	 * This needs to happen in order for the JavaFX objects to initialize properly before testing
	 */
	@BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {
        });
    }
	
	// declaring and initializing globally is the only way the further tests can run.
	Defines definesTest = new Defines();


	/**
	 * @author claudia
	 * Testing the IMGAGES Hashmap to be the correct size
	 */
	@Test
	void IMAGETest() {
		/**
		 * There are 13 different images that are in the resource folder that need to be turned into
		 * Image objects
		 * Expected output: 13
		 */
		int returned = definesTest.IMAGE.size();
		assertEquals(13, returned);
	}
	
	/**
	 * @author claudia
	 * Testing the IMVIEW Hashmap to be the correct size
	 */
	@Test
	void IMVIEWTest() {
		/**
		 * There are 13 different images that are in the resource folder that need to be turned into
		 * Image objects
		 * Expected output: 13
		 */
		int returned = definesTest.IMVIEW.size();
		assertEquals(13, returned);
	}
	
	/**
	 * @author claudia
	 * Testing the coefficients for Pipe Sprite
	 */
	@Test
	void PipesTest() {
		/**
		 * testing PIPE_COUNT to return the current amount
		 * expected output 2
		 */
		int returnedCount = definesTest.PIPE_COUNT;
		assertEquals(2, returnedCount);
		
		/**
		 * testing PIPE_WIDTH to return the current amount
		 * expected output 70
		 */
		int returnedWidth = definesTest.PIPE_WIDTH;
		assertEquals(70, returnedWidth);
		
		/**
		 * testing UPPER_PIPE_HEIGHT to return the current amount
		 * expected output 210
		 */
		int returnedUpperHeight = definesTest.UPPER_PIPE_HEIGHT;
		assertEquals(210, returnedUpperHeight);
		
		/**
		 * testing PIPE_SPACING to return the current amount
		 * expected output 200
		 */
		int returnedPipeSpacing = definesTest.PIPE_SPACING;
		assertEquals(200, returnedPipeSpacing);
		
		
	}
}
	