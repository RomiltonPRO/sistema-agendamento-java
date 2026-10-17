package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    // Método atualizado para retornar o ID gerado (int) em vez de void
    public int salvar(Cliente cliente) {
        // Removemos o 'id' da instrução SQL para deixar o MySQL gerar automaticamente
        String sql = "INSERT INTO cliente (nome, email, telefone) VALUES (?, ?, ?)";
        
        // Adicionamos o comando RETURN_GENERATED_KEYS para capturar o ID criado
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            
            stmt.executeUpdate();
            System.out.println("💾 Cliente salvo no Banco de Dados MySQL com sucesso!");
            
            // Pega o ID que o MySQL acabou de gerar e devolve para o sistema
            try (java.sql.ResultSet chavesGeradas = stmt.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    return chavesGeradas.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar cliente no banco: " + e.getMessage());
        }
        return 0; // Retorna 0 caso dê erro
    }

    // Mantemos o método buscarPorId intacto
    public Cliente buscarPorId(int idBuscado) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idBuscado);
            
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar cliente: " + e.getMessage());
        }
        return null; 
    }
}