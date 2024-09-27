package pasu.vadivasal.regLogin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.NotificationReceiverActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;


/**
 * Created by developer on 16/8/17.
 */


public class SocialLoginCustom extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleApiClient mGoogleApiClient;
    private RadioGroup radioGroup;
    private TextView mDetailTextView;
    private ProgressDialog mProgressDialog;
    private CallbackManager mCallbackManager;
    Button otp_signup, google_signUp;
    private int loginAs;
    //private LoginButton loginButton;

    class C08626 implements DialogInterface.OnClickListener {
        C08626() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -1:
                    dialog.dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    private void showWhyPhoneAlert() {
        Utils.showAlert(this, "About this app", getString(R.string.why_need_user_number), "OK", "", new C08626(), true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

//
        setContentView(R.layout.signup_chosser_lay);
        
        
        

findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showWhyPhoneAlert();
    }
});
        // Views
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        otp_signup = findViewById(R.id.otp_signup);
        google_signUp = findViewById(R.id.google_signup);
        findViewById(R.id.btnLater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(SocialLoginCustom.this, MainActivity.class);
//                            returnIntent.putExtra("result", "");
//                            setResult(Activity.RESULT_OK, returnIntent);
                SessionSave.saveSession(Appconstants.LOGIN_TYPE,0,SocialLoginCustom.this);
                startActivity(returnIntent);
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                System.out.println("___c"+i);
                if (i == R.id.radioButton) {
                    google_signUp.setVisibility(View.VISIBLE);
                    otp_signup.setVisibility(View.GONE);
                } else {
                    if(i==R.id.radioButton2)
                        loginAs=1;
                    else
                        loginAs=2;
                    google_signUp.setVisibility(View.GONE);
                    otp_signup.setVisibility(View.VISIBLE);
                }
            }
        });
        // Button listeners
        findViewById(R.id.google_signup).setOnClickListener(this);
        otp_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i=new Intent(SocialLoginCustom.this, SignUpActivity.class);
              i.putExtra("type",loginAs);
              startActivity(i);
            }
        });
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        // Initialize Facebook Login button

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result","");
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode" + requestCode + "__" + resultCode);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println("requestCode" + requestCode + "__" + result.isSuccess());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                System.out.println("requestCode" + requestCode + "__" + result.getSignInAccount().getDisplayName());
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        System.out.println("firebaseAuthWithGoogle" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        signOut();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            SessionSave.saveSession(Appconstants.USER_PROFILE_IMAGE,user.getPhotoUrl().toString(),SocialLoginCustom.this);
                            SessionSave.saveSession(Appconstants.USER_PROFILE_NAME,user.getDisplayName().toString(),SocialLoginCustom.this);
                            SessionSave.saveSession(Appconstants.USER_PROFILE_GOOGLE_ID,user.getUid().toString(),SocialLoginCustom.this);
                            SessionSave.saveSession(Appconstants.USER_PROFILE_EMAIL_ID,user.getEmail().toString(),SocialLoginCustom.this);
                            SessionSave.saveSession(Appconstants.USER_PROFILE_PHONE_NUMBER,user.getPhoneNumber(),SocialLoginCustom.this);
                            SessionSave.saveSession(Appconstants.LOGIN_TYPE,0,SocialLoginCustom.this);
                            Intent returnIntent = new Intent(SocialLoginCustom.this, MainActivity.class);
//                            returnIntent.putExtra("result", "");
//                            setResult(Activity.RESULT_OK, returnIntent);
                            startActivity(returnIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SocialLoginCustom.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]


    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        // Firebase sign out
        // mAuth.signOut();
        try {
            LoginManager.getInstance().logOut();
            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                           // updateUI(null);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            //for (String key : getIntent().getExtras().keySet()) {
                Bundle remoteMessage = getIntent().getExtras();
              //  Log.d(TAG, "Key: " + key + " Value: " + value);


                try {
                    if (remoteMessage.getString("open") != null) {
                        Intent  resultIntent =new Intent(SocialLoginCustom.this, NotificationReceiverActivity.class);
                        System.out.println(TAG+"toopen"+remoteMessage.getString("open"));
                        resultIntent.putExtra("open", remoteMessage.getString("open"));
                        resultIntent.putExtra("id", remoteMessage.getString("id"));
                        resultIntent.putExtra("content", remoteMessage.getString("content"));
                        resultIntent.putExtra("message", remoteMessage.getString("message"));
                        resultIntent.putExtra("videos", remoteMessage.getString("videos"));
                        resultIntent.putExtra("about", remoteMessage.getString("about"));
                        startActivity(resultIntent);                   //  resultIntent.putExtra("some_msg",remoteMessage.getData().get("open"));
this.setIntent(null);
finish();

                    }else{
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                        {
                            Intent returnIntent = new Intent(SocialLoginCustom.this, MainActivity.class);
                            startActivity(returnIntent);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    {
                        Intent returnIntent = new Intent(SocialLoginCustom.this, MainActivity.class);
                        startActivity(returnIntent);
                        finish();
                    }
                }
                

        }else
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent returnIntent = new Intent(SocialLoginCustom.this, MainActivity.class);
            startActivity(returnIntent);
            finish();
        }
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                       // updateUI(null);
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.google_signup) {
            signIn();
        } else if (i == R.id.email_signup) {
//            revokeAccess();
            Intent intent = new Intent(SocialLoginCustom.this, Signup.class);
            intent.putExtra("type", 0);
            startActivity(intent);
        } else if (i == R.id.link_login) {
            Intent intent = new Intent(SocialLoginCustom.this, Signup.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.processing));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
