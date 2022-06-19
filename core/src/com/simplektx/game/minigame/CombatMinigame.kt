package com.simplektx.game.minigame

import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.Action
import com.simplektx.game.minigame.action.NoAction
import com.simplektx.game.minigame.interaction.Hit
import com.simplektx.game.minigame.interaction.Interaction
import com.simplektx.game.minigame.interaction.Parry

class CombatMinigame(private val player: Player, private val enemy: Enemy) {
    var playerAction: Action = NoAction()
    var enemyAction: Action = enemy.action
    var interactions: MutableList<Interaction> = mutableListOf()

    fun update(deltaMs: Long) {
        interactions.forEach { it.update(deltaMs)}
        enemyAction.update(deltaMs)
        playerAction.update(deltaMs)

        process(playerAction.interact(enemyAction))

        enemy.update(deltaMs)
        enemyAction = enemy.action
    }

    private fun process(interaction: Interaction) {
        when (interaction) {
            is Hit -> {
                if (playerAction == interaction.initiator) {
                    playerAction = NoAction()
                }
                else if (enemyAction == interaction.initiator) {
                    enemy.action = NoAction()
                }
                interactions.add(interaction)
            }
            is Parry -> {
                playerAction = NoAction()
                enemy.action = NoAction()
                interactions.add(interaction)
            }
        }
        interactions.removeAll { it.isFinished}
    }

    fun receive(combatInput: CombatInput) {
        playerAction = when (combatInput) {
            is CombatInput.SwingInput -> {
                player.swing(combatInput)
            }
            is CombatInput.StabInput -> {
                player.stab(combatInput)
            }
        }
    }
}