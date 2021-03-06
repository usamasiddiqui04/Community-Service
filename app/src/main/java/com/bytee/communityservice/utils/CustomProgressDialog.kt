package com.bytee.communityservice.utils

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import com.bytee.communityservice.module.RegistrationActivity
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.ProgressdialogBinding

class CustomProgressDialog {

    lateinit var dialog: CustomDialog
    lateinit var view : ProgressdialogBinding


    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title: CharSequence?): Dialog {
        val inflater = (context as RegistrationActivity).layoutInflater
        view = ProgressdialogBinding.inflate(inflater)
        if (title != null) {

            view.cpTitle.text = title
        }

        // Card Color
        view.cpCardview.setCardBackgroundColor(Color.parseColor("#70000000"))

        // Progress Bar Color
        setColorFilter(view.cpPbar.indeterminateDrawable, Color.TRANSPARENT)

        // Text Color
        view.cpTitle.setTextColor(ResourcesCompat.getColor(context.resources,R.color.grey, null))

        dialog = CustomDialog(context)
        dialog.setContentView(view.root)
        dialog.show()
        return dialog
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(R.color.white)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}

