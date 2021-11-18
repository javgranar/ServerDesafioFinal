package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;

import src.Status;
import src.StatusBuilder;
import src.StatusService;

@Component
@RestControllerEndpoint(id = "customEndpoint")
public class ServerController {
	
	@Autowired
	StatusService service;
	
	@Autowired
	StatusBuilder builder;
	
	private List<Status> listado = new ArrayList<>();
	
	private Integer counter = 0;
	
	@ReadOperation
	public String changeStatus() {
		counter++;
		builder.withStatus("Hola");
		Integer id = service.save(builder.build());
		return service.get(id).get().getStatus();
	}
	
	@ReadOperation
	public List<Status> estados(){
		listado = service.getAll();
		return listado;
	}
	
	@WriteOperation
	public void writeOperation(@Selector String newStatus) {
		builder.withStatus(newStatus);
		listado.add(builder.build());
		service.save(builder.build());
	}
	
	@DeleteOperation
	public void deleteOperation(@Selector Integer id) {
		Status s = service.get(id).get();
		listado.remove(s);
		service.delete(id);
	}
	
}