package br.com.fiap.brindes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "lojas")
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOJAS")
    @SequenceGenerator(name = "SQ_LOJAS", sequenceName = "SQ_LOJAS", allocationSize = 1)
    @Column(name = "ID_LOJA")
    private Long id;

    @Column(unique = true, name = "nm_loja")
    private String nome;

    @ManyToMany
    private Set<Produto> produtosComercializados = new LinkedHashSet<>();

}
