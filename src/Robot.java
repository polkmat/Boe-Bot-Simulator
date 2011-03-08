import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**The Robot class defines a Robot to be simulated.  It creates the graphics for
 * the robot's body, the sensors on the robot, and an arrow pointing in the direction
 * of the robot.  It also stores the location of the robot and the direction in 
 * which it is pointing.
 * 
 * When asked to make a move, Robot will check the move before making it.  If it is 
 * an illegal move, Robot will attempt to make a smaller move in the same direction,
 * moving until the Robot object is touching the obstacle that is blocking it.
 * 
 * @author Matthew Polk
 *
 */
public class Robot {
	GeneralPath body, arrow;
	int height, width;
	double[][] points;
	double xloc,yloc;
	ArrayList<Sensor> sensors;
	double angle;
	Room room;
	
	/**This is the constructor for the robot.  It sets or instantiates all variables
	 * and calls the drawPath method.
	 * 
	 * @param _height The height of the robot.
	 * @param _width The width of the robot.
	 * @param _xloc The starting x location of the first vertex of the robot's body.
	 * @param _yloc The starting y location of the first vertex of the robot's body.
	 * @param _angle The starting angle of the robot.
	 */
	public Robot(int _height, int _width, int _xloc, int _yloc, double _angle, Room _room){
		sensors = new ArrayList<Sensor>();
		points = new double[7][2];
		width = _width;
		height = _height;
		xloc = _xloc;
		yloc = _yloc;
		angle = _angle;
		room = _room;
		
		points[0][0] = xloc;
		points[0][1] = yloc;
		points[1][0] = Math.sin(Math.PI+angle)*height+xloc;
		points[1][1] = Math.cos(Math.PI+angle)*height+yloc;
		points[2][0] = Math.sin((Math.PI/2)+angle)*width+points[1][0];
		points[2][1] = Math.cos((Math.PI/2)+angle)*width+points[1][1];
		points[3][0] = Math.sin((Math.PI/2)+angle)*width+xloc;
		points[3][1] = Math.cos((Math.PI/2)+angle)*width+yloc;
		points[4][0] = (points[0][0]+points[3][0])/2;
		points[4][1] = (points[0][1]+points[3][1])/2;
		points[5][0] = (3*points[0][0]+points[1][0])/4;
		points[5][1] = (3*points[0][1]+points[1][1])/4;
		points[6][0] = (3*points[3][0]+points[2][0])/4;
		points[6][1] = (3*points[3][1]+points[2][1])/4;
		
		body = new GeneralPath();
		arrow = new GeneralPath();
		drawPath();
	}
	/**The drawPath method draws the shapes to be displayed on the screen.  It uses
	 * a GeneralPath to move through the vertices of the robot's body, and closes the
	 * path.  It also draws the arrow.
	 * 
	 */
	public void drawPath(){
		body.reset();
		body.moveTo(points[0][0], points[0][1]);
		
		for(int i=1;i<4;i++){
			body.lineTo(points[i][0], points[i][1]);
		}
		
		body.closePath();
		
		arrow.reset();
		arrow.moveTo(points[4][0], points[4][1]);
		for(int i=5;i<7;i++){
			arrow.lineTo(points[i][0], points[i][1]);
		}
		arrow.closePath();
	}
	
	/**The forward method is used in order to move the robot in a straight line.
	 * When given a positive distance, the robot object will move dist pixels in
	 * the direction of angle.  A negative dist will move the robot backwards by
	 * dist pixels.
	 * 
	 * @param dist The number of pixels to move forwards or backwards, in the
	 * 				direction of angle.
 	 */
	public void foreward(int _dist){
		int dist = _dist;
		while(dist != 0){
			xloc += (dist * Math.sin(angle)); 
			yloc += (dist * Math.cos(angle));
			
			points[0][0] = xloc;
			points[0][1] = yloc;
			points[1][0] = Math.sin(Math.PI+angle)*height+xloc;
			points[1][1] = Math.cos(Math.PI+angle)*height+yloc;
			points[2][0] = Math.sin((Math.PI/2)+angle)*width+points[1][0];
			points[2][1] = Math.cos((Math.PI/2)+angle)*width+points[1][1];
			points[3][0] = Math.sin((Math.PI/2)+angle)*width+xloc;
			points[3][1] = Math.cos((Math.PI/2)+angle)*width+yloc;
			points[4][0] = (points[0][0]+points[3][0])/2;
			points[4][1] = (points[0][1]+points[3][1])/2;
			points[5][0] = (3*points[0][0]+points[1][0])/4;
			points[5][1] = (3*points[0][1]+points[1][1])/4;
			points[6][0] = (3*points[3][0]+points[2][0])/4;
			points[6][1] = (3*points[3][1]+points[2][1])/4;
			
			for(Sensor s : sensors){
				s.x += (dist * Math.sin(angle)); 
				s.y += (dist * Math.cos(angle));
				s.points[0][0] = s.x;
				s.points[0][1] = s.y;
				s.points[1][0] = -1*Math.sin(Math.PI+angle)*s.height+s.x;
				s.points[1][1] = -1*Math.cos(Math.PI+angle)*s.height+s.y;
				s.points[2][0] = Math.sin((Math.PI/2)+angle)*s.width+s.points[1][0];
				s.points[2][1] = Math.cos((Math.PI/2)+angle)*s.width+s.points[1][1];
				s.points[3][0] = Math.sin((Math.PI/2)+angle)*s.width+s.x;
				s.points[3][1] = Math.cos((Math.PI/2)+angle)*s.width+s.y;
				s.drawPath();
			}
			
			drawPath();
			if(checkMove())
				dist = 0;
			else if(_dist > 0)
				dist = -1;
			else
				dist = 1;
		}
	}
	
