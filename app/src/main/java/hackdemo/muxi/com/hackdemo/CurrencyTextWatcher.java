package hackdemo.muxi.com.hackdemo;

import android.text.Editable;
import android.text.TextWatcher;

import java.text.NumberFormat;

/**
 * Created by kilda on 3/8/2018.
 */

class CurrencyTextWatcher implements TextWatcher {

    boolean mEditing;

    public CurrencyTextWatcher(){
        mEditing=false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public synchronized  void afterTextChanged(Editable editable) {
//        if(!mEditing) {
//            mEditing = true;
//
//            String digits = editable.toString().replaceAll("\\D", "");
//            NumberFormat nf = NumberFormat.getCurrencyInstance();
//            try{
//                String formatted = nf.format(Double.parseDouble(digits)/100);
//                editable.replace(0, editable.length(), formatted);
//
//                if(Long.parseLong(editable.toString()) == 0)
//                    editable.clear();
//
//            } catch (NumberFormatException nfe) {
//                editable.clear();
//            }
//            mEditing = false;
//        }
    }
}
