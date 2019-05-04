package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ChoiceBox algorithmBox;
    @FXML
    Button encode;
    @FXML
    TextField encodedInput;
    @FXML
    Label binaryCode, hexCode;
    @FXML
    TextField input, output;
    @FXML
    Text feedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorithmBox.getItems().setAll(Algorithm.values());
        algorithmBox.getSelectionModel().selectFirst();

    }

    public void encode() {

        Algorithm algorithm = Algorithm.valueOf(String.valueOf(algorithmBox.getValue()));
        Encodable encoder = new AlgorithmFactory().createAlgorithm(algorithm);

        String inputASCI = input.getText();
        String CRCbinary = "0";
        if (inputASCI != null)
            CRCbinary = encoder.encode(inputASCI);

        String message = converToBinary(inputASCI) + CRCbinary;

        encodedInput.setText(message);
        binaryCode.setText(CRCbinary);
        hexCode.setText(Long.toString(Long.parseLong(CRCbinary, 2), 16).toUpperCase());

    }


    public void decode() {

        Algorithm algorithm = Algorithm.valueOf(String.valueOf(algorithmBox.getValue()));
        Encodable encoder = new AlgorithmFactory().createAlgorithm(algorithm);

        String message = encodedInput.getText();
        String CRC = encoder.extractCode(message);
        String data = convertToString(encoder.extractData(message));
        boolean correct = encoder.decode(data, CRC);
        feedback.setFont(new Font(18));
        if (correct) {
            feedback.setText("Nie wykryto błędów");
            feedback.setFill(Color.GREEN);
        } else {
            feedback.setText("Podczas transmisji wystąpiły przekłamania, bity zostały zmienione");
            feedback.setFill(Color.RED);
        }

        System.out.println("DATA " + data + " CRC " + CRC + " MESSAGE " + message);
        System.out.println(correct);

        output.setText(data);

    }

    static String converToBinary(String text) {
        byte[] stringTab = text.getBytes();
        StringBuilder binaryText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            binaryText.append(String.format("%8s", Integer.toBinaryString(stringTab[i])).replace(' ', '0'));
        }
        return binaryText.toString();
    }

    static String convertToString(String binaryString) {
        byte[] bval = new BigInteger(binaryString, 2).toByteArray();
        return new String(bval);

    }
}
