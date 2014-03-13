package RocketUML.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class Toolbar extends JFrame
{
	ImageIcon image1 = new ImageIcon("add.png");
	ImageIcon image2 = new ImageIcon("delete.png");
	JPanel panel = new JPanel();
	JToolBar toolbar = new JToolBar();
	
	public Toolbar()
	{
		toolbar.setFloatable(true);
		JButton add = new JButton(image1);
		JButton delete = new JButton(image2);
		toolbar.add(add);
		toolbar.add(delete);
		toolbar.setAlignmentX(0);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(toolbar);

	}
}
