package nl.tue.vc.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import nl.tue.vc.application.utils.Utils;
import nl.tue.vc.model.BoxParameters;

public class Octree {

	private Node root;
	private Node node;
	private BoxParameters boxParameters;
	private Group octreeVolume;
	private double sizeX;
	private double sizeY;
	private double sizeZ;
	private double centerX;
	private double centerY;
	private double centerZ;
	private int octreeHeight;

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
		this.sizeX = boxParams.getSizeX();
		this.sizeY = boxParams.getSizeY();
		this.sizeZ = boxParams.getSizeZ();
		this.centerX = boxParams.getCenterX();
		this.centerY = boxParams.getCenterY();
		this.centerZ = boxParams.getCenterZ();
		this.octreeHeight = octreeHeight;
		this.node = constructRootNode(Color.BLACK, sizeX, sizeY, sizeZ, this.centerX, this.centerY, this.centerZ, this.octreeHeight);
		root = node;
		//root = generateOctreeFractal(this.levels);
		this.octreeVolume = new Group();
		this.boxParameters = boxParams;
	}
	
	public Octree(double sizeX, double sizeY, double sizeZ, double centerValX, double centerValY, double centerValZ, int octreeHeight) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.centerX = centerValX;
		this.centerY = centerValY;
		this.centerZ = centerValZ;
		this.octreeHeight = octreeHeight;
		this.node = constructRootNode(Color.BLACK, sizeX, sizeY, sizeZ, centerX, centerY, centerZ, octreeHeight);
		root = node;		
		this.octreeVolume = new Group();
		this.boxParameters = new BoxParameters();
		this.boxParameters.setSizeX(sizeX);
		this.boxParameters.setSizeY(sizeY);
		this.boxParameters.setSizeZ(sizeZ);		
		this.boxParameters.setCenterX(centerX);
		this.boxParameters.setCenterY(centerY);
		this.boxParameters.setCenterZ(centerZ);
	}
	
	public void splitNodes(int newOctreeHeight){
		int deltaHeight = newOctreeHeight - octreeHeight;
		Utils.debugNewLine("octree height: " + octreeHeight, false);
		Utils.debugNewLine("new octree height: " + newOctreeHeight, false);
		Utils.debugNewLine("split root with " + deltaHeight + " levels", false);
		if (deltaHeight > 0){
			root = root.splitNode(deltaHeight);
			octreeHeight = newOctreeHeight;
		}
		
	}

	private Node constructRootNode(Color Color, double sizeX, double sizeY, double sizeZ, double centerX, double centerY, double centerZ, int octreeHeight){
		String message = "RootNode: {SizeX: " + sizeX + ", SizeY: " + sizeY + ", SizeZ: " + sizeZ;
		message += ", CenterX: " + centerX + ", CenterY: " + centerY + ", CenterZ: " + centerZ + ", Height: " + octreeHeight + "}";
		
		Utils.debugNewLine(message, true);
		if (octreeHeight > 0){
			return new InternalNode(Color, sizeX, sizeY, sizeZ, centerX, centerY, centerZ, octreeHeight);
		} else {
			return new Leaf(Color, sizeX, sizeY, sizeZ, centerX, centerY, centerZ);
		}
		// this.node = new InternalNode(Color.BLACK, boxSize, this.centerX, this.centerY, this.centerZ, this.levels);
		// this.node = new InternalNode(Color.BLACK, boxSize, centerX, centerY, centerZ, levels);
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void setRoot(Node root){
		this.root = root;
	}
	
	
	public Node generateOctreeFractal() {
		System.out.println("========================== Generate Octree Fractal with Height: " + this.octreeHeight);
		root = generateOctreeFractalAux(this.octreeHeight);
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
		double childrenSizeX = this.sizeX / 2;
		double childrenSizeY = this.sizeY / 2;
		double childrenSizeZ = this.sizeZ / 2;
		if (level == 0) {
			return generateInternalNode(childrenSizeX, childrenSizeY, childrenSizeZ);
		} else {
			Node internalNode = new InternalNode(Color.BLACK, childrenSizeX, childrenSizeY, childrenSizeZ);
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

	private Node generateInternalNode(double sizeX, double sizeY, double sizeZ) {

		node.getChildren()[0] = new Leaf(Color.BLACK, sizeX/2, sizeY/2, sizeZ/2);

		// create node 1
		node.getChildren()[1] = new Leaf(Color.GRAY, sizeX/2, sizeY/2, sizeZ/2);

		// create node 2
		node.getChildren()[2] = new Leaf(Color.WHITE, sizeX/2, sizeY/2, sizeZ/2);

		// create node 3
		node.getChildren()[3] = new Leaf(Color.GRAY, sizeX/2, sizeY/2, sizeZ/2);

		// create node 4
		node.getChildren()[4] = new Leaf(Color.BLACK, sizeX/2, sizeY/2, sizeZ/2);

		// create node 5
		node.getChildren()[5] = new Leaf(Color.GRAY, sizeX/2, sizeY/2, sizeZ/2);

		// create node 6
		node.getChildren()[6] = new Leaf(Color.WHITE, sizeX/2, sizeY/2, sizeZ/2);

		// create node 7
		node.getChildren()[7] = new Leaf(Color.GRAY, sizeX/2, sizeY/2, sizeZ/2);

		return node;
	}

	public Node getInternalNode() {
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

	public int getOctreeHeight() {
		return octreeHeight;
	}

	public void setLevels(int octreeHeight) {
		this.octreeHeight = octreeHeight;
	}

}