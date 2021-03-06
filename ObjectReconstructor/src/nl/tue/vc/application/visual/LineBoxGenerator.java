package nl.tue.vc.application.visual;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import nl.tue.vc.model.BoxParameters;
import nl.tue.vc.model.NodeColor;
import nl.tue.vc.voxelengine.DeltaStruct;

public class LineBoxGenerator implements VoxelGenerator {

	public Box generateVoxel(BoxParameters boxParameters, DeltaStruct deltas, NodeColor nodeColor){
		
		Box box = new Box(boxParameters.getSizeX(), boxParameters.getSizeY(), boxParameters.getSizeZ());
		box.setTranslateX(boxParameters.getCenterX());
		box.setTranslateY(boxParameters.getCenterY());
		box.setTranslateZ(boxParameters.getCenterZ());
		
		PhongMaterial textureMaterial = new PhongMaterial();
		textureMaterial.setDiffuseColor(Color.CHOCOLATE);
		box.setMaterial(textureMaterial);
		box.setDrawMode(DrawMode.LINE);

		return box;
	}

}
