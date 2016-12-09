package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class SquareStation extends Station{

	public SquareStation(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}


	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setLineWidth(4.0);
		//gc.strokeRect(this.x, this.y, 25, 25);
		double[] X = new double[]{100,125,150};
		double[] Y = new double[]{100,50,100};
		//gc.strokePolygon(X,Y,3);
		//gc.strokeArc(this.x, this.y, 50, 50, 55, 70, ArcType.ROUND);
		double[] X1 = new double[]{75,100,100,125,125,100,100,75,75,50,50,75};
		double[] Y1 = new double[]{50,50,75,75,100,100,125,125,100,100,75,75};
		gc.strokePolygon(X1,Y1,12);
	}
	
}