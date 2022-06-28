package com.simplektx.game.utils

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.Action
import com.simplektx.game.minigame.action.Block
import com.simplektx.game.minigame.action.Stab
import com.simplektx.game.minigame.action.Swing
import com.simplektx.game.minigame.interaction.Interaction
import ktx.graphics.use
import ktx.math.ImmutableVector2
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun SpriteBatch.draw(texture: Texture) {
    this.draw(texture, texture.width.toFloat(), texture.height.toFloat())
}

fun SpriteBatch.write(interactions: List<Interaction>, bitmapFont: BitmapFont = BitmapFont()) {
    use {
        interactions.forEach {bitmapFont.draw(this, it.displayText, it.position.x, it.position.y)}
    }
}

fun SpriteBatch.write(text: String, bitmapFont: BitmapFont, position: Vector2, color: Color = Color.BLACK) {
    bitmapFont.color = color
    use {
        bitmapFont.draw(this, text, position.x, position.y)
    }
}

fun ShapeRenderer.draw(action: Action?, camera: Camera, color: Color = Color.BLACK) {
    when (action) {
        is Swing -> draw(action, camera, color)
        is Stab -> draw(action, camera, color)
        is Block -> draw(action, camera, color)
    }
}

fun ShapeRenderer.draw(combatInput: CombatInput?, camera: Camera, color: Color = Color.BLACK) {
    when (combatInput) {
        is CombatInput.SwingInput -> draw(combatInput, camera, color)
        is CombatInput.StabInput -> draw(combatInput, camera, color)
        is CombatInput.BlockInput -> draw(combatInput, camera, color)
    }
}

fun ShapeRenderer.draw(stabInput: CombatInput.StabInput, camera: Camera, color: Color = Color.GRAY) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        circle(stabInput.center, (min(2000, stabInput.duration) * 0.01).toFloat(), camera)
    }
    setColor(currentColor)
}

fun ShapeRenderer.draw(swingInput: CombatInput.SwingInput, camera: Camera, color: Color = Color.GRAY) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        rectLine(swingInput.start, swingInput.end, camera)
    }
    setColor(currentColor)
}

fun ShapeRenderer.draw(blockInput: CombatInput.BlockInput, camera: Camera, color: Color = Color.GRAY) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        rectLine(blockInput.start, blockInput.end, camera)
    }
    setColor(currentColor)
}

fun ShapeRenderer.draw(stab: Stab, camera: Camera, color: Color = Color.BLACK) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        circle(stab.center, stab.endRadius.toFloat(), camera)
    }
    use(ShapeRenderer.ShapeType.Filled, camera) {
        circle(stab.center, stab.currentRadius.toFloat(), camera)
    }
    setColor(currentColor)
}

fun ShapeRenderer.draw(swing: Swing, camera: Camera, color: Color = Color.BLACK) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        rectLine(swing.start, swing.end, camera)
    }
    use(ShapeRenderer.ShapeType.Filled, camera) {
        rectLine(swing.start, swing.current, camera, 3f)
    }
    setColor(currentColor)
}

fun ShapeRenderer.draw(block: Block, camera: Camera, color: Color = Color.GREEN) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        rectLine(block.start, block.end, camera)
    }
    use(ShapeRenderer.ShapeType.Filled, camera) {
        rectLine(block.start, block.current, camera, 3f)
    }
    setColor(currentColor)
}

fun ShapeRenderer.circle(center: ImmutableVector2, radius: Float, camera: Camera) {
    val centerUnprojected = camera.unproject(Vector3(center.x, center.y, 1f))
    circle(centerUnprojected.x, centerUnprojected.y, radius)
}

fun ShapeRenderer.rectLine(start: ImmutableVector2, end: ImmutableVector2, camera: Camera, width: Float = 5f) {
    val startUnprojected = camera.unproject(Vector3(start.x, start.y, 1f))
    val endUnprojected = camera.unproject(Vector3(end.x, end.y, 1f))
    rectLine(startUnprojected.x, startUnprojected.y, endUnprojected.x, endUnprojected.y, width)
}