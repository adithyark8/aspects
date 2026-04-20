package com.example.aspects

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private var selectedDate: LocalDate = LocalDate.now()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        // ── Day binder ──────────────────────────────────────────
        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.tvDay.text = data.date.dayOfMonth.toString()

                when {
                    // selected day — white text on colored circle
                    data.date == selectedDate -> {
                        container.tvDay.setTextColor(Color.WHITE)
                        container.tvDay.setBackgroundResource(R.drawable.selected_bg)
                    }
                    // today — colored text, no background
                    data.date == LocalDate.now() -> {
                        container.tvDay.setTextColor(Color.parseColor("#6200EE"))
                        container.tvDay.background = null
                    }
                    // normal day in current month
                    data.position == DayPosition.MonthDate -> {
                        container.tvDay.setTextColor(Color.parseColor("#111111"))
                        container.tvDay.background = null
                    }
                    // days from previous/next month — greyed out
                    else -> {
                        container.tvDay.setTextColor(Color.parseColor("#CCCCCC"))
                        container.tvDay.background = null
                    }
                }

                // tap to select a date
                container.tvDay.setOnClickListener {
                    val previousDate = selectedDate
                    selectedDate = data.date
                    calendarView.notifyDateChanged(previousDate)
                    calendarView.notifyDateChanged(selectedDate)
                }
            }
        }

        // ── Month header binder ──────────────────────────────────
        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthHeaderContainer> {

                override fun create(view: View) = MonthHeaderContainer(view)

                override fun bind(container: MonthHeaderContainer, data: CalendarMonth) {
                    container.tvMonthYear.text = data.yearMonth.format(
                        DateTimeFormatter.ofPattern("MMMM yyyy")
                    )
                }
            }

        // ── Setup: show 12 months back and 12 forward ────────────
        val currentMonth = YearMonth.now()

        calendarView.setup(
            currentMonth.minusMonths(12),
            currentMonth.plusMonths(12),
            firstDayOfWeekFromLocale()
        )

        calendarView.scrollToMonth(currentMonth)
    }
}