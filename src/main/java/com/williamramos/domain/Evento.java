package com.williamramos.domain;

import com.williamramos.enuns.TipoRecorencia;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class Evento {
    private String nome;
    private String descricao;
    private List<String> participantes;
    private String recorrencia;
    private TipoRecorencia tipoRecorencia;
    private LocalDateTime inicio;
    private LocalDateTime fim;

}
