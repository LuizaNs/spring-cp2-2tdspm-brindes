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
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTOS")
    @SequenceGenerator(name = "SQ_PRODUTOS", sequenceName = "SQ_PRODUTOS", allocationSize = 1)
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(unique = true, name = "nm_produto")
    private String nome;

    @Column(name = "vl_preco")
    private Double preco;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "CATEGORIA",
            referencedColumnName = "ID_CATEGORIA",
            foreignKey = @ForeignKey(
                    name = "FK_CATEGORIA_PRODUTO"
            )
    )
    private Categoria categoria;


}
