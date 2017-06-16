import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Simulate {
	
	int[][] M;
	int size,x,y;
	double stickiness;
	
	public Simulate(int size,double stickiness){
		this.size=size;
		this.stickiness=stickiness;
		M=new int[size][size];
		M[(size/2)+1][(size/2)]=1; //Initialize center element
	}
	
	public static void main(String[] args)throws IOException{
		double[] stickValues={0.04,0.7,0.3,0.001};
		for(int iteration=0;iteration<stickValues.length;iteration++)
			new Simulate(501,stickValues[iteration]).simulateDla(20000);
	}
	
	public void simulateDla(int numberOfParticles){
		
		for(int count=0;count<numberOfParticles;count++){
			System.out.println("Particle number: "+count);
			particle();
		}
			
		//printMatrix();
		writeImage();
	}
	
	public void particle(){
		
		Random rand = new Random();
		int edge;
		do{
			edge=rand.nextInt(4);
			if(edge==0){
				x=0; y= rand.nextInt(size);
			}
			else if(edge==1){
				y=0; x=rand.nextInt(size);
			}
			else if(edge==2){
				x=size-1; y=rand.nextInt(size);
			}
			else{
				y=size-1; x=rand.nextInt(size);
			}
			
		}while(M[x][y]==1);
			
		M[x][y]=1;
		while(!checkNeighbour()){
			gotoRandomEmptyNeighbour();
			
		}
	}
	
	public boolean checkNeighbour(){
		
		if(Math.random() <= stickiness){
			if((x-1)>=0 && (y-1)>=0 && M[x-1][y-1]==1){
				return true;
			}
			else if((x-1)>=0 && M[x-1][y]==1){
				return true;
			}
			else if((x-1)>=0 && (y+1)<size && M[x-1][y+1]==1){
				return true;
			}
			else if((x+1)<size && (y-1)>=0 && M[x+1][y-1]==1){
				return true;
			}
			else if((x+1)<size && M[x+1][y]==1){
				return true;
			}
			else if((x+1)<size && (y+1)<size && M[x+1][y+1]==1){
				return true;
			}
			else if((y-1)>=0 && M[x][y-1]==1){
				return true;
			}
			else if((y+1)<size && M[x][y+1]==1){
				return true;
			}
		}
		return false;
	}

	public void gotoRandomEmptyNeighbour(){
		
		ArrayList<Integer> possibleX=new ArrayList<Integer>();
		ArrayList<Integer> possibleY=new ArrayList<Integer>();
		
		if((x-1)>=0 && (y-1)>=0 && M[x-1][y-1]==0){
			possibleX.add(x-1);
			possibleY.add(y-1);
		}
		if((x-1)>=0 && M[x-1][y]==0){
			possibleX.add(x-1);
			possibleY.add(y);
		}
		if((x-1)>=0 && (y+1)<size && M[x-1][y+1]==0){
			possibleX.add(x-1);
			possibleY.add(y+1);
		}
		if((x+1)<size && (y-1)>=0 && M[x+1][y-1]==0){
			possibleX.add(x+1);
			possibleY.add(y-1);
		}
		if((x+1)<size && M[x+1][y]==0){
			possibleX.add(x+1);
			possibleY.add(y);
		}
		if((x+1)<size && (y+1)<size && M[x+1][y+1]==0){
			possibleX.add(x+1);
			possibleY.add(y+1);
		}
		if((y-1)>=0 && M[x][y-1]==0){
			possibleX.add(x);
			possibleY.add(y-1);
		}
		if((y+1)<size && M[x][y+1]==0){
			possibleX.add(x);
			possibleY.add(y+1);
		}
		
		int newX,newY;
		
		Random rand = new Random();
		
		if(possibleX.size()!=0){
			int index = rand.nextInt(possibleX.size());
			newX = possibleX.get(index);
			newY = possibleY.get(index);
			M[x][y]=0;
			M[newX][newY]=1;
			x=newX;
			y=newY;
		}
		
		//System.out.println(x+","+y+" : "+index+"/"+possibleX.size());
		
	}
	
	public void printMatrix(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++)
				System.out.print(M[i][j]);
			System.out.println();
		}
	}

	public void writeImage() {
	    String path = "images/Stickiness_"+(""+stickiness).replace(".", "_")+".png";
	   
	    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
	    for (int x = 0; x < size; x++) {
	        for (int y = 0; y < size; y++) {
	        	if(M[x][y]==0)
	        		image.setRGB(x, y,new Color(255,255,255).getRGB());
	        	else
	        		image.setRGB(x, y,new Color(0,0,0).getRGB());
	        }
	    }

	    File ImageFile = new File(path);
	    try {
	        ImageIO.write(image, "png", ImageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
