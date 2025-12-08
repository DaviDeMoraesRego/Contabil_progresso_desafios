package br.com.contabil.progresso.desafios.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContabilProgressoDesafiosDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String clerkId;
    
    private int desafiosId;
    
    private boolean completo;
}
