package view;

import controller.InductionSWController;
import controller.interfaces.IGui;
import model.Inductee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class ViewInducteesFrame extends JFrame implements IGui
{
    //The "scope" of this variable is the entire
    //class. So other methods and inner classes can see it.
    private JButton displayScoreButton;
    private JButton showPhotoButton;
    private JButton deleteButton;
    private JButton okButton;
//    private JButton cancelButton;
//    private JButton saveButton;
    private JTable inducteesTable;
    private InducteesTableModel tableModel;

    public ViewInducteesFrame(String title)
    {
        super(title);
        //This is what we are going to use as the
        //content of our JFrame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Call to method to create side panel
        JPanel sidePanel = createSideButtonPanel();
        mainPanel.add(sidePanel, BorderLayout.EAST);

        //Call to method to create bottom panel
        JPanel bottomPanel = createBottomButtonPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //Call to method to create table scroll pane
        JScrollPane tableScrollPane = createTableScrollPane();
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
    }

    public void refreshGUI()
    {
        //This "wakes up" the table model and tells it
        //that the data model it is connected to has changed.
        this.tableModel.fireTableDataChanged();
    }

    private JScrollPane createTableScrollPane()
    {
        inducteesTable = new JTable();

        //In future weeks we'll have a call to the
        //controller here to get us the list of players which
        //it manages as data model objects
        tableModel =
                new InducteesTableModel(InductionSWController.getInstance().getPersistor().read());

        inducteesTable.setModel(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(inducteesTable);
        //Can also be done this way
        //JScrollPane tableScrollPane = new JScrollPane();
        //tableScrollPane.add(inducteesTable)
        return tableScrollPane;
    }

    private JPanel createBottomButtonPanel()
    {
        okButton = new JButton("OK");
//        cancelButton = new JButton("Cancel");
//        saveButton = new JButton("Save");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
//        buttonPanel.add(cancelButton);
//        buttonPanel.add(saveButton);

//        saveButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e)
//            {
//                InductionSWController.getInstance().save();
//                JOptionPane.showMessageDialog(ViewInducteesFrame.this, "Inductees saved");
//            }
//        });
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        return buttonPanel;
    }

    private JPanel createSideButtonPanel()
    {
        displayScoreButton = new JButton("Display Score");
        showPhotoButton = new JButton("Show Photo");
        deleteButton = new JButton("Delete");

        //Create an instance of inner class
        //SideButtonsActionListener
        //When we create an instance of the inner class we pass
        //it a reference to its containing class.
        ButtonsActionListener buttonListener =
                new ButtonsActionListener(this);

        displayScoreButton.addActionListener(buttonListener);
        showPhotoButton.addActionListener(buttonListener);
        deleteButton.addActionListener(buttonListener);

        JPanel sideButtonPanel = new JPanel();

        BoxLayout boxL = new BoxLayout(sideButtonPanel, BoxLayout.Y_AXIS);
        sideButtonPanel.setLayout(boxL);

// Can also be written like this in one line.
//		sideButtonPanel.setLayout(
//		new BoxLayout(sideButtonPanel, BoxLayout.X_AXIS));
        sideButtonPanel.add(displayScoreButton);
        sideButtonPanel.add(Box.createVerticalStrut(5));
        sideButtonPanel.add(showPhotoButton);
        sideButtonPanel.add(Box.createVerticalStrut(5));
        sideButtonPanel.add(deleteButton);

        return sideButtonPanel;
    }

    public void showImage(BufferedImage img, String text) {
//        JFrame frame0 = new JFrame();
//
//        //frame0.getContentPane().add(new JPanelOpenCV(img));
//        // frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame0.setTitle(text);
//        frame0.setSize(img.getWidth(), img.getHeight() + 30);
//        frame0.setLocation(x, y);
//        frame0.setVisible(true);
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame(text);
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(), img.getHeight() + 30);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Inner class implementation of ActionListener
    private class ButtonsActionListener implements ActionListener
    {
        //This is to allow this inner class to refer to its
        //containing class (i.e. ViewInducteesFrame)
        private ViewInducteesFrame outerClass;

        public ButtonsActionListener(ViewInducteesFrame outerClass)
        {
            this.outerClass = outerClass;
        }

        public void actionPerformed(ActionEvent e)
        {
            //We know that the source of any ActionEvent
            //in this program MUST be a JButton seeing as
            //we only added an instance of this listener to
            //JButtons
            JButton sourceButton = (JButton)e.getSource();
            //          DISPLAY SCORE BUTTON
            if(sourceButton.equals(displayScoreButton)) {
                //Check if row is selected
                if (inducteesTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog
                            (outerClass,
                                    "You need to select a row in the table",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Inductee i = InductionSWController.getInstance().getPersistor().read().get(inducteesTable.getSelectedRow());

                    final JDialog frame = new JDialog(outerClass, i.getName(), true);


                    JPanel displayScorePanel = new JPanel();

                    displayScorePanel.setLayout(new BorderLayout());
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(0, 1));
                    panel.add(new JLabel("This inductee scored: " + i.getQuizPercentCorrect() + "%"));
                    panel.add(new JLabel("" + i.getWrongAnswers().size() + " wrong answers out of " +
                            InductionSWController.getInstance().getQuestionnaire().getQuestions().size() + "." ));
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(new JSeparator(JSeparator.HORIZONTAL));

                    for (String wrongAnswer : i.getWrongAnswers()) {
                    /* create a label with the wrong answer and add it to the panel
                     * wrongAnswer format: Wrong_answer|Correct_answer|Question_index
                     * Wrong answer = 0, correct answer = 1 question index = 2*/
                        String[] data = wrongAnswer.split("\\|");

                        panel.add(new JLabel("Question " + (Integer.parseInt(data[2]) + 1) + ":"));
                        panel.add(new JLabel(InductionSWController.getInstance().getQuestionnaire()
                                .getQuestions().get(Integer.parseInt(data[2])).getText()));

                        panel.add(new JLabel("Given answer: " + data[0]));
                        panel.add(new JLabel("Correct Answer: " + data[1]));
                        panel.add(Box.createVerticalStrut(5));
                        panel.add(new JSeparator(JSeparator.HORIZONTAL));
                    }

                    displayScorePanel.add(panel, BorderLayout.CENTER);

                    frame.getContentPane().add(displayScorePanel);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
            else if(sourceButton.equals(showPhotoButton))
            {
                //Check if row is selected
                if(inducteesTable.getSelectedRow() == -1)
                {
                    JOptionPane.showMessageDialog
                            (outerClass,
                                    "You need to select a row in the table",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {

                    Inductee ind = InductionSWController.getInstance().getPersistor().read().get
                            (inducteesTable.getSelectedRow());

                    BufferedImage bi = ind.getPhoto();

                    if (bi != null) {
                        showImage(
                                InductionSWController.getInstance().getDataModel().get
                                        (inducteesTable.getSelectedRow()).getPhoto(),
                                InductionSWController.getInstance().getDataModel().get
                                        (inducteesTable.getSelectedRow()).getName()
                                );
                    } else {
                        JOptionPane.showMessageDialog
                                (outerClass,
                                        "No photo available.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                    }


                }
            }
            //              DELETE BUTTON
            else
            {
                //Check if row is selected
                if(inducteesTable.getSelectedRow() == -1)
                {
                    JOptionPane.showMessageDialog
                            (outerClass,
                                    "You need to select a row in the table",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String message = "WARNING: Are you sure you want to delete this inductee ? \nTHIS OPERATION IS UNRECOVERABLE!!";
                    int answer =
                            JOptionPane.showConfirmDialog(outerClass, message);
                    if(answer == JOptionPane.YES_OPTION)
                    {
                        //TODO: Delete the Inductee file
                        JOptionPane.showMessageDialog
                                (outerClass,
                                        "Feature added soon",
                                        "Sorry!", JOptionPane.INFORMATION_MESSAGE);
//                        ArrayList<Inductee> inductees = InductionSWController.getInstance().getDataModel().getInductees();
//                        inductees.remove(inducteesTable.getSelectedRow());
//                        InductionSWController.getInstance().getDataModel().setInductees(inductees);
//                        InductionSWController.getInstance().save();
//                        refreshGUI();
                    }
                    else if (answer == JOptionPane.NO_OPTION)
                    {

                    }
                    else
                    {

                    }
                }
            }

        }
    }

}