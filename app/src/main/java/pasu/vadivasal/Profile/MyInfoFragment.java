package pasu.vadivasal.Profile;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatSpinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;


import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.PlayerData;
import pasu.vadivasal.view.Button;
import pasu.vadivasal.view.EditText;


public class MyInfoFragment extends Fragment {
    private String[] battingStyle = new String[0];
    String bowlingTypeId = "";
    Button btnUpdate;
    private int cityId = 1;
    private SimpleDateFormat dateFormatter;
    AutoCompleteTextView edtLocation;
    EditText etEmail;
    EditText etMobile;
    EditText etName;
    private DatePickerDialog fromDatePickerDialog;
    String imagePath;
    ImageView ivClear;
    public SlideDateTimeListener listener = new C08224();
    PlayerData playerData;
    String playingRoleId = "";
    String sBattingType = "LHB";
    String sBowlingType;
    String sPlayingRole;
    AppCompatSpinner spinBatingStyle;
    AppCompatSpinner spinBowlingStyle;
    AppCompatSpinner spinPlayingRole;
    EditText tvDOB;
   // User user;

    class C08191 implements OnClickListener {
        C08191() {
        }

        public void onClick(View view) {
            if (MyInfoFragment.this.isValid()) {
                MyInfoFragment.this.updateUserApiCall();
            }
        }
    }

    class C08202 implements OnClickListener {
        C08202() {
        }

        public void onClick(View view) {
            MyInfoFragment.this.setDateTimeField();
        }
    }

    class C08213 implements OnClickListener {
        C08213() {
        }

        public void onClick(View view) {
            MyInfoFragment.this.tvDOB.setText("");
        }
    }

    class C08224 extends SlideDateTimeListener {
        C08224() {
        }

        public void onDateTimeSet(Date date) {
            MyInfoFragment.this.tvDOB.setText(MyInfoFragment.this.dateFormatter.format(date));
        }

        public void onDateTimeCancel() {
            System.out.println("onDateTimeCancel");
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.raw_my_profile_info, container, false);
       // ButterKnife.bind((Object) this, rootView);
        initComponent();
        System.out.println("ON Create");
        return rootView;
    }

    private void initComponent() {
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        this.btnUpdate.setOnClickListener(new C08191());
    }

