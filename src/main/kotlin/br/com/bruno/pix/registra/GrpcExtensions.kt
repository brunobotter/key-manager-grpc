package br.com.bruno.pix.registra

import br.com.bruno.pix.RegistraChavePixRequest
import br.com.bruno.pix.TipoDeChave
import br.com.bruno.pix.TipoDeChave.UNKNOWN_TIPO_CHAVE
import br.com.bruno.pix.TipoDeConta
import br.com.bruno.pix.TipoDeConta.UNKNOWN_TIPO_CONTA

fun RegistraChavePixRequest.toModel() : NovaChavePix {
    return NovaChavePix(
        clienteId = clienteId,
        tipo = when (tipoDeChave) {
            UNKNOWN_TIPO_CHAVE -> null
            else -> TipoDeChave.valueOf(tipoDeChave.name)
        },
        chave = chave,
        tipoDeConta = when (tipoDeConta) {
            UNKNOWN_TIPO_CONTA -> null
            else -> TipoDeConta.valueOf(tipoDeConta.name)
        }
    )
}