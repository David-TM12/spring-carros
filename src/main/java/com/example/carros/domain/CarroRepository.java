package com.example.carros.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarroRepository extends JpaRepository<Carro, Long> {

	List<Carro> getCarrosByTipo(String tipo);

	List<Carro> findByTipo(String tipo);
}
