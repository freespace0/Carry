/* Write by
 * Ekkalak Leelasornchai 5830622421 
 * Burin Naowarat 5831034621
 * Progmeth project
 */ 
package main;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import screen.GameScreen;

import model.ArcStation;
import model.CrossStation;
import model.SquareStation;
import model.ThreadHolder;
import model.Transporter;
import model.TriangleStation;


public class Main extends Application{
//	public static Main instance;
	private Stage primaryStage;
	public static GameLogic gl;
	public static GameScreen gs;
	private static final int width=1024,heigth=768;
	
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		StackPane root = new StackPane();
	//	instance = this;
		this.primaryStage = primaryStage;
		gs = new GameScreen(root,width,heigth);
		gl = new GameLogic(gs);
		
		//Scene scene = new Scene(gs);
		primaryStage.setResizable(false);
		primaryStage.setScene(gs);				
		primaryStage.setTitle("Carry");
		primaryStage.show();
		
	}
	
	public void stop(){
		ThreadHolder.instance.stopAll();
		ThreadHolder.instance.update();
		gl.getController().interrupt();
		gl.stopSound();
		System.out.println("controller interrupt:"+gl.getController().isInterrupted());
		ThreadHolder.instance.check();
	}
}
