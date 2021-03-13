package com.joaodanieljr.desafio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaodanieljr.desafio.Repository.ClienteRepository;
import com.joaodanieljr.desafio.domain.Cliente;
import com.joaodanieljr.desafio.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( "Objeto não encontrado! ID: " 
																	+ id 
																	+ ", Tipo: " 
																	+ Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj =  find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}
	
	
	public void delete(Integer id) {
		find(id);
		clienteRepository.deleteById(id);
	}
	
	
	private void updateData (Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpf(obj.getCpf());
		newObj.setDataNasc(obj.getDataNasc());
	}
}
