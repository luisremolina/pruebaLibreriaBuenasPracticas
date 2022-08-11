package com.ceiba.biblioteca.model.repository;

import com.ceiba.biblioteca.model.entity.PrestamoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoEntity, Integer> {

}
