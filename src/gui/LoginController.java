package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;

    @FXML
    public void initialize() {
        limitarCaracteres(nomeField, 100);
        limitarCaracteres(cpfField, 14);
    }

    @FXML
    private void onLogin() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Preencha o nome e o CPF.");
            return;
        }

        navegarPara("/view/consulta.fxml");
    }

    @FXML
    private void onEsqueceuSenha() {
        navegarPara("/view/cadastro.fxml");
    }

    private void navegarPara(String caminho) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Stage stage = (Stage) nomeField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            mostrarAlerta("Erro ao navegar: " + e.getMessage());
        }
    }

    private void limitarCaracteres(TextField field, int max) {
        field.textProperty().addListener((obs, antigo, novo) -> {
            if (novo.length() > max) field.setText(antigo);
        });
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}