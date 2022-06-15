package com.simplektx.game.minigame

import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInput

class CombatMinigame(val player: Player, val enemy: Enemy) {
    var playerAction: Action = NoAction()
    var enemyAction: Action = NoAction()
    var interactions: MutableList<Interaction> = mutableListOf()

    fun update(deltaMs: Long) {
        interactions.forEach { it.update(deltaMs)}
        enemyAction.update(deltaMs)
        playerAction.update(deltaMs)

        enemy.update(deltaMs)
        enemyAction = enemy.action

        when (val newInteraction = playerAction.interact(enemyAction)) {
            !is NoInteraction -> {
                interactions.add(newInteraction)
                playerAction = NoAction()
                enemy.action = NoAction()
            }
        }
        interactions.removeAll { it.isFinished}
    }

    fun receive(combatInput: CombatInput) {
        when (combatInput) {
            is CombatInput.SwingInput -> {
                playerAction = player.swing(combatInput)
            }
        }
    }
}