package pasu.vadivasal.regLogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView.OnEditorActionListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.MainActivity;
import pasu.vadivasal.Profile.UserProfileActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.ProfileData;
import pasu.vadivasal.view.Button;
import pasu.vadivasal.view.EditText;
import pasu.vadivasal.view.TextView;

public class VerificationActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = "VerificationActfffff";
    private static VerificationActivity inst;
    private final long DELAY = 60000;
    @BindView(R.id.btnLater)
    Button btnLater;
    @BindView(R.id.btnLoginViaOTP)
    Button btnLoginViaOTP;
    @BindView(R.id.btnResend)
    Button btnResend;
    @BindView(R.id.btnVerifyOtp)
    Button btnVerifyOtp;
    private String countryCode;
    @BindView(R.id.etCode)
    EditText etCode;

    private FirebaseAuth mAuth;
    //    @BindView(R.id.GifImageView)
//    GifImageView gifImageView;
    @BindView(R.id.ilCode)
    TextInputLayout ilayoutEnterCode;
    private boolean isPin;
    private boolean isReset = true;
    //    @BindView(R.id.ivBack)
//    ImageView ivBack;
    private OnGlobalLayoutListener keyboardLayoutListener = new C08974();
    @BindView(R.id.layOr)
    LinearLayout layOr;
    @BindView(R.id.layoutNoInternet)
    LinearLayout layoutNoInternet;
    private boolean newUser;
    private String phoneNo;
    ProgressDialog progressDialog = null;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tvActivationCode)
    TextView tvActivationCode;
    @BindView(R.id.tvDontReceiveCode)
    TextView tvDontReceiveCode;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvSmsAcknoledge)
    TextView tvSmsAcknoledge;
    private Timer waitTimer = new Timer();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private int type;
    @BindView(R.id.progress_lay)
    LinearLayout progress_lay;

    private String googleUserID, googleUserName, googleUserImage;
    private ProgressDialog mProgressDialog;

    class C08941 implements OnEditorActionListener {
        C08941() {
        }

        public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            VerificationActivity.this.btnVerifyOtp(VerificationActivity.this.btnVerifyOtp);
            return true;
        }
    }

    class C08952 implements Runnable {
        C08952() {
        }

        public void run() {
            //   VerificationActivity.this.scrollView.fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
        }
    }

    class C08963 implements Runnable {
        C08963() {
        }

        public void run() {
            VerificationActivity.this.enableResend(true);
        }
    }

    class C08974 implements OnGlobalLayoutListener {
        C08974() {
        }

        public void onGlobalLayout() {
            int navigationBarHeight = 0;
            int resourceId = VerificationActivity.this.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = VerificationActivity.this.getResources().getDimensionPixelSize(resourceId);
            }
            int statusBarHeight = 0;
            resourceId = VerificationActivity.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = VerificationActivity.this.getResources().getDimensionPixelSize(resourceId);
            }
            Rect rect = new Rect();
            VerificationActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (VerificationActivity.this.scrollView.getRootView().getHeight() - ((statusBarHeight + navigationBarHeight) + rect.height()) <= 0) {
                VerificationActivity.this.scrollView.fullScroll(33);
            } else {
                VerificationActivity.this.scrollView.fullScroll(130);
            }
        }
    }

    class C09039 extends TimerTask {

        class C09021 implements Runnable {
            C09021() {
            }

            public void run() {
                VerificationActivity.this.enableResend(true);
            }
        }

        C09039() {
        }

        public void run() {
            VerificationActivity.this.runOnUiThread(new C09021());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            googleUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            googleUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            googleUserImage = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        }
        setContentView((int) R.layout.activity_verification);
        type = getIntent().getIntExtra("type", 1);
        ButterKnife.bind((Activity) this);
        this.layoutNoInternet.setVisibility(View.GONE);
        this.etCode.setOnEditorActionListener(new C08941());

        if (getIntent().hasExtra(AppConstants.EXTRA_IS_RESET_FLOW)) {
            this.isReset = getIntent().getBooleanExtra(AppConstants.EXTRA_IS_RESET_FLOW, false);
            if (this.isReset) {
                this.phoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                this.countryCode = "+91";
                this.tvPhoneNumber.setText(getText(R.string.verify_number));
                this.isPin = true;
                this.newUser = false;
                this.tvSmsAcknoledge.setText(getString(R.string.verify_otp_msg));
                this.tvActivationCode.setVisibility(View.GONE);
            }
        }
        if (getIntent().hasExtra(AppConstants.EXTRA_PHONE_NO)) {
            this.phoneNo = getIntent().getStringExtra(AppConstants.EXTRA_PHONE_NO);
            this.countryCode = getIntent().getStringExtra("country_code");
            this.newUser = getIntent().getBooleanExtra(AppConstants.EXTRA_NEW_USER, false);
            this.isPin = getIntent().getBooleanExtra(AppConstants.EXTRA_IS_PIN, false);
            this.tvPhoneNumber.setText(String.format(Locale.getDefault(), "%s %s", new Object[]{this.countryCode, this.phoneNo}));
        }
        if (getIntent().hasExtra(AppConstants.EXTRA_OTP_FROM_API_RESPONSE)) {
            // this.etCode.setText(getIntent().getStringExtra(AppConstants.EXTRA_OTP_FROM_API_RESPONSE));
        }
        if (!Utils.isNetworkAvailable(this)) {
            loadNoInternetConnectionView(R.id.layoutNoInternet, R.id.layoutVerification, this);
        }
        //this.gifImageView.setGifImageResource(R.drawable.cricheroes_man_batting_bowling);
        this.btnResend.setVisibility(View.GONE);
        this.btnLoginViaOTP.setVisibility(View.GONE);
        this.layOr.setVisibility(View.GONE);
        this.tvDontReceiveCode.setVisibility(View.GONE);
        this.scrollView.getViewTreeObserver().addOnGlobalLayoutListener(this.keyboardLayoutListener);
//        this.ivBack.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new C08952(), 1000);

        new Handler().postDelayed(new C08963(), 30000);
    }

    private void enableResend(boolean enable) {
        if (enable) {
            //  this.gifImageView.setVisibility(View.GONE);
            this.btnResend.setVisibility(View.VISIBLE);
            if (this.isReset) {
                this.tvDontReceiveCode.setVisibility(View.VISIBLE);
            } else {
                this.tvDontReceiveCode.setVisibility(View.VISIBLE);
            }
            return;
        }
        //  this.gifImageView.setVisibility(View.VISIBLE);
        this.btnResend.setVisibility(View.GONE);
        this.btnLoginViaOTP.setVisibility(View.GONE);
        this.layOr.setVisibility(View.GONE);
        this.tvDontReceiveCode.setVisibility(View.GONE);
    }

