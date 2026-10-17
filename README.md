# 📅 Sistema de Agendamentos Web

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)

Um sistema completo de gestão de agendamentos full-stack, construído de forma **100% nativa**. Sem frameworks pesadas no Back-End, apenas o poder do Java puro a comunicar com o MySQL de forma eficiente! 🚀

---

## ✨ Funcionalidades em Destaque

*   **Interface Dinâmica e Intuitiva:** Dashboard limpo para o registo de clientes e serviços em tempo real.
*   **API RESTful Nativa:** Back-End construído do zero utilizando o pacote `com.sun.net.httpserver`.
*   **Persistência de Dados Robusta:** Padrão DAO (Data Access Object) bem estruturado.
*   **Relacionamento Inteligente:** Base de dados MySQL com tabelas interligadas por Chaves Estrangeiras e IDs gerados automaticamente (`AUTO_INCREMENT`).

---

## 🛠️ Tecnologias Utilizadas

**Front-End**
*   HTML5 & CSS3
*   JavaScript Vanilla (comunicação com o servidor via Fetch API)

**Back-End**
*   Java (Nativo)
*   JDBC (Java Database Connectivity)

**Base de Dados**
*   MySQL

---

## 🚀 Como Executar o Projeto Localmente

### 1. Pré-requisitos
*   Java JDK instalado no computador.
*   MySQL Server (ou XAMPP) a correr localmente.
*   Git instalado.

### 2. Configurar a Base de Dados
Abra o seu gestor de MySQL (Workbench, phpMyAdmin ou extensão do VS Code) e execute o seguinte script para preparar o ambiente:

```sql
CREATE DATABASE sistema_agendamento;
USE sistema_agendamento;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE agendamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data VARCHAR(20) NOT NULL,
    hora VARCHAR(20) NOT NULL,
    servico VARCHAR(100) NOT NULL,
    cliente_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
3. Configurar as Credenciais
No ficheiro ConexaoBanco.java, certifique-se de que o utilizador e a palavra-passe correspondem à sua instalação local do MySQL:

Java
private static final String USUARIO = "root";
private static final String SENHA = "sua_palavra_passe"; // Altere aqui para a sua senha real
### 4. Arrancar os Motores!
1. Clone este repositório no seu terminal:
   ```bash
   git clone [https://github.com/RomiltonPRO/sistema-agendamento-java.git](https://github.com/RomiltonPRO/sistema-agendamento-java.git)
Abra a pasta do projeto no VS Code (ou na sua IDE preferida).

Execute o ficheiro principal ServidorWeb.java.

Assim que o terminal mostrar a mensagem verde de sucesso, abra o ficheiro .html principal (o seu Dashboard) no navegador e comece a testar!

💡 Próximos Passos (Roadmap)
[ ] Implementar botões para edição e eliminação de agendamentos.

[ ] Adicionar sistema de login e controlo de acessos.

[ ] Melhorar o design responsivo para ecrãs de telemóvel.

Desenvolvido com muita dedicação, café e código nativo! ☕


*(Se por acaso estavas a referir-te ao código Java de algum dos ficheiros, como o `AgendamentoDAO.java` ou `ServidorWeb.java`, diz-me qual deles precisas e eu envio o código final completo desse ficheiro!)*
