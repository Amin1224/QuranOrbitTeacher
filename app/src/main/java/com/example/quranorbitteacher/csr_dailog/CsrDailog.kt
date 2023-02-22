package com.example.quranorbitteacher.csr_dailog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.AssignTrialDaysBinding
import com.example.quranorbitteacher.databinding.ChoseCallOptionBinding
import com.example.quranorbitteacher.databinding.ClassDurationBinding
import com.example.quranorbitteacher.databinding.IdApprovedBinding
import com.example.quranorbitteacher.databinding.InterestedInfoBinding
import com.example.quranorbitteacher.databinding.RequiredCourseBinding
import com.example.quranorbitteacher.databinding.RequiredCurrencyBinding
import com.example.quranorbitteacher.databinding.ShowCountryBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CsrDailog {

    companion object {

        //method will return user interest.trial booking or schedule call but data get in interested dialog
        fun showDialogOne(context: Context, function: (String) -> Unit) {
            val dialog = BottomSheetDialog(context)
            val binding = ChoseCallOptionBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(binding.root)
            binding.nonInterested.setOnClickListener {
                dialog.dismiss()
            }
            binding.backPress.setOnClickListener {
                dialog.dismiss()
            }
            binding.interested.setOnClickListener {
                interestedDialog(context, function)
                dialog.dismiss()
            }
            dialog.show()
        }

        //method will return user interest.trial booking or schedule call
        private fun interestedDialog(context: Context, function: (String) -> Unit) {
            val dialog = BottomSheetDialog(context)
            val binding = InterestedInfoBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(binding.root)

            binding.csrReport.setOnClickListener {
                function.invoke("TrialBooking")
                dialog.dismiss()
            }
            binding.backPress.setOnClickListener {

                dialog.dismiss()
            }
            binding.scheduleCall.setOnClickListener {
                function.invoke("ScheduleCall")
                dialog.dismiss()
            }
            dialog.show()
        }

        //method will return course name
        fun requiredCourse(context: Context, name: ImageView, course: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: RequiredCourseBinding =
                RequiredCourseBinding.inflate(LayoutInflater.from(context))
            bind.tajweed.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    course.invoke("Tajweed")
                    window.dismiss()
                }
            }
            bind.hafiz.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    course.invoke("Hafiz")
                    window.dismiss()
                }
            }
            bind.tarjama.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    course.invoke("Tarjoma")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
        }

        //method will return country name
        fun requiredCountry(context: Context, name: ImageView, country: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: ShowCountryBinding =
                ShowCountryBinding.inflate(LayoutInflater.from(context))
            bind.pakistan.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    country.invoke("Pakistan")
                    window.dismiss()
                }
            }
            bind.england.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    country.invoke("England")
                    window.dismiss()
                }
            }
            bind.canada.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    country.invoke("Canada")
                    window.dismiss()
                }
            }
            bind.japan.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    country.invoke("Japan")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)

          //  window.setBackgroundDrawable()
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dailog_bc))

        }

        //method will return currency name
        fun requiredCurrency(context: Context, name: ImageView, currency: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: RequiredCurrencyBinding =
                RequiredCurrencyBinding.inflate(LayoutInflater.from(context))
            bind.rupeesMenu.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    currency.invoke("Rupees")
                    window.dismiss()
                }
            }
            bind.dollar.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    currency.invoke("Dollar")
                    window.dismiss()
                }
            }
            bind.pound.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    currency.invoke("Pound")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
        }

        //method will return class duration
        fun classDuration(context: Context, name: ImageView, duration: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: ClassDurationBinding =
                ClassDurationBinding.inflate(LayoutInflater.from(context))
            bind.thirty.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    duration.invoke("30 Minutes")
                    window.dismiss()
                }
            }
            bind.forthyFive.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    duration.invoke("45 Minutes")
                    window.dismiss()
                }
            }
            bind.sixty.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    duration.invoke("60 Minutes")
                    window.dismiss()
                }
            }
            bind.ninty.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    duration.invoke("90 Minutes")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
        }

        //method will return Assign days trial
        fun trialDays(context: Context, assignDays: (String) -> Unit) {
            var daysAssign: String? = null
            val dialog = Dialog(context)
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bind = AssignTrialDaysBinding.inflate(layoutInflater)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(bind.root)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            bind.seven.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    daysAssign = "Assign 7 Days"
                }
            }
            bind.fifteen.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    daysAssign = "Assign 7 Days"
                }
            }
            bind.thirty.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    daysAssign = "Assign 7 Days"
                }
            }

            bind.assign.setOnClickListener {
                assignDays.invoke(daysAssign.toString())
                dialog.dismiss()
            }
            bind.cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        fun idApproved(context: Context) {
            val dialog = Dialog(context)
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bind = IdApprovedBinding.inflate(layoutInflater)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(bind.root)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )


            bind.okay.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }
}