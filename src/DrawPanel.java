import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DrawPanel extends JPanel{
	Room room;
	Robot bot;
	
	public DrawPanel(int x, int y, Room _room, Robot _bot){
		room = _room;
		bot = _bot;
		
		setSize(510,510);
		setBackground(Color.black);
	}
	
	public void paintRobot(Robot _bot){
		bot = _bot;
		repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		for(int i=0;i<room.obstacles.size();i++){
			g2.fill(room.obstacles.get(i));
		}

		g2.setColor(Color.red);
		g2.fill(bot.body);
		g2.setColor(Color.green);
		g2.fill(bot.arrow);
		g2.setColor(Color.blue);
		for(Sensor s:bot.sensors){
			g2.fill(s.body);
		}
	}
}
