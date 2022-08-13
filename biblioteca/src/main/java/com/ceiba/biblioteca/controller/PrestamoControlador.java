package com.ceiba.biblioteca.controller;


import com.ceiba.biblioteca.model.dto.BuscarRegistroDTO;
import com.ceiba.biblioteca.model.dto.RegistroDTO;
import com.ceiba.biblioteca.model.repository.PrestamoRepository;
import com.ceiba.biblioteca.model.service.IPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;

@RestController
@RequestMapping("prestamo")
public class PrestamoControlador {

    @Autowired
    IPrestamoService iPrestamoService;

    //@Autowired
    //PrestamoRepository prestamoRepository;

    @GetMapping("/{id-prestamo}")
    public BuscarRegistroDTO buscarPrestamo (@PathVariable(name="id-prestamo") int idPrestamo) {
        return iPrestamoService.buscarPrestamo(idPrestamo);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<HashMap<String, String>> registrarPrestamo(@RequestBody RegistroDTO registroDto){
        return iPrestamoService.registrarPrestamo(registroDto);
    }
}

