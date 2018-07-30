package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller {
    @FXML private TextArea textArea;
    @FXML private Button button;
    @FXML private Label outputLabel;
    @FXML private TextField textField;

    private Connection connection;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mainacad1", "eugeny", "123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonClick(ActionEvent actionEvent) {
        String text = textField.getText();
        outputLabel.setText("Hello, " + text);
    }

    public void hello() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", ButtonType.NO, ButtonType.YES );
        alert.setContentText("Здесь был " + textField.getText());
        alert.setTitle("Info");
        alert.setHeaderText("Удалить его?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.YES)) {
            String text = queryToDB();
            textArea.setText(text);
        }
    }

    private String queryToDB() {
        StringBuilder builder = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from Student");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                builder.append(String.format("%3d|%-30s|%-50s|%3d\n", id, name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return builder.toString();
        }
    }

    public void checkText() {
        button.setDisable(textField.getText().isEmpty());
    }

}
