package controller.interfaces;

import model.DataModel;
import model.Inductee;

import java.util.ArrayList;

/**
 * Created by Jamie on 29/02/16.
 */
public interface IPersistor {
//    public void writeInductee(Inductee i, DataModel dm);
    //public void writeQuestion(ArrayList<MultipleChoiceQuestion> dataModel);
    ArrayList<Inductee> read();

    void write(DataModel dm);

    void writeInductee(Inductee i);

}
