package br.com.contabil.progresso.desafios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import br.com.contabil.progresso.desafios.dto.ContabilProgressoDesafiosDto;
import br.com.contabil.progresso.desafios.dto.ResponseDto;
import br.com.contabil.progresso.desafios.service.ContabilProgressoDesafioService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/contabil-progresso-desafios/v1")
public class ContabilProgressoDesafiosController {

	@Autowired
	ContabilProgressoDesafioService service;
	
	@PostMapping
	public ResponseEntity<ResponseDto<String>> create (@RequestBody @Valid ContabilProgressoDesafiosDto dto) throws Exception{
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseDto<String>(service.create(dto), null));
	}
	
	@GetMapping("/usuario/{clerkId}/licao/{licaoId}/")
	public ResponseEntity<ResponseDto<Boolean>> isLicaoCompleta (@PathVariable("clerkId") String clerkId, @PathVariable("licaoId") int licaoId) throws Exception {
		return ResponseEntity
			.ok(new ResponseDto<Boolean>(service.isLicaoCompleta(clerkId, licaoId), null));
	}
	
	@PutMapping("/usuario/{clerkId}/desafio/{desafioId}/")
	public ResponseEntity<ResponseDto<String>> updateCompletoStatus (@PathVariable("clerkId") String clerkId, @PathVariable("desafioId") int desafioId) throws Exception{
		return ResponseEntity
				.ok(new ResponseDto<String>(service.updateCompletoStatus(clerkId, desafioId), null));
	}
	
	@DeleteMapping("/{clerkId}/{desafioId}/")
	public ResponseEntity<ResponseDto<String>> resetLicao(@PathVariable("clerkId") String clerkId, @PathVariable("desafioId") int desafioId) throws Exception {
		return ResponseEntity
			.ok(new ResponseDto<String>(service.resetLicao(clerkId, desafioId), null));
	}
	
	@GetMapping("/usuario/{clerkId}/desafio/{desafioId}")
	public ResponseEntity<ResponseDto<ContabilProgressoDesafiosDto>> findByUsuarioAndDesafioId (@PathVariable("clerkId") String clerkId, @PathVariable("desafioId") int desafioId) throws Exception {
		return ResponseEntity
			.ok(new ResponseDto<ContabilProgressoDesafiosDto>(service.findByUsuarioAndDesafioId(clerkId, desafioId), null));
	}
}
