package com.patrickbabb.bounce

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport

const val hudWidth = 500f
const val hudHeight = 300f

class Main: ApplicationAdapter() {
    private lateinit var paddle: Paddle
    private lateinit var ball: Ball

    private lateinit var uiViewport: FitViewport
    private lateinit var uiCamera: OrthographicCamera
    private lateinit var uiFont: BitmapFont
    private lateinit var uiBatch: SpriteBatch

    private var score = 0
    private val layout = GlyphLayout()

    override fun create() {
        paddle = Paddle()
        ball = Ball()

        uiCamera = OrthographicCamera()
        uiViewport = FitViewport(hudWidth, hudHeight, uiCamera)
        uiBatch = SpriteBatch()
        uiFont = BitmapFont(Gdx.files.internal("assets/fonts/verdana_32.fnt"))
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        ball.begin(ShapeRenderer.ShapeType.Filled)
        ball.circle(ball.xPosition, ball.yPosition, ball.radius)
        ball.end()

        paddle.begin(ShapeRenderer.ShapeType.Filled)
        paddle.rect(paddle.xPosition, paddle.yPosition, paddle.width, paddle.height)
        paddle.end()

        renderUi()
        paddleHit()
        paddleMovement()
        ballMovement()
    }

    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height, true)
    }

    override fun dispose() {
        uiBatch.dispose()
        uiFont.dispose()
    }

    private fun renderUi() {
        val scoreText = "SCORE: $score"
        layout.setText(uiFont, scoreText)
        uiBatch.projectionMatrix = uiCamera.combined

        uiBatch.run {
            uiBatch.begin()
            uiFont.draw(uiBatch, layout, 20f, hudHeight - layout.height)
            uiBatch.end()
        }
    }

    private fun paddleMovement() {
        when {
            Gdx.input.isKeyPressed(Input.Keys.UP) ->
                if (paddle.yPosition == 480f - paddle.height) {
                    paddle.yPosition = 480f - paddle.height
                } else {
                    paddle.yPosition += paddle.ySpeed
                }

            Gdx.input.isKeyPressed(Input.Keys.DOWN) ->
                if (paddle.yPosition == 0f) {
                    paddle.yPosition = 0f
                } else {
                    paddle.yPosition -= paddle.ySpeed
                }
        }
    }

    private fun ballMovement() {
        ball.xPosition += ball.xSpeed
        ball.yPosition += ball.ySpeed

        if (ball.xPosition > 785f) {
            ball.xSpeed = -6
        }

        if (ball.xPosition < 15f) {
            // TODO: implement game over logic
            ball
        }

        if (ball.yPosition > 465f) {
            ball.ySpeed = -6
        }

        if (ball.yPosition < 15f) {
            ball.ySpeed = 6
        }
    }

    private fun paddleHit() {
        if ((ball.xPosition > paddle.xPosition) &&
            (ball.xPosition < paddle.xPosition + paddle.width) &&
            (ball.yPosition < paddle.yPosition + paddle.height) &&
            (ball.yPosition > paddle.yPosition)
        ) {
            score += 1
            ball.xSpeed = 6
        }
    }
}

