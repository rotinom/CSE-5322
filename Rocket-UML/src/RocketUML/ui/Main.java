package RocketUML.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame {
    private JTabbedPane tabs;
    private int diagramNum = 1;
    private ArrayList<JLabel> tabLabels = new ArrayList<JLabel>();


    public Main() {
   		Menu gMenu = new Menu(this);
        //hide toolbar for now
		Toolbar Tool = new Toolbar();
        getContentPane().add(Tool.panel, BorderLayout.WEST);

        //Create and set up the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth()/2);
        int height = (int) (screenSize.getHeight()/2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(gMenu.menuBar);
        setLocation(width/2, height/2);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);

        tabs=new JTabbedPane();
        getContentPane().add(tabs);

        tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(tabs.getSelectedIndex() >= 0 && tabs.getSelectedIndex() != tabs.getTabCount()-1) {
                    try {
                        ModelViewController.getInstance().setCurrentDiagram(((ModelView)tabs.getSelectedComponent()).getName());
                    }
                    catch (Exception ex) {
                        System.out.println("Exception on tab index " + tabs.getSelectedIndex());
                    }
                }
            }
        });

        JPopupMenu tabPopup = new JPopupMenu();
        JMenuItem item = new JMenuItem("Rename");
        tabPopup.add(item);
        tabs.setComponentPopupMenu(tabPopup);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tabs.getSelectedIndex() >= 0 && tabs.getSelectedIndex() != tabs.getTabCount() - 1) {
                    try {
                        String oldName = ((ModelView) tabs.getSelectedComponent()).getName();
                        String newName = JOptionPane.showInputDialog("Please enter new text for: " + oldName);
                        //update the tabs model view, the label of the tab, and the controller
                        ((ModelView) tabs.getSelectedComponent()).setName(newName);
                        for(JLabel title : tabLabels) {
                            if(title.getText().equals(oldName)) {
                                title.setText(newName);
                            }
                        }
                        ModelViewController.getInstance().changeDiagramName(oldName, newName);
                        ModelViewController.getInstance().setCurrentDiagram(newName);
                    } catch (Exception ex) {
                        System.out.println("Exception on tab index " + tabs.getSelectedIndex());
                    }
                }
            }
        });

        addPlusTab(); //must do this first
        addDiagramTab("Diagram 1");
        pack();
    }

    private void addDiagramTab(String name) {

        final ModelView workSpace = new ModelView();
        workSpace.setName(name);

        tabs.insertTab(null, null, workSpace, "", 0);
        int pos = tabs.indexOfComponent(workSpace);
        FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
        JPanel tabPanel = new JPanel(f);
        tabPanel.setOpaque(false);

        JLabel title = new JLabel(name);
        ImageIcon icon = new ImageIcon("resources/closeTabDisabled.png");
        ImageIcon activeIcon = new ImageIcon("resources/closeTabEnabled.png");
        JButton closeButton = new JButton();
        closeButton.setIcon(icon);
        closeButton.setRolloverIcon(activeIcon);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setBorder(null);
        closeButton.setFocusable(false);

        tabLabels.add(title);
        tabPanel.add(title);
        tabPanel.add(closeButton);
        tabPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        tabs.setTabComponentAt(pos, tabPanel);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelViewController.getInstance().removeDiagram(workSpace.getName());
                tabs.remove(workSpace);
            }
        };
        closeButton.addActionListener(listener);
        ModelViewController.getInstance().setCurrentDiagram(name);
        tabs.setSelectedIndex(0);
    }

    private void addPlusTab() {
        JPanel blankPanel = new JPanel();
        tabs.insertTab(null, null, blankPanel, "", 0);
        int pos = tabs.indexOfComponent(blankPanel);
        tabs.setEnabledAt(pos,false);
        FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
        JPanel tabPanel = new JPanel(f);
        tabPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon("resources/newTabDisabled.png");
        ImageIcon activeIcon = new ImageIcon("resources/newTabEnabled.png");
        JButton closeButton = new JButton();
        closeButton.setIcon(icon);
        closeButton.setRolloverIcon(activeIcon);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setBorder(null);
        closeButton.setFocusable(false);
        tabPanel.add(closeButton);

        tabs.setTabComponentAt(pos, tabPanel);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDiagramTab("Diagram" + ++diagramNum);
            }
        };
        closeButton.addActionListener(listener);
    }

    public void loadDiagrams() {
        tabs.removeAll();
        diagramNum = 0;
        addPlusTab();
        ArrayList<String> names = ModelViewController.getInstance().getDiagramNames();
        for (String name : names) {
            addDiagramTab(name);
            diagramNum++;
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}