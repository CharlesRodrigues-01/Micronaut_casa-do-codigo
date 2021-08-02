package br.com.zupacademy.charles.cadastroAutor.controller

import br.com.zupacademy.charles.cadastroAutor.requestDto.NovoAutorRequest
import br.com.zupacademy.charles.cadastroAutor.response.DetalhesAutorResponse
import br.com.zupacademy.charles.cadastroAutor.response.EnderecoResponse
import br.com.zupacademy.charles.cadastroAutor.utils.EnderecoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutorControllerTest {

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve cadastrar um novo autor`() {

        val novoAutorRequest = NovoAutorRequest(
            "Charles",
            "teste@teste.com.br",
            "auhsuhas",
            "EXL1772",
            "00000-000",
            "1A"
        )

        val enderecoResponse = EnderecoResponse("rua Tal", "Campinas", "SP")
        Mockito.`when`(enderecoClient.consultaCep(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", novoAutorRequest)

        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue((response.header("Location").matches("/autores/\\d".toRegex())))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock() : EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }
}