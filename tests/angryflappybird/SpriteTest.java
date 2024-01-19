/**
 * Team Cat - JUnit Test for Sprite.java
 * @author Claudia
 */
package angryflappybird;
import javafx.application.Platform;
import javafx.scene.image.Image;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Test Class to test the Sprites from the angryflappybird package
 * @author Claudia
 */
class SpriteTest {
	/**
	 * Due to issues with our Sprites being made from an  abstract class, to avoid further issues
	 * we will re-declare and initialize Sprite objects in each test.
	 */
	
	// declaring Sprites
	Sprite testTurtle;
	Sprite testFish;
	Sprite testPipe;
	Sprite testEgg;
	Sprite testFloor;

	/*
	 * This needs to happen in order for the JavaFX objects to initialize properly before testing
	 */
	@BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {
        });
    }

	/**
	 * Testing the getter for PositionX on any Sprite
	 * @author Claudia 
	 */
	@Test
	void testGetPositionX() {
		/**
		 * Expected output: 100
		 */
		
		/**
		 * an Image object that is made to fill the Image parameter for any child class from Sprite
		 * the image might not fit the name of the class that will be tested, however this does not affect it 
		 */
		Image img = new Image("/resources/images/turtle.png", 100,
	           100, false, false);

		// Creating Sprites we use in the AngryFlappyBird.java class
		testTurtle = new Turtle(100, 200, img);
		testFish = new Fish(100, 200, img);
		testPipe = new Pipes(100, 200, img);
		testEgg = new Egg(100, 200, img);
		testFloor = new Floor(100, 200, img);
		
		// testing getPositionX() function for each sprite
		double returnedTurtle = testTurtle.getPositionX();
		assertEquals(100, returnedTurtle);
		
		double returnedFish = testFish.getPositionX();
		assertEquals(100, returnedFish);
		
		double returnedPipe = testPipe.getPositionX();
		assertEquals(100, returnedPipe);
		
		double returnedEgg = testEgg.getPositionX();
		assertEquals(100, returnedEgg);
		
		double returnedFloor = testFloor.getPositionX();
		assertEquals(100, returnedFloor);
		
	}
	
	/**
	 * Testing the getter for PositionY on any Sprite
	 * @author Claudia
	 */
	@Test
	void testGetPositionY() {
		/**
		 * Expected output: 200
		 */
		
		/**
		 * an Image object that is made to fill the Image parameter for any child class from Sprite
		 * the image might not fit the name of the class that will be tested, however this does not affect it 
		 */
		Image img = new Image("/resources/images/turtle.png", 100,
	           100, false, false);

		// Creating Sprites we use in the AngryFlappyBird.java class
		testTurtle = new Turtle(100, 200, img);
		testFish = new Fish(100, 200, img);
		testPipe = new Pipes(100, 200, img);
		testEgg = new Egg(100, 200, img);
		testFloor = new Floor(100, 200, img);
		
		// testing getPositionY() function for each sprite
		double returnedTurtle = testTurtle.getPositionY();
		assertEquals(200, returnedTurtle);
		
		double returnedFish = testFish.getPositionY();
		assertEquals(200, returnedFish);
		
		double returnedPipe = testPipe.getPositionY();
		assertEquals(200, returnedPipe);
		
		double returnedEgg = testEgg.getPositionY();
		assertEquals(200, returnedEgg);
		
		double returnedFloor = testFloor.getPositionY();
		assertEquals(200, returnedFloor);
		
	}
	
	/**
	 * Testing the getter for VelocityX on any Sprite
	 * @author Claudia
	 */
	@Test
	void testGetVelocityY() {
		/**
		 * Expected output: 50
		 */
		
		/**
		 * an Image object that is made to fill the Image parameter for any child class from Sprite
		 * the image might not fit the name of the class that will be tested, however this does not affect it 
		 */
		Image img = new Image("/resources/images/turtle.png", 100,
	           100, false, false);

		// Creating Sprites we use in the AngryFlappyBird.java class
		testTurtle = new Turtle(100, 200, img);
		testFish = new Fish(100, 200, img);
		testPipe = new Pipes(100, 200, img);
		testEgg = new Egg(100, 200, img);
		testFloor = new Floor(100, 200, img);
		
		// setting the velocity for every Sprite
		testTurtle.setVelocity(10, 50);
		testFish.setVelocity(10, 50);
		testPipe.setVelocity(10, 50);
		testEgg.setVelocity(10, 50);
		testFloor.setVelocity(10, 50);
			
		
		// testing getVelocityY() function for each sprite
		double returnedTurtle = testTurtle.getVelocityY();
		assertEquals(50, returnedTurtle);
		
		double returnedFish = testFish.getVelocityY();
		assertEquals(50, returnedFish);
		
		double returnedPipe = testPipe.getVelocityY();
		assertEquals(50, returnedPipe);
		
		double returnedEgg = testEgg.getVelocityY();
		assertEquals(50, returnedEgg);
		
		double returnedFloor = testFloor.getVelocityY();
		assertEquals(50, returnedFloor);
		
	}
	
	/**
	 * Testing the getter for VelocityX on any Sprite
	 * @author Claudia
	 */
	@Test
	void testGetVelocityX() {
		/**
		 * Expected output: 10
		 */
		
		/**
		 * an Image object that is made to fill the Image parameter for any child class from Sprite
		 * the image might not fit the name of the class that will be tested, however this does not affect it 
		 */
		Image img = new Image("/resources/images/turtle.png", 100,
	           100, false, false);

		// Creating Sprites we use in the AngryFlappyBird.java class
		testTurtle = new Turtle(100, 200, img);
		testFish = new Fish(100, 200, img);
		testPipe = new Pipes(100, 200, img);
		testEgg = new Egg(100, 200, img);
		testFloor = new Floor(100, 200, img);
		
		// setting the velocity for every Sprite
		testTurtle.setVelocity(10, 50);
		testFish.setVelocity(10, 50);
		testPipe.setVelocity(10, 50);
		testEgg.setVelocity(10, 50);
		testFloor.setVelocity(10, 50);
			
		
		// testing getVelocityY() function for each sprite
		double returnedTurtle = testTurtle.getVelocityX();
		assertEquals(10, returnedTurtle);
		
		double returnedFish = testFish.getVelocityX();
		assertEquals(10, returnedFish);
		
		double returnedPipe = testPipe.getVelocityX();
		assertEquals(10, returnedPipe);
		
		double returnedEgg = testEgg.getVelocityX();
		assertEquals(10, returnedEgg);
		
		double returnedFloor = testFloor.getVelocityX();
		assertEquals(10, returnedFloor);
		
	}

}

