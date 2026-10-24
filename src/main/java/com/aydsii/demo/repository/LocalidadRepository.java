package com.aydsii.demo.repository;

import com.aydsii.demo.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

    Optional<Localidad> findByNombreLocalidadIgnoreCase(String nombreLocalidad);
}