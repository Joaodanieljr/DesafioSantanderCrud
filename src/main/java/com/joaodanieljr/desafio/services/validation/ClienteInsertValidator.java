package com.joaodanieljr.desafio.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.joaodanieljr.desafio.domain.Cliente;
import com.joaodanieljr.desafio.dto.ClienteDTO;
import com.joaodanieljr.desafio.repository.ClienteRepository;
import com.joaodanieljr.desafio.resources.exceptions.FieldMessage;



public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = null;
		if(map.get("id") != null) {
			uriId = Integer.parseInt(map.get("id"));
		}
		
		
		
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente em = repo.findByEmail(objDTO.getEmail());
		Cliente c = repo.findByCpf(objDTO.getCpf());
		
		
		if(uriId == null && em != null) list.add(new FieldMessage("email", "Email ja existente"));
		
		if(uriId == null && c != null) 	list.add(new FieldMessage("cpf", "cpf ja existente"));
		
		if(uriId != null && em != null && !em.getId().equals(uriId)) list.add(new FieldMessage("email", "Email ja existente"));
		
		if(uriId != null && c != null && !c.getId().equals(uriId))	list.add(new FieldMessage("cpf", "cpf ja existente"));
		
		
		for(FieldMessage e: list){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}