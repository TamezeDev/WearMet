package com.zekiee.wearmet.widget.state

import com.zekiee.wearmet.domain.model.Weather

sealed interface WidgetState {
    data class Message(val text: String) : WidgetState
    data class Data(val name: String, val weather: Weather) : WidgetState
}