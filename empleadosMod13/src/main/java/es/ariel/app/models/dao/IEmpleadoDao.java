package es.ariel.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.ariel.app.models.entity.Empleado;

public interface IEmpleadoDao extends PagingAndSortingRepository<Empleado, Long> {

}
