package br.com.zupacademy.charles.cadastroAutor.controller

import br.com.zupacademy.charles.cadastroAutor.model.Autor
import br.com.zupacademy.charles.cadastroAutor.model.Endereco
import br.com.zupacademy.charles.cadastroAutor.repository.AutorRepository
import br.com.zupacademy.charles.cadastroAutor.response.DetalhesAutorResponse
import br.com.zupacademy.charles.cadastroAutor.response.EnderecoResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BuscaDetalhesAutoresControllerTest {

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @BeforeEach
    internal fun setUp(){
        val enderecoResponse = EnderecoResponse("rua tal", "Campinas", "SP")
        val endereco = Endereco(enderecoResponse, "13820-000", "1A")
        autor = Autor("Charles", "teste@teste.com.br", "auhsuhas", "EXL1772", endereco)

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`(){

        val response = client.toBlocking().exchange("/autores?email=${autor.email}", DetalhesAutorResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.email, response.body()!!.email)
        assertEquals(autor.descricao, response.body()!!.descricao)

    }
}