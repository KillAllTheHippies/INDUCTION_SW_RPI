package controller;

/**
 * Created by Jamie on 18/03/16.
 */

import controller.interfaces.IPersistor;
import model.DataModel;

import java.io.*;


public class FilePersistor implements IPersistor{

    private static final String FILE_LOCATION = "C:\\InductionApp\\DataModel.dat";

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


//    public void writeInductee(Inductee i, DataModel dm) {
//        dm.addInductee(i);
//        write(dm);
//    }

    public DataModel read() {


        if (new File(InductionSWController.DATAMODEL_FILE_LOCATION).isFile()) {

            try {
                FileInputStream fis = new FileInputStream(InductionSWController.DATAMODEL_FILE_LOCATION);
                ObjectInputStream ois = new ObjectInputStream(fis);
                //We know that a DataModel object was serialized INTO
                //the file, therefore a DataModel object MUST be coming
                //out of the file => We can cast it to a DataModel object.
                DataModel serializedObject = (DataModel) ois.readObject();
                ois.close();
                fis.close();
                return serializedObject;

            } catch (IOException ioex)  // if there is an error reading file
            {
                System.out.println("Error reading file");
                System.out.println(ioex.getMessage());

            } catch (ClassNotFoundException cnfe) {

                System.out.println("Class not found exception");
                System.out.println(cnfe.getMessage());
            }
            //We will only come to this piece of code if something has
            //failed in relation to reading the serialized file above.
            //If we arrive here we still must return something.
            return null;

        } else // else file doesn't exist so return an empty datamodel
        {
            // initialise the datamodel .
//            DataModel dm = new DataModel();
//            return dm;
            return new DataModel();
        }

    }
    }






