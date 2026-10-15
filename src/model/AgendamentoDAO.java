package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    // Método que recebe o agendamento e salva no MySQL
    public void salvar(Agendamento agendamento) {
        String sql = "INSERT INTO agendamento (id, data, hora, servico, cliente_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, agendamento.getId());
            stmt.setString(2, agendamento.getData());
            stmt.setString(3, agendamento.getHora());
            stmt.setString(4, agendamento.getServico());
            stmt.setInt(5, agendamento.getCliente().getId());
            
            stmt.executeUpdate();
            System.out.println("💾 Agendamento saved no Banco de Dados MySQL com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar agendamento no banco: " + e.getMessage());
        }
    }

    // Método que vai no MySQL buscar TODOS os agendamentos cadastrados
    public List<Agendamento> listarTodos() {
        List<Agendamento> lista = new ArrayList<>();
        
        String sql = "SELECT a.id AS ag_id, a.data, a.hora, a.servico, " +
                     "c.id AS cli_id, c.nome, c.email, c.telefone " +
                     "FROM agendamento a " +
                     "INNER JOIN cliente c ON a.cliente_id = c.id";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cli = new Cliente(
                    rs.getInt("cli_id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );

                Agendamento ag = new Agendamento(
                    rs.getInt("ag_id"),
                    rs.getString("data"),
                    rs.getString("hora"),
                    rs.getString("servico"),
                    cli
                );

                lista.add(ag);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar agendamentos: " + e.getMessage());
        }
        return lista;
    }
}
