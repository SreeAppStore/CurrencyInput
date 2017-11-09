package com.app.exp.editextamount;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Custom Currency editText
 * Format INTEGER_PART_COUNT.INTEGER_PART_COUNT
 */
class CurrencyTextWatcher implements TextWatcher {

    private int INTEGER_PART_COUNT;
    private int DECIMAL_PART_COUNT;
    private final AppCompatEditText currencyEditText;
    private String ZEROS;
    private int FIELD_LENGTH;
    private int cursorPosition;

    /**
     * Constructor for CurrencyTextWatcher
     * <p> param paramEditText: editText </p>
     * <p> param i: INTEGER_PART_COUNT   </p>
     * <p> param d: DECIMAL_PART_COUNT   </p>
     */
    CurrencyTextWatcher(AppCompatEditText paramEditText, int integerPart, int decimalPart) {
        this.currencyEditText = paramEditText;
        currencyEditText.setText(getDefaultString());
        currencyEditText.setSelection(currencyEditText.getText().length());
        INTEGER_PART_COUNT = integerPart;
        DECIMAL_PART_COUNT = decimalPart;
        FIELD_LENGTH = integerPart + decimalPart;
        ZEROS = getOffsetString();
    }

    @Override
    public void afterTextChanged(Editable paramEditable) {
        currencyEditText.removeTextChangedListener(this);
        String temp = paramEditable.toString();
        int size = temp.length();
        if (size < FIELD_LENGTH) {
            temp = ZEROS + temp;
        }
        if (!temp.contains(".")) {
            temp = temp.substring(
                    0,
                    INTEGER_PART_COUNT - 1)
                    + temp.substring(
                    size - DECIMAL_PART_COUNT,
                    size
            );
        }
        temp = temp.replace(".", "");
        currencyEditText.setText("");
        size = temp.length();
        if (size < FIELD_LENGTH) {
            temp = ZEROS + temp;
        }
        size = temp.length();
        temp = temp.substring(
                size - (INTEGER_PART_COUNT + DECIMAL_PART_COUNT),
                size - DECIMAL_PART_COUNT)
                + "."
                + temp.substring(
                size - DECIMAL_PART_COUNT,
                size);
        currencyEditText.setText(temp);
        currencyEditText.setSelection(cursorPosition);
        currencyEditText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(
            CharSequence paramCharSequence,
            int start,
            int count,
            int after) {
        cursorPosition = currencyEditText.getSelectionStart();
    }

    @Override
    public void onTextChanged(
            CharSequence paramCharSequence,
            int start,
            int before,
            int count) {
    }

    private String getOffsetString() {
        String offsetTempText = "";
        for (int i = 0; i < FIELD_LENGTH; i++) {
            offsetTempText += "0";
        }
        return offsetTempText;
    }

    private String getDefaultString() {
        String defaultTempText = "";
        for (int i = 0; i < INTEGER_PART_COUNT; i++) {
            defaultTempText += "0";
        }
        defaultTempText += ".";
        for (int i = 0; i < DECIMAL_PART_COUNT; i++) {
            defaultTempText += "0";
        }
        return defaultTempText;
    }
}
