package controller;

/**
 * Created by Jamie on 18/03/16.
 */

import controller.interfaces.IPersistor;
import model.DataModel;
import model.Inductee;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;


public class FilePersistor implements IPersistor{

    private static final String FILE_LOCATION = "C:\\InductionApp\\DataModel.dat";

//    public void write(DataModel dm)
//    {
//        try
//        {
//            FileOutputStream fos = new FileOutputStream(InductionSWController.DATAMODEL_FILE_LOCATION);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(dm);
//            oos.close();
//            fos.close();
//            System.out.println("Datamodel written");
//        }
//        catch(IOException ioex)
//        {
//            System.out.println(ioex.getMessage());
//        }
//
//    }
    public void write(DataModel dm)
    {


        try
        {
            FileOutputStream fos = new FileOutputStream(InductionSWController.DATAMODEL_FILE_LOCATION);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dm);
            oos.close();
            fos.close();
            System.out.println("Datamodel written");
        }
        catch(IOException ioex)
        {
            System.out.println(ioex.getMessage());
        }

    }


    public void writeInductee(Inductee i) {
        String randomFileName = UUID.randomUUID().toString() + ".ind";
        try
        {
            File f = new File("Inductees\\" + randomFileName);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(i);
            oos.close();
            fos.close();
            System.out.println("Inductee written");
        }
        catch(IOException ioex)
        {
            System.out.println(ioex.getMessage());
        }
    }

    public ArrayList<Inductee> read() {
        final File folder = new File("Inductees");
        return readInducteesFromFolder(folder);



    }
    public ArrayList<Inductee> readInducteesFromFolder(final File folder) {
        ArrayList<Inductee> inductees = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                readInducteesFromFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());

                try {
                    FileInputStream fis = new FileInputStream(fileEntry.getPath());
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    //We know that a DataModel object was serialized INTO
                    //the file, therefore a DataModel object MUST be coming
                    //out of the file => We can cast it to a DataModel object.
                    Inductee serializedObject = (Inductee) ois.readObject();
                    ois.close();
                    fis.close();
                    // add the inductee to the arraylist
                    inductees.add(serializedObject);

                } catch (IOException ioex)  // if there is an error reading file
                {
                    System.out.println("Error reading file");
                    System.out.println(ioex.getMessage());

                } catch (ClassNotFoundException cnfe) {

                    System.out.println("Class not found exception");
                    System.out.println(cnfe.getMessage());
                }

            }
        }
        return inductees;
    }


    }






