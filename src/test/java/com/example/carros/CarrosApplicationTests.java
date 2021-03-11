package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarrosApplicationTests {

	@Autowired
	private CarroService service;

	@Test
	public void test1() {

		Carro carro = new Carro();

		carro.setNome("Ferrari");
		carro.setTipo("Esportivos");

		CarroDTO c = service.insert(carro);

		assertNotNull(c);
		Long id = c.getId();
		assertNotNull(id);

//		buscar o objeto
		Optional<CarroDTO> op = service.getCarrosById(id);
		assertTrue(op.isPresent());

		op.get();
		assertEquals("Ferrari", c.getNome());
		assertEquals("Esportivos", c.getTipo());

//		Deletar o objeto
		service.deleteCar(id);

//		Verificar se deletou
		assertFalse(service.getCarrosById(id).isPresent());

	}
	@Test
	public void testLista(){
		List<CarroDTO> carros = service.getCarros();

		assertEquals(30, carros.size());
	}
	@Test
	public void testGet() {

		Optional<CarroDTO> op = service.getCarrosById(11L);
		assertTrue(op.isPresent());

		CarroDTO c = op.get();
		assertEquals("Ferrari FF", c.getNome());
	}
	@Test
	public void testListByTipo(){
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());
	}

}