	private boolean checkMove() {
		for(Sensor s:sensors){
			s.release();
			for(int i=0;i<room.obstacles.size();i++){
				if(s.body.intersects(room.obstacles.get(i).getBounds2D())){
					s.press();
				}
			}
		}
		for(int i=0;i<room.obstacles.size();i++){
			if(body.intersects(room.obstacles.get(i).getBounds2D())){
				return false;
			}
		}
		
		return true;
	}
	/**The rotate method will rotate the robot by ang radians counterclockwise.
	 * 
	 * @param ang The number of radians to rotate counterclockwise.  A negative
	 * 				ang value will rotate the robot clockwise.
	 */
	public void rotate(double _ang){
		double ang = _ang;
		while(true){
			angle += ang;
			angle = angle%(2*Math.PI);
			double x = (points[0][0]+points[2][0])/2;
			double y = (points[0][1]+points[2][1])/2;
			for(int i=0;i<points.length;i++){//Shift the robot so it is centered at the origin.
				points[i][0] -= x;
				points[i][1] -= y;
			}
			
			for(Sensor s: sensors){
				for(int i=0;i<s.points.length;i++){//Shift the robot so it is centered at the origin.
					s.points[i][0] -= x;
					s.points[i][1] -= y;
				}
			}
			
			//Rotate the robot about the origin.
			double[][] rot = new double[2][2];
			rot[0][0] = Math.cos(ang);
			rot[1][0] = Math.sin(ang);
			rot[0][1] = -1*rot[1][0];
			rot[1][1] = rot[0][0];
			points = Matrix.multiply(points, rot);
			
			for(Sensor s: sensors){
				s.points = Matrix.multiply(s.points, rot);
			}
			
			//Shift the robot back to its original location.
			for(int i=0;i<points.length;i++){
				points[i][0] += x;
				points[i][1] += y;
			}
			
			for(Sensor s: sensors){
				for(int i=0;i<s.points.length;i++){//Shift the robot so it is centered at the origin.
					s.points[i][0] += x;
					s.points[i][1] += y;
				}
			}
			
			//Shift the current xLoc and yLoc by the same shift used above.
			double[][] init = new double[1][2];
			init[0][0] = xloc-x;
			init[0][1] = yloc-y;
			
			//Rotate the xLoc and yLoc about the origin.
			init = Matrix.multiply(init, rot);
			//Save the new xLoc and yLoc.
			xloc = init[0][0]+x;
			yloc = init[0][1]+y;
			
			for(Sensor s: sensors){
				//Shift the current xLoc and yLoc by the same shift used above.
				init[0][0] = s.x-x;
				init[0][1] = s.y-y;
				
				//Rotate the xLoc and yLoc about the origin.
				init = Matrix.multiply(init, rot);
				//Save the new xLoc and yLoc.
				s.x = init[0][0]+x;
				s.y = init[0][1]+y;
			}
			
			drawPath();
			for(Sensor s: sensors){
				s.drawPath();
			}
			if(!checkMove()){
				ang = -1*_ang/10;
				//undo the rotation by 1/10 of the original rotation and try again.
			}
			else break;
		}
	}
	
	public void addSensor(int x, int y, int width, int height){
		sensors.add(new Sensor(x+xloc,y+yloc,width,height, angle));
	}
}
