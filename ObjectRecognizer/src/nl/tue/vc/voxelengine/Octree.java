package nl.tue.vc.voxelengine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Mat;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import nl.tue.vc.application.ApplicationConfiguration;
import nl.tue.vc.projection.IntersectionStatus;
import nl.tue.vc.projection.TransformMatrices;
import nl.tue.vc.projection.Vector3D;
import nl.tue.vc.projection.VolumeModel;

public class Octree {

	private Node root;
	private Node node;
	private BoxParameters boxParameters;
	private Group octreeVolume;
	private double boxSize;
	private double centerX;
	private double centerY;
	private double centerZ;
	private int levels;

	/**
	 *        +---------+-----------+
	 *       +     2   +    3     + |
	 *     + -------+-----------+   |
	 *   +    6   +     7    +  | 3 |
	 *  ---------------------   | + |
	 *  |        |          | 7 +   |
	 *  |    6   |    7     | + | 1 + 
	 *  --------------------+   | + 
	 *  |        |          | 5 + 
	 *  |    4   |    5     | + 
	 *  --------------------+ 
	 *    
	 */

	public Octree(BoxParameters boxParams, int octreeHeight) {
		this.boxSize = boxParams.getBoxSize();
		this.centerX = boxParams.getCenterX();
		this.centerY = boxParams.getCenterY();
		this.centerZ = boxParams.getCenterZ();
		this.levels = levels;
		this.node = constructRootNode(Color.BLACK, boxSize, this.centerX, this.centerY, this.centerZ, this.levels);
		root = node;
		//root = generateOctreeFractal(this.levels);
		this.octreeVolume = new Group();
		this.boxParameters = boxParams;
	}
	
	public Octree(double size, double centerValX, double centerValY, double centerValZ, int octreeHeight) {
		this.boxSize = size;
		this.centerX = centerValX;
		this.centerY = centerValY;
		this.centerZ = centerValZ;
		this.levels = levels;
		this.node = constructRootNode(Color.BLACK, boxSize, centerX, centerY, centerZ, levels);
		root = node;		
		this.octreeVolume = new Group();
		this.boxParameters = new BoxParameters();
		this.boxParameters.setBoxSize((int)boxSize);
		this.boxParameters.setCenterX((int)centerX);
		this.boxParameters.setCenterY((int)centerY);
		this.boxParameters.setCenterZ((int)centerZ);
	}

	private Node constructRootNode(Color nodeColor, double boxSize, double centerX, double centerY, double centerZ, int octreeHeight){
		if (octreeHeight > 0){
			return new InternalNode(nodeColor, boxSize, centerX, centerY, centerZ, octreeHeight);
		} else {
			return new Leaf(nodeColor, boxSize, centerX, centerY, centerZ);
		}
		// this.node = new InternalNode(Color.BLACK, boxSize, this.centerX, this.centerY, this.centerZ, this.levels);
		// this.node = new InternalNode(Color.BLACK, boxSize, centerX, centerY, centerZ, levels);
	}
	
	public Node getRoot() {
		return root;
	}
	
	public Node generateOctreeFractal() {
		System.out.println("========================== Levels: " + this.levels);
		root = generateOctreeFractalAux(this.levels);
		return root;
	}

	public Node generateOctreeFractal(int level) {
//		DeltaStruct deltas = root.getDisplacementDirection();
//		BoxParameters params = root.getBoxParameters();
		root = generateOctreeFractalAux(level);
//		root.setBoxParameters(params);
//		root.setDisplacementDirection(deltas);
		return root;
	}

	private Node generateOctreeFractalAux(int level) {
		double nodesBoxSize = this.boxSize / 2;
		if (level == 0) {
			return generateInternalNode(nodesBoxSize);
		} else {
			Node internalNode = new InternalNode(Color.BLACK, nodesBoxSize);
			// create node 0
			internalNode.getChildren()[0] = generateOctreeFractalAux(level - 1);

			// create node 1
			internalNode.getChildren()[1] = generateOctreeFractalAux(level - 1);

			// create node 2
			internalNode.getChildren()[2] = generateOctreeFractalAux(level - 1);

			// create node 3
			internalNode.getChildren()[3] = generateOctreeFractalAux(level - 1);

			// create node 4
			internalNode.getChildren()[4] = generateOctreeFractalAux(level - 1);

			// create node 5
			internalNode.getChildren()[5] = generateOctreeFractalAux(level - 1);

			// create node 6
			internalNode.getChildren()[6] = generateOctreeFractalAux(level - 1);

			// create node 7
			internalNode.getChildren()[7] = generateOctreeFractalAux(level - 1);

			return internalNode;
		}

	}

	private Node generateInternalNode(double nodesBoxSize) {

		node.getChildren()[0] = new Leaf(Color.BLACK, nodesBoxSize/2);

		// create node 1
		node.getChildren()[1] = new Leaf(Color.RED, nodesBoxSize/2);

		// create node 2
		node.getChildren()[2] = new Leaf(Color.GREEN, nodesBoxSize/2);

		// create node 3
		node.getChildren()[3] = new Leaf(Color.YELLOW, nodesBoxSize/2);

		// create node 4
		node.getChildren()[4] = new Leaf(Color.GRAY, nodesBoxSize/2);

		// create node 5
		node.getChildren()[5] = new Leaf(Color.BROWN, nodesBoxSize/2);

		// create node 6
		node.getChildren()[6] = new Leaf(Color.CYAN, nodesBoxSize/2);

		// create node 7
		node.getChildren()[7] = new Leaf(Color.ORANGE, nodesBoxSize/2);

		return node;
	}

	public Node getInernalNode() {
		return this.node;
	}

	public BoxParameters getBoxParameters() {
		return boxParameters;
	}

	public void setBoxParameters(BoxParameters boxParameters) {
		this.boxParameters = boxParameters;
	}

	public Group getOctreeVolume() {
		return this.octreeVolume;
	}

	public void setOctreeVolume(Group octreeVolume) {
		this.octreeVolume = octreeVolume;
	}
	
	@Override
	public String toString(){
		return root.toString();
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

}
