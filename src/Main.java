import model.Cliente;
import model.ClienteDAO;
import model.Agendamento;
import model.AgendamentoDAO;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);        
        int opcao = 0;

                while (opcao != 4) {
            System.out.println("==========================");
            System.out.println("||SISTEMA DE AGENDAMENTO||");
            System.out.println("==========================");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Criar Agendamento");
            System.out.println("3 - Listar Agendamentos");
            System.out.println("4 - Sair do Sistema");
            System.out.print("Escolha uma opção: ");
            opcao = teclado.nextInt();
            teclado.nextLine(); 

            switch (opcao) {

                case 1:
                    System.out.println("\n=== NOVO CADASTRO DE CLIENTE ===");
                    System.out.print("Digite o ID do cliente: ");
                    int idCli = teclado.nextInt();
                    teclado.nextLine(); 

                    System.out.print("Digite o nome: ");
                    String nomeCli = teclado.nextLine();

                    System.out.print("Digite o e-mail: ");
                    String emailCli = teclado.nextLine();

                    System.out.print("Digite o telefone: ");
                    String telefoneCli = teclado.nextLine();

                    
                    Cliente novoCliente = new Cliente(idCli, nomeCli, emailCli, telefoneCli);

                    ClienteDAO clienteDao = new ClienteDAO();
                    clienteDao.salvar(novoCliente);

                    break;


                case 2:
                    System.out.println("\n=== NOVO AGENDAMENTO DE SERVIÇO ===");
                    
                    System.out.print("Digite o ID do cliente que está agendando: ");
                    int idBuscado = teclado.nextInt();
                    teclado.nextLine(); 

                   
                    ClienteDAO clienteDaoBusca = new ClienteDAO();
                    Cliente clienteEncontrado = clienteDaoBusca.buscarPorId(idBuscado);

                    if (clienteEncontrado == null) {
                        System.out.println("❌ Erro: Nenhum cliente encontrado no banco com o ID " + idBuscado + "!");
                        break; 
                    }

               
                    System.out.println("👤 Cliente encontrado no MySQL: " + clienteEncontrado.getNome());
                    
                    System.out.print("Digite o ID do agendamento: ");
                    int idAgendamento = teclado.nextInt();
                    teclado.nextLine(); 

                    System.out.print("Digite a data (Ex: 20/10/2026): ");
                    String dataAgendamento = teclado.nextLine();

                    System.out.print("Digite o horário (Ex: 14:30): ");
                    String horaAgendamento = teclado.nextLine();

                    System.out.print("Digite o tipo de serviço: ");
                    String servicoAgendamento = teclado.nextLine();

                  
                    Agendamento novoAgendamento = new Agendamento(idAgendamento, dataAgendamento, horaAgendamento, servicoAgendamento, clienteEncontrado);

   
                    AgendamentoDAO agendamentoDao = new AgendamentoDAO();
                    agendamentoDao.salvar(novoAgendamento);

                    break;


                case 3:
                    System.out.println("\n=== LISTA DE AGENDAMENTOS CADASTRADOS (MYSQL) ===");

                    AgendamentoDAO agendamentoDaoListagem = new AgendamentoDAO();
                    java.util.List<Agendamento> todosAgendamentos = agendamentoDaoListagem.listarTodos();

                    if (todosAgendamentos.isEmpty()) {
                        System.out.println("⚠️ Nenhum agendamento realizado até o momento.");
                        break;
                    }

                    for (Agendamento ag : todosAgendamentos) {
                        System.out.println("\n-----------------------------------------");
                        System.out.println("ID Agendamento: " + ag.getId());
                        System.out.println("Serviço:        " + ag.getServico());
                        System.out.println("Data/Hora:      " + ag.getData() + " às " + ag.getHora());
                        System.out.println("Cliente:        " + ag.getCliente().getNome());
                        System.out.println("Contato:        " + ag.getCliente().getTelefone());
                        System.out.println("-----------------------------------------");
                    }
                    break;


                case 4:
                    System.out.println("\nSaindo do sistema... Obrigado!");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
                    break;
            } 
        } 


         teclado.close(); 
    }

}

