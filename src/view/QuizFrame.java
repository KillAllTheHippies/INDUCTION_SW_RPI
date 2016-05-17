package view;


import controller.InductionSWController;
import model.MultipleChoiceQuestion;
import model.Questionnaire;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Jamie on 07/03/16.
 * Frame that holds the Questionnaire
 */
public class QuizFrame extends JFrame {

    private JButton scoreAssessmentButton;
    private JButton cancelInductionButton;

    private ArrayList<JRadioButton> btnArray;

    private Questionnaire questionnaire;
    private ArrayList<QuestionPanel> questionPanels;


    public QuizFrame(String title) throws HeadlessException {
        super(title);
        btnArray = new ArrayList<>();
        questionPanels = new ArrayList<>();
        questionnaire = InductionSWController.getInstance().getQuestionnaire();

        // Content of our JFrame
        JPanel mainPanel = new JPanel();

        // set border layout
        mainPanel.setLayout(new BorderLayout());

        JPanel centrePanel = createCenterPanel();
        JScrollPane sp = new JScrollPane(centrePanel);
        mainPanel.add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomButtonPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);


        // add to the layout
        this.add(mainPanel);
        this.setUndecorated(true); // Remove window border
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }



    private JPanel createCenterPanel() {

        JPanel mainPanel = new JPanel(new GridLayout(0, 1));

        for (MultipleChoiceQuestion quest : questionnaire.getQuestions()) {

            questionPanels.add(new QuestionPanel(quest));

        } // end for

        /* Must get used to this foreach syntax. */
        questionPanels.forEach(mainPanel::add);

//        for (QuestionPanel p : questionPanels) {
//            mainPanel.add(p);
//        }


        return mainPanel;
    }

    private JPanel createBottomButtonPanel() {
        // Instantiate panel, button & listener, add listener to button,
        // add button to panel, return panel.
        JPanel buttonPanel = new JPanel();
        scoreAssessmentButton = new JButton("Score Assessment");
        cancelInductionButton = new JButton("Cancel Induction");
        ButtonsActionListener buttonListener =
                new ButtonsActionListener(this);

        scoreAssessmentButton.addActionListener(buttonListener);
        cancelInductionButton.addActionListener(buttonListener);

        buttonPanel.add(cancelInductionButton);
        buttonPanel.add(Box.createHorizontalStrut(120));
        buttonPanel.add(scoreAssessmentButton);

        return buttonPanel;
    }

    private class ButtonsActionListener implements ActionListener {
        //This is to allow this inner class to refer to its
        //containing class (i.e. UserInputFrame)
        private QuizFrame outerClass;

        public ButtonsActionListener(QuizFrame outerClass) {
            this.outerClass = outerClass;
        }

        public boolean validateQuestionnaire() {
            for (QuestionPanel qPanel : questionPanels) {
                if (qPanel.getButtonGroup().getSelection() == null) {
                    return false;
                }
            }
            return true; // true if no nulls
        }

        public void actionPerformed(ActionEvent e) {
            //Listener for button clicks.
            JButton sourceButton = (JButton) e.getSource();

            // ------------------SCORE ASSESSMENT BUTTON------------------

            if (sourceButton.equals(scoreAssessmentButton)) {
                if (validateQuestionnaire()) {

                    int quizScore = InductionSWController.getInstance().calculateQuizScore
                            (InductionSWController.getInstance().getCurrentInductee());
                    int quizSize = InductionSWController.getInstance().getQuestionnaire().getQuestions().size();

                    final JDialog frame = new JDialog(outerClass, "Score", true);
                    // remove close button window border
                    frame.setUndecorated(true);


                    // IF QUIZ IS PASSED
                    if (InductionSWController.getInstance().isQuizPassed((quizScore * 100) / quizSize)) {

                        JPanel passedPanel = new JPanel();
                        passedPanel.setLayout(new BorderLayout());

                        JPanel panel = new JPanel();
                        panel.setLayout(new GridLayout(0, 1));

                        passedPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                        panel.setBorder(new EmptyBorder(10,10,10,10));

                        JLabel scoreLabel = new JLabel("Your Score was: " + quizScore + " correct out of " + quizSize +
                                "(" + (quizScore * 100) / quizSize + "%)");


                        JLabel wrongLabel = new JLabel("Please pay attention to the questions that you answered incorrectly.");
                        panel.add(new JLabel("Congratulations! You have passed your induction."));
                        panel.add(scoreLabel);
                        if (quizScore < quizSize)
                            panel.add(wrongLabel);
                        else
                            panel.add(new JLabel("You answered all questions correctly!"));

                            panel.add(Box.createVerticalStrut(5));
                        panel.add(new JSeparator(JSeparator.HORIZONTAL));

                        for (String wrongAnswer : InductionSWController.getInstance().getCurrentInductee().getWrongAnswers()) {
                    /* create a label with the wrong answer and add it to the panel
                     * wrongAnswer format: Wrong_answer|Correct_answer|Question_index
                     * Wrong answer = 0, correct answer = 1 question index = 2*/
                            String[] data = wrongAnswer.split("\\|");

                            panel.add(new JLabel("Question " + (Integer.parseInt(data[2]) + 1) + ":"));
                            panel.add(new JLabel(InductionSWController.getInstance().getQuestionnaire()
                                    .getQuestions().get(Integer.parseInt(data[2])).getText()));

                            panel.add(new JLabel("Your answer: " + data[0]));
                            panel.add(new JLabel("The Correct Answer was: " + data[1]));
                            panel.add(Box.createVerticalStrut(5));
                            panel.add(new JSeparator(JSeparator.HORIZONTAL));


                        }

                        JPanel bottomButtonPanel = new JPanel();
                        JButton completeInductionBtn = new JButton("Complete induction");
                        completeInductionBtn.addActionListener(new ButtonsActionListener(outerClass) {
                            public void actionPerformed(ActionEvent e) {
                                // persist the Inductee
                                if (InductionSWController.getInstance().getCurrentInductee() != null) {
                                    InductionSWController.getInstance().saveCurrentInductee();
                                    // Write the datamodel
                                    InductionSWController.getInstance().save();
                                } else {
                                    System.out.println("CURRENT INDUCTEE IS NULL!!!!!!!!!!!!!!!!!!!!");
                                }

                                // Clear the current Inductee
//                                InductionSWController.getInstance().setCurrentInductee(null);

                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new MainDashBoardFrame();
                                    }
                                });
                                outerClass.dispose();
                            }
                        });
                        bottomButtonPanel.add(completeInductionBtn);
                        passedPanel.add(panel, BorderLayout.CENTER);
                        passedPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
                        frame.getContentPane().add(passedPanel);

                        frame.pack();

                        // Center the dialog
                        frame.setLocationRelativeTo(null);
//                        frame.setLocation(100,100);
                        frame.setVisible(true);


                    } else { // Else Quiz is FAILED
                        JPanel dialogPanel = new JPanel();
                        dialogPanel.setLayout(new BorderLayout());

                        JPanel panel = new JPanel();
                        panel.setLayout(new GridLayout(0, 1));

                        dialogPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                        panel.setBorder(new EmptyBorder(10,10,10,10));

                        JLabel scoreLabel = new JLabel("Your Score was: " + quizScore + " correct out of " + quizSize +
                                "(" + (quizScore * 100) / quizSize + "%)");
                        JLabel passLabel = new JLabel("Unfortunately you have NOT passed. " +
                                "Please watch the induction video again and attempt to answer at least " +
                                InductionSWController.QUIZ_PASS_PERCENTAGE + "% correct.");

                        panel.add(scoreLabel);
                        panel.add(passLabel);
                        panel.add(Box.createVerticalStrut(5));
                        panel.add(new JSeparator(JSeparator.HORIZONTAL));


                        JPanel bottomButtonPanel = new JPanel();
                        JButton reTakeQuizBtn = new JButton("Re-take Assessment");
                        reTakeQuizBtn.addActionListener(new ButtonsActionListener(outerClass) {
                            public void actionPerformed(ActionEvent e) {
                                InductionSWController.getInstance().launchVideo();
                                outerClass.dispose();
                            }
                        });


                        JButton quitBtn = new JButton("Quit");
                        quitBtn.addActionListener(new ButtonsActionListener(outerClass) {
                            public void actionPerformed(ActionEvent e) {
                                // TODO: return to main dashboard
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        new MainDashBoardFrame();
                                    }
                                });


                                outerClass.dispose();
                            }
                        });

                        bottomButtonPanel.add(reTakeQuizBtn);
                        bottomButtonPanel.add(quitBtn);
                        dialogPanel.add(bottomButtonPanel, BorderLayout.SOUTH);


                        dialogPanel.add(panel, BorderLayout.CENTER);
                        frame.getContentPane().add(dialogPanel);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                    }
                } else { // ELSE QUESTIONNAIRE NOT VALIDATED
                    JOptionPane.showMessageDialog(null, "You need to answer all questions", "Error", JOptionPane.ERROR_MESSAGE);

                }
            } else { // ELSE THE BUTTON IS CANCEL INDUCTION
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MainDashBoardFrame();
                    }
                });
                dispose();
            }

        }

    }

}

