package pasu.vadivasal.regLogin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView.OnEditorActionListener;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.android.logger.Logger;
import pasu.vadivasal.globalAdapter.SpinnerAdapter;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Country;
import pasu.vadivasal.view.Button;
import pasu.vadivasal.view.EditText;
import pasu.vadivasal.view.TextView;

public class SignUpActivity extends BaseActivity implements OnItemSelectedListener, OnClickListener {
    private static final String TAG = "Signup";
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.btnLater)
    Button btnLater;
    @BindView(R.id.btnPhoneNumber)
    Button btnPhoneNumber;
    @BindView(R.id.btnPin)
    Button btnPin;
    ArrayList<Country> countries=new ArrayList<>();
    String[] country;
    String[] countryCode;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    private OnGlobalLayoutListener keyboardLayoutListener = new C08593();
    @BindView(R.id.layoutNoInternet)
    LinearLayout layoutNoInternet;
    int maxLength = 10;
    private ProgressDialog progressDialog;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.spCountryPicker)
    AppCompatSpinner spCountryPicker;
    @BindView(R.id.tvCountryCode)
    TextView tvCountryCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private int type;

    class C08571 implements OnEditorActionListener {
        C08571() {
        }

        public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            Utils.hideKeyboard(SignUpActivity.this, SignUpActivity.this.etPhoneNumber);
            return true;
        }
    }

    class C08582 implements Runnable {
        C08582() {
        }

        public void run() {
         //   SignUpActivity.this.scrollView.fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
        }
    }

    class C08593 implements OnGlobalLayoutListener {
        C08593() {
        }

        public void onGlobalLayout() {
            int navigationBarHeight = 0;
            int resourceId = SignUpActivity.this.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = SignUpActivity.this.getResources().getDimensionPixelSize(resourceId);
            }
            int statusBarHeight = 0;
            resourceId = SignUpActivity.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = SignUpActivity.this.getResources().getDimensionPixelSize(resourceId);
            }
            Rect rect = new Rect();
            SignUpActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (SignUpActivity.this.scrollView.getRootView().getHeight() - ((statusBarHeight + navigationBarHeight) + rect.height()) <= 0) {
                SignUpActivity.this.scrollView.fullScroll(33);
            } else {
                SignUpActivity.this.scrollView.fullScroll(130);
            }
        }
    }

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getIntExtra("type",1);
        setContentView((int) R.layout.activity_sign_up);
        ButterKnife.bind((Activity) this);
        this.layoutNoInternet.setVisibility(View.GONE);
        this.btnDone.setOnClickListener(this);
        this.btnPin.setOnClickListener(this);
        this.btnLater.setOnClickListener(this);
        this.btnPhoneNumber.setOnClickListener(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(btnPhoneNumber!=null)
//                btnPhoneNumber.performClick();
//            }
//        },500);
        // CricHeroes.getApp();
        //this.countries = CricHeroes.database.getCountries();

        try {
            //   JsonArray json = (JsonArray) response.getData();
            // Logger.m176d("JSON " + json);
//            JSONArray countryArray = new JSONArray(json.toString());
//            if (countryArray != null && countryArray.length() > 0) {
//                ContentValues[] contentValues = new ContentValues[countryArray.length()];
//                for (int i = 0; i < countryArray.length(); i++) {
//                    contentValues[i] = new Country(countryArray.getJSONObject(i)).getContentValue();
//                }
//                CricHeroes.getApp();
//                CricHeroes.database.insert(CountryMaster.TABLE, contentValues);


            JSONObject coun = null;
            JSONObject coun2 = null;
            try {
                coun = new JSONObject();
                coun.put(Appconstants.Countries.COUNTRY_ID, 1);
                coun.put(Appconstants.Countries.COUNTRY_NAME, "INDIA");
                coun.put(Appconstants.Countries.MOBILE_MAX_LENGTH, 10);
                coun.put(Appconstants.Countries.COUNTRY_CODE, "+91");
                coun.put(Appconstants.Countries.IS_ACTIVE, 0);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                coun2 = new JSONObject();
                coun2.put(Appconstants.Countries.COUNTRY_ID, 2);
                coun2.put(Appconstants.Countries.COUNTRY_NAME, "USA");
                coun2.put(Appconstants.Countries.MOBILE_MAX_LENGTH, 10);
                coun2.put(Appconstants.Countries.COUNTRY_CODE, "+1");
                coun2.put(Appconstants.Countries.IS_ACTIVE, 0);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


            Country indiac = new Country(coun);
this.countries.add(indiac);
this.countries.add(new Country(coun2));

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

        int len = this.countries.size();
        this.country = new String[len];
        this.countryCode = new String[len];
        int i = 0;
        Iterator it = this.countries.iterator();
        while (it.hasNext())

        {

            Country c = (Country) it.next();
            System.out.println("dhoool"+i+ c.getCountryName());
            this.country[i] = c.getCountryName();
            this.countryCode[i] = c.getCountryCode();
            i++;
        }
        this.spCountryPicker.setAdapter(new
                SpinnerAdapter(this, this.country));
        this.spCountryPicker.setOnItemSelectedListener(this);
        this.etPhoneNumber.setOnEditorActionListener(new

                C08571());
        if (VERSION.SDK_INT >= 23)

        {
            requestSmsPermission();
        }
        if (!Utils.isNetworkAvailable(this))

        {
            loadNoInternetConnectionView(R.id.layoutNoInternet, R.id.layoutSignUp);
        }
        this.scrollView.getViewTreeObserver().

                addOnGlobalLayoutListener(this.keyboardLayoutListener);
        new

                Handler().

                postDelayed(new C08582(), 1000);
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.RECEIVE_SMS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECEIVE_SMS", "android.permission.READ_SMS"}, 123);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Log.i("Permission", "SMS permission was NOT granted.");
                    return;
                } else {
                    Log.i("Permission", "SMS permission has now been granted. Showing result.");
                    return;
                }
            default:
                return;
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(this.etPhoneNumber.getText().toString().trim())) {
            ((TextInputLayout) findViewById(R.id.ilPhoneNumber)).setError(getString(R.string.error_please_enter_phone_number));
            this.etPhoneNumber.requestFocus();
            return false;
        } else if (this.etPhoneNumber.getText().toString().trim().length() >= this.maxLength) {
            return true;
        } else {
            ((TextInputLayout) findViewById(R.id.ilPhoneNumber)).setError(getString(R.string.error_please_enter_valid__phone_number));
            this.etPhoneNumber.requestFocus();
            return false;
        }
    }

    private void showAlert(final int loginType) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_confirm_contact);
        ((TextView) dialog.findViewById(R.id.txt_phone)).setText(String.format("%s %s", new Object[]{this.tvCountryCode.getText().toString(), this.etPhoneNumber.getText().toString()}));
        ((TextView) dialog.findViewById(R.id.dialog_txt_edit)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.dialog_txt_yes)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                if (loginType == 0) {
                    SignUpActivity.this.verifyNumberAndSendOtp();
                    return;
                }
