package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

public class TriangleStation extends Station{

	public TriangleStation(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	public void AddPassenger(){
		Random R = new Random();
		int r = R.nextInt(4);
		Passenger p = null;
		if(r%4 == 0)p = new SquarePassenger(getX() + 22 + getNumberOfPassengers()*12, this.y);
		else if(r%4 == 1) p = new ArcPassenger(getX() + 22 + getNumberOfPassengers()*12, this.y);
		else if(r%4 == 2) p = new CrossPassenger(getX() + 22 + getNumberOfPassengers()*12, this.y);
		else if(r%4 == 3) p = new TrianglePassenger(getX() + 22 + getNumberOfPassengers()*12, this.y);
		passengers.add(p);
		
	}

	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setLineWidth(4.0);
		
		double[] X = new double[]{getX()+11.0,getX()+22,getX()};
		double[] Y = new double[]{getY(),getY()+22,getY()+22};
		gc.strokePolygon(X,Y,3);
		
	}



	
}
