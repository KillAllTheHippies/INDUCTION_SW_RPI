package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by Jamie on 29/02/16.
 * <p>
 * Initial frame to be created when application is first started
 */
public class BackEndFrame extends JFrame {

    private JButton closeButton;
    private JButton addUserButton;
    private JButton showTweetsButton;
    private JButton displayUsersButton;
    private JButton updateButton;
    private JButton addTweetButton;

    private JTextField tfQuestion;
    private JTextField tfAns1;
    private JTextField tfAns2;
    private JTextField tfAns3;
    private JTextField tfAns4;


    public BackEndFrame(String title) throws HeadlessException {

        super(title);

        // Content of our JFrame
        JPanel mainPanel = new JPanel();

        // set border layout
        mainPanel.setLayout(new BorderLayout());

        // create the side and bottom panels and add them to the layout
        JPanel sidePanel = createSideButtonPanel();
        mainPanel.add(sidePanel, BorderLayout.EAST);
        JPanel bottomPanel = createBottomButtonPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // create the Question input panel

        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // add to the layout
        this.getContentPane().add(mainPanel);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        JLabel lblQuestion = new JLabel("Question:");
        JLabel lblAns1 = new JLabel("Answer 1:");

        JLabel lblAns2 = new JLabel("Answer 2:");
        JLabel lblAns3 = new JLabel("Answer 3:");
        JLabel lblAns4 = new JLabel("Answer 4:");

        tfQuestion = new JTextField("", 20);
        tfAns1 = new JTextField("", 20);
        tfAns1.setBackground(Color.green);
        tfAns2 = new JTextField("", 20);
        tfAns2.setBackground(Color.red);
        tfAns2.setOpaque(true);
        tfAns3 = new JTextField("", 20);
        tfAns3.setBackground(Color.red);
        tfAns4 = new JTextField("", 20);
        tfAns4.setBackground(Color.red);


        JPanel panel = new JPanel(new MigLayout("wrap 3"));
        panel.add(lblQuestion, "cell 0 0");
        panel.add(tfQuestion, "span 2");
        panel.add(lblAns1, "cell 0 1");
        panel.add(tfAns1, "span 2");
        panel.add(lblAns2, "cell 0 2");
        panel.add(tfAns2, "span 2");
        panel.add(lblAns3, "cell 0 3");
        panel.add(tfAns3, "span 2");
        panel.add(lblAns4, "cell 0 4");
        panel.add(tfAns4, "span 2");

        return panel;
    }

    /**
     * This method creates the bottom JPanel, puts buttons on it and returns the fully constructed JPanel
     */
    private JPanel createBottomButtonPanel() {
        // Instantiate panel, button & listener, add listener to button,
        // add button to panel, return panel.
        JPanel buttonPanel = new JPanel();
        closeButton = new JButton("Close");
        ButtonsActionListener buttonListener =
                new ButtonsActionListener(this);

        closeButton.addActionListener(buttonListener);

        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    private JPanel createSideButtonPanel() {
        // instantiate the buttons
        this.addUserButton = new JButton("Add User");
        this.displayUsersButton = new JButton("Display Users");
        this.updateButton = new JButton("Update User");
        this.showTweetsButton = new JButton("Show Tweets");
        this.addTweetButton = new JButton("Add Tweet");


        // Instantiate the listener for the buttons,
        // passing it in a reference to this class (TwitterFrame)
        // and assign it to the buttons
        ButtonsActionListener buttonListener = new ButtonsActionListener(this);
        addUserButton.addActionListener(buttonListener);
        displayUsersButton.addActionListener(buttonListener);
        showTweetsButton.addActionListener(buttonListener);
        updateButton.addActionListener(buttonListener);
        addTweetButton.addActionListener(buttonListener);

        // Create panel, assign layout, add components.
        JPanel sideButtonPanel = new JPanel();
        sideButtonPanel.setLayout(new BoxLayout(sideButtonPanel, BoxLayout.Y_AXIS));
        sideButtonPanel.add(addUserButton);
        sideButtonPanel.add(Box.createVerticalStrut(5));
        sideButtonPanel.add(displayUsersButton);
        sideButtonPanel.add(Box.createVerticalStrut(5));
        sideButtonPanel.add(updateButton);
        sideButtonPanel.add(Box.createVerticalStrut(20));
        sideButtonPanel.add(addTweetButton);
        sideButtonPanel.add(Box.createVerticalStrut(5));
        sideButtonPanel.add(showTweetsButton);


        return sideButtonPanel;
    }


    //Inner class implementation of ActionListener
    private class ButtonsActionListener implements ActionListener {
        //This is to allow this inner class to refer to its
        //containing class (i.e. UserInputFrame)
        private BackEndFrame outerClass;

        public ButtonsActionListener(BackEndFrame outerClass) {
            this.outerClass = outerClass;
        }

        public void actionPerformed(ActionEvent e) {
            //Listener for button clicks.
            JButton sourceButton = (JButton) e.getSource();

            // ------------------ADD USER BUTTON------------------
            if (sourceButton.equals(addUserButton)) {


            // ------------------CLOSE BUTTON------------------
            } else if (sourceButton.equals(closeButton)) {
                dispose();

            // ------------------SHOW TWEETS BUTTON------------------
            } else if (sourceButton.equals(showTweetsButton)) {

            }
            // ------------------UPDATE BUTTON------------------
             else if (sourceButton.equals(updateButton)) {

            }
            // ------------------ADD TWEET BUTTON------------------
            else if (sourceButton.equals(addTweetButton)) {

            }
            // ------------------DISPLAY USERS BUTTON------------------
            else {


            } // end else

        }// end actionperformed

    }
}
