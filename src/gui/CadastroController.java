package gui;

import dao.ClienteDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Cliente;

import java.sql.SQLException;

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private DatePicker dataNascimentoPicker;
    @FXML private TextField telefoneField;
    @FXML private TextField enderecoField;
    @FXML private TextField bairroField;
    @FXML private TextField cidadeField;
    @FXML private TextField estadoField;
    @FXML private TextField cepField;

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        limitarCaracteres(cpfField, 14);   // 000.000.000-00
        limitarCaracteres(cepField, 9);    // 00000-000
        limitarCaracteres(estadoField, 2); // SP
        limitarCaracteres(telefoneField, 15);
    }

    private void limitarCaracteres(TextField field, int max) {
        field.textProperty().addListener((obs, antigo, novo) -> {
            if (novo.length() > max) {
                field.setText(antigo);
            }
        });
    }

    @FXML
    private void onCadastrar() {
        if (!validarCampos()) return;

        Cliente c = new Cliente();
        c.setNome(nomeField.getText().trim());
        c.setCpf(cpfField.getText().trim());
        c.setDataNascimento(dataNascimentoPicker.getValue());
        c.setTelefone(telefoneField.getText().trim());
        c.setEndereco(enderecoField.getText().trim());
        c.setBairro(bairroField.getText().trim());
        c.setCidade(cidadeField.getText().trim());
        c.setEstado(estadoField.getText().trim());
        c.setCep(cepField.getText().trim());

        try {
            clienteDAO.cadastrar(c);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Cliente cadastrado com sucesso!");
            limparCampos();
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        return campoVazio(nomeField, "Nome") &&
                campoVazio(cpfField, "CPF") &&
                campoVazio(telefoneField, "Telefone") &&
                campoVazio(enderecoField, "Endereço") &&
                campoVazio(bairroField, "Bairro") &&
                campoVazio(cidadeField, "Cidade") &&
                campoVazio(estadoField, "Estado") &&
                campoVazio(cepField, "CEP") &&
                dataNascimentoPicker.getValue() != null;
    }

    private boolean campoVazio(TextField field, String nome) {
        if (field.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "O campo " + nome + " é obrigatório.");
            field.requestFocus();
            return false;
        }
        return true;
    }

    @FXML
    private void onIrParaAlterar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/consulta.fxml"));
            Stage stage = (Stage) nomeField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir tela de alteração: " + e.getMessage());
        }
    }

    @FXML
    private void onVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) nomeField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao voltar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.clear();
        cpfField.clear();
        dataNascimentoPicker.setValue(null);
        telefoneField.clear();
        enderecoField.clear();
        bairroField.clear();
        cidadeField.clear();
        estadoField.clear();
        cepField.clear();
        nomeField.requestFocus();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}