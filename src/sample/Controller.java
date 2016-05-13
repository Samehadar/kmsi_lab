package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Map;

public class Controller {

    @FXML
    TextField textField1;

    @FXML
    TextField textField2;

    @FXML
    TextField textField3;

    @FXML
    TextField textField4;

    @FXML
    TextField textField5;

    @FXML
    TextField textField6;

    @FXML
    TextField textField7;

    @FXML
    TextField textField8;

    @FXML
    TextField textField9;

    @FXML
    TextField textField10;

    @FXML
    TextField textField11;


    @FXML
    TextArea textArea1;

    @FXML
    TextArea textArea2;

    XORCipher cipher;

    public void onClick(){
        textArea1.clear();
        textArea2.clear();
        textField10.clear();

        setFunc();
        setReg();
        Generator.createSet();

        for (int i = 0; i < Generator.array.size(); i++) {
            textArea1.appendText(Generator.array.get(i) + " ");
            if (i % 8 == 7) textArea1.appendText("\n");
        }
        for (int i = 0; i < Generator.array.size(); i++) {
            textArea2.appendText(Generator.generateNumbers.get(i) + " ");
            if (i % 8 == 7) textArea2.appendText("\n");
        }
        textField10.setText(Generator.array.size() + "");
    }

    public void setFunc(){
        try {
            Generator.F1 = Byte.parseByte(textField1.getText());
            Generator.F2 = Byte.parseByte(textField2.getText());
            Generator.F3 = Byte.parseByte(textField3.getText());
            Generator.F4 = Byte.parseByte(textField4.getText());
            Generator.F5 = Byte.parseByte(textField5.getText());
            Generator.F6 = Byte.parseByte(textField6.getText());
            Generator.F7 = Byte.parseByte(textField7.getText());
            Generator.F8 = Byte.parseByte(textField8.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void setReg(){
        try {
            Generator.reg = Short.parseShort(textField9.getText());
            Generator.startReg = Generator.reg;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doEncryption(ActionEvent actionEvent) {
        if (cipher == null)
            setTextAreas("Не выбран файл для шифрования", "Не выбран файл для шифрования");
        //cipher = new XORCipher(textArea1.getText());
        cipher.encryption();
        textArea2.setText(cipher.getEncryptedText());
    }

    public void doDecryption(ActionEvent actionEvent) {
        if (cipher == null)
            setTextAreas("Не выбран файл для шифрования", "Не выбран файл для шифрования");
        //cipher = new XORCipher();
        //cipher.setEncryptedText(textArea1.getText());
        cipher.decryption();
        textArea1.setText(cipher.getDecryptedText());
    }

    public void openFileForE(ActionEvent actionEvent) throws Exception{
        StringBuilder result = new StringBuilder();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Set File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String s = "";
            while(!((s = reader.readLine())== null)){
                result.append(s);
            }
            reader.close();
        }
        if (cipher == null)
            cipher = new XORCipher();
        cipher.setDecryptedText(result.toString());

        setTextAreas(result.toString(), "");
    }


    public void openFileForD(ActionEvent actionEvent) throws Exception {
        StringBuilder result = new StringBuilder();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Set File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String s = "";
            while(!((s = reader.readLine())== null)){
                result.append(s);
            }
            reader.close();
        }
        if (cipher == null)
            cipher = new XORCipher();
        cipher.setEncryptedText(result.toString());

        setTextAreas("", result.toString());
    }

    public void saveDToFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Set File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        //File selectedFile = fileChooser.showOpenDialog();
        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null)
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            if (cipher != null) {
                writer.write(cipher.getDecryptedText());
                writer.close();
            }
        }
    }


    public void saveEToFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Set File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        //File selectedFile = fileChooser.showOpenDialog();
        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null)
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            if (cipher != null) {
                writer.write(cipher.getEncryptedText());
                writer.close();
            }
        }
    }

    public void setTextAreas(String text1, String text2){
        textArea1.setText(text1);
        textArea2.setText(text2);
    }

    public void getCRC(ActionEvent actionEvent) {
        textArea1.clear();
        textArea2.clear();
        Byte f1 = Byte.parseByte(textField1.getText());
        Byte f2 = Byte.parseByte(textField2.getText());
        Byte f3 = Byte.parseByte(textField3.getText());
        Byte f4 = Byte.parseByte(textField4.getText());
        Byte f5 = Byte.parseByte(textField5.getText());
        Byte f6 = Byte.parseByte(textField6.getText());
        Byte f7 = Byte.parseByte(textField7.getText());
        Byte f8 = Byte.parseByte(textField8.getText());
        byte[] bytes = {f8, f7, f6, f5, f4, f3, f2, f1};
        CRChash hasher = new CRChash(bytes);
        hasher.setParentFunc(textField9.getText());
        textField11.setText(hasher.getCRC());
        Map<String, String> pairs = hasher.getCollision();
        Map<String, Integer> counts = hasher.counts;
        for (Map.Entry<String, Integer> pair : counts.entrySet()){
            textArea1.appendText(pair.getKey() + " " + pair.getValue() + "\n");
        }
        for (Map.Entry<String, String> pair : pairs.entrySet()) {
            textArea2.appendText(pair.getKey() + " " + pair.getValue() + "\n");
        }
    }
}
