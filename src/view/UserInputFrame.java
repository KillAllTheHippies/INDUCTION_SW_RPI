package view;

import controller.InductionSWController;
import model.Inductee;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Jamie on 29/02/16.
 * <p>
 * Initial frame to be created when application is first started
 */
public class UserInputFrame extends JFrame {

    private JButton cancelButton;
    private JButton beginInductionButton;
//    private JButton addUserButton;
//    private JButton takeQuizButton;
    private JButton displayInducteesButton;
//    private JButton BackEndButton;
//    private JButton displayVideoButton;

    private JTextField tfName;
    private JTextField tfCompany;
    private JTextField tfJobTitle;
    private JTextField tfCarReg;
    private JTextField tfSupervisor;
    private JTextField tfCompetencies;


    public UserInputFrame(String title) throws HeadlessException {

        super(title);

        // Content of our JFrame
        JPanel mainPanel = new JPanel();

        // set border layout
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create the side and bottom panels and add them to the layout
        JPanel sidePanel = createSideButtonPanel();
        mainPanel.add(sidePanel, BorderLayout.EAST);
        JPanel bottomPanel = createBottomButtonPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // create the Inductee info input panel

        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // add to the layout
        this.getContentPane().add(mainPanel);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.setUndecorated(true);

    }

    //Show image on frame
    public void showImage(BufferedImage img, String text) {
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame(text);
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(), img.getHeight() + 30);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createCenterPanel() {

        JLabel lblName = new JLabel("Name:");
        JLabel lblSupervisor = new JLabel("Supervisor:");
        JLabel lblCompany = new JLabel("Company:");
        JLabel lblJobTitle = new JLabel("Job Title:");
        JLabel lblCompetencies = new JLabel("Competencies:");
        JLabel lblCarReg = new JLabel("Vehicle Registration:");


        tfName = new JTextField("", 20);
        tfSupervisor = new JTextField("", 20);
        tfCompany = new JTextField("", 20);
        tfJobTitle = new JTextField("", 20);
        tfCompetencies = new JTextField("", 20);
        tfCarReg = new JTextField("", 20);

        JButton btnPhotographCompetencies = new JButton("Photograph Competencies");
        btnPhotographCompetencies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // CREATE THE INDUCTEE
                InductionSWController.getInstance().createInductee(tfName.getText(), tfCompany.getText(),
                        tfJobTitle.getText(), tfSupervisor.getText(), tfCarReg.getText(),
                        tfCompetencies.getText(), System.currentTimeMillis());
                System.out.println("user added");
                // ADD THE PHOTO
                try {
                    InductionSWController.getInstance().getCurrentInductee().setPhoto(InductionSWController.getInstance().takePicture()
                    );
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new MigLayout("wrap 3"));
        panel.add(lblName, "cell 0 0");
        panel.add(tfName, "span 2");
        panel.add(lblCompany, "cell 0 1");
        panel.add(tfCompany, "span 2");
        panel.add(lblJobTitle, "cell 0 2");
        panel.add(tfJobTitle, "span 2");
        panel.add(lblSupervisor, "cell 0 3");
        panel.add(tfSupervisor, "span 2");
        panel.add(lblCarReg, "cell 0 4");
        panel.add(tfCarReg, "span 2");
        panel.add(lblCompetencies, "cell 0 5");
        panel.add(tfCompetencies, "span 2");
        panel.add(Box.createVerticalStrut(20), "cell 0 6");
        panel.add(btnPhotographCompetencies, "cell 1 7");
        return panel;
    }

    /**
     * This method creates the bottom JPanel, puts buttons on it and returns the fully constructed JPanel
     */
    private JPanel createBottomButtonPanel() {
        // Instantiate panel, button & listener, add listener to button,
        // add button to panel, return panel.
        JPanel buttonPanel = new JPanel();
        cancelButton = new JButton("Cancel");
        beginInductionButton = new JButton("BEGIN INDUCTION");
        ButtonsActionListener buttonListener =
                new ButtonsActionListener(this);

        cancelButton.addActionListener(buttonListener);
        beginInductionButton.addActionListener(buttonListener);

        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(beginInductionButton);


        return buttonPanel;
    }

