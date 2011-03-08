import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**This class 
 * 
 * @author Matthew Polk
 *
 */
public class RobotSimulator2DPanel implements ActionListener{
	JFrame frame;
	JPanel panel;
	DrawPanel dp;
	Robot bot;
	private final int delay = 50;
	Timer clock;
	
	public RobotSimulator2DPanel(String title){
		frame = new JFrame(title);
		panel = new JPanel();
		Room room = new Room(500,500);
		room.addObstacle(200,200,75,75 );
		bot = new Robot(60, 40, 100, 100, Math.PI/4, room);
		bot.addSensor(0, 0, 40, 10);
		dp = new DrawPanel(500,500, room, bot);
		TextPanel text = new TextPanel(this);
		Container cp = frame.getContentPane();//create a container
		cp.setLayout(new BorderLayout());//set default border layout
	    cp.add(dp, BorderLayout.CENTER);
	    cp.add(text,BorderLayout.EAST);
	    frame.pack();
		frame.setSize(750,535);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clock = new Timer(delay,this);
		clock.start();
	}
	public void drawRobot(){
		bot.foreward(3);
		if(bot.sensors.get(0).getStatus()){
			bot.foreward(-12);
			bot.rotate(-.1);
		}
		dp.paintRobot(bot);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		drawRobot();
	}
}
