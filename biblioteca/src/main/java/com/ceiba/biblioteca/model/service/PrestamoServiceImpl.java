package com.ceiba.biblioteca.model.service;

import com.ceiba.biblioteca.model.dto.RegistroDTO;
import com.ceiba.biblioteca.model.dto.RespuestaDTO;
import com.ceiba.biblioteca.model.entity.PrestamoEntity;
import com.ceiba.biblioteca.model.repository.PrestamoRepository;
import com.ceiba.biblioteca.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PrestamoServiceImpl implements  IPrestamoService{

    @Autowired
    PrestamoRepository prestamoRepository;

    @Override
    public RespuestaDTO guardarPrestamo(RegistroDTO registroDTO) {
        int tipoUsuario = registroDTO.getTipoUsuario();
        System.out.println("Tipo de usuario" + tipoUsuario);
        String idUser = registroDTO.getIdentificacionUsuario();
        Calendar calendar = Calendar.getInstance();
        if(tipoUsuario > 3 || tipoUsuario < 1){
//            return "Tipo de usuario no permitido en la biblioteca";
            System.out.println("Tipo de usuario no permitido en la biblioteca");
            return new RespuestaDTO(3, LocalDate.now());
        }
        if(tipoUsuario == 3){
            if(findByIdPrestamo(idUser)){
                System.out.println("Este usuario ya tiene  1 libro prestado prestamos activos");
                return new RespuestaDTO(3, LocalDate.now());
            }
        }
        System.out.println("Antes de calendario");
        if (CalendarUtil.isWeekend(calendar))
            calendar.add(Calendar.DAY_OF_WEEK, 2);

        if(tipoUsuario == 1){
            calendar.add(Calendar.DAY_OF_WEEK, 10);
        }else if(tipoUsuario == 2){
            calendar.add(Calendar.DAY_OF_WEEK, 8);
        }else {
            calendar.add(Calendar.DAY_OF_WEEK, 7);
        }

//        System.out.println("fecha" + );
        PrestamoEntity responseData = new PrestamoEntity(
                registroDTO.getIdentificacionUsuario(),
                registroDTO.getIsbn(),registroDTO.getTipoUsuario(),
                LocalDate.now(),
                CalendarUtil.getLocalDate(calendar)
        );

        prestamoRepository.save(responseData);
        Optional<PrestamoEntity> pres = prestamoRepository.findById(responseData.getIdPrestamo());

        RespuestaDTO response = new RespuestaDTO(pres.get().getIdPrestamo(), pres.get().getFechaDevolucion());
        return response;

    }

    @Override
    public boolean findByIdPrestamo(String idUsuario) {

        List<PrestamoEntity> lista = new ArrayList<>();
        lista = prestamoRepository.findAll();

        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getIdUsuario() == idUsuario){
                return true;
            }
        }
       return false;
    }

    @Override
    public int countPrestados() {
        List<PrestamoEntity> lista = new ArrayList<PrestamoEntity>();
        lista = prestamoRepository.findAll();
        return lista.size();
    }


}
