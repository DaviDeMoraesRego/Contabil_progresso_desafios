package br.com.contabil.progresso.desafios.adapter;

import org.springframework.stereotype.Component;

import br.com.contabil.progresso.desafios.dto.ContabilProgressoDesafiosDto;
import br.com.contabil.progresso.desafios.entity.ContabilProgressoDesafiosEntity;

@Component
public class ContabilProgressoDesafiosAdapter {

	public ContabilProgressoDesafiosEntity adapterDtoToEntity (ContabilProgressoDesafiosDto dto) {
		
		ContabilProgressoDesafiosEntity entity = new ContabilProgressoDesafiosEntity();
		
		entity.setClerkId(dto.getClerkId());
		entity.setDesafiosId(dto.getDesafiosId());
		entity.setCompleto(dto.isCompleto());
		
		return entity;
	}
	
	public ContabilProgressoDesafiosDto adapterEntityToDto (ContabilProgressoDesafiosEntity entity) {
		
		ContabilProgressoDesafiosDto dto = new ContabilProgressoDesafiosDto();
		
		dto.setClerkId(entity.getClerkId());
		dto.setDesafiosId(entity.getDesafiosId());
		dto.setCompleto(entity.isCompleto());
		
		return dto;
	}
}
