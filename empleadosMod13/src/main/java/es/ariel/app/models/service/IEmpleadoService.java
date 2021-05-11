package es.ariel.app.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ariel.app.models.entity.Empleado;

public interface IEmpleadoService {

	public void save(Empleado Empleado);
	
	public Empleado findOne(Long id);
	
	public Page<Empleado> findAll(Pageable page);
	
	public void delete(Long id);		

	public List<Empleado> findAll();
	
}
