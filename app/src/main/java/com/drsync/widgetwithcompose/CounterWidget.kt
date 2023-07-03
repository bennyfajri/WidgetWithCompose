package com.drsync.widgetwithcompose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.drsync.widgetwithcompose.CounterWidget.countKey

object CounterWidget : GlanceAppWidget() {

    val countKey = intPreferencesKey("count")

    @Composable
    override fun Content() {
        val count = currentState(key = countKey) ?: 0
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = ColorProvider(Color.White),
                    fontSize = 26.sp
                )
            )
            Row(modifier = GlanceModifier.padding(16.dp)) {
                Button(
                    text = "Incr",
                    onClick = actionRunCallback(IncrementActionCallback::class.java),
                )
                Spacer(modifier = GlanceModifier.width(16.dp))
                Button(
                    text = "Decr",
                    onClick = actionRunCallback(DecrementActionCallback::class.java)
                )
            }
        }
    }
}

class SimpleCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget
}

class IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { pref ->
            val currentCount = pref[countKey]
            if (currentCount != null) {
                pref[countKey] = currentCount + 1
            } else {
                pref[countKey] = 1
            }
        }
        CounterWidget.update(context, glanceId)
    }
}

class DecrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { pref ->
            val currentCount = pref[countKey]
            if(currentCount != null) {
                pref[countKey] = currentCount - 1
            } else {
                pref[countKey] = -1
            }
        }
        CounterWidget.update(context, glanceId)
    }
}