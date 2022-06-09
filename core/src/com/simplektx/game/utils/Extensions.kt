package com.simplektx.game.utils

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.simplektx.game.Line
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

fun ShapeRenderer.draw(lineToDraw: Line, camera: Camera, color: Color = Color.BLACK) {
    val currentColor = getColor()
    setColor(color)
    use(ShapeRenderer.ShapeType.Line, camera) {
        line(lineToDraw, camera)
    }
    setColor(currentColor)
}

fun ShapeRenderer.line(lineToDraw: Line, camera: Camera) {
    // TODO: Camera extension
    val startUnprojected = camera.unproject(Vector3(lineToDraw.start.x, lineToDraw.start.y, 1f))
    val endUnprojected = camera.unproject(Vector3(lineToDraw.end.x, lineToDraw.end.y, 1f))
    line(startUnprojected, endUnprojected)
}
