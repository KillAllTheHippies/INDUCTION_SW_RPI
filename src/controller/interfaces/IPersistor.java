package controller.interfaces;

import model.DataModel;

/**
 * Created by Jamie on 29/02/16.
 */
public interface IPersistor {
//    public void writeInductee(Inductee i, DataModel dm);
    //public void writeQuestion(ArrayList<MultipleChoiceQuestion> dataModel);
    DataModel read();

    void write(DataModel dm);

}
