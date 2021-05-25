package br.com.bruno.pix.integracao

import io.micronaut.http.HttpResponse
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue

@Client("\${itau.contas.url}")
interface ContasDeClientesNoItauCliente {

    @Get("/api/v1/clientes/{clienteId}/contas{?tipo}")
    fun buscaContaPorTipo(@PathVariable clienteId: String, @QueryValue tipo: String): HttpResponse<DadosDaContaResponse>
}