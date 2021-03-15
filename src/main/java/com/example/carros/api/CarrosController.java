package com.example.carros.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
	
	@Autowired
	private CarroService service;
	
	@GetMapping
	public ResponseEntity get() {
		return ResponseEntity.ok(service.getCarros());
//		return new ResponseEntity<>(service.getCarros(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity getCarroById(@PathVariable("id") Long id) {

		Optional<CarroDTO> carro = service.getCarrosById(id);

		return carro.map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());

//		if(carro.isPresent()){
//			return ResponseEntity.ok(carro.get());
//		}else {
//			return ResponseEntity.notFound().build();
//		}

//		return service.getCarrosById(id);
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity getCarByTipo(@PathVariable("tipo") String tipo) {

		List<CarroDTO> carros = service.getCarrosByTipo(tipo);

		return carros.isEmpty() ?
			ResponseEntity.noContent().build():
				ResponseEntity.ok(carros);

	}
	
	@PostMapping
	public ResponseEntity post(@RequestBody Carro carro) {

			CarroDTO c = service.insert(carro);
			URI location = getUri(c.getId());
			return ResponseEntity.created(location).build();

	}

	private URI getUri(Long id){
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity put(@PathVariable("id") long id, @RequestBody Carro carro) {

		carro.setId(id);

		CarroDTO c = service.update(carro, id);

		return c != null ?
				ResponseEntity.ok(c) :
				ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity del(@PathVariable("id") long id) {
		
		service.deleteCar(id);
		return ResponseEntity.ok().build();
	}
	
}
