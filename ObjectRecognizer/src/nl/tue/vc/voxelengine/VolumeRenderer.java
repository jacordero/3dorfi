package nl.tue.vc.voxelengine;
	
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class VolumeRenderer extends Application {
	
	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;
	private static final int SCENE_DEPTH = 400;
	
	@Override
	public void start(Stage primaryStage) {
		
	
		
		// configure values for the volume to render
		BoxParameters boxParameters = new BoxParameters();
		
		int boxSize = 200;
		boxParameters.setBoxSize(boxSize);
		boxParameters.setCenterX(SCENE_WIDTH/2);
		boxParameters.setCenterY(SCENE_HEIGHT/2);
		boxParameters.setCenterZ(SCENE_DEPTH/2);
		
		Octree octree = new Octree(boxSize);
		octree.generateOctreeFractal(1);
		System.out.println(octree.getRoot().toString());
		VolumeGenerator volGenerator = new VolumeGenerator(octree, boxParameters);
				
		// Create a Light
		PointLight light = new PointLight();
		light.setTranslateX(SCENE_WIDTH/2 + 350);
		light.setTranslateY(SCENE_HEIGHT + 100);
		light.setTranslateX(300);
		
		// Create a Camera to view the 3D shape
		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(100);
		camera.setTranslateY(-50);
		camera.setTranslateZ(300);
				
		// Add the shapes and the light to the group
		
		Group root = volGenerator.getVolume();
		
	
		RotateTransition rotation = new RotateTransition(Duration.seconds(20), root);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setFromAngle(360);
		rotation.setToAngle(0);
		rotation.setAutoReverse(true);
		rotation.setAxis(Rotate.Y_AXIS);
		rotation.play();
		
		
		// Create a Scene with depth buffer enabled
		// width and height
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, true);
		// Add the Camera to the scene
		scene.setCamera(camera);
		// Add the Scene to the Stage
		primaryStage.setScene(scene);
		// Set the Title of the Stage
		primaryStage.setTitle("An Example with Predefined 3D Shapes");
		// Display the Stage
		primaryStage.show();
		
	}
	
	// add function to construct set of squares
	
	// add function to rotate the view
	
	public static void main(String[] args) {
		launch(args);
	}
}