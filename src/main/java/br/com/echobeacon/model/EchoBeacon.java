package br.com.echobeacon.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EchoBeacon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numeroIdentificacao;

    @NotBlank(message = "O campo 'statusConexao' é obrigatório")
    private String statusConexao;

    @NotBlank(message = "O campo 'dataRegistro' é obrigatório")
    @PastOrPresent(message = "O campo 'dataRegistro' deve ser uma data passada ou presente")
    private LocalDate dataRegistro;

}