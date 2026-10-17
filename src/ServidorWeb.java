import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import model.Agendamento;
import model.AgendamentoDAO;
import model.Cliente;
import model.ClienteDAO;

public class ServidorWeb {
    public static void main(String[] args) throws Exception {
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/agendamentos", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
               
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                String metodo = exchange.getRequestMethod();

                if ("OPTIONS".equals(metodo)) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                if ("GET".equals(metodo)) {
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                    try {
                        AgendamentoDAO dao = new AgendamentoDAO();
                        java.util.List<Agendamento> lista = dao.listarTodos();

                        StringBuilder jsonBuilder = new StringBuilder();
                        jsonBuilder.append("[\n");

                        for (int i = 0; i < lista.size(); i++) {
                            Agendamento ag = lista.get(i);
                            
                            jsonBuilder.append("  {\n");
                            jsonBuilder.append("    \"nome\": \"").append(ag.getCliente().getNome()).append("\",\n");
                            jsonBuilder.append("    \"servico\": \"").append(ag.getServico()).append("\",\n");
                            jsonBuilder.append("    \"data\": \"").append(ag.getData()).append("\",\n");
                            jsonBuilder.append("    \"horario\": \"").append(ag.getHora()).append("\",\n");
                            jsonBuilder.append("    \"status\": \"Confirmado\"\n");
                            jsonBuilder.append("  }");
                            
                            if (i < lista.size() - 1) {
                                jsonBuilder.append(",\n");
                            } else {
                                jsonBuilder.append("\n");
                            }
                        }
                        jsonBuilder.append("]");

                        String jsonResposta = jsonBuilder.toString();
                        
                        byte[] respostaBytes = jsonResposta.getBytes("UTF-8");
                        exchange.sendResponseHeaders(200, respostaBytes.length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(respostaBytes);
                        os.close();

                    } catch (Exception e) {
                        System.out.println("Erro ao buscar dados do banco: " + e.getMessage());
                        String erroMsg = "[]"; 
                        exchange.sendResponseHeaders(500, erroMsg.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(erroMsg.getBytes());
                        os.close();
                    }
                }

                if ("POST".equals(metodo)) {
                    try {
                        InputStream is = exchange.getRequestBody();
                        String corpoRequisicao = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                        String nome = extrairValorJSON(corpoRequisicao, "nome");
                        String servico = extrairValorJSON(corpoRequisicao, "servico");
                        String data = extrairValorJSON(corpoRequisicao, "data");
                        String horario = extrairValorJSON(corpoRequisicao, "horario");

                        // 1. Prepara e salva o Cliente PRIMEIRO
                        Cliente novoCliente = new Cliente(0, nome, "", "");
                        ClienteDAO clienteDao = new ClienteDAO();
                        int idGerado = clienteDao.salvar(novoCliente);

                        // 2. Recria o Cliente, mas agora com o ID real que veio do banco
                        Cliente clienteComId = new Cliente(idGerado, nome, "", "");

                        // 3. Prepara e salva o Agendamento vinculando ao Cliente correto
                        Agendamento novoAgendamento = new Agendamento(0, data, horario, servico, clienteComId);
                        AgendamentoDAO agendamentoDao = new AgendamentoDAO();
                        agendamentoDao.salvar(novoAgendamento);

                        String resposta = "{\"mensagem\": \"Agendamento criado com sucesso!\"}";
                        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                        exchange.sendResponseHeaders(201, resposta.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(resposta.getBytes("UTF-8"));
                        os.close();
                        
                        System.out.println("Novo agendamento gravado via site: " + nome);

                    } catch (Exception e) {
                        System.out.println("Erro ao gravar agendamento: " + e.getMessage());
                        String erroMsg = "{\"erro\": \"Falha ao gravar no banco de dados\"}"; 
                        exchange.sendResponseHeaders(500, erroMsg.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(erroMsg.getBytes());
                        os.close();
                    }
                }
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor Web rodando com MySQL! O Back-End está online.");
    }

    private static String extrairValorJSON(String json, String chave) {
        String padrao = "\"" + chave + "\":\"";
        int inicio = json.indexOf(padrao);
        if (inicio == -1) {
            padrao = "\"" + chave + "\": \"";
            inicio = json.indexOf(padrao);
        }
        if (inicio == -1) return "";
        
        inicio += padrao.length();
        int fim = json.indexOf("\"", inicio);
        return json.substring(inicio, fim);
    }
}