// Fica à escuta do momento em que o utilizador clica no botão "Salvar"
document.getElementById('form-agendamento').addEventListener('submit', function(evento) {
    // Impede a página de recarregar automaticamente
    evento.preventDefault();

    // Recolhe os valores que foram escritos nos campos de texto
    const novoAgendamento = {
        nome: document.getElementById('nome').value,
        servico: document.getElementById('servico').value,
        data: document.getElementById('data').value,
        horario: document.getElementById('horario').value,
        status: "Confirmado" // Definimos um estado padrão para os novos registos
    };

    // Envia os dados para a API Java através do método POST
    fetch('http://localhost:8080/agendamentos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(novoAgendamento) // Converte os dados para o formato JSON
    })
    .then(resposta => {
        if (resposta.ok) {
            alert("Agendamento registado com sucesso!");
            window.location.href = "dashboard.html"; // Redireciona de volta para a tabela
        } else {
            alert("Ocorreu um erro ao guardar. Verifica o Back-End.");
        }
    })
    .catch(erro => {
        console.error("Erro na comunicação:", erro);
        alert("Erro de ligação ao servidor Java.");
    });
});