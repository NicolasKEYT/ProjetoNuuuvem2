package com.seuprojeto.lambda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filme {
    private int id;
    private String titulo;
    private int ano;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
}
