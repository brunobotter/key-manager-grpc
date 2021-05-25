package br.com.bruno.pix.registra

import br.com.bruno.pix.ChavePix
import br.com.bruno.pix.ContaAssociada
import br.com.bruno.pix.TipoDeChave
import br.com.bruno.pix.TipoDeConta
import br.com.bruno.pix.shared.validation.ValidUUID
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NovaChavePix(
    @field:ValidUUID @field:NotBlank val clienteId: String?,
    @field:NotNull val tipo: TipoDeChave?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoDeConta: TipoDeConta?
) {

    fun toModel(conta: ContaAssociada): ChavePix {
        return ChavePix(
            clienteId = UUID.fromString(this.clienteId),
            tipo = TipoDeChave.valueOf(this.tipo!!.name),
            chave = if(this.tipo == TipoDeChave.ALEATORIA) UUID.randomUUID().toString() else this.chave!!,
            tipoDeConta = TipoDeConta.valueOf(this.tipoDeConta!!.name),
            conta = conta
        )
    }

}
