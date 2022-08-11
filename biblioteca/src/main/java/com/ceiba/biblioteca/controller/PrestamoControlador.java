package com.ceiba.biblioteca.controller;


import com.ceiba.biblioteca.model.dto.RegistroDTO;
import com.ceiba.biblioteca.model.dto.RespuestaDTO;
import com.ceiba.biblioteca.model.repository.PrestamoRepository;
import com.ceiba.biblioteca.model.service.IPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prestamo")
public class PrestamoControlador {

    @Autowired
    IPrestamoService iPrestamoService;

    @Autowired
    PrestamoRepository prestamoRepository;

    @GetMapping()
    public int totalPrestado (){
        return iPrestamoService.countPrestados();
    }

    @PostMapping
    @ResponseBody
    public RespuestaDTO registrarPrestamo(@RequestBody RegistroDTO registroDto){
        return iPrestamoService.guardarPrestamo(registroDto);
    }
}

