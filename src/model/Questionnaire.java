package model;

import controller.InductionSWController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jamie on 27/03/16.
 */
public class Questionnaire {

    private ArrayList<MultipleChoiceQuestion> questions;
    private boolean[] answers;
    private MultipleChoiceQuestion question;

    public Questionnaire() {
        this.questions = new ArrayList<>();


        // initialise the answers array (to the size of the amount of questions)
        try{

            BufferedReader in = new BufferedReader(new FileReader(InductionSWController.QUESTIONNAIRE_LOCATION));
            String s;
            int index = 0;
            while((s = in.readLine()) != null){

                String[] var = s.split("="); // use = as our delimiter


                    question = new MultipleChoiceQuestion(var[0]);
                    question.addChoice(var[1], true);
                    question.addChoice(var[2], false);
                    question.addChoice(var[3], false);
                    question.addChoice(var[4], false);



                /* Shuffle the choices */
                ArrayList<QChoice> tempChoices = question.getChoices();
                Collections.shuffle(tempChoices);
                question.setChoices(tempChoices);

                // Set the index of the question.
                question.setIndex(index);
                questions.add(question);
                index++;

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        this.answers = new boolean[questions.size()];

//


    }

    public void addQuestion(MultipleChoiceQuestion q) {
        questions.add(q);
    }

    public void addAnswer(boolean ans, int index) {

        this.answers[index] = ans;


    }
    public ArrayList<MultipleChoiceQuestion> getQuestions() {
        return questions;
    }

    public boolean[] getAnswers() {
        return answers;
    }

    public String getCorrectAnswer(int index) {
        for (int i = 0 ; i < 4; i++) {
            if (questions.get(index).getChoices().get(i).isCorrect()) {
                return questions.get(index).getChoices().get(i).getText();
            }

        }
        return "The code should not execute this";
    }
}
