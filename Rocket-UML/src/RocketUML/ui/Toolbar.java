package RocketUML.ui;

import RocketUML.model.ProjectElement;
import RocketUML.visitor.CodeGenerationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener
{
    ImageIcon undoImage = new ImageIcon(getClass().getResource("/undo.png"));
    ImageIcon redoImage = new ImageIcon(getClass().getResource("/redo.png"));
    ImageIcon exportCppImage = new ImageIcon(getClass().getResource("/export_cplusplus.png"));
    ImageIcon exportJavaImage = new ImageIcon(getClass().getResource("/export_java.png"));
    ImageIcon saveImage = new ImageIcon(getClass().getResource("/save.png"));
    ImageIcon openImage = new ImageIcon(getClass().getResource("/open.png"));
	JPanel panel = new JPanel();
	JToolBar toolbar = new JToolBar();

    private Main gui;
    private String pathName;

    JButton  save, open, undo, redo, exportCpp, exportJava;

    ModelViewController controller = ModelViewController.getInstance();

	public Toolbar(Main in)
	{
        gui = in;
		toolbar.setFloatable(false);

        save = new JButton(saveImage);
        save.setToolTipText("Save Project");
        save.addActionListener(this);
        toolbar.add(save);

        open = new JButton(openImage);
        open.setToolTipText("Open Project");
        open.addActionListener(this);
        toolbar.add(open);

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
        if(e.getSource() == save)
        {
            JFileChooser openChooser = new JFileChooser();
            int val = openChooser.showSaveDialog(Toolbar.this);
            if(val == JFileChooser.APPROVE_OPTION)
            {
                pathName = openChooser.getSelectedFile().getPath();
                Serialization.getInstance().serialize(pathName);
            }
        }
        else if(e.getSource() == open)
        {
            JFileChooser openChooser = new JFileChooser();
            int val = openChooser.showOpenDialog(Toolbar.this);
            if(val == JFileChooser.APPROVE_OPTION)
            {
                pathName = openChooser.getSelectedFile().getPath();
                Serialization.getInstance().deserialize(pathName);
                gui.loadDiagrams();
                gui.repaint();
            }
        }
        else if(e.getSource() == undo)
        {
            controller.UndoState();
            gui.loadDiagrams();
            gui.repaint();
        }
        else if(e.getSource() == redo)
        {

        }
        else if(e.getSource() == exportCpp)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int val = chooser.showSaveDialog(Toolbar.this);
            if(val == JFileChooser.APPROVE_OPTION)
            {
                pathName = chooser.getSelectedFile().getPath();
                CodeGenerationController cgc = CodeGenerationController.create();
                cgc.generateCppCode(ProjectElement.getInstance(), pathName);
            }

        }
        else if(e.getSource() == exportJava)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int val = chooser.showSaveDialog(Toolbar.this);
            if(val == JFileChooser.APPROVE_OPTION)
            {
                pathName = chooser.getSelectedFile().getPath();
                CodeGenerationController cgc = CodeGenerationController.create();
                cgc.generateJavaCode(ProjectElement.getInstance(), pathName);
            }

        }
    }
}
