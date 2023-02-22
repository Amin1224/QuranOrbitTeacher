package com.example.quranorbitteacher.registration_dailog

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.*
import com.google.rpc.context.AttributeContext.Resource

class RegistrationDailog {
    companion object {

        fun shiftDailog(context: Context, name: TextView, shiftTime: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: ShiftBinding = ShiftBinding.inflate(LayoutInflater.from(context))
            bind.morning.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    shiftTime.invoke("Morning")
                    window.dismiss()
                }
            }
            bind.evening.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    shiftTime.invoke("Evening")
                    window.dismiss()
                }
            }
            bind.night.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    shiftTime.invoke("Night")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
            AppCompatResources.getDrawable(context, R.drawable.dailog_bc)
        //    window.setBackgroundDrawable(ResourcesCompat.getDrawable(Resources(null),R.drawable.dailog_bc))

        }

        fun genderDailog(context: Context, name: ImageView, gender: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: GenderBinding = GenderBinding.inflate(LayoutInflater.from(context))
            bind.male.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    gender.invoke("Male")
                    window.dismiss()
                }
            }

            bind.female.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    gender.invoke("Female")
                    window.dismiss()
                }
            }

            bind.anyone.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    gender.invoke("Anyone")
                    window.dismiss()
                }
            }

            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dailog_bc))

        }
        //account type selected
        fun accountType(context: Context, name: ImageView, accountType: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: AccountTypeBinding = AccountTypeBinding.inflate(LayoutInflater.from(context))

            bind.directorSale.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    accountType.invoke("Director Sale")
                    window.dismiss()
                }
            }

            bind.floorManager.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    accountType.invoke("Floor Manager")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
            AppCompatResources.getDrawable(context, R.drawable.dailog_bc)
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dailog_bc))

        }

        fun qualification(context: Context, name: TextView, qualification: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: QualificationBinding =
                QualificationBinding.inflate(LayoutInflater.from(context))
            bind.matric.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    qualification.invoke("Matric")
                    window.dismiss()
                }
            }

            bind.intermediate.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    qualification.invoke("Intermediate")
                    window.dismiss()
                }
            }

            bind.bachelor.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    qualification.invoke("Bachelor")
                    window.dismiss()
                }
            }
            bind.master.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    qualification.invoke("Master")
                    window.dismiss()
                }
            }

            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dailog_bc))

        }

        fun language(context: Context, name: ImageView, language: (String) -> Unit) {
            val window = PopupWindow(context)
            val bind: LanguageBinding =
                LanguageBinding.inflate(LayoutInflater.from(context))
            bind.english.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    language.invoke("English")
                    window.dismiss()
                }
            }

            bind.urdu.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    language.invoke("Urdu")
                    window.dismiss()
                }
            }

            bind.pashto.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    language.invoke("Pashto")
                    window.dismiss()
                }
            }
            bind.punjabi.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    language.invoke("Punjabi")
                    window.dismiss()
                }
            }
            bind.farsi.setOnCheckedChangeListener { compoundButton, state ->
                if (state) {
                    language.invoke("Farsi")
                    window.dismiss()
                }
            }
            window.contentView = bind.root
            window.isOutsideTouchable = true
            window.showAsDropDown(name)
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dailog_bc))

        }
    }
}