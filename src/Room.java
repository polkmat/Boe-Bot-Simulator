import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Room {
	public final int initX = 0;
	public final int initY = 0;
	public ArrayList<Rectangle2D.Double> obstacles;
	public GeneralPath border;
	int width,height;
	
	public Room(int _width, int _height){
		width = _width;
		height = _height;
		border = new GeneralPath();
		border.moveTo(initX, initY);
		border.lineTo(initX, initY+height);
		border.lineTo(initX+width, initY+height);
		border.lineTo(initX+width, initY);
		border.closePath();
		//border = new Rectangle2D(initX, initY,width,height);
		
		obstacles = new ArrayList<Rectangle2D.Double>();
		obstacles.add(new Rectangle2D.Double(0,0,10,height));
		obstacles.add(new Rectangle2D.Double(0,0,width,10));
		obstacles.add(new Rectangle2D.Double(0,height-10,width,10));
		obstacles.add(new Rectangle2D.Double(width-10,0,10,height));
	}
	
	public void addObstacle(int x, int y, int h, int w){
		obstacles.add(new Rectangle2D.Double(x,y,h,w));
	}
	
}
