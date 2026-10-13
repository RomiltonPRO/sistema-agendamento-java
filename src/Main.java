import model.Cliente;
import model.Agendamento;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Cliente> bancoClientes = new ArrayList<>();
        ArrayList<Agendamento> bancoAgendamentos = new ArrayList<>();
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
                    bancoClientes.add(novoCliente);

                    System.out.println("✅ Cliente cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.println("\n=== NOVO AGENDAMENTO DE SERVIÇO ===");
                    System.out.print("Digite o ID do cliente que está agendando: ");
                    int idBuscado = teclado.nextInt();
                    teclado.nextLine(); 

                    Cliente clienteEncontrado = null;
                    for (Cliente c : bancoClientes) {
                        if (c.getId() == idBuscado) {
                            clienteEncontrado = c;
                            break;
                        }
                    }

                    if (clienteEncontrado == null) {
                        System.out.println("❌ Erro: Nenhum cliente encontrado com este ID!");
                        break; 
                    }

                    System.out.println("👤 Cliente encontrado: " + clienteEncontrado.getNome());
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
                    bancoAgendamentos.add(novoAgendamento);

                    System.out.println("✅ Agendamento realizado com sucesso!");
                    break;

                case 3:
                    System.out.println("\n=== LISTA DE AGENDAMENTOS CADASTRADOS ===");
                    if (bancoAgendamentos.isEmpty()) {
                        System.out.println("⚠️ Nenhum agendamento realizado até o momento.");
                        break;
                    }

                    for (Agendamento ag : bancoAgendamentos) {
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

