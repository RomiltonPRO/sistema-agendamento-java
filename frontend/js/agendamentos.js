// Aguarda a página carregar totalmente para buscar os dados
document.addEventListener("DOMContentLoaded", () => {
    carregarAgendamentos();
});

function carregarAgendamentos() {
    // Faz a requisição para a sua API Java (Back-End)
    fetch('http://localhost:8080/agendamentos')
        .then(resposta => resposta.json()) // Transforma a resposta em formato JSON
        .then(dados => {
            // Encontra a tabela no seu HTML
            const corpoTabela = document.getElementById('tabela-agendamentos');
            
            // Limpa a tabela antes de preencher
            corpoTabela.innerHTML = ''; 

            // Percorre a lista de dados que veio do banco e desenha as linhas
            dados.forEach(agendamento => {
                const linha = document.createElement('tr');
                
                // Monta o visual de cada linha com os dados reais
                linha.innerHTML = `
                    <td style="padding: 15px; border-bottom: 1px solid #eee;">${agendamento.nome}</td>
                    <td style="padding: 15px; border-bottom: 1px solid #eee;">${agendamento.servico}</td>
                    <td style="padding: 15px; border-bottom: 1px solid #eee;">${agendamento.data}</td>
                    <td style="padding: 15px; border-bottom: 1px solid #eee;">${agendamento.horario}</td>
                    <td style="padding: 15px; border-bottom: 1px solid #eee;">
                        <span style="background-color: #d1fae5; color: #065f46; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold;">
                            ${agendamento.status}
                        </span>
                    </td>
                    <td style="padding: 15px; border-bottom: 1px solid #eee;">✏️ 🗑️</td>
                `;
                
                // Adiciona a linha preenchida na tabela
                corpoTabela.appendChild(linha);
            });
        })
        .catch(erro => {
            console.error("Erro ao carregar dados da API:", erro);
        });
}