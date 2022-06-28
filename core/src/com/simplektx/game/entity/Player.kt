package com.simplektx.game.entity

import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.Block
import com.simplektx.game.minigame.action.NoAction
import com.simplektx.game.minigame.action.Stab
import com.simplektx.game.minigame.action.Swing
import com.simplektx.game.minigame.interaction.*

class Player : Entity("Player", 10) {

    // TODO: Use callbacks / signals so that prep can be interrupted but not execution
    fun swing(swingInput: CombatInput.SwingInput) {
        if (!isExecutingAction) {
            action = Swing(
                1, 1, 1000, 10 * swingInput.distance.toLong(),
                swingInput.start, swingInput.end
            )
        }
    }

    fun stab(stabInput: CombatInput.StabInput) {
        if (!isExecutingAction) {
            // TODO: Fix executionTime
            action = Stab(
                1, 1, 1000, 1000,
                stabInput.center
            )
        }
    }

    fun block(blockInput: CombatInput.BlockInput) {
        if (!isExecutingAction) {
            action = Block(blockInput.distance, blockInput.start, blockInput.end)
        }
    }

    override fun process(interaction: Interaction) {
        val isInitiator = (action == interaction.initiator)
        when (interaction) {
            is Hit -> {
                if (!isInitiator) {
                    currentHealth -= 1
                }
            }
            is BlockHit -> {
                action = NoAction()
                // TODO: Take damage / figure stuff out
            }
            is Parry -> {
                action = NoAction()
                // TODO: take damage/ spend stamina / figure stuff out
            }
            is BlockMiss -> {
                // TODO: figure out?
            }
        }
        if (isInitiator) {
            action = NoAction()
        }
    }
}
