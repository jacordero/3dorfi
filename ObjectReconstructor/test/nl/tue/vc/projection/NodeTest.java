package nl.tue.vc.projection;

import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point3;

import javafx.scene.paint.Color;
import nl.tue.vc.voxelengine.DeltaStruct;

public abstract class NodeTest {

	protected Color color;
	
	protected double boxSize;
	
	protected double positionCenterX;
	
	protected double positionCenterY;
	
	protected double positionCenterZ;
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	public double getBoxSize() {
		return boxSize;
	}
	
	public void setBoxSize(int boxSize) {
		this.boxSize = boxSize;
	}
	
	public double getPositionCenterX(){
		return positionCenterX;
	}
	
	public void setPositionCenterX(double positionCenterX){
		this.positionCenterX = positionCenterX;
	}
	
	public double getPositionCenterY(){
		return positionCenterY;
	}
	
	public void setPositionCenterY(double positionCenterY){
		this.positionCenterY = positionCenterY;
	}
	
	public double getPositionCenterZ(){
		return positionCenterZ;
	}
	
	public void setPositionCenterZ(double positionCenterZ){
		this.positionCenterZ = positionCenterZ;
	}
	
	
	public MatOfPoint3f getCorners(){
		
		double displacementSize = boxSize / 2;
		DeltaStruct displacementDirection;
		Point3[] corners = new Point3[8];
		for (int i = 0; i < 8; i++){
			displacementDirection = computeDisplacementDirections(i);
			double xPosition = positionCenterX + (displacementDirection.deltaX * displacementSize);
			double yPosition = positionCenterY + (displacementDirection.deltaY * displacementSize);
			double zPosition = positionCenterZ + (displacementDirection.deltaZ * displacementSize);
			Point3 corner = new Point3(xPosition, yPosition, zPosition);
			corners[i] = corner;
		}
		
		return new MatOfPoint3f(corners);
	}
	
	protected DeltaStruct computeDisplacementDirections(int index) {
		DeltaStruct deltas = new DeltaStruct();
		switch (index) {
		case 0:
			deltas.deltaX = -1;
			deltas.deltaY = 1;
			deltas.deltaZ = 1;
			break;
		case 1:
			deltas.deltaX = 1;
			deltas.deltaY = 1;
			deltas.deltaZ = 1;
			break;
		case 2:
			deltas.deltaX = -1;
			deltas.deltaY = -1;
			deltas.deltaZ = 1;
			break;
		case 3:
			deltas.deltaX = 1;
			deltas.deltaY = -1;
			deltas.deltaZ = 1;
			break;
		case 4:
			deltas.deltaX = -1;
			deltas.deltaY = 1;
			deltas.deltaZ = -1;
			break;
		case 5:
			deltas.deltaX = 1;
			deltas.deltaY = 1;
			deltas.deltaZ = -1;
			break;
		case 6:
			deltas.deltaX = -1;
			deltas.deltaY = -1;
			deltas.deltaZ = -1;
			break;
		case 7:
			deltas.deltaX = 1;
			deltas.deltaY = -1;
			deltas.deltaZ = -1;
			break;
		default:
			throw new RuntimeException("Invalid index value " + index);
		}

		return deltas;
	}

	
	abstract NodeTest[] getChildren();
	
	abstract boolean isLeaf();
	
	
	
	@Override
	public String toString() {
		return "{BoxSize: " + boxSize + ", centerX: " + positionCenterX + ", centerY: " + positionCenterY + ", centerZ: " + positionCenterZ + ", Color: " + color.toString() +"}";
	}
	
}
