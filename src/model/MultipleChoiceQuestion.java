package model;

import java.io.Serializable;
import java.util.ArrayList;

/**

 A question with multiple choices.

 */

public class MultipleChoiceQuestion  implements Serializable

{
    private int index; // Location in the ArrayList
    private String text;
    private ArrayList<QChoice> choices;

    /**

     Constructs a choice question with no choices.

     */

    public MultipleChoiceQuestion(String questionText)

    {
//        super(questionText);
        this.text = questionText;
        choices = new ArrayList<>();

    }

    /**
     * Default Constructor
     */
    public MultipleChoiceQuestion() {
//        super();
    }

    public boolean checkAnswer(int response)

    {
        return choices.get(response).isCorrect();
    }


    public String getAnswerText(int answerIndex) {
        return choices.get(answerIndex).getText();
    }
    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<QChoice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<QChoice> choices) {
        this.choices = choices;

    }

    /**

     Adds an answer choice to this question.

     @param choice the choice to add

     @param correct true if this is the correct choice, false otherwise

     */

    public void addChoice(String choice, boolean correct)

    {
        QChoice c = new QChoice(choice, correct);
        choices.add(c);

//        if (correct)
//
//        {
//
//            // Convert choices.size() to string
//
//            String choiceString = "" + choices.size();
//
//            setAnswer(choiceString);
//
//        }

    }

//    public void display()
//
//    {
//
//// Display the question text
//
//        super.display();
//
//// Display the answer choices
//
//        for (int i = 0; i < choices.size(); i++)
//
//        {
//
//            int choiceNumber = i + 1;
//
//            System.out.println(choiceNumber + ": " + choices.get(i));
//
//        }
//
//    }

}
