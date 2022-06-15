package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2

class NoInteraction(action: Action, other: Action) : Interaction(action, other, 0L, "NoInteraction", Vector2(400f, 400f)) {
    override fun update(deltaMs: Long) {}
}