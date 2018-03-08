package hackdemo.muxi.com.hackdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mLogin;
    Button mEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEntrar = findViewById(R.id.bt_login);
        mLogin = findViewById(R.id.et_login);

        mEntrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == mEntrar.getId()){
            String input = mLogin.getText().toString();
            if(input.isEmpty()){
                Toast.makeText(this,"Insira seu número",Toast.LENGTH_LONG).show();
            }
            else if(input.length() < 8){
                Toast.makeText(this,"O código de login deve conter 8 dígitos",Toast.LENGTH_LONG).show();
            }
            else{
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }

    }
}