    public boolean isValid() {
        if (!Utils.isNetworkAvailable(getActivity())) {
            Utils.showToast(getActivity(), getString(R.string.alert_no_internet_found), 1, false);
            return false;
        } else if (Utils.isEmptyString(this.etName.getText().toString())) {
            Utils.showToast(getActivity(), getString(R.string.error_please_enter_name), 1, false);
            return false;
        } else if (!Utils.isEmptyString(this.etEmail.getText().toString()) && !Utils.isEmailValid(this.etEmail.getText().toString())) {
            Utils.showToast(getActivity(), getString(R.string.error_please_enter_valid_email), 1, false);
            return false;
        } else if (!Utils.isEmptyString(this.edtLocation.getText().toString())) {
            return true;
        } else {
            //Utils.showToast(getActivity(), getString(R.string.error_please_enter_valid_location), 1, false);
            return false;
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // this.user = CricHeroes.getApp().getCurrentUser();
//        this.etName.setText(this.user.getName().trim());
//        this.etName.setSelection(this.etName.getText().toString().length());
//        this.etEmail.setText(this.user.getEmail());
//        this.etMobile.setText(this.user.getMobile());
//        this.etMobile.setFocusable(false);
//        this.tvDOB.setText(this.user.getDob());
//        this.sPlayingRole = this.user.getPlayerRole();
//        this.sBowlingType = this.user.getBowlingType();
//        this.sBattingType = this.user.getBattingHand();
//        this.cityId = this.user.getCityId();
//        setAdapterForCityTown();
//        setPlayerRoleData();
//        setBowlingType();
//        this.tvDOB.setFocusable(false);
//        this.tvDOB.setOnClickListener(new C08202());
//        this.ivClear.setOnClickListener(new C08213());
//        this.battingStyle = getResources().getStringArray(R.array.arrayBattingStyle);
//        this.spinBatingStyle.setAdapter(new ArrayAdapter(getActivity(), R.layout.raw_spinner_item, R.id.tvName, this.battingStyle));
//        if (this.sBattingType == null || !this.sBattingType.equalsIgnoreCase("LHB")) {
//            this.spinBatingStyle.setSelection(1);
//        } else {
//            this.spinBatingStyle.setSelection(0);
//        }
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        new SlideDateTimePicker.Builder(getFragmentManager()).setListener(this.listener).setInitialDate(Utils.getDateFromString(this.tvDOB.getText().toString(), "yyyy-MM-dd")).setMaxDate(new Date()).setTheme(R.style.AppTheme).setIndicatorColor(Color.parseColor("#BB4235")).build().show();
    }

    private void setPlayerData(PlayerData data) {
        try {
            if (this.playerData == null) {
                this.playerData = data;
                if (this.playerData != null) {
//                    this.etName.setText(this.playerData.getName());
//                    this.user.setProfilePhoto(this.playerData.getProfilePhoto());
//                    this.etName.setSelection(this.etName.getText().toString().length());
//                    this.tvDOB.setText(this.playerData.getDob());
//                    this.sPlayingRole = this.playerData.getPlayingRole();
//                    this.sBowlingType = this.playerData.getBowlingStyle();
//                    this.sBattingType = this.playerData.getBattingHand();
//                    setPlayerRoleData();
//                    setBowlingType();
//                    this.battingStyle = getResources().getStringArray(R.array.arrayBattingStyle);
//                    this.spinBatingStyle.setAdapter(new ArrayAdapter(getActivity(), R.layout.raw_spinner_item, R.id.tvName, this.battingStyle));
//                    if (this.sBattingType == null || !this.sBattingType.equalsIgnoreCase("LHB")) {
//                        this.spinBatingStyle.setSelection(1);
//                    } else {
//                        this.spinBatingStyle.setSelection(0);
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPlayerRoleData() {
//        CricHeroes.getApp();
//        final ArrayList<PlayingRole> playingRoleArray = CricHeroes.database.getPlayerSkill();
//        String[] playingRole = new String[playingRoleArray.size()];
//        int pos = 0;
//        int i = 0;
//        while (i < playingRoleArray.size()) {
//            playingRole[i] = ((PlayingRole) playingRoleArray.get(i)).getRole();
//            if (this.sPlayingRole != null && this.sPlayingRole.equalsIgnoreCase(((PlayingRole) playingRoleArray.get(i)).getRole())) {
//                this.playingRoleId = "" + ((PlayingRole) playingRoleArray.get(i)).getPkPlayingRoleId();
//                pos = i;
//            }
//            i++;
//        }
//        this.spinPlayingRole.setAdapter(new ArrayAdapter(getActivity(), R.layout.raw_spinner_item, R.id.tvName, playingRole));
//        this.spinPlayingRole.setSelection(pos);
//        this.spinPlayingRole.setOnItemSelectedListener(new OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                Iterator it = playingRoleArray.iterator();
//                while (it.hasNext()) {
//                    PlayingRole playingRole1 = (PlayingRole) it.next();
//                    if (MyInfoFragment.this.spinPlayingRole.getSelectedItem().toString().equalsIgnoreCase(playingRole1.getRole())) {
//                        MyInfoFragment.this.playingRoleId = "" + playingRole1.getPkPlayingRoleId();
//                    }
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
    }

    private void setBowlingType() {
       // CricHeroes.getApp();
       // final ArrayList<BowlingType> bowlingArray = CricHeroes.database.getBowlingStyle();
     //   String[] bowlingStrArray = new String[bowlingArray.size()];
        int pos = 0;
        int i = 0;
//        while (i < bowlingArray.size()) {
////            bowlingStrArray[i] = ((BowlingType) bowlingArray.get(i)).getType();
////            if (this.sBowlingType != null && this.sBowlingType.equalsIgnoreCase(((BowlingType) bowlingArray.get(i)).getType())) {
////                this.bowlingTypeId = "" + ((BowlingType) bowlingArray.get(i)).getPkBowlingTypeId();
////                pos = i;
////            }
//            i++;
//        }
      //  this.spinBowlingStyle.setAdapter(new ArrayAdapter(getActivity(), R.layout.raw_spinner_item, R.id.tvName, bowlingStrArray));
        this.spinBowlingStyle.setSelection(pos);
        this.spinBowlingStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               // Iterator it = bowlingArray.iterator();
//                while (it.hasNext()) {
////                    BowlingType bowlingType = (BowlingType) it.next();
////                    if (MyInfoFragment.this.spinBowlingStyle.getSelectedItem().toString().equalsIgnoreCase(bowlingType.getType())) {
////                        MyInfoFragment.this.bowlingTypeId = "" + bowlingType.getPkBowlingTypeId();
////                    }
//                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setAdapterForCityTown() {
//        CricHeroes.getApp();
//        final ArrayList<City> cities = CricHeroes.database.getCities();
//        String[] citiesName = new String[cities.size()];
//        for (int i = 0; i < cities.size(); i++) {
//            citiesName[i] = ((City) cities.get(i)).getCityName();
//            if (((City) cities.get(i)).getPkCityId() == this.cityId) {
//                this.edtLocation.setText(((City) cities.get(i)).getCityName());
//                this.cityId = ((City) cities.get(i)).getPkCityId();
//                this.edtLocation.setSelection(((City) cities.get(i)).getCityName().length());
//            }
//        }
//        final ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.raw_autocomplete_city_item, citiesName);
//        this.edtLocation.setThreshold(2);
//        this.edtLocation.setAdapter(adapter);
//        this.edtLocation.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Iterator it = cities.iterator();
//                while (it.hasNext()) {
//                    City city = (City) it.next();
//                    if (((String) adapter.getItem(position)).equalsIgnoreCase(city.getCityName())) {
//                        MyInfoFragment.this.cityId = city.getPkCityId();
//                    }
//                }
//            }
//        });
    }

    public void onStop() {
    //    ApiCallManager.cancelCall("update_user");
        super.onStop();
    }

    private void updateUserApiCall() {
        if (this.spinBatingStyle.getSelectedItemPosition() == 0) {
            this.sBattingType = "LHB";
        } else {
            this.sBattingType = "RHB";
        }
//        UpdateProfileRequest updatePlayerRequest = new UpdateProfileRequest(this.user.getUserId(), !TextUtils.isEmpty(this.etName.getText().toString()) ? this.etName.getText().toString().trim() : this.etName.getText().toString(), "" + this.cityId, this.etEmail.getText().toString(), this.playingRoleId, this.sBattingType, this.bowlingTypeId, this.tvDOB.getText().toString());
//        final Dialog dialog = Utils.showProgress(getActivity(), true);
//        ApiCallManager.enqueue("update_user", CricHeroes.apiClient.updateUserProfile(Utils.udid(getActivity()), CricHeroes.getApp().getAccessToken(), updatePlayerRequest), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                int i = 0;
//                Utils.hideProgress(dialog);
//                if (err != null) {
//                    Logger.m176d("err " + err);
//                    Utils.showToast(MyInfoFragment.this.getActivity(), err.getMessage(), 1, false);
//                    return;
//                }
//                JsonObject json = (JsonObject) response.getData();
//                Logger.m176d("JSON " + json);
//                try {
//                    JSONObject jsonObject = new JSONObject(json.toString());
//                    MyInfoFragment.this.user.setName(!TextUtils.isEmpty(MyInfoFragment.this.etName.getText().toString()) ? MyInfoFragment.this.etName.getText().toString().trim() : MyInfoFragment.this.etName.getText().toString());
//                    MyInfoFragment.this.user.setEmail(MyInfoFragment.this.etEmail.getText().toString());
//                    MyInfoFragment.this.user.setDob(MyInfoFragment.this.tvDOB.getText().toString());
//                    MyInfoFragment.this.user.setMobile(MyInfoFragment.this.etMobile.getText().toString());
//                    MyInfoFragment.this.user.setPlayerRole(MyInfoFragment.this.sPlayingRole);
//                    MyInfoFragment.this.user.setPkPlayingRoleId(Utils.isEmptyString(MyInfoFragment.this.playingRoleId) ? 0 : Integer.parseInt(MyInfoFragment.this.playingRoleId));
//                    MyInfoFragment.this.user.setBattingHand(MyInfoFragment.this.sBattingType);
//                    MyInfoFragment.this.user.setBowlingType(MyInfoFragment.this.sBowlingType);
//                    User user = MyInfoFragment.this.user;
//                    if (!Utils.isEmptyString(MyInfoFragment.this.bowlingTypeId)) {
//                        i = Integer.parseInt(MyInfoFragment.this.bowlingTypeId);
//                    }
//                    user.setPkBowlingTypeId(i);
//                    MyInfoFragment.this.user.setCityId(MyInfoFragment.this.cityId);
//                    CricHeroes.getApp().loginUser(MyInfoFragment.this.user.toJson());
//                    CricHeroes.getApp();
//                    CricHeroes.database.insert(UserMaster.TABLE, new ContentValues[]{MyInfoFragment.this.user.getContentValue()});
//                    Utils.showToast(MyInfoFragment.this.getActivity(), "Your profile successfully updated.", 2, false);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void setCityId(int cityid) {
        this.cityId = cityid;
        try {
            setAdapterForCityTown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(final PlayerData playerData, int cityId) {
        System.out.println("setPlayerData");
        this.cityId = cityId;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MyInfoFragment.this.setPlayerData(playerData);
                try {
                    MyInfoFragment.this.setAdapterForCityTown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }
}
