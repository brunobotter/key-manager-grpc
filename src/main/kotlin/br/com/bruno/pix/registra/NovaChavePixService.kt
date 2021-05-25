package br.com.bruno.pix.registra

import br.com.bruno.pix.ChavePix
import br.com.bruno.pix.ChavePixRepository
import br.com.bruno.pix.grpc.handlers.ChavePixExistenteException
import br.com.bruno.pix.integracao.ContasDeClientesNoItauCliente
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(@Inject val repository: ChavePixRepository,
                          @Inject val itauClient: ContasDeClientesNoItauCliente) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registra(@Valid novaChave: NovaChavePix): ChavePix {

        // 1. verifica se chave já existe no sistema
        if (repository.existsByChave(novaChave.chave))
            throw ChavePixExistenteException("Chave Pix '${novaChave.chave}' existente")

        // 2. busca dados da conta no ERP do ITAU
        val response = itauClient.buscaContaPorTipo(novaChave.clienteId!!, novaChave.tipoDeConta!!.name) // 1
        val conta = response.body()?.toModel() ?: throw IllegalStateException("Cliente não encontrado no Itau") // 1

        // 3. grava no banco de dados
        val chave = novaChave.toModel(conta)
        repository.save(chave)

        return chave
    }
}