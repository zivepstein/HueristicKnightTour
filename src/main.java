import objectdraw.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class main extends WindowController {
	private int sizeX=8;
	private int sizeY=sizeX;
	private double width = 700/sizeY;
	private FramedRect square;
	public boolean ColorMe = false;
	public boolean hideBoard = false;
	private int[][] chessboard = new int[sizeX][sizeY];
	public  ArrayList<Tile> covered = new ArrayList<Tile>();
	public int sum = 0;
	public Tile startingTile = new Tile(0,3);
	public Tile end = new Tile(0,3);
	

		
	public void begin() {
		for(int i =0; i<sizeX; i++){
			for(int j =0; j<sizeY; j++){
				if (!hideBoard){
					new FramedRect(width*i, width*j, width, width, canvas);
				}
				//new Text(edgeNum(new Tile(i,j)), width*i, width*j, canvas);
				chessboard[i][j] = 0;
			}
		}	
	HeuristicWalk(startingTile, ColorMe);
	sum = 0;
	}


public int edgeNum(Tile t){
	int count = 0;
	Tile[] moves = {new Tile(-1,-2),new Tile(1,-2),new Tile(-1,2),new Tile(1,2),new Tile(2,1),new Tile(-2,1),new Tile(2,-1),new Tile(-2,-1)};
	for (Tile move : moves){
		Tile nextPosition = t.TileAdd (move);
		if (!(nextPosition.x < 0 || nextPosition.x > sizeX-1 ||nextPosition.y < 0 ||nextPosition.y > sizeY-1 )){
			if (chessboard[nextPosition.x][nextPosition.y] ==0){
				count++;
			}
		}
	
	}
	return count;
	}
	

public ArrayList<Tile> findEdges(Tile t){
	ArrayList<Tile> edges = new ArrayList<Tile>();
	Tile[] moves = {new Tile(-1,-2),new Tile(1,-2),new Tile(-1,2),new Tile(1,2),new Tile(2,1),new Tile(-2,1),new Tile(2,-1),new Tile(-2,-1)};
	for (Tile move : moves){
		Tile nextPosition = t.TileAdd (move);
		if (!(nextPosition.x < 0 || nextPosition.x > sizeX-1 ||nextPosition.y < 0 ||nextPosition.y > sizeY-1 )){
			if (chessboard[nextPosition.x][nextPosition.y] ==0){
				edges.add(nextPosition);
			}
		}
	}
	return edges;
}


public void HeuristicWalk(Tile t, boolean colorMe){
	ArrayList<Tile> moves = findEdges(t);
	if (moves.size() >= 1){
		Collections.sort(moves);
		Tile min =moves.get(0);	
		drawLine(t, min, colorMe);
		sum++;
		chessboard[t.x][t.y] =1;
		HeuristicWalk(min, colorMe);
		} else{
			end = t;
		}
	}


public void drawLine(Tile t, Tile d, boolean colorMe){
	Line line = new Line ((width/2)+(width*t.x),(width/2)+(width*t.y), (width/2)+(width*d.x), (width/2)+(width*d.y), canvas);
	 if (colorMe){
		 int roo;
		 int xval = t.x-d.x;
		 int yval = t.y-d.y;
		 roo = xval + yval*47;
		switch (roo){	
		   case 49 :   line.setColor(Color.BLUE);
		   				break;
		   case -49 :   line.setColor(Color.ORANGE);
			break;	
		   case 45 :   line.setColor(Color.GREEN);
			break;
		   case -45 :   line.setColor(Color.RED);
			break;
		   case 95 :   line.setColor(Color.CYAN);
			break;
		   case -95 :   line.setColor(Color.MAGENTA);
			break;
		   case 93 :   line.setColor(Color.BLACK);
			break;
		   case -93 :   line.setColor(Color.PINK);
			break;
			
		   }
	 }
	 
}

public class Tile implements Comparable<Tile>{ 
	  public final int x; 
	  public final int y; 
	  public Tile(int x, int y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	 public Tile TileAdd(Tile t){
		 Tile sum = new Tile(x+t.x,y+t.y);
		 return(sum);
	 }
	 public int compareTo(Tile t){
		 if (edgeNum(this) > edgeNum(t)){
			 return 1;
		 } else if (edgeNum(this) > edgeNum(t)){
			 return 0;
	 
		 } else{
			 return -1;
		 }
	 }
	} 
}
			