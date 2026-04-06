package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    TextArea textArea1;

    @FXML
    TextArea textArea2;

    @FXML
    TextArea textArea3;

    @FXML
    TextField textField1;

    @FXML
    TextField textField2;

    RSA cipher;

    public void createRSACipher(ActionEvent actionEvent) { //227 , 373
        cipher = new RSA(Integer.parseInt(textField1.getText()),
                Integer.parseInt(textField2.getText()));
        textArea1.setText(cipher.toString());
    }

    public void clickEncryption(ActionEvent actionEvent) {
        cipher.setOpenText(textArea2.getText());
        cipher.encryption();
        textArea3.setText(cipher.getCloseText().toString());
    }

    public void clickDecryption(ActionEvent ectionEvent){
        cipher.setCloseText(textArea2.getText());
        cipher.decryption();
        textArea3.setText(RSA.toRusString(cipher.getOpenText()));
    }
}
