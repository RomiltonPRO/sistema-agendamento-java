// Assim que a página carregar, verifica o que precisa rodar
document.addEventListener("DOMContentLoaded", () => {
    // Se existir a tabela na página atual, carrega os dados
    if (document.getElementById("tabelaAgendamentos")) {
        carregarAgendamentos();
    }

    // Se existir o formulário na página atual, ativa o escutador de envio
    if (document.getElementById("agendamentoForm")) {
        configurarFormulario();
    }
});

// 📊 FUNÇÃO 1: Carrega os dados na tabela do Dashboard
async function carregarAgendamentos() {
    console.log("Buscando agendamentos salvos no banco de dados...");

    try {
        // Mock dos dados que virão das suas classes DAO do Java
        const agendamentosFakes = [
            { id: 1, cliente: "Carlos Lima", servico: "Consultoria Geral", data: "27/10/2026", hora: "10:00 - 11:00", status: "Confirmado" },
            { id: 2, cliente: "Daniela Castro", servico: "Manutenção Preventiva", data: "28/10/2026", hora: "14:00 - 15:00", status: "Pendente" },
            { id: 3, cliente: "Eduardo Costa", servico: "Atendimento Padrão", data: "29/10/2026", hora: "16:00 - 17:00", status: "Confirmado" }
        ];

        const tabela = document.getElementById("tabelaAgendamentos");
        tabela.innerHTML = ""; 

        agendamentosFakes.forEach(agendamento => {
            const classeStatus = agendamento.status === "Confirmado" ? "confirmado" : "pendente";
            
            const linha = `
                <tr>
                    <td>${agendamento.cliente}</td>
                    <td>${agendamento.servico}</td>
                    <td>${agendamento.data}</td>
                    <td>${agendamento.hora}</td>
                    <td><span class="status-badge ${classeStatus}">${agendamento.status}</span></td>
                    <td>
                        <button class="btn-action edit" onclick="editar(${agendamento.id})">✏️</button>
                        <button class="btn-action delete" onclick="excluir(${agendamento.id})">🗑️</button>
                    </td>
                </tr>
            `;
            tabela.innerHTML += linha;
        });

    } catch (error) {
        console.error("Erro ao buscar dados do servidor Java:", error);
    }
}

// ➕ FUNÇÃO 2: Captura os dados digitados na tela de Novo Agendamento
function configurarFormulario() {
    document.getElementById("agendamentoForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Impede o navegador de recarregar a página

        // Capturando os valores dos inputs do HTML
        const nomeCliente = document.getElementById("clienteNome").value;
        const tipoServico = document.getElementById("servicoTipo").value;
        const dataEscolha = document.getElementById("dataAgendamento").value;
        const horaEscolha = document.getElementById("horaAgendamento").value;

        // Montando o objeto JSON (Padrão de transferência de dados)
        const novoAgendamento = {
            cliente: nomeCliente,
            servico: tipoServico,
            data: dataEscolha,
            hora: horaEscolha,
            status: "Pendente" // Todo agendamento novo começa como pendente
        };

        console.log("Objeto pronto para ser enviado ao Java/DAO:", novoAgendamento);

        // Exibe um alerta simulando o sucesso da operação
        alert(`Sucesso!\nAgendamento para "${nomeCliente}" capturado no Front-End.\nPróximo passo: Salvar no MySQL usando o Java.`);
        
        // Redireciona o usuário de volta para o Painel Geral
        window.location.href = "dashboard.html";
    });
}

// 🛠️ FUNÇÕES DE AÇÃO DA TABELA
function editar(id) {
    alert("Função Editar: Enviando comando para o Java alterar o ID " + id);
}

function excluir(id) {
    if(confirm("Deseja deletar este agendamento do MySQL?")) {
        alert("Função Excluir: Executando DAO.deletar() no Java para o ID " + id);
    }
}

// 🚪 LOGOUT (BOTÃO SAIR)
const btnLogout = document.getElementById('btnLogout');
if (btnLogout) {
    btnLogout.addEventListener('click', function(event) {
        event.preventDefault();
        if(confirm("Deseja realmente sair do sistema coringa?")) {
            window.location.href = "index.html";
        }
    });
}
