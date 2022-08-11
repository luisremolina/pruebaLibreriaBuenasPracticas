package com.ceiba.biblioteca.model.dto;

import lombok.Data;

import java.time.LocalDate;

//@Data
public class RespuestaDTO {

    private int id;
    private LocalDate fechaMaximaDeEntrega;


    public RespuestaDTO(int id, LocalDate fechaMaximaDeEntrega) {
        this.id = id;
        this.fechaMaximaDeEntrega = fechaMaximaDeEntrega;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaMaximaDeEntrega() {
        return fechaMaximaDeEntrega;
    }

    public void setFechaMaximaDeEntrega(LocalDate fechaMaximaDeEntrega) {
        this.fechaMaximaDeEntrega = fechaMaximaDeEntrega;
    }
}


