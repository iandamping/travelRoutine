package com.example.junemon.travelroutine.helper

import android.content.Context
import android.widget.EditText

class ValidateEditTextHelper {
    companion object {
        fun validates(ctx: Context, editTextTarget: EditText, validate: Type): Boolean {
            if (editTextTarget != null) {
                if (editTextTarget.text.toString().isEmpty()) {
                    editTextTarget.requestFocus()
                    editTextTarget.setError("Please Fill This First")
                    return false
                }
            }
            return false
        }

    }

    public enum class Type {
        EMPTY
    }
}
