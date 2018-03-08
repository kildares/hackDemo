package hackdemo.muxi.com.hackdemo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    TextView mQuantity;
    TextView mTotals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        mQuantity = findViewById(R.id.tv_num_trx);
        mTotals = findViewById(R.id.tv_totals);

        loadTotals();

    }


    public void loadTotals()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String totalKey = getString(R.string.key_approved_totals);
        String numberKey = getString(R.string.key_approved_number);

        String totals = sharedPreferences.getString(totalKey,"0");
        String number = sharedPreferences.getString(numberKey,"0");

        totals =    (totals.length() == 1) ? "0.0" + totals :
                    (totals.length() == 2) ? "0." + totals  :
                    totals.substring(0,totals.length()-2) + "." + totals.substring(totals.length() - 2);

        mQuantity.setText("Number of approved transactions: " + number);
        mTotals.setText("Approved Transactions totals: " + totals);
    }
}
