package br.com.fiap.brindes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder



@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CATEGORIAS")
    @SequenceGenerator(name = "SQ_CATEGORIAS", sequenceName = "SQ_CATEGORIAS", allocationSize = 1)
    @Column(name = "ID_CATEGORIA")
    private Long id;

    @Column(unique = true, name = "nm_categoria")
    private String nome;

    }

    //Canetas
    //Camisetas
    //Canecas
    //Mochilas


