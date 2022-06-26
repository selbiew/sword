package com.simplektx.game.minigame.interaction

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.action.Action

class BlockMiss(initiator: Action, receiver: Action)
    : Interaction(initiator, receiver, 300, "BlockMiss", Vector2(700f, 700f)) {
}