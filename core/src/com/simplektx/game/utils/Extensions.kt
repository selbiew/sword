package com.simplektx.game.utils

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.simplektx.game.Line
import com.simplektx.game.minigame.Action
import com.simplektx.game.minigame.Interaction
import com.simplektx.game.minigame.Swing
import ktx.graphics.use
import kotlin.math.abs

fun Vector2.xDst(v: Vector2): Float {
    return abs(this.x - v.x)
}

fun Vector2.yDst(v: Vector2): Float {
    return abs((this.y - v.y))
}

fun Pixmap.drawLine(v1: Vector2, v2: Vector2) {
    this.drawLine(v1.x.toInt(), v1.y.toInt(), v2.x.toInt(), v2.y.toInt())
}

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
    }
}

fun ShapeRenderer.draw(swing: Swing, camera: Camera, color: Color = Color.BLACK) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        rectLine(swing.start, swing.end, camera)
    }
//    println("exectionTimeMs: ${swing.executionTimeMs}")
//    println("swing.executionProgress: ${swing.executionProgress}")
//    println("swing.current: ${swing.current}")
    use(ShapeRenderer.ShapeType.Filled, camera) {
        rectLine(swing.start, swing.current, camera, 3f)
    }
    setColor(currentColor)
}

fun ShapeRenderer.line(lineToDraw: Line, camera: Camera) {
    // TODO: Camera extension
    val startUnprojected = camera.unproject(Vector3(lineToDraw.start.x, lineToDraw.start.y, 1f))
    val endUnprojected = camera.unproject(Vector3(lineToDraw.end.x, lineToDraw.end.y, 1f))
    line(startUnprojected, endUnprojected)
}

fun ShapeRenderer.rectLine(start: Vector2, end: Vector2, camera: Camera, width: Float = 5f) {
    val startUnprojected = camera.unproject(Vector3(start.x, start.y, 1f))
    val endUnprojected = camera.unproject(Vector3(end.x, end.y, 1f))
    rectLine(startUnprojected.x, startUnprojected.y, endUnprojected.x, endUnprojected.y, width)
}