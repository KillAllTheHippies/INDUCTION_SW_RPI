package model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jamie on 07/03/16.
 * Collection of Inductees
 */
public class DataModel implements Serializable{
    private ArrayList<Inductee> inductees;


    public DataModel() {
        this.inductees = new ArrayList<Inductee>();
    }

    public void addInductee(Inductee i) {
        /* Set the index to the position in the arraylist
         that the inductee will be added to *************/
        i.setIndex(inductees.size());

        inductees.add(i);
    }

    public Inductee getInductee(int index) {
        return inductees.get(index);
    }


    public ArrayList<Inductee> getInductees() {
        return inductees;
    }

    public void setInductees(ArrayList<Inductee> inductees) {
        this.inductees = inductees;
    }
}
