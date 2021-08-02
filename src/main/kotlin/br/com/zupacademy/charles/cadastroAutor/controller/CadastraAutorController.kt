package br.com.zupacademy.charles.cadastroAutor.controller

import br.com.zupacademy.charles.cadastroAutor.repository.AutorRepository
import br.com.zupacademy.charles.cadastroAutor.requestDto.NovoAutorRequest
import br.com.zupacademy.charles.cadastroAutor.response.EnderecoResponse
import br.com.zupacademy.charles.cadastroAutor.utils.EnderecoClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient
) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any> {

        val enderecoResponse: HttpResponse<EnderecoResponse> = enderecoClient.consultaCep(request.cep)

        if (enderecoResponse.body() == null) return HttpResponse.badRequest()
        val autor = request.toModel(enderecoResponse.body()!!)

        autorRepository.save(autor)
        println("Autor => ${autor.nome}")

        val uri = UriBuilder.of("/autores/{id}")
                            .expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)

    }
}