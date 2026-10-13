package model;

public class Agendamento {

    private int id;
    private String data;
    private String hora;
    private String servico;
    private Cliente cliente;

    public Agendamento(int id, String data, String hora, String servico, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.servico = servico;
        this.cliente = cliente;
    }

     public int getId() {
        return this.id;
    }

    public String getData() {
        return this.data;
    }

    public String getHora() {
        return this.hora;
    }

    public String getServico() {
        return this.servico;
    }

    public Cliente getCliente() {
        return this.cliente;
    }



}