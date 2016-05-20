package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**

 A question with multiple choices.

 */

public class ImageChoiceQuestion extends MultipleChoiceQuestion implements Serializable

{

    transient ArrayList<BufferedImage> choices;

    /**

     Constructs a choice question with no choices.

     */

    public ImageChoiceQuestion(String text)

    {
//        super(text);

        choices = new ArrayList<BufferedImage>();

    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//        out.writeInt(choices.size()); // how many images are serialized?
//        for (BufferedImage eachImage : choices) {
//            ImageIO.write(eachImage, "png", out); // png is lossless
//        }
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        final int imageCount = in.readInt();
//        choices = new ArrayList<BufferedImage>(imageCount);
//        for (int i=0; i<imageCount; i++) {
//            choices.add(ImageIO.read(in));
//        }
//    }

    /**

     Adds an answer choice to this question.

     @param choice the choice to add

     @param correct true if this is the correct choice, false otherwise

     */

    public void addImageChoice(String choice, boolean correct)

    {

//        choices.add(choice);

        if (correct)

        {

            // Convert choices.size() to string

            String choiceString = "" + choices.size();

//            setAnswer(choiceString);

        }

    }




    public ArrayList<BufferedImage> getImageChoices() {
        return choices;
    }

}

