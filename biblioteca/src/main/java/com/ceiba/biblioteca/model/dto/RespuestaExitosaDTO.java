package com.ceiba.biblioteca.model.dto;

public class RespuestaExitosaDTO {

    private String message;


    public RespuestaExitosaDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
