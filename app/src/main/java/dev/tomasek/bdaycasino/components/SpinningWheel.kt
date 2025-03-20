package dev.tomasek.bdaycasino.components

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.tomasek.bdaycasino.model.WheelSegment
import dev.tomasek.bdaycasino.viewmodel.MainViewModel
import dev.tomasek.bdaycasino.R
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SpinningWheel(segments: List<WheelSegment>, viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val spinningPrice = 20
    val user = viewModel.user.observeAsState()

    var soundDuration by remember { mutableStateOf(0) }
    var canSpin by remember { mutableStateOf(true) }
    var targetRotation by remember { mutableStateOf(0f) }
    var winningLabel by remember { mutableStateOf("") }
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    val animatedRotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(durationMillis = soundDuration, easing = FastOutSlowInEasing)
    )
    val context = LocalContext.current

    LaunchedEffect(targetRotation) {
        if (targetRotation > 0f) {
            delay(timeMillis = soundDuration.toLong())
            canSpin = true
            val normalizedRotation = (animatedRotation % 360 + 360) % 360
            val segmentAngle = 360f / segments.size
            val segmentIndex =
                (((360 - normalizedRotation)) / segmentAngle).toInt() % segments.size
            user.value?.let {
                it.credits -= spinningPrice
                it.prize += segments[segmentIndex].prize
            }
            winningLabel = segments[segmentIndex].label
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Box(modifier = modifier.size(300.dp).rotate(-90f)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawWheel(segments, animatedRotation)
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArrow()
        }
    }

    Button(
        enabled = canSpin && (user.value?.credits ?: 0) >= spinningPrice,
        onClick = {
            canSpin = false
            mediaPlayer?.release()
            mediaPlayer = null
            val newRotation = (360 * 5) + (0..360).random()
            targetRotation += newRotation
            val segmentIndex = ((targetRotation % 360 + (360 / segments.size) / 2) / (360 / segments.size)).toInt() % segments.size
            if (segmentIndex in segments.indices) {
                mediaPlayer = createSoundEffect(context, segments[segmentIndex].soundResId)
                soundDuration = mediaPlayer?.duration ?: 0
                mediaPlayer?.start()
            }
        },
    ) {
        Text("Spin")
    }
    Text(text = "${stringResource(id = R.string.credits)}: ${user.value?.credits.toString()}")
    Text(text = "${stringResource(id = R.string.total_prize)}: ${user.value?.prize.toString()}")
    Text(text = "${winningLabel} CZK")
}

fun createSoundEffect(context: Context, soundResId: Int): MediaPlayer {
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer.setOnCompletionListener { it.release() }
    return mediaPlayer
}

fun isColorTooDark(color: Color): Boolean {
    val darkness = 1 - (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return darkness >= 0.5
}

fun DrawScope.drawWheel(segments: List<WheelSegment>, rotation: Float) {
    val sweepAngle = 360f / segments.size
    val radius = size.minDimension / 2

    segments.forEachIndexed { index, segment ->
        rotate(degrees = rotation + (index * sweepAngle)) {
            drawArc(
                color = segment.color,
                startAngle = 0f,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset.Zero,
                size = size
            )
        }

        val angle = Math.toRadians((rotation + (index * sweepAngle) + sweepAngle / 2).toDouble())
        val labelX = (radius * 0.7 * cos(angle)).toFloat() + size.width / 2
        val labelY = (radius * 0.7 * sin(angle)).toFloat() + size.height / 2
        val textColor = if (isColorTooDark(segment.color)) android.graphics.Color.WHITE else android.graphics.Color.BLACK

        rotate(degrees = 90f, pivot = Offset(x = labelX, y = labelY)) {
            drawContext.canvas.nativeCanvas.drawText(
                segment.label,
                labelX,
                labelY,
                android.graphics.Paint().apply {
                    color = textColor
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 40f
                }
            )
        }
    }
}

fun DrawScope.drawArrow() {
    val arrowWidth = 40.dp.toPx()
    val arrowHeight = 10.dp.toPx()
    val centerX = (size.width - arrowWidth) + (arrowWidth / 4)
    val centerY = (size.height / 2) - (arrowHeight / 2)

    drawRoundRect(
        color = Color.DarkGray,
        topLeft = Offset(centerX, centerY),
        size = androidx.compose.ui.geometry.Size(arrowWidth, arrowHeight),
        cornerRadius = CornerRadius(arrowWidth / 2, arrowWidth / 2)
    )
}