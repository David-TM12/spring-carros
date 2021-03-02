package com.example.carros.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros() {
		List<Carro> carro = rep.findAll();

		List<CarroDTO> list = carro.stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());

//		List<CarroDTO> list = new ArrayList<>();
//		for(Carro c : carros){
//			list.add(new CarroDTO(c));
//		}
		return list;
	}
	
	public Optional<CarroDTO> getCarrosById(Long id) {
		return rep.findById(id).map(c -> CarroDTO.create(c));
	}
	
	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo).stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());
	}

	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não é possível inserir o registro");

		return CarroDTO.create(rep.save(carro));
		
	}

	public CarroDTO update(Carro carro, long id) {
		Assert.notNull(id, "Não foi possivel atualizar o registro");

		//busca o carro no banco de dados
		Optional<Carro> optional = rep.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			//copiar as propiedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id "+ db.getId());

			//Atualiza o carro
			rep.save(db);

			return CarroDTO.create(db);

		} else {
			return null;
//			throw  new RuntimeException("não foi possivel atualizar o registro");
		}
		
	}

	public Boolean deleteCar(long id) {

		Optional<CarroDTO> carro = getCarrosById(id);
		if(carro.isPresent()) {
			rep.deleteById(id);
			return true;
		}
		return false;
		
	}

}
