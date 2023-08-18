package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

// JPA <Java Persistence API>
@Table(name = "medicos")
@Entity(name = "Medico")
@Getter //utilizando o Lombok para gerar os metodos getters
@NoArgsConstructor //gerar o construtor default sem argumentos
@AllArgsConstructor //ter um construtor que recebe todos os campos
@EqualsAndHashCode(of = "id") //gerar o equals e hashcode, passando o of para gerar apenas em cima do id
public class Medico {
//mapeando as entidades
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // gerar valores de id automaticamente
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded //Embeddable Attribute, fazem com que esse campo endereço faça parte da mesma tabela de medicos
    private Endereco endereco;
    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
