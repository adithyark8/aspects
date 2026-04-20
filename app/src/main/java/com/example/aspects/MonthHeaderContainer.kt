package com.example.aspects

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer

class MonthHeaderContainer(view: View) : ViewContainer(view) {
    val tvMonthYear: TextView = view.findViewById(R.id.tvMonthYear)
}