package gui;

import dao.ClienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;

public class AlterarController {

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtDataNascimento;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtBairro;
    @FXML private TextField txtCidade;
    @FXML private TextField txtEstado;
    @FXML private TextField txtCep;

    private Cliente c;
    private final ClienteDAO dao = new ClienteDAO();

    public void setCliente(Cliente c) {
        this.c = c;

        txtId.setText(String.valueOf(c.getId()));
        txtNome.setText(c.getNome());
        txtCpf.setText(c.getCpf());
        txtDataNascimento.setText(c.getDataNascimento().toString());
        txtTelefone.setText(c.getTelefone());
        txtEndereco.setText(c.getEndereco());
        txtBairro.setText(c.getBairro());
        txtCidade.setText(c.getCidade());
        txtEstado.setText(c.getEstado());
        txtCep.setText(c.getCep());
    }

    @FXML
    public void salvar(ActionEvent event) {
        try {
            c.setNome(txtNome.getText());
            c.setCpf(txtCpf.getText());
            c.setDataNascimento(LocalDate.parse(txtDataNascimento.getText()));
            c.setTelefone(txtTelefone.getText());
            c.setEndereco(txtEndereco.getText());
            c.setBairro(txtBairro.getText());
            c.setCidade(txtCidade.getText());
            c.setEstado(txtEstado.getText());
            c.setCep(txtCep.getText());

            dao.alterar(c);

            mostrarAlerta("Cliente atualizado com sucesso!", Alert.AlertType.INFORMATION);
            fecharJanela();

        } catch (SQLException e) {
            mostrarAlerta("Erro ao atualizar: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Erro nos dados (verifique a data yyyy-MM-dd)", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}