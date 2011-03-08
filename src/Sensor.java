import java.awt.geom.GeneralPath;



public class Sensor {
	GeneralPath body;
	double x,y,width,height;
	double[][] points;
	private boolean pressed;
	
	public Sensor(double _x, double _y, double _width, double _height, double ang){
		x = _x;
		y = _y;
		width = _width;
		height = _height;
		points = new double[4][2];
		points[0][0] = x;
		points[0][1] = y;
		points[1][0] = -1*Math.sin(Math.PI+ang)*height+x;
		points[1][1] = -1*Math.cos(Math.PI+ang)*height+y;
		points[2][0] = Math.sin((Math.PI/2)+ang)*width+points[1][0];
		points[2][1] = Math.cos((Math.PI/2)+ang)*width+points[1][1];
		points[3][0] = Math.sin((Math.PI/2)+ang)*width+x;
		points[3][1] = Math.cos((Math.PI/2)+ang)*width+y;
		
		pressed = false;
		body = new GeneralPath();
		drawPath();
	}
	
	public void drawPath(){
		body.reset();
		body.moveTo(points[0][0], points[0][1]);
		for(int i=1;i<points.length;i++){
			body.lineTo(points[i][0],points[i][1]);
		}
		body.closePath();
	}
	
	public void press(){
		pressed = true;
	}
	public void release(){
		pressed = false;
	}
	
	public boolean getStatus(){
		return pressed;
	}
}
