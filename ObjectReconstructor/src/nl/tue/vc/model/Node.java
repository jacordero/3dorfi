package nl.tue.vc.model;

import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point3;

import javafx.scene.paint.Color;
import nl.tue.vc.model.BoxParameters;
import nl.tue.vc.voxelengine.DeltaStruct;

public abstract class Node {

	protected NodeColor color;
	
	protected double sizeX;
	
	protected double sizeY;
	
	protected double sizeZ;
	
	protected double positionCenterX;
	
	protected double positionCenterY;
	
	protected double positionCenterZ;
	
	protected int depth;
	
	protected BoxParameters boxParameters;
	
	DeltaStruct displacementDirection;
	
	public DeltaStruct getDisplacementDirection() {
		return displacementDirection;
	}

	public void setDisplacementDirection(DeltaStruct displacementDirection) {
		this.displacementDirection = displacementDirection;
	}

	public BoxParameters getBoxParameters() {
		return boxParameters;
	}

	public void setBoxParameters(BoxParameters boxParameters) {
		this.boxParameters = boxParameters;
	}

	public NodeColor getColor() {
		return color;
	}
	
	public void setColor(NodeColor newColor) {
		color = newColor;
	}
	
	public double getSizeX() {
		return sizeX;
	}
	
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}
	
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public double getSizeZ() {
		return sizeZ;
	}
	
	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
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
	
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public MatOfPoint3f getCorners(){
		
		double displacementX = sizeX / 2;
		double displacementY = sizeY / 2;
		double displacementZ = sizeZ / 2;
		Point3[] corners = new Point3[8];
		for (int i = 0; i < 8; i++){
			displacementDirection = computeDisplacementDirections(i);
			double xPosition = positionCenterX + (displacementDirection.deltaX * displacementX);
			double yPosition = positionCenterY + (displacementDirection.deltaY * displacementY);
			double zPosition = positionCenterZ + (displacementDirection.deltaZ * displacementZ);
			Point3 corner = new Point3(xPosition, yPosition, zPosition);
			corners[i] = corner;
		}
		
		return new MatOfPoint3f(corners);
	}
	
	protected DeltaStruct computeDisplacementDirections(int index) {
		return OctreeUtils.computeDisplacementDirections(index);
	}

	
	abstract public Node[] getChildren();
	
	abstract public boolean isLeaf();
	
	public abstract void setChildNode(Node childNode, int childIndex);
	
	abstract public Node splitNode(int levels, int maxDepth);
	
	abstract public String printContent(String space);
	
	
	@Override
	public String toString() {
		String str = "{Color: ";
		if (color == NodeColor.BLACK){
			str += " black}";
		} else if (color == NodeColor.GRAY){
			str += " gray}";
		} else if (color == NodeColor.WHITE){
			str += " white}";
		}
		return str;
	}
}