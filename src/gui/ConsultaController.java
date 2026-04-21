package gui;

import dao.ClienteDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ConsultaController {

    @FXML private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colCpf;
    @FXML private TableColumn<Cliente, String> colDataNasc; // Data de Nascimento
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEndereco;
    @FXML private TableColumn<Cliente, String> colBairro;
    @FXML private TableColumn<Cliente, String> colCidade;
    @FXML private TableColumn<Cliente, String> colEstado;
    @FXML private TableColumn<Cliente, String> colCEP;


    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCEP.setCellValueFactory(new PropertyValueFactory<>("cep"));
        carregarClientes();
    }

    private void carregarClientes() {
        try {
            List<Cliente> lista = clienteDAO.listarTodos();
            tabelaClientes.setItems(FXCollections.observableArrayList(lista));
        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    @FXML
    private void onAlterar() {
        Cliente c = tabelaClientes.getSelectionModel().getSelectedItem();
        if (c == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Alterar.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            loader.<AlterarController>getController().setCliente(c);

            stage.showAndWait();
            carregarClientes();

        } catch (Exception e) {
            mostrarAlerta("Erro ao abrir tela.");
        }
    }

    @FXML
    private void onExcluir() {
        Cliente c = tabelaClientes.getSelectionModel().getSelectedItem();
        if (c == null) return;

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este cliente?");

        ButtonType resposta = confirmacao.showAndWait().orElse(ButtonType.CANCEL);

        if (resposta == ButtonType.OK) {
            try {
                clienteDAO.excluir(c.getId());
                carregarClientes();
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir.");
            }
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}