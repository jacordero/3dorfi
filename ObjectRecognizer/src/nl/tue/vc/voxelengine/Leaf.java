package nl.tue.vc.voxelengine;

import javafx.scene.paint.Color;

public class Leaf extends Node{

	
	
	public Leaf(Color color, double boxSize, double centerX, double centerY, double centerZ) {
		this.boxSize = boxSize;
		this.positionCenterX = centerX;
		this.positionCenterY = centerY;
		this.positionCenterZ = centerZ;
		this.color = color;
	}
	
	@Override
	boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	Node[] getChildren() {
		return null;
	}
	
	@Override
	public String toString() {
		return "Leaf -> " + super.toString();
	}
}
