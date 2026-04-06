package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.text.ParseException;

public class Controller {

    @FXML
    public TextField textField1;

    @FXML
    public TextField textField2;

    @FXML
    public TextField textField3;

    @FXML
    public TextField textField4;

    @FXML
    public TextField textField5;

    @FXML
    public TextField textField6;

    @FXML
    public TextField textField7;

    @FXML
    public TextArea textArea1;

    @FXML
    public TextArea textArea2;


    public void onClick(){
        textArea1.clear();
        textArea2.clear();
        textField5.clear();
        textField6.clear();
        textField7.clear();


        try {
            int a = Integer.parseInt(textField1.getText());
            int c = Integer.parseInt(textField2.getText());
            int m = Integer.parseInt(textField3.getText());
            int x0 = Integer.parseInt(textField4.getText());

            int[] set = Generator.createSet(a, c, m, x0);
            char[] set2 = Generator.bitmas;

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < set.length; i++) {
                result.append(set[i] + " ");
                if (i % 10 == 9)
                    result.append("\n");
            }
            textArea1.setText(result.toString());

            result = new StringBuilder();
            for (int i = 0; i < set2.length; i++) {
                result.append(set2[i] + " ");
                if (i % 8 == 7)
                    result.append("\n");
            }
            textArea2.setText(result.toString());

            textField7.setText(Generator.bitmas.length + ""); // length

            textField5.setText("1: " + Generator.countOdd1Bit + " 0: " + Generator.countEven1Bit);

            textField6.setText("Odd: " + Generator.countOdd8Bit + " Even: " + Generator.countEven8Bit);


        } catch (NumberFormatException e) {
            textArea1.setText("Error");
            textArea2.setText("Error");
        }
    }



}
