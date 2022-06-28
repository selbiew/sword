package com.simplektx.game.minigame

import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.Action
import com.simplektx.game.minigame.action.NoAction
import com.simplektx.game.minigame.interaction.*

class CombatMinigame(val player: Player, val enemy: Enemy) {
    val playerAction: Action get() = player.action
    val enemyAction: Action get() = enemy.action
    var interactions: MutableList<Interaction> = mutableListOf()

    fun update(deltaMs: Long) {
        interactions.forEach { it.update(deltaMs)}
        enemyAction.update(deltaMs)
        playerAction.update(deltaMs)

        process(playerAction.interact(enemyAction))

        enemy.update(deltaMs)
    }

    private fun process(interaction: Interaction) {
        if (interaction !is NoInteraction) {
            player.process(interaction)
            enemy.process(interaction)
            interactions.add(interaction)
            interactions.removeAll { it.isFinished }
        }
    }

    fun receive(combatInput: CombatInput) {
        when (combatInput) {
            is CombatInput.SwingInput -> {
                player.swing(combatInput)
            }
            is CombatInput.StabInput -> {
                player.stab(combatInput)
            }
            is CombatInput.BlockInput -> {
                player.block(combatInput)
            }
        }
    }
}