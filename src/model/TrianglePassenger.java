/* Write by
 * Ekkalak Leelasornchai 5830622421 
 * Burin Naowarat 5831034621
 * Progmeth project
 */ 
package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TrianglePassenger extends Passenger{

	public TrianglePassenger(double x, double y,double size,Color color) {
		super(x, y, size, color);
		// TODO Auto-generated constructor stub
	}


	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(color);		

		gc.setGlobalAlpha(1);
		double[] X = new double[]{getX()+5/size,getX()+10/size,getX()};
		double[] Y = new double[]{getY(),getY()+10/size,getY()+10/size};
		gc.fillPolygon(X,Y,3);
	}


	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Triangle";
	}
	
}
