package view;

import controller.InductionSWController;
import controller.interfaces.IGui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jamie on 07/04/16.
 */
public class MainDashBoardFrame extends JFrame implements IGui{

    public MainDashBoardFrame() throws HeadlessException {
        /* DEFAULT CONSTRUCTOR */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);

        // add to the layout
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        this.setUndecorated(true);
        this.add(mainPanel);
//        this.setSize(300,300);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,1));
        JButton beginInductionBtn = new JButton("Click here to begin induction");
//        beginInductionBtn.setBorder(new EmptyBorder(100,100,100,100));
        beginInductionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // reset the current inductee
                InductionSWController.getInstance().setCurrentInductee(null);
                UserInputFrame uif = new UserInputFrame("Induction Application");
                uif.setVisible(true);
                uif.setSize(640,480);
                uif.setLocationRelativeTo(null);
                dispose(); // Hide the Dashboard
//                setVisible(false); // hide the dashboard
            }
        });


        centerPanel.add(beginInductionBtn);


        return centerPanel;
    }

    @Override
    public void refreshGUI() {

    }
}
