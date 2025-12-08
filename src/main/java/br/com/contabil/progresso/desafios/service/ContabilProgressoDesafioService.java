package br.com.contabil.progresso.desafios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import br.com.contabil.progresso.desafios.adapter.ContabilProgressoDesafiosAdapter;
import br.com.contabil.progresso.desafios.dto.ContabilProgressoDesafiosDto;
import br.com.contabil.progresso.desafios.entity.ContabilProgressoDesafiosEntity;
import br.com.contabil.progresso.desafios.exception.BadRequestException;
import br.com.contabil.progresso.desafios.exception.InternalServerError;
import br.com.contabil.progresso.desafios.exception.NotFoundException;
import br.com.contabil.progresso.desafios.repository.ContabilProgressoDesafiosRepository;

@Service
public class ContabilProgressoDesafioService {

	@Autowired
	ContabilProgressoDesafiosRepository repository;

	@Autowired
	ContabilProgressoDesafiosAdapter adapter;

	public String create(ContabilProgressoDesafiosDto dto) throws Exception {
		String id;
		try {
			ContabilProgressoDesafiosEntity entity = adapter.adapterDtoToEntity(dto);
			id = repository.save(entity).getClerkId();
		} catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			throw new BadRequestException(e.getMessage());
		} catch (Exception e) {
			throw new InternalServerError(e.getMessage());
		}
		return id;
	}

	public boolean isLicaoCompleta(String clerkId, int licaoId) throws Exception {
		try {
			int result = repository.isLicaoCompleta(clerkId, licaoId);

			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new InternalServerError(e.getMessage());
		}
	}

	public String updateCompletoStatus(String clerkId, int desafioId) throws Exception {
		String id;
		try {
			ContabilProgressoDesafiosEntity entity = repository.findByUsuarioAndDesafioId(clerkId, desafioId)
					.orElseThrow(() -> new NotFoundException("Nenhum Registro encontrado."));
			repository.updateCompletoStatus(entity.getClerkId(), entity.getDesafiosId());
			id = entity.getClerkId();
		}catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new InternalServerError(e.getMessage());
		}
		return id;
	}

	public String resetLicao(String clerkId, int desafiosId) throws Exception {
		String message = "falha.";

		try {
			repository.resetLicao(clerkId, desafiosId);
			message = "progresso resetado.";
		} catch (Exception e) {
			throw new InternalServerError(e.getMessage());
		}
		return message;
	}

	public ContabilProgressoDesafiosDto findByUsuarioAndDesafioId(String clerkId, int desafioId) throws Exception {
		ContabilProgressoDesafiosDto dto;
		try {
			ContabilProgressoDesafiosEntity entity = repository.findByUsuarioAndDesafioId(clerkId, desafioId)
					.orElseThrow(() -> new NotFoundException("Nenhum Registro encontrado."));
			dto = adapter.adapterEntityToDto(entity);
		}catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new InternalServerError(e.getMessage());
		}
		return dto;
	}
}