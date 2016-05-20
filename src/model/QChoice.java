package model;

import java.awt.image.BufferedImage;

/**
 * Created by Jamie on 29/03/16.
 */
public class QChoice {
    private String text;
    private BufferedImage image;
    private boolean correct;

    public QChoice(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }
    public QChoice(BufferedImage image, boolean correct) {
        this.image = image;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }
public BufferedImage getImage() {
        return image;
    }

//    public void setText(String text) {
//        this.text = text;
//    }

    public boolean isCorrect() {
        return correct;
    }

//    public void setCorrect(boolean correct) {
//        this.correct = correct;
//    }
}