//    @OnClick({2131756318})
//    void ivBack(View view) {
//        finish();
//    }

    private boolean validateEnterCode() {
        Utils.hideKeyboard(this, getCurrentFocus());
        if (this.etCode.getText().toString().trim().isEmpty()) {
            this.ilayoutEnterCode.setError(getString(R.string.error_valid_code));
            this.etCode.requestFocus();
            return false;
        }
        this.ilayoutEnterCode.setErrorEnabled(false);
        return true;
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeoutg
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        hideProgressDialog();
    }

    @OnClick({R.id.btnVerifyOtp})
    void btnVerifyOtp(View view) {
        if (validateEnterCode()) {
//            final Dialog dialog = Utils.showProgress(this, true);
            if (this.isReset) {
                showProgressDialog();
                signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(mVerificationId, etCode.getText().toString()));


//                ApiCallManager.enqueue("verify_otp", CricHeroes.apiClient.verifyUserOtp(Utils.udid(this), CricHeroes.getApp().getAccessToken(), new VerifyOtpRequest(this.countryCode, this.phoneNo, this.etCode.getText().toString().trim())), new CallbackAdapter() {
//                    public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                        Utils.hideProgress(dialog);
//                        if (err != null) {
//                            Utils.showToast(VerificationActivity.this, err.getMessage(), 1, false);
//                            return;
//                        }
//                        Logger.m177d("verify User otp: %s", response);
//                        try {
//                            JsonObject data = (JsonObject) response.getData();
//                            if (data != null) {
//                                Utils.showToast(VerificationActivity.this, new JSONObject(data.toString()).optString("message"), 2, false);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        VerificationActivity.this.startActivity(new Intent(VerificationActivity.this, ResetPinActivity.class));
//                    }
//                });
//                return;
//            }
//            ApiCallManager.enqueue("verify_otp", CricHeroes.apiClient.verifyOtp(Utils.udid(this), new VerifyOtpRequest(this.countryCode, this.phoneNo, this.etCode.getText().toString().trim())), new CallbackAdapter() {
//                public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                    Utils.hideProgress(dialog);
//                    if (err != null) {
//                        Utils.showToast(VerificationActivity.this, err.getMessage(), 1, false);
//                        return;
//                    }
//                    Logger.m177d("verify otp response: %s", response);
//                    JsonObject userData = (JsonObject) response.getData();
//                    userData.addProperty("access_token", response.getAccessToken());
//                    CricHeroes.getApp().loginUser((JsonObject) response.getData());
//                    PreferenceUtil.getInstance(VerificationActivity.this, AppConstants.APP_PREF).putBoolean(AppConstants.PREF_IS_SET_PIN, VerificationActivity.this.isPin);
//                    User user;
//                    if (VerificationActivity.this.newUser) {
//                        user = User.fromJson(userData);
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(UserMaster.TABLE, new ContentValues[]{user.getContentValue()});
//                        String ids = PreferenceUtil.getInstance(VerificationActivity.this, AppConstants.APP_PREF).getString(AppConstants.PREF_MY_PLAYER_IDS);
//                        if (!ids.contains(String.valueOf(user.getUserId()))) {
//                            if (ids.equalsIgnoreCase("")) {
//                                ids = String.valueOf(user.getUserId());
//                            } else {
//                                ids = ids + "," + String.valueOf(user.getUserId());
//                            }
//                            PreferenceUtil.getInstance(VerificationActivity.this, AppConstants.APP_PREF).putString(AppConstants.PREF_MY_PLAYER_IDS, ids);
//                        }
//                        VerificationActivity.this.syncData(Utils.udid(VerificationActivity.this), null, null, null, user.getCountryCode().replace(MqttTopic.SINGLE_LEVEL_WILDCARD, ""));
//                        return;
//                    }
//                    user = CricHeroes.getApp().getCurrentUser();
//                    VerificationActivity.this.syncData(Utils.udid(VerificationActivity.this), null, null, null, user.getCountryCode().replace(MqttTopic.SINGLE_LEVEL_WILDCARD, ""));
//                }
//            });
            }
        }
    }

    @OnClick({R.id.btnLater})
    void btnLater(View view) {
        SessionSave.saveSession(Appconstants.LOGIN_TYPE, 0, VerificationActivity.this);
        Intent intent = new Intent(this, MainActivity.class);
        //intent.setFlags(268468224);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btnLoginViaOTP})
    void btnLoginViaOTP(View view) {
//        Intent intent = new Intent(this, SignInWithPinActivity.class);
//        intent.putExtra(AppConstants.EXTRA_PHONE_NO, this.phoneNo);
//        intent.putExtra("country_code", this.countryCode);
//        startActivity(intent);
    }

    private void syncData(final String udid, Long page, Long datetime, final Long serverDateTime, final String countryCode) {
//        if (this.progressDialog == null && !isFinishing()) {
//            try {
//                this.progressDialog = Utils.showProgress(this, getString(R.string.loadin_country_data), false);
//            } catch (Exception e) {
//            }
//            CricHeroes.getApp();
//            CricHeroes.database.deleteMetadata();
//        }
//        ApiCallManager.enqueue("get_metadata", CricHeroes.apiClient.metadata(udid, page, datetime, serverDateTime, 1000, countryCode), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                if (err != null) {
//                    VerificationActivity.this.resumeApp(VerificationActivity.this.progressDialog);
//                    return;
//                }
//                Log.d("SplashActivity", "response: " + response);
//                try {
//                    ContentValues[] contentValues;
//                    int i;
//                    JsonObject json = (JsonObject) response.getData();
//                    Logger.m176d("JSON " + json);
//                    JSONObject jSONObject = new JSONObject(json.toString());
//                    JSONArray countryArray = jSONObject.optJSONArray(Countries.NAME);
//                    JSONArray stateArray = jSONObject.optJSONArray(States.NAME);
//                    JSONArray cityArray = jSONObject.optJSONArray(Cities.NAME);
//                    JSONArray groundArray = jSONObject.optJSONArray(Grounds.NAME);
//                    JSONArray extraTypeArray = jSONObject.optJSONArray(ExtraTypes.NAME);
//                    JSONArray bowlingTypeArray = jSONObject.optJSONArray(BowlingTypes.NAME);
//                    JSONArray playingRoleArray = jSONObject.optJSONArray(PlayingRoles.NAME);
//                    JSONArray dismissTypeArray = jSONObject.optJSONArray(DismissTypes.NAME);
//                    JSONArray skills = jSONObject.optJSONArray(Skills.NAME);
//                    JSONArray matchNoteType = jSONObject.optJSONArray(MatchNoteTypes.NAME);
//                    if (skills != null) {
//                        PreferenceUtil.getInstance(VerificationActivity.this, AppConstants.APP_PREF).putString(AppConstants.PREF_SKILLS, skills.toString());
//                    }
//                    if (countryArray != null && countryArray.length() > 0) {
//                        contentValues = new ContentValues[countryArray.length()];
//                        for (i = 0; i < countryArray.length(); i++) {
//                            contentValues[i] = new Country(countryArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CountryMaster.TABLE, contentValues);
//                    }
//                    if (stateArray != null && stateArray.length() > 0) {
//                        contentValues = new ContentValues[stateArray.length()];
//                        for (i = 0; i < stateArray.length(); i++) {
//                            contentValues[i] = new State(stateArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(StateMaster.TABLE, contentValues);
//                    }
//                    if (cityArray != null && cityArray.length() > 0) {
//                        contentValues = new ContentValues[cityArray.length()];
//                        for (i = 0; i < cityArray.length(); i++) {
//                            contentValues[i] = new City(cityArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CityMaster.TABLE, contentValues);
//                    }
//                    if (groundArray != null && groundArray.length() > 0) {
//                        contentValues = new ContentValues[groundArray.length()];
//                        for (i = 0; i < groundArray.length(); i++) {
//                            contentValues[i] = new Ground(groundArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(GroundMaster.TABLE, contentValues);
//                    }
//                    if (extraTypeArray != null && extraTypeArray.length() > 0) {
//                        contentValues = new ContentValues[extraTypeArray.length()];
//                        for (i = 0; i < extraTypeArray.length(); i++) {
//                            contentValues[i] = new ExtraType(extraTypeArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CricHeroesContract.ExtraType.TABLE, contentValues);
//                    }
//                    if (bowlingTypeArray != null && bowlingTypeArray.length() > 0) {
//                        contentValues = new ContentValues[bowlingTypeArray.length()];
//                        for (i = 0; i < bowlingTypeArray.length(); i++) {
//                            contentValues[i] = new BowlingType(bowlingTypeArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CricHeroesContract.BowlingType.TABLE, contentValues);
//                    }
//                    if (playingRoleArray != null && playingRoleArray.length() > 0) {
//                        contentValues = new ContentValues[playingRoleArray.length()];
//                        for (i = 0; i < playingRoleArray.length(); i++) {
//                            contentValues[i] = new PlayingRole(playingRoleArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CricHeroesContract.PlayingRole.TABLE, contentValues);
//                    }
//                    if (dismissTypeArray != null && dismissTypeArray.length() > 0) {
//                        contentValues = new ContentValues[dismissTypeArray.length()];
//                        for (i = 0; i < dismissTypeArray.length(); i++) {
//                            contentValues[i] = new DismissType(dismissTypeArray.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CricHeroesContract.DismissType.TABLE, contentValues);
//                    }
//                    if (matchNoteType != null && matchNoteType.length() > 0) {
//                        contentValues = new ContentValues[matchNoteType.length()];
//                        for (i = 0; i < matchNoteType.length(); i++) {
//                            contentValues[i] = new MatchNoteType(matchNoteType.getJSONObject(i)).getContentValue();
//                        }
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(CricHeroesContract.MatchNoteType.TABLE, contentValues);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (response.hasPage() && response.getPage().hasNextPage()) {
//                    VerificationActivity.this.syncData(udid, Long.valueOf(response.getPage().getNextPage()), Long.valueOf(response.getPage().getDatetime()), serverDateTime, countryCode);
//                    return;
//                }
//                System.out.println("datetime " + response.getPage().getServerdatetime());
//                PreferenceUtil.getInstance(VerificationActivity.this, AppConstants.APP_PREF).putLong(AppConstants.PREF_SYNC_DATE_TIME, Long.valueOf(response.getPage().getServerdatetime()));
//                VerificationActivity.this.resumeApp(VerificationActivity.this.progressDialog);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        LocalBroadcastManager.getInstance(this).
//                registerReceiver(receiver, new IntentFilter("otp"));

        getOTP();
    }

    private void getOTP() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                if (progressDialog != null)
                    progressDialog.cancel();
                hideProgressDialog();
                Log.d(TAG, "onVerificationCompleted:" + credential.getSmsCode());
                if (credential.getSmsCode() != null)
                    etCode.setText("" + credential.getSmsCode());
                progress_lay.setVisibility(View.VISIBLE);
                btnVerifyOtp.setVisibility(View.GONE);
                btnLater.setVisibility(View.GONE);
                signInWithPhoneAuthCredential(credential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (progressDialog != null)
                    progressDialog.cancel();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // userPhoneNo.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                if (progressDialog != null)
                    progressDialog.cancel();
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            //  FirebaseUser user = task.getResult()
                            registerUser();
                        } else {
                            hideProgressDialog();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //  mVerificationField.setError("Invalid code.");
                                Utils.showAlert(VerificationActivity.this, "", getString(R.string.invalid_code), getString(R.string.ok), "", null, true);
//                                Snackbar.make(findViewById(android.R.id.content), "Invalid code",
//                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
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


    private void registerUser() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("profile/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, VerificationActivity.this) + "");
//        ProfileData profileData=new ProfileData();
//        profileData.setName(googleUserName);
//        profileData.setPhonenumber(Long.parseLong(phoneNo));
//        profileData.setProfileImageUrl(googleUserImage);
//        profileData.setType(type);

        HashMap<String, Object> data = new HashMap<>();
        data.put("phonenumber", Long.parseLong(phoneNo));
        data.put("type", type);
        data.put("name", googleUserName);
        data.put("profileImageUrl", googleUserImage);
        data.put("googleUserID", googleUserID);
        data.put("mail", SessionSave.getSession(Appconstants.USER_PROFILE_EMAIL_ID, VerificationActivity.this));

        ref.updateChildren(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                SessionSave.saveSession(Appconstants.USER_PROFILE_PHONE_NUMBER, phoneNo, VerificationActivity.this);
                SessionSave.saveSession(Appconstants.LOGIN_TYPE, type, VerificationActivity.this);
                hideProgressDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int[] startingLocation = new int[2];
                        etCode.getLocationOnScreen(startingLocation);
                        startingLocation[0] = 500;
                        startingLocation[1] = 500;
                        System.out.println("idddddddd@" + SessionSave.getSession(Appconstants.USER_PROFILE_EMAIL_ID, VerificationActivity.this) + "___" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, VerificationActivity.this));
                        UserProfileActivity.startUserProfileFromLocation(startingLocation, (AppCompatActivity) VerificationActivity.this, true, SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, VerificationActivity.this), SessionSave.getSession(Appconstants.USER_PROFILE_NAME, VerificationActivity.this), SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE, VerificationActivity.this));
                        ((AppCompatActivity) VerificationActivity.this).overridePendingTransition(0, 0);
                        //   startActivity(new Intent(VerificationActivity.this, UserProfileActivity.class));
                    }
                }, 100);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //  LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void resumeApp(ProgressDialog progressDialog) {
        Utils.hideProgress(progressDialog);
        if (this.newUser) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra(AppConstants.EXTRA_PHONE_NO, this.phoneNo);
            intent.putExtra("country_code", this.countryCode);
            startActivity(intent);
            return;
        }
        startSyncTeamData();
        gotoMainActivity();
    }

    private void startSyncTeamData() {
        //      startService(new Intent("android.intent.action.SYNC", null, this, TeamsDataIntentService.class));
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        //    intent.setFlags(268468224);
        intent.putExtra(AppConstants.EXTRA_IS_PIN, this.isPin);
        SessionSave.saveSession(Appconstants.LOGIN_TYPE, type, VerificationActivity.this);
        startActivity(intent);
        finish();
    }
