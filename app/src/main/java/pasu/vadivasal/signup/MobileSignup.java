package pasu.vadivasal.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.regLogin.VerificationActivity;

/**
 * Created by Admin on 16-11-2017.
 */

public class MobileSignup extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.btnLater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionSave.saveSession(Appconstants.LOGIN_TYPE,0,MobileSignup.this);
                startActivity(new Intent(MobileSignup.this,MainActivity.class));
            }
        });
    }
}
