import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TextPanel extends JPanel{
	RobotSimulator2DPanel master;
	JScrollPane paneI, paneO;
	JTextArea input;
	JTextArea output;
	JLabel labelI, labelO;
	public TextPanel(RobotSimulator2DPanel top){
		master = top;
		Container cp = new Container();
		cp.setLayout(new BorderLayout());
		input = new JTextArea(12,20);
		output = new JTextArea(12,20);
		paneI = new JScrollPane(input);
		paneO = new JScrollPane(output);
		labelI = new JLabel("Type your code here:");
		labelO = new JLabel("Messages from me:");
		cp.add(labelI,BorderLayout.NORTH);
		cp.add(paneI,BorderLayout.CENTER);
		//cp.add(labelO,BorderLayout.WEST);
		cp.add(paneO,BorderLayout.SOUTH);
		add(cp);
	}
}