//
//    public void onStop() {
////        ApiCallManager.cancelCall("resend_otp");
////        ApiCallManager.cancelCall("verify_otp")
/// ;
//        super.onStop();
//    }

    @OnClick({R.id.btnResend})
    void btnResend(final View view) {
        // progressDialog = Utils.showProgress(this, getString(R.string.please_wait), false);
        enableResend(false);
        new Handler().postDelayed(new C08952(), 1000);
        new Handler().postDelayed(new C08963(), 30000);
        resendVerificationCode(phoneNo, mResendToken);
        //        ApiCallManager.enqueue("resend_otp", CricHeroes.apiClient.resendOtp(Utils.udid(this), new ResendOtpRequest(this.phoneNo, this.countryCode)), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                Utils.hideProgress(progressDialog);
//                TextView tvError = (TextView) VerificationActivity.this.findViewById(R.id.tvError);
//                tvError.setVisibility(View.GONE);
//                if (err == null) {
//                    VerificationActivity.this.enableResend(false);
//                    VerificationActivity.this.setTimer();
//                    Logger.m177d("verify otp response: %s", response);
//                    String otp = Utils.findTextWithRegex(((JsonObject) response.getData()).get("message").getAsString(), "\\d{5}");
//                    if (otp != null) {
//                        VerificationActivity.this.etCode.setText(otp);
//                    }
//                } else if (err.getCode() == 17004) {
//                    view.setEnabled(false);
//                    tvError.setVisibility(View.VISIBLE);
//                    tvError.setText(err.getMessage());
//                    VerificationActivity.this.enableResend(true);
//                } else {
//                    VerificationActivity.this.enableResend(true);
//                }
//            }
//        });
    }

    private void setTimer() {
        this.waitTimer.cancel();
        this.waitTimer = new Timer();
        this.waitTimer.schedule(new C09039(), 60000);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.waitTimer != null) {
            this.waitTimer.cancel();
        }
    }

    protected void onStart() {
        super.onStart();
        inst = this;
    }


    public static VerificationActivity instance() {
        return inst;
    }

    public void onClick(View view) {
        view.getId();
    }
}
