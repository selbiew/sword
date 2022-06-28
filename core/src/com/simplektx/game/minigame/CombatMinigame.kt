package com.simplektx.game.minigame

import com.badlogic.gdx.utils.TimeUtils
import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.*
import com.simplektx.game.minigame.interaction.*

class CombatMinigame(val player: Player, val enemy: Enemy) {
    val playerAction: Action get() = player.action
    val enemyAction: Action get() = enemy.action

    var playerPlannedInput: CombatInput? = null

    var interactions: MutableList<Interaction> = mutableListOf()

    fun update(deltaMs: Long) {
        interactions.forEach { it.update(deltaMs)}
        enemyAction.update(deltaMs)
        playerAction.update(deltaMs)
        if (player.isExecutingAction) {
            playerPlannedInput = null
        }
        process(playerAction.interact(enemyAction))

        enemy.update(deltaMs)
        (playerPlannedInput as? CombatInput.StabInput)?.update(deltaMs)
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

    fun updatePlannedAction(combatInput: CombatInput?) {
        if (!player.isExecutingAction) {
            playerPlannedInput = combatInput
        }
    }
}