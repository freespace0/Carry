package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TriangleStation extends Station{

	public TriangleStation(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	public void AddPassenger(){
		Random R = new Random();
		int r = R.nextInt(4);
		Passenger p = null;
		if(r%4 == 0)p = new SquarePassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		else if(r%4 == 1) p = new ArcPassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		else if(r%4 == 2) p = new CrossPassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		else if(r%4 == 3) p = new TrianglePassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		passengers.add(p);
		
	}

	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setLineWidth(4.0);
		gc.setStroke(Color.BLACK);
		double[] X = new double[]{getX()+11.0,getX()+22,getX()};
		double[] Y = new double[]{getY(),getY()+22,getY()+22};
		gc.strokePolygon(X,Y,3);
		
	}
	@Override
	public double getCenterX() {
		// TODO Auto-generated method stub
		return x + 11;
	}
	@Override
	public double getCenterY() {
		// TODO Auto-generated method stub
		return y + 11;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Triangle";
	}
	@Override
	public void recievePeople(Passenger p) {
		// TODO Auto-generated method stub
		Passenger newP = null;
		if(p.getType().compareTo("Square") == 0 ) newP = new SquarePassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		else if(p.getType().compareTo("Arc") == 0) newP = new ArcPassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		else if(p.getType().compareTo("Cross") == 0) newP = new CrossPassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		else if(p.getType().compareTo("Triangle") == 0 ) newP = new TrianglePassenger(getX() + 22 + getNumberOfPassengers()*12, this.y,1,Color.BLACK);
		passengers.add(newP);
	}



	
}
