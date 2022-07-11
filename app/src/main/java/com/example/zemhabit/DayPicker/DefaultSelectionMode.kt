package com.example.zemhabit.DayPicker

/**
 * An implementation of [SelectionMode] that allows users to select and
 * deselect days without any restrictions.
 */
open class DefaultSelectionMode : SelectionMode {

    companion object {
        /**
         * Creates a new instance of a [DefaultSelectionMode]
         */
        @JvmStatic
        fun create(): SelectionMode {
            return DefaultSelectionMode()
        }
    }

    override fun getSelectionStateAfterSelecting(
        lastSelectionState: SelectionState,
        dayToSelect: MaterialDayPicker.Weekday
    ): SelectionState {
        return lastSelectionState.withDaySelected(dayToSelect)
    }

    override fun getSelectionStateAfterDeselecting(
        lastSelectionState: SelectionState,
        dayToDeselect: MaterialDayPicker.Weekday
    ): SelectionState {
        return lastSelectionState.withDayDeselected(dayToDeselect)
    }
}
