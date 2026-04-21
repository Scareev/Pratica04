package dao;

import factory.ConnectionFactory;
import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // INSERT
    public void cadastrar(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, data_nascimento, telefone, endereco, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setDate(3, Date.valueOf(c.getDataNascimento()));
            ps.setString(4, c.getTelefone());
            ps.setString(5, c.getEndereco());
            ps.setString(6, c.getBairro());
            ps.setString(7, c.getCidade());
            ps.setString(8, c.getEstado());
            ps.setString(9, c.getCep());
            ps.executeUpdate();
        }
    }

    // SELECT ALL
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }
    // UPDATE
    public void alterar(Cliente c) throws SQLException {
        String sql = "UPDATE cliente SET nome=?, cpf=?, data_nascimento=?, telefone=?, endereco=?, bairro=?, cidade=?, estado=?, cep=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setDate(3, Date.valueOf(c.getDataNascimento()));
            ps.setString(4, c.getTelefone());
            ps.setString(5, c.getEndereco());
            ps.setString(6, c.getBairro());
            ps.setString(7, c.getCidade());
            ps.setString(8, c.getEstado());
            ps.setString(9, c.getCep());
            ps.setInt(10, c.getId());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // MAPEAR ResultSet -> Cliente
    // private pq só vai ser usado AQUI
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        c.setTelefone(rs.getString("telefone"));
        c.setEndereco(rs.getString("endereco"));
        c.setBairro(rs.getString("bairro"));
        c.setCidade(rs.getString("cidade"));
        c.setEstado(rs.getString("estado"));
        c.setCep(rs.getString("cep"));
        return c;
    }
}