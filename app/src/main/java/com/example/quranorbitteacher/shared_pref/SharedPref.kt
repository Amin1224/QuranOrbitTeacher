package com.example.quranorbitteacher.shared_pref

import android.content.Context

class SharedPref(context: Context) {

    private val PREFERENCE_COUNT = "COUNTER"
    private val ATTENTED_CALLS = "ATTENTED_CALLS_COUNTER"
    private val TRIAL_BOOKING = "TRIAL_BOOKING_COUNTER"
    private val SCHUDULE_CALL = "SCHEDULE_CALLS_COUNTER"

    private val prifsFileName = context.packageName
    val prefs = context.getSharedPreferences(prifsFileName, 0)

    var dialCalls: Int
        get() = prefs.getInt(PREFERENCE_COUNT, 0)
        set(value) = prefs.edit().putInt(PREFERENCE_COUNT, value).apply()


    var attendedCalls: Int
        get() = prefs.getInt(ATTENTED_CALLS, 0)
        set(value) = prefs.edit().putInt(ATTENTED_CALLS, value).apply()

    var trialBooking: Int
        get() = prefs.getInt(TRIAL_BOOKING, 0)
        set(value) = prefs.edit().putInt(TRIAL_BOOKING, value).apply()

    var schuduleCall: Int
        get() = prefs.getInt(SCHUDULE_CALL, 0)
        set(value) = prefs.edit().putInt(SCHUDULE_CALL, value).apply()
}