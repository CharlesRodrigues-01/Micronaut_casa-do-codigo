package br.com.zupacademy.charles.cadastroAutor.requestDto

import br.com.zupacademy.charles.cadastroAutor.model.Autor
import br.com.zupacademy.charles.cadastroAutor.model.Endereco
import br.com.zupacademy.charles.cadastroAutor.response.EnderecoResponse
import br.com.zupacademy.charles.cadastroAutor.validations.Placa
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NovoAutorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank @field:Size(max = 400) val descricao: String,
    @field:NotBlank @field: Placa val placaCarro: String,
    @field:NotBlank val cep: String,
    @field:NotBlank val numero: String
) {
    fun toModel(enderecoResponse: EnderecoResponse): Autor {
        val endereco = Endereco(enderecoResponse, cep , numero)
        return Autor(nome, email, descricao, placaCarro, endereco)
    }
}