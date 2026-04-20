package com.example.aspects
import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val tvDay: TextView = view.findViewById(R.id.tvDay)
    val dotEvent: View = view.findViewById(R.id.dotEvent)
}
