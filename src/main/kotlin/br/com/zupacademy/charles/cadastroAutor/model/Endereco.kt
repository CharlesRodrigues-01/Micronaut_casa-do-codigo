package br.com.zupacademy.charles.cadastroAutor.model

import br.com.zupacademy.charles.cadastroAutor.response.EnderecoResponse
import javax.persistence.Embeddable

@Embeddable
class Endereco(
    enderecoResponse: EnderecoResponse,
    val cep: String,
    val numero: String
) {
    val rua = enderecoResponse.rua
    val cidade = enderecoResponse.cidade
    val estado = enderecoResponse.estado
}
