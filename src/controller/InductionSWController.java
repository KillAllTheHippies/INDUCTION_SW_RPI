package controller;




import controller.interfaces.IGui;
import controller.interfaces.IPersistor;
import model.DataModel;
import model.Inductee;
import model.MultipleChoiceQuestion;
import model.Questionnaire;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.io.File;
//import view.JPanelOpenCV;

public class InductionSWController
{
    //THIS IS THE STATIC PART
    //This is the static variable which will point at the
    //instance of WorldCupController once created.
    private static InductionSWController instance = null;

    //This is the static method which "manages" the static
    //instance. A static method is required to access a static
    //variable.
    //If the instance is not created it will be created. If it is
    //already created then we don't need to create another instance.
    //Either way the one and only instance gets returned.
    public static InductionSWController getInstance()
    {
        if(instance == null)
        {
            instance = new InductionSWController();
        }
        return instance;
    }

    /////EVERYTHING BELOW THIS IS THE "INSTANCE PART"

    //Reference to the data model
    private DataModel dataModel;
    private Questionnaire questionnaire;
    private Inductee currentInductee;
    public static final int QUIZ_PASS_PERCENTAGE = 70;
//    public static final String QUESTIONNAIRE_LOCATION = "C:\\InductionApp\\Questionnaire.txt";
    public static final String QUESTIONNAIRE_LOCATION = "Questionnaire.txt";
//    public static final String VLC_LIB_LOCATION = "C:\\InductionApp\\lib";
    public static final String VLC_LIB_LOCATION = "lib";
    public static final String DATAMODEL_FILE_LOCATION = "DataModel.dat";
//    public static final String DATAMODEL_FILE_LOCATION = "C:\\InductionApp\\DataModel.dat";
    public static final String VIDEO_FILE_LOCATION = "InductionVideo.mp4";
//    public static final String VIDEO_FILE_LOCATION = "C:\\InductionApp\\InductionVideo.mp4";
    //private ArrayList<Inductee> dataModel;

    //Reference to the GUI
    //Any GUI which implements this interface can be
    //communicated with by this controller.
    //If we had just put private UserInputFrame gui then
    //we would be restricting this controller to only being
    //capable of connecting to a Swing GUI.
    private IGui gui;

    //Add a reference to the persistor.
    private IPersistor persistor;

    //Default constructor
    //Making this private means that it can only be called
    //from inside this class (i.e. Only our getInstance()
    //method can call this now. Nobody outside this class
    //can create an instance of it.
    private InductionSWController() {

        this.questionnaire = new Questionnaire();
    }

    public void setDataModel(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    public DataModel getDataModel()
    {
        return this.dataModel;
    }

    public Questionnaire getQuestionnaire(){ return this.questionnaire;}

//    public void setQuestionnaire(Questionnaire questionnaire) {
//        this.questionnaire = questionnaire;
//    }


    public void setGuiReference(IGui gui)
    {
        this.gui = gui;
    }

    public IGui getGuiReference()
    {
        return this.gui;
    }

    public void setPersistor(IPersistor persistor)
    {
        this.persistor = persistor;
    }

    public IPersistor getPersistor()
    {
        return this.persistor;
    }

    public Inductee createInductee( String name, String company, String jobTitle, String supervisor, String carReg, String competencies, long dateOfInduction) {
        Inductee i = new Inductee( name, company, jobTitle, supervisor, carReg, competencies, dateOfInduction);

//        // Capture the picture and add it to the inductee
//        try {
//            i.setPhoto(takePicture());
//            System.out.println("picture taken, createInductee");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Add the inductee to the datamodel
        // TODO: This will be deleted and current inductee will be added
//        this.dataModel.addInductee(i);

        // Set the current inductee
        this.currentInductee = i;

//        this.newlyAddedInductees.add(i);
        return i;
    }

    public void saveCurrentInductee() {

        if (currentInductee != null) {
            this.dataModel.addInductee(currentInductee);
        } else {
            System.out.println("CURRENTINDUCTEE IS NULL");
        }
    }
    public Inductee getCurrentInductee() {
        return currentInductee;
    }
    public void setCurrentInductee(Inductee i) {
        this.currentInductee = i;
    }

    public BufferedImage takePicture() throws InterruptedException {

        //TODO: Run the command that takes a picture on RPI
        Process p;
        try {

            p = Runtime.getRuntime().exec(new String[]{"bash", "-c","raspistill -e png -o new.png -w 800 -h 600"});

//            MyInputStreamReader reader1 = new MyInputStreamReader(p.getInputStream());
//            reader1.setTag("in");
//            reader1.start();
//
//            MyInputStreamReader reader2 = new MyInputStreamReader(p.getErrorStream());
//            reader2.setTag("in");
//            reader2.start();


            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return loadImage("new.png");
    }

    public BufferedImage loadImage(String file) {
        BufferedImage img;

        try {
            File input = new File(file);
            img = ImageIO.read(input);

            return img;
        } catch (Exception e) {
            System.out.println("erro");
        }

        return null;
    }


    // Write the newly added inductees and clear the arraylist
    public void save()
    {
        this.persistor.write(this.dataModel);
        System.out.println("save method of controller called");
//        this.persistor.writeInductee(this.newlyAddedInductees);
//        this.newlyAddedInductees.clear();
    }


    public boolean checkAnswer(MultipleChoiceQuestion q, int ans) {
        // Check the answer given the question and the answer
        return q.checkAnswer(ans);
    }

    public boolean isQuizPassed(int percentageCorrect) {
        if (percentageCorrect < QUIZ_PASS_PERCENTAGE)
            return false;
        else
            return true;

    }

    public int calculateQuizScore(Inductee inductee) {

        int amountCorrect = 0;
        ArrayList<String> wrongAnswers = new ArrayList<>();
        for (int j = 0; j < questionnaire.getQuestions().size(); j++) {
            if (checkAnswer(questionnaire.getQuestions().get(j), inductee.getAnswer(j))) {
                amountCorrect++;

            } else {
                // get the text of the answer given by the inductee
                wrongAnswers.add(""+questionnaire.getQuestions().get(j).getAnswerText(inductee.getAnswer(j))
                        // Delimiter and correct answer
                        +"|" +questionnaire.getCorrectAnswer(j) +
                        // Delimiter and index of question
                        "|" + (j));
            }

        }
        // Assign the wrong answers to the current Inductee
        currentInductee.setWrongAnswers(wrongAnswers);
        return amountCorrect;
    }



    public void launchVideo() {
        //TODO: Launch omxplayer
        VideoPlayer vp = new VideoPlayer();
//        controller.VideoPlayer.main(null);
    }


//    public void saveDataModel()
//    {
//        FilePersistor fps = new FilePersistor();
//        fps.write(this.dataModel);
//    }
}