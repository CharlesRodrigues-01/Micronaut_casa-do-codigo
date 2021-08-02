package br.com.zupacademy.charles.cadastroAutor.utils

import br.com.zupacademy.charles.cadastroAutor.response.EnderecoResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8081/cep")
interface EnderecoClient {

    @Get("/{cep}", consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_JSON])
    fun consultaCep(cep: String) : HttpResponse<EnderecoResponse>
}