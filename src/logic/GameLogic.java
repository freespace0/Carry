/* Write by
 * Ekkalak Leelasornchai 5830622421 
 * Burin Naowarat 5831034621
 * Progmeth project
 */ 
package logic;

import java.util.Random;

import exception.SoundNotFoundException;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import main.Main;
import model.ArcStation;
import model.CrossStation;
import model.Item;
import model.Line;
import model.LineHolder;
import model.Point;
import model.SquareStation;
import model.Station;
import model.StationHolder;
import model.ThreadHolder;
import model.TriangleStation;
import screen.GameScreen;
import utility.InputUtility;

public class GameLogic {
	private int creatingFailCount;
	private boolean isClickedStation;
	private Station prevStation;
	private Station clickStation;
	private static Item item;
	private int preindex=-1,index1=-1,status,prestatus;
	public static boolean isGameOver;
	private Thread controller;
	private AudioClip sound;
	
	public GameLogic(GameScreen gs){
		isGameOver = false;
		isClickedStation = false;
		addStation("Square");
		addStation("Arc");
		addStation("Cross");
		addStation("Triangle");
		setStatus(0);
		prestatus = 0;
		playSound(Scorebar.getInstance().getNumberOfCrowded());
		creatingFailCount = 0;
		item = new Item();
		
		controller = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int state=0;
				while(true){
					try {
					//	System.out.println("Yo");

						Thread.sleep(125);
						if(!isGameOver){
							Platform.runLater(()->{						
								gs.clearScreen();
								//gs.drawArea();
								gs.drawBar(gs.getGraphicsContext());
								gs.draw();
								Scorebar.getInstance().updateTime();
								StationHolder.getInstance().updateStation();
							});
	
								Control();
								//addTransportToLine();
						}
						else{
								gs.drawGameOver();
								if(Resources.isFoundGameOver()){
									getSound().stop();
									Resources.soundGameOver.play();
								}
								ThreadHolder.instance.stopAll();
								break;
						}


					} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Thread.interrupted();
							break;
							
					}
					
					//ThreadHolder.instance.update();
					InputUtility.postUpdate();
					
				}
			}
		});
	
		Thread creating = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				while(creatingFailCount <= 10){
					System.out.println("creating station");
					try {
						Thread.sleep(15000);
						Platform.runLater(() -> addStation() );
					} catch (InterruptedException e) {
					// 	TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
				}
			}
		});
		
	
		ThreadHolder.instance.addThread(creating);
		ThreadHolder.instance.addThread(controller);
		
		creating.start(); controller.start();  
	}


	public void addStation(){
		Random r = new Random();
		int x,y,c=0;
		do{
			if(c>=1000) {
				System.out.println("fail");
				creatingFailCount ++;
				return ;
			}
			x = r.nextInt(GameScreen.width-50);
			y = r.nextInt(GameScreen.heigth-30);
			c++;
		}while(!isFreeSpace(x,y));
		
		int s = r.nextInt(4);
		Station newStation;
		if(s == 0) newStation = new SquareStation(x-10, y-10);
		else if(s == 1) newStation = new ArcStation(x-10, y-10);
		else if(s == 2) newStation = new CrossStation(x, y);
		else newStation = new TriangleStation(x, y);
		
		StationHolder.getInstance().addStation(newStation);
	}
	
	public void addStation(String type){
		Random r = new Random();
		int x,y,c=0;
		do{
			if(c>=1000) {
				System.out.println("fail");
				creatingFailCount ++;
				return ;
			}
			x = r.nextInt(GameScreen.width-50);
			y = r.nextInt(GameScreen.heigth-30);
			c++;
		}while(!isFreeSpace(x,y));
		Station newStation;
		if(type.compareTo("Square") == 0) newStation = new SquareStation(x-10, y-10);
		else if(type.compareTo("Arc")== 0) newStation = new ArcStation(x-10, y-10);
		else if(type.compareTo("Cross") == 0) newStation = new CrossStation(x, y);
		else newStation = new TriangleStation(x, y);
		
		StationHolder.getInstance().addStation(newStation);
	}
	

	private boolean isFreeSpace(int x,int y){
		return !( isScorebar(x,y) || isControlbar(y) || isStationNear(x,y) || isOutOfScreen(x, y)) ;	
	}
	
	private boolean isScorebar(int x,int y){
		if( x>= 600 && x<=1024 && y>=0 && y<=72) return true;
		else return false;
	}
	
	private boolean isControlbar(int y){
		if(y>=700-50 && y <= 768) return true;
		else return false;
	}
	

	private boolean isStationNear(int x,int y){
		return StationHolder.getInstance().isStationNear(x,y);
	}
	
	private boolean isOutOfScreen(int x,int y){
		if(x<=30 || x>= GameScreen.width-30-30-50) return true;
		if(y<=30 || y>= GameScreen.width-30-30) return true;
		return false;
	}
	

	private void Control(){
			int count = 0;
			for(Station s: StationHolder.getInstance().getStations()){
				if(s.isClowded())count++;
			}
			
			if(count == 0)status = 0;
			else status = 1;
		    if(status != prestatus){
		    	//stopSound();
		    	if(sound!=null) sound.stop();
		    	playSound(getStatus());
		    }
		    else if(sound!=null){
		    	if(!sound.isPlaying()) sound.play();
		    }
		    prestatus = getStatus();
			
			int index = LineController.getInstance().IndexisLineControl(InputUtility.getMouseX(), InputUtility.getMouseY());
			if(index != -1){
				for(int i=0;i<7;i++)LineController.getInstance().getStatus()[i] = false;
				LineController.getInstance().getStatus()[index] = true;
				System.out.println(index);
			}
			else {
				for(int i=0;i<7;i++)LineController.getInstance().getStatus()[i] = false;
				/*for(int i=0;i<5;i++){
					LineController.getInstance().getSizes()[i]=20;
				}*/
			} 
			
			
			preindex = index;
			index = -1;
			if(InputUtility.isMouseLeftDown()){
				if(StationHolder.getInstance().isStation(InputUtility.getMouseX(), InputUtility.getMouseY())!=null ||
						index1 != -1){
					playSoundClick();
				}
				if(index1 == 5){ // delete line
					int index2 = LineController.getInstance().IndexisLineControl(InputUtility.getMouseX(), InputUtility.getMouseY());
					if(index2 >= 0 && index2 <= 4){ // find which line to delete
						for(Line l: LineHolder.getInstance().getLines()){
							if(l.getColor() == LineController.getInstance().getColors()[index2]){
								item.addItem(l.getNumberTransporter());
								LineHolder.getInstance().getLines().remove(l);
							 	LineController.getInstance().getIsUsed()[index2] = false;
							 	break;
							}
						}
					}
				}
				else if(index1 == 6){ // add transporter to line
					int index3 = LineController.getInstance().IndexisLineControl(InputUtility.getMouseX(), InputUtility.getMouseY());
					if(index3 >= 0 && index3 <= 4){ // find which line to add transporter
						for(Line l: LineHolder.getInstance().getLines()){
							if(l.getColor() == LineController.getInstance().getColors()[index3]){
							 	addTransportToLine(l);
							}
						}
					}
				}
			}
			
			
			
			if(InputUtility.isMouseLeftDown()){
			
				if((index1 <5 && index1>=0 )|| isClickedStation){ // check click line on line controller
						
						clickStation = StationHolder.getInstance().isStation(InputUtility.getMouseX(), InputUtility.getMouseY());
						
						if(clickStation != null){  // is click station
							if(!isClickedStation){ // is clicked station? 
								isClickedStation = true;
								System.out.println("ccl");
								prevStation = clickStation;
							}
							else{ // clicked station before then connect previous to current 
								int check = 0;
								for(Line l : LineHolder.getInstance().getLines()){  // select create new line or extend ?
								
										if(l.getColor() != LineController.getInstance().getColors()[index1])continue;
											if(((int)prevStation.getCenterX() == l.firstPoint().getX()) && ((int)prevStation.getCenterY() == l.firstPoint().getY())){
												//l.addPoint(x1, y1, x2, y2, append);
												check = 1;
												System.out.println("tor");
												l.addPoint(prevStation, clickStation, false);
											}
									
										if(((int)prevStation.getCenterX() == l.lastPoint().getX()) && ((int)prevStation.getCenterY() == l.lastPoint().getY())){
											//l.addPoint(x1, y1, x2, y2, append);
											check = 1;
											System.out.println("tor");
											l.addPoint(prevStation, clickStation, true);
										}
								}
								Line L;
								if(check == 0 && LineController.getInstance().getIsUsed()[index1] == false && prevStation != clickStation){
									// TODO add transporter to new line
									System.out.println("cre");
									L = new Line(LineController.getInstance().getColors()[index1]);
									L.addPoint(prevStation, clickStation, true);
									LineHolder.getInstance().addLine(L);
									System.out.println("this condition");
								}
								
								
								clickStation = null;
								isClickedStation = false;
							}
						}
						else {
							isClickedStation = false;
							//System.out.println();
							prevStation = null;
							
						
						}
				}	
	
				clickStation = StationHolder.getInstance().isStation(InputUtility.getMouseX(), InputUtility.getMouseY());
				if(clickStation == null){
					
					index1 = LineController.getInstance().IndexisLineControl(InputUtility.getMouseX(), InputUtility.getMouseY());
					
				}
			}
	
		}


	public static void addTransportToLine(Line l){
		if(item.canUse()){
			if(l.addTransporter()) 
				item.useItem();
		}
	}
	private void playSound(int status){
		
		if(status == 0)setSound(Resources.soundNormal);
		else setSound(Resources.soundExcited);
		if(getSound()!=null)
			getSound().play();
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public AudioClip getSound() {
		return sound;
	}


	public void setSound(AudioClip sound) {
		this.sound = sound;
	}
	
	public Thread getController(){
		return controller;
	}
	
	public void stopSound(){
		sound.stop();
	}
	
	public void playSoundClick(){
		if(Resources.isFoundClick()) 
			Resources.soundClick.play();
	}
}