//                Intent intent = new Intent(SignUpActivity.this, SignInWithPinActivity.class);
//                intent.putExtra(AppConstants.EXTRA_PHONE_NO, SignUpActivity.this.etPhoneNumber.getText().toString());
//                intent.putExtra("country_code", SignUpActivity.this.tvCountryCode.getText().toString());
//                SignUpActivity.this.startActivity(intent);
            }
        });
        dialog.show();
    }

    private void showWhyPhoneAlert() {
        Utils.showAlert(this, "Why my phone number?", getString(R.string.why_need_user_number), "OK", "", new C08626(), true);
    }

    public void onStop() {
        // ApiCallManager.cancelCall("sign_in");
        super.onStop();
    }

    private void verifyNumberAndSendOtp() {
        final String countryCode = this.tvCountryCode.getText().toString();
        final String phoneNo = this.etPhoneNumber.getText().toString();
        final String userPhoneNo = String.format("%s %s", new Object[]{countryCode, phoneNo});
        final ProgressDialog progressDialog = Utils.showProgress(this, getString(R.string.msg_verify_phone, new Object[]{userPhoneNo}), false);
        //JsonObject data = (JsonObject) response.getData();
        boolean newUser = true;
        boolean isPin = false;
        Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
        intent.putExtra(AppConstants.EXTRA_PHONE_NO, phoneNo);
        intent.putExtra("country_code", countryCode);
        intent.putExtra("type",type);
        intent.putExtra(AppConstants.EXTRA_NEW_USER, newUser);
        intent.putExtra(AppConstants.EXTRA_IS_PIN, isPin);
        String otp = Utils.findTextWithRegex("", "\\d{5}");
        if (otp != null) {
            intent.putExtra(AppConstants.EXTRA_OTP_FROM_API_RESPONSE, otp);
        }
        SignUpActivity.this.startActivity(intent);
        finish();
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                Log.d(TAG, "onVerificationCompleted:" + credential);
//                signInWithPhoneAuthCredential(credential);
//
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Log.w(TAG, "onVerificationFailed", e);
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                   // userPhoneNo.setError("Invalid phone number.");
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
//                            Snackbar.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCodeSent(String verificationId,
//                                   PhoneAuthProvider.ForceResendingToken token) {
//                Log.d(TAG, "onCodeSent:" + verificationId);
////                mVerificationId = verificationId;
////                mResendToken = token;
//            }
//        };
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNo,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);
//

//        ApiCallManager.enqueue("sign_in", CricHeroes.apiClient.signin(Utils.udid(this), new SigninRequest(phoneNo, countryCode)), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                Utils.hideProgress(progressDialog);
//                if (err != null) {
//                    //Logger.m177d("sign in response: %s", Integer.valueOf(err.getCode()));
//                    Utils.showToast(SignUpActivity.this, err.getMessage(), 1, false);
//                    return;
//                }
////                Logger.m177d("sign in response: %s", response);
//
//            }
//        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        this.tvCountryCode.setText(this.countryCode[position]);
        InputFilter[] FilterArray = new InputFilter[1];
        this.maxLength = ((Country) this.countries.get(position)).getMobileMaxLength();
        FilterArray[0] = new LengthFilter(this.maxLength);
        this.etPhoneNumber.setFilters(FilterArray);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                Utils.hideKeyboard(this, this.etPhoneNumber);
                if (validate()) {
                    showAlert(0);
                    return;
                }
                return;
            case R.id.btnPin:
                Utils.hideKeyboard(this, this.etPhoneNumber);
                if (validate()) {
                    showAlert(1);
                    return;
                }
                return;
            case R.id.btnPhoneNumber:
                showWhyPhoneAlert();
                return;
            case R.id.btnLater:
                Utils.hideSoftKeyboard(this);
                SessionSave.saveSession(Appconstants.LOGIN_TYPE,0,SignUpActivity.this);
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return;
            default:
                return;
        }
    }
}
