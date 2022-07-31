package com.patrickbabb.bounce

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Ball: ShapeRenderer()  {
    private val randomX = (400..750).random().toFloat()
    private val randomY = (1..470).random().toFloat()

    var xPosition = randomX
    var yPosition = randomY
    var xSpeed = 4
    var ySpeed = 4
    var radius = 15f
}