package com.example.carros.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public Iterable<Carro> getCarros() {
		return rep.findAll();
	}
	
	public Optional<Carro> getCarrosById(Long id) {
		return rep.findById(id);
	}
	
	public Iterable<Carro> getCarrosByTipo(String tipo) {
		return rep.getCarrosByTipo(tipo);
	}

	public Carro insert(Carro carro) {
		return rep.save(carro);
		
	}

	public Carro update(Carro carro, long id) {
		
		Assert.notNull(id, "Não foi possivel atualizar o registro");
		
		//busca o carro no banco de dados
		Optional<Carro> optional = getCarrosById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			//copiar as propiedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id "+ db.getId());
			
			//Atualiza o carro
			rep.save(db);
			
			return db;
			
		} else {
			throw  new RuntimeException("não foi possivel atualizar o registro");
		}
		
	}

	public void deleteCar(long id) {
		Optional<Carro> carro = getCarrosById(id);
		if(carro.isPresent()) {
			rep.deleteById(id);
		}
		
	}

}
