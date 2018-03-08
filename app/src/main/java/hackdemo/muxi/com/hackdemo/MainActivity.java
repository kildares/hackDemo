package hackdemo.muxi.com.hackdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.muxi.bmoreira.pwpservices_sdk.PWPServicesSDK.Service.PWPSPaymentListener;
import br.com.muxi.bmoreira.pwpservices_sdk.PWPServicesSDK.Service.PWPServicesManager;
import br.com.muxi.bmoreira.pwpservices_sdk.PWPServicesSDK.data.PWPSTransaction;
import br.com.muxi.bmoreira.pwpservices_sdk.PWPServicesSDK.data.PWPSTransactionStatus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PWPSPaymentListener{

    private EditText mProduto;
    private EditText mValor;
    private Button mPagar;
    private PWPServicesManager pwpServicesManager;

    private String mLastProcessedVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProduto = (EditText) findViewById(R.id.et_nome_produto);
        mValor = (EditText) findViewById(R.id.et_valor);
        mPagar = (Button) findViewById(R.id.bt_pagar);

        pwpServicesManager = new PWPServicesManager(this);
        pwpServicesManager.startService();

        mValor.addTextChangedListener(new CurrencyTextWatcher());

        mPagar.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pwpServicesManager.stopService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,ReportActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mPagar.getId()){
            makePayment();
        }
    }

    public void makePayment()
    {
        String nome = mProduto.getText().toString();
        String valor = mValor.getText().toString();
        if(nome.isEmpty()){
            Toast.makeText(this,"Insira o nome do produto",Toast.LENGTH_LONG).show();
        }
        else if(valor.isEmpty()){
            Toast.makeText(this,"Insira o valor do produto",Toast.LENGTH_LONG).show();
        }
        else if(Long.parseLong(valor) == 0){
            Toast.makeText(this,"Insira um valor diferente de zero",Toast.LENGTH_LONG).show();
        }
        else
        {
            valor = valor.replace(".","");
            PWPSTransaction pwpsTransaction = new PWPSTransaction();
            pwpsTransaction.setAmount(Long.parseLong(valor));
            pwpsTransaction.setCurrency(PWPSTransaction.CurrencyType.BRL);
            pwpsTransaction.setType(PWPSTransaction.TransactionType.CREDIT);
            pwpServicesManager.makePayment(pwpsTransaction,MainActivity.this);

            mLastProcessedVal = valor;
        }

    }

    @Override
    public void onPayment(PWPSTransactionStatus transactionStatus) {
        Log.d("PAYMENT",transactionStatus.toString());
        Toast.makeText(this,transactionStatus.toString(),Toast.LENGTH_LONG).show();
        if(transactionStatus.getTransactionFinishedStatus() == PWPSTransactionStatus.TransactionFinishedStatus.SUCCESS)
        {
            saveTransaction();
        }
    }

    public void saveTransaction()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String keyTotals = getString(R.string.key_approved_totals);
        String keyNumber = getString(R.string.key_approved_number);

        String totals = sharedPreferences.getString(keyTotals,"0");
        String numbers = sharedPreferences.getString(keyNumber,"0");

        Long totalVal = Long.parseLong(totals);
        Integer numberTrx = Integer.parseInt(numbers);

        totalVal += Long.parseLong(mLastProcessedVal);
        numberTrx++;

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(keyTotals, totalVal.toString());
        editor.putString(keyNumber, numberTrx.toString());

        editor.apply();
    }

    @Override
    public void onNotify(String message) {

    }

    @Override
    public void onCancelOperation(String message) {

    }

}