    private JPanel createSideButtonPanel() {
        // instantiate the buttons
//        this.addUserButton = new JButton("Add User");
        this.displayInducteesButton = new JButton("Display Inductees");
//        this.BackEndButton = new JButton("Launch Backend");
//        this.takeQuizButton = new JButton("Take Quiz");
//        this.displayVideoButton = new JButton("Display Video");


        // Instantiate the listener for the buttons,
        // passing it in a reference to this class (TwitterFrame)
        // and assign it to the buttons
        ButtonsActionListener buttonListener = new ButtonsActionListener(this);
//        addUserButton.addActionListener(buttonListener);
        displayInducteesButton.addActionListener(buttonListener);
//        takeQuizButton.addActionListener(buttonListener);
//        BackEndButton.addActionListener(buttonListener);
//        displayVideoButton.addActionListener(buttonListener);

        // Create panel, assign layout, add components.
        JPanel sideButtonPanel = new JPanel();
        sideButtonPanel.setLayout(new BoxLayout(sideButtonPanel, BoxLayout.Y_AXIS));
//        sideButtonPanel.add(addUserButton);
        sideButtonPanel.add(Box.createVerticalStrut(50));
        sideButtonPanel.add(displayInducteesButton);
//        sideButtonPanel.add(Box.createVerticalStrut(5));
//        sideButtonPanel.add(BackEndButton);
//        sideButtonPanel.add(Box.createVerticalStrut(20));
//        sideButtonPanel.add(displayVideoButton);
//        sideButtonPanel.add(Box.createVerticalStrut(5));
//        sideButtonPanel.add(takeQuizButton);


        return sideButtonPanel;
    }


    //Inner class implementation of ActionListener
    private class ButtonsActionListener implements ActionListener {
        //This is to allow this inner class to refer to its
        //containing class (i.e. UserInputFrame)
        private UserInputFrame outerClass;

        public ButtonsActionListener(UserInputFrame outerClass) {
            this.outerClass = outerClass;
        }

        public void actionPerformed(ActionEvent e) {
            //Listener for button clicks.
            JButton sourceButton = (JButton) e.getSource();

            // ------------------ADD USER BUTTON------------------
//            if (sourceButton.equals(addUserButton)) {
//                InductionSWController.getInstance().createInductee(tfName.getText(), tfCompany.getText(),
//                        tfJobTitle.getText(), tfSupervisor.getText(), tfCarReg.getText(),
//                        tfCompetencies.getText(), System.currentTimeMillis());
//                System.out.println("user added");
//        }
        // ------------------CANCEL BUTTON------------------
        if (sourceButton.equals(cancelButton)) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MainDashBoardFrame();
                    }
                });
                dispose();


            }
            // ------------------BEGIN INDUCTION BUTTON------------------
            else if (sourceButton.equals(beginInductionButton)) {


//                Inductee i = InductionSWController.getInstance().
//                        createInductee(tfName.getText(), tfCompany.getText(), tfJobTitle.getText(),
//                                tfSupervisor.getText(), tfCarReg.getText(), tfCompetencies.getText(),
//                                System.currentTimeMillis());
//                InductionSWController.getInstance().setCurrentInductee(i);

            if (InductionSWController.getInstance().getCurrentInductee() == null) {
                String message = "You have not supplied any photographs of your proof of competencies\nAre you sure you wish to continue??";
                int answer =
                        JOptionPane.showConfirmDialog(outerClass, message);
                if (answer == JOptionPane.YES_OPTION) {
                    // Collect the inductee info
                    InductionSWController.getInstance().createInductee(tfName.getText(), tfCompany.getText(),
                            tfJobTitle.getText(), tfSupervisor.getText(), tfCarReg.getText(),
                            tfCompetencies.getText(), System.currentTimeMillis());
                    // Launch the video
                    InductionSWController.getInstance().launchVideo();
                    dispose();
                } else if (answer == JOptionPane.NO_OPTION) {

                } else {

                }

            } else {
                InductionSWController.getInstance().launchVideo();
                dispose();
            }
//                QuizFrame qf = new QuizFrame("Quiz", outerClass);
//                qf.setSize(400,300);
                // hide the frame
//                outerClass.setVisible(false);
//                i.getIndex()
            }
//            // ------------------TAKE QUIZ BUTTON------------------
//            else if (sourceButton.equals(takeQuizButton)) {
//                QuizFrame qf = new QuizFrame("Quiz");
//
//                qf.setSize(400, 300);
//            }
//            // ------------------Launch Backend BUTTON------------------
//            else if (sourceButton.equals(BackEndButton)) {
//                BackEndFrame bef = new BackEndFrame("Back End");
//                bef.setSize(400, 300);
//                bef.setVisible(true);
//            }
//            // ------------------DISPLAY VIDEO BUTTON------------------
//            else if (sourceButton.equals(displayVideoButton)) {
//                InductionSWController.getInstance().launchVideo();
//            }
            // ------------------DISPLAY INDUCTEES BUTTON------------------
            else {
                DisplayInducteesFrame dif = new DisplayInducteesFrame("View Inductees");
                dif.setSize(800, 300);

                dif.setVisible(true);

            } // end else

        }// end actionperformed

    }
}
