package br.com.zupacademy.charles.cadastroAutor.controller

import br.com.zupacademy.charles.cadastroAutor.repository.AutorRepository
import br.com.zupacademy.charles.cadastroAutor.response.DetalhesAutorResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional

@Controller("/autores")
class BuscaDetalhesAutoresController(val autorRepository : AutorRepository) {


    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email : String): HttpResponse<Any> {

        if(email.isBlank()) {
            val autores = autorRepository.findAll()
            val resposta = autores.map { autor -> DetalhesAutorResponse(autor.nome, autor.email, autor.descricao) }
            return HttpResponse.ok(resposta)
        }
        val possivelAutor = autorRepository.buscaPorEmail(email)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesAutorResponse(autor.nome, autor.email, autor.descricao))
    }
}
