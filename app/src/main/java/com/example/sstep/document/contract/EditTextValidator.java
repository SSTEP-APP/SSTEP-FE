package com.example.sstep.document.contract;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextValidator implements TextWatcher {
    private EditText editText;
    private ValidationListener validationListener;

    public EditTextValidator(EditText editText, ValidationListener validationListener) {
        this.editText = editText;
        this.validationListener = validationListener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // 변경 전 텍스트에 대한 처리 로직
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // 텍스트 변경 중에 대한 처리 로직
        String text = editText.getText().toString().trim();
        boolean hasContent = !text.isEmpty();
        validationListener.onValidationResult(hasContent);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // 변경 후 텍스트에 대한 처리 로직
    }

    public interface ValidationListener {
        void onValidationResult(boolean hasContent);
    }
}