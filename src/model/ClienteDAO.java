package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (id, nome, email, telefone) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            
            stmt.executeUpdate();
            System.out.println("💾 Cliente salvo no Banco de Dados MySQL com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar cliente no banco: " + e.getMessage());
        }
    }

    
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
