package com.simplektx.game.minigame.interaction

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.action.Action

class NoInteraction(action: Action, other: Action)
    : Interaction(action, other, 0L, "NoInteraction", Vector2(400f, 400f))