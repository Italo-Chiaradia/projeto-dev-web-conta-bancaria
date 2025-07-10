/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conta.bancaria.model;

import java.util.List;

/**
 *
 * @author italo
 */
public class Pagina<T> {
    private final List<T> conteudo;
    private final int paginaAtual;
    private final int totalPaginas;
    private final long totalDeRegistros;

    public Pagina(List<T> conteudo, int paginaAtual, int totalPaginas, long totalDeRegistros) {
        this.conteudo = conteudo;
        this.paginaAtual = paginaAtual;
        this.totalPaginas = totalPaginas;
        this.totalDeRegistros = totalDeRegistros;
    }

    public List<T> getConteudo() {
        return conteudo;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public long getTotalDeRegistros() {
        return totalDeRegistros;
    }
    
    
}
