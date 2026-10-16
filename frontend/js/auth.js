document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    console.log("Tentativa de login com:", email);

    
    if (email === "admin@teste.com" && senha === "123456") {
        alert("Login realizado com sucesso!");
        window.location.href = "dashboard.html";
    } else {
        alert("E-mail ou senha incorretos! (Use admin@teste.com e 123456)");
    }
});
