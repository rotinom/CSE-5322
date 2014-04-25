package RocketUML.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener
{
    ImageIcon undoImage = new ImageIcon(getClass().getResource("/undo.png"));
    ImageIcon redoImage = new ImageIcon(getClass().getResource("/redo.png"));
    ImageIcon addClassImage = new ImageIcon(getClass().getResource("/add_class.png"));
    ImageIcon removeClassImage = new ImageIcon(getClass().getResource("/remove_class.png"));
    ImageIcon exportCppImage = new ImageIcon(getClass().getResource("/export_cplusplus.png"));
    ImageIcon exportJavaImage = new ImageIcon(getClass().getResource("/export_java.png"));
	JPanel panel = new JPanel();
	JToolBar toolbar = new JToolBar();

    private Main gui;

    JButton  addClass, removeClass, undo, redo, exportCpp, exportJava;

    ModelViewController controller = ModelViewController.getInstance();

	public Toolbar(Main in)
	{

        gui = in;
		toolbar.setFloatable(false);

        addClass = new JButton(addClassImage);
        addClass.setToolTipText("Add Class");
        addClass.addActionListener(this);
        toolbar.add(addClass);

        removeClass = new JButton(removeClassImage);
        removeClass.setToolTipText("Remove Class");
        removeClass.addActionListener(this);
        toolbar.add(removeClass);

		undo = new JButton(undoImage);
        undo.setToolTipText("Undo");
        undo.addActionListener(this);
        toolbar.add(undo);

		redo = new JButton(redoImage);
        redo.setToolTipText("Redo");
        redo.addActionListener(this);
		toolbar.add(redo);

        exportCpp = new JButton(exportCppImage);
        exportCpp.setToolTipText("Export C++ Code");
        exportCpp.addActionListener(this);
        toolbar.add(exportCpp);

        exportJava = new JButton(exportJavaImage);
        exportJava.setToolTipText("Export Java Code");
        exportJava.addActionListener(this);
        toolbar.add(exportJava);

		toolbar.setAlignmentX(0);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(toolbar);

        controller = ModelViewController.getInstance();

    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == addClass)
        {
            controller.createElement("","Class",gui.getWidth()/3, gui.getHeight()/3);
            gui.repaint();
        }
        else if(e.getSource() == removeClass)
        {

        }
        else if(e.getSource() == undo)
        {
           controller.undoMemento();
           gui.repaint();
        }
        else if(e.getSource() == redo)
        {

        }
        else if(e.getSource() == exportCpp)
        {

        }
        else if(e.getSource() == exportJava)
        {

        }
    }
}
