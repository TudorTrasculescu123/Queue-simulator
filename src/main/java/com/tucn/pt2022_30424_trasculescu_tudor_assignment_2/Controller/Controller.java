package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Controller;
import java.io.IOException;

import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic.SimulationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private Button start;

    @FXML
    private TextArea numberClients;

    @FXML
    private TextArea numberQueues;

    @FXML
    private  TextArea maxSimTime;

    @FXML
    private TextArea minService;

    @FXML
    private TextArea maxService;

    @FXML
    private TextArea minArrival;

    @FXML
    private TextArea maxArrival;

    @FXML
    private TextArea resultedText;

    public void startButton() throws IOException {
        int nrClinets = 0, nrQueues = 0, maxSTime = 0, minSer = 0, maxSer = 0, minArr = 0, maxArr = 0;
        if(checkString(numberClients.getText())){
            nrClinets = Integer.parseInt(numberClients.getText());
        }
        if(checkString(numberQueues.getText())){
            nrQueues = Integer.parseInt(numberQueues.getText());
        }
        if(checkString(maxSimTime.getText())){
            maxSTime = Integer.parseInt(maxSimTime.getText());
        }
        if(checkString(minService.getText())){
            minSer = Integer.parseInt(minService.getText());
        }
        if(checkString(maxService.getText())){
            maxSer = Integer.parseInt(maxService.getText());
        }
        if(checkString(minArrival.getText())){
            minArr = Integer.parseInt(minArrival.getText());
        }
        if(checkString(maxArrival.getText())){
            maxArr = Integer.parseInt(maxArrival.getText());
        }
        if(nrClinets == 0 || nrQueues == 0 || maxSTime == 0 || minSer == 0 || maxSer == 0 || minArr == 0 || maxArr == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Some input is wrong or missing!");
            alert.showAndWait();
        }else if(minSer >= maxSer){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Minimum service time should be less than the maximum service time!");
            alert.showAndWait();
        }else if(minArr >= maxArr){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText("Minimum arrival time should be less than the maximum arrival time!");
            alert.showAndWait();
        }else{
            SimulationManager simulation = new SimulationManager(maxSTime, maxSer, minSer, nrQueues, nrClinets, minArr, maxArr);
            //FileWriter myWriter = new FileWriter("log3.txt");
            //simulation.setFileWriter(myWriter);
            simulation.setController(this);
            Thread thread = new Thread(simulation);
            thread.start();
            start.setDisable(true);
        }
    }

    public void setText(String s){
        resultedText.appendText(s);
    }

    private boolean checkString(String s){
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(s);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
}