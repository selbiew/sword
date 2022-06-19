package com.simplektx.game.minigame.interaction

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.action.Action

class Parry(initiator: Action, receiver: Action, executionTimeMs: Long, position: Vector2)
    : Interaction(initiator, receiver, executionTimeMs, "Parry", position)
