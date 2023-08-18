package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

//DTO <Data Transfer Object>
public record DadosCadastroMedico(
        @NotBlank(message = "Nome é obrigatório") //verifica se nao é nulo e nem vazio
        String nome,
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "Formato do e-mail inválido") //verifica se está em formato de email
        String email,
        @NotBlank(message = "Telefone é obrigatório")
        String telefone,
        @NotBlank
        @Pattern(regexp= "\\d{4,6}", message = "Formato de CRM é inválido") //Expressão regular
        String crm,
        @NotNull(message = "Especialidade é obrigatória")
        Especialidade especialidade,
        @NotNull(message = "Dados do endereço são obrigatórios")
        @Valid //validar tmb os objetos que estão como atributos
        DadosEndereco endereco) {
}
