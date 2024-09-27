package pasu.vadivasal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import pasu.vadivasal.News.NewsListFragment;
import pasu.vadivasal.Profile.UserProfileActivity;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.dashboard.DashboardAdapter;
import pasu.vadivasal.dashboard.DashboardMainFragment;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.matchList.MatchListMainFragment;
import pasu.vadivasal.moreSettings.MoreListFragment;
import pasu.vadivasal.photographyInsta.AutoLoadingFragment;
import pasu.vadivasal.regLogin.SignUpActivity;
import pasu.vadivasal.regLogin.SocialLoginCustom;
import pasu.vadivasal.view.CircleImageView;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextMessage;
    private BottomNavigationView navigation;
    private AlertDialog UserRegisterAlert;
    private Fragment currentFragment;
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        finish();
    }

    public void tournamentMore(){
//        Menu menu = navigation.getMenu();
//        menu.findItem(R.id.action_favorites).setIcon(favDrawable);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //  mTextMessage.setText(R.string.title_home);
                   // if(!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE,MainActivity.this))
                    getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new DashboardMainFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    if(!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE,MainActivity.this))
                    getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new MatchListMainFragment()).commit();
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.news_feed:
                    if(!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE,MainActivity.this))
                    showFragment(AutoLoadingFragment.newInstance("InstaMaterial"), false);
                    // getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new InstaMaterial()).commit();
//                    startActivity(new Intent(MainActivity.this, pasu.vadivasal.newsfeed.MainActivity.class));
                    //   getSupportFragmentManager().beginTransaction().add(R.id.main_frag,new MatchListMainFragment()).commit();
//                    mTextMessage.setText(R.string.title_dashboard);
//                    Intent i=new Intent(MainActivity.this, NewsDetailActivity.class);
//                    i.putExtra("isprofile",true);
//                    startActivity(i);
                    return true;
                case R.id.photo_navi:
                    //    mTextMessage.setText(R.string.title_notifications);
                    if(!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE,MainActivity.this))
                    getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new NewsListFragment()).commit();
//                    Intent i=new Intent(MainActivity.this, NewsDetailActivity.class);
//                    i.putExtra("isprofile",true);
//                    startActivity(i);
                    return true;
                case R.id.navigation_more:
                    //    mTextMessage.setText(R.string.title_notifications);
                    if(!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE,MainActivity.this))
                        getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new MoreListFragment()).commit();
//                    Intent i=new Intent(MainActivity.this, NewsDetailActivity.class);
//                    i.putExtra("isprofile",true);
//                    startActivity(i);
                    return true;

            }
            return false;
        }


//        if (i == 1) {
//            Animation animation = new TranslateAnimation(0,-((width/2)-100), 0, -((height/2)+120));
//            animation.setDuration(1000);
//            animation.setFillAfter(true);
//            cardtranlation.startAnimation(animation);
//            cardtranlation.setVisibility(View.VISIBLE);
//        } else if (i == 2) {
//            Animation animation = new TranslateAnimation(0,100, 0,  -((height/2)+120));
//            animation.setDuration(1000);
//            animation.setFillAfter(true);
//            cardtranlation.startAnimation(animation);
//            cardtranlation.setVisibility(View.VISIBLE);
//        }
//               else if(i==3){
//            Animation animation = new TranslateAnimation(0,-((width/2)-100), 0,-220);
//            animation.setDuration(1000);
//            animation.setFillAfter(true);
//            cardtranlation.startAnimation(animation);
//            cardtranlation.setVisibility(View.VISIBLE);
//        }
//               else if(i==4){
//            Animation animation = new TranslateAnimation(0, 100, 0,-220);
//            animation.setDuration(1000);
//            animation.setFillAfter(true);
//            cardtranlation.startAnimation(animation);
//            cardtranlation.setVisibility(View.VISIBLE);
//            i=0;
//        }

    };
    private RecyclerView rvDashboard;
    private DashboardAdapter dashboardAdapter;
    public CircleImageView toolbar_profile_image;
    public pasu.vadivasal.view.TextView tvAddImage;

    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = getSupportFragmentManager();

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction()
                .replace(R.id.main_frag, fragment);

        ft.addToBackStack(null);
        // ft.disallowAddToBackStack();
        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().get("message") != null) {
                String value = String.valueOf(getIntent().getExtras().get("message"));
                if (!value.equals(null)) {
                    Utils.showAlert(this, getString(R.string.app_name), value, "", "", null, true);
//                startActivity(new Intent(this, Main2Activity.class));
                }
            }
        }
        setContentView(R.layout.activity_main);
         navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new DashboardMainFragment()).commit();
        toolbar_profile_image = (CircleImageView) findViewById(R.id.toolbar_profile_image);
        tvAddImage = (pasu.vadivasal.view.TextView) findViewById(R.id.textAddImage);
     //   Picasso.with(this).load(SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,this)).error(R.drawable.user).into(toolbar_profile_image);
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
//
//
//           else if(!SessionSave.getSession("profileImageUrl",MainActivity.this).equals(""))
//                Picasso.with(this).load(SessionSave.getSession("profileImageUrl",MainActivity.this)).error(R.drawable.user).into(toolbar_profile_image);
//            else
//                Picasso.with(this).load(R.drawable.user).error(R.drawable.user).into(toolbar_profile_image);
//        } else {
//            Picasso.with(this).load(R.drawable.user).error(R.drawable.user).into(toolbar_profile_image);
//        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE,MainActivity.this)){
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                   if (SessionSave.getSessionInt(Appconstants.LOGIN_TYPE, MainActivity.this) != 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int[] startingLocation = new int[2];
                                view.getLocationOnScreen(startingLocation);
                                startingLocation[0] += view.getWidth() / 2;
                                //  startingLocation[1]=500;
                                Bundle b = new Bundle();

//                                Toast.makeText(g)
                                UserProfileActivity.startUserProfileFromLocation(startingLocation, (AppCompatActivity) MainActivity.this, true, SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,MainActivity.this), SessionSave.getSession(Appconstants.USER_PROFILE_NAME,MainActivity.this), SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,MainActivity.this));
                                ((AppCompatActivity) MainActivity.this).overridePendingTransition(0, 0);
                            }
                        }, 100);

                    } else {
                        showUserAlert();
                    }
                } else {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, SocialLoginCustom.class));
                }
                }
            }
        });


//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationViewBehavior());

    }


    public void showLikedSnackbar() {
        ((pasu.vadivasal.newsfeed.MainActivity) getSupportFragmentManager().findFragmentById(R.id.main_frag)).showLikedSnackbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FirebaseInstanceId.getInstance()!=null){
        System.out.println("refreshed"+ FirebaseInstanceId.getInstance().getToken());}
       changeToolbarImage();
    }


    private void confirmLogout() {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.btn_positive),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // followUnFollow(false);
                                FirebaseAuth.getInstance().signOut();
                                Picasso.with(MainActivity.this).load(R.drawable.user).error(R.drawable.user).into(toolbar_profile_image);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.btn_negative),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create();
        d.setMessage(getString(R.string.logout_alert));
        d.show();
    }


    public void showUserAlert() {
        Context context = MainActivity.this;

        if (!((Activity) context).isFinishing()) {
            try {
                final AlertDialog.Builder ab = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
                View dialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.more_profile_alert, null);
                ab.setView(dialogView);
                ab.setCancelable(true);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
                        Picasso.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).error(R.drawable.user).into((ImageView) dialogView.findViewById(R.id.alert_image));
                    pasu.vadivasal.view.TextView alert_name = (pasu.vadivasal.view.TextView) dialogView.findViewById(R.id.alert_name);
                    LinearLayout alert_logout_label = dialogView.findViewById(R.id.alert_logout_label);
                    alert_name.setText(getString(R.string.hi) + " " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"  !");
                    dialogView.findViewById(R.id.alert_register_bull_owner).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(MainActivity.this, SignUpActivity.class);
                            i.putExtra("type",Appconstants.TYPE_BULL_OWNER);
                            startActivity(i);
                            UserRegisterAlert.dismiss();
                        }
                    });
                    dialogView.findViewById(R.id.alert_register_tamper).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(MainActivity.this, SignUpActivity.class);
                            i.putExtra("type",Appconstants.TYPE_PLAYER);
                            startActivity(i);
                            UserRegisterAlert.dismiss();
                        }
                    });
                    dialogView.findViewById(R.id.alert_register_photographer).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(MainActivity.this, SignUpActivity.class);
                            i.putExtra("type",Appconstants.TYPE_PHOTOGRAPHER);
                            startActivity(i);
                            UserRegisterAlert.dismiss();
                        }
                    });

                    //                if (title.length() == 0) {
//                    tvTitle.setVisibility(View.GONE);
//                } else {
//                    tvTitle.setText(title);
//                }
//                if (nagativeButtonTitle.length() != 0) {
//                    ab.setNegativeButton((CharSequence) nagativeButtonTitle, dialogClickListener);
//                }
//                System.out.println("message ...d"+message.replace("\\n","<br>"));
//                tvDescription.setText(Html.fromHtml(message.replace("\\n","<br>")));
//                ab.setPositiveButton((CharSequence) positiveButtonTitle, dialogClickListener);
                    UserRegisterAlert = ab.create();
                    UserRegisterAlert.show();
                    alert_logout_label.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmLogout();
                            UserRegisterAlert.dismiss();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(UserRegisterAlert!=null && UserRegisterAlert.isShowing())
            UserRegisterAlert.dismiss();
    }

    public void changeToolbarImage(){
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frag);
        if (currentFragment instanceof AutoLoadingFragment) {
            tvAddImage.setVisibility(View.VISIBLE);
            toolbar_profile_image.setVisibility(View.GONE);
        } else{
            tvAddImage.setVisibility(View.GONE);
            toolbar_profile_image.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(toolbar_profile_image!=null && MainActivity.this!=null && !SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,MainActivity.this).equals(""))
                        Picasso.with(MainActivity.this)
                                .load(SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,MainActivity.this))
                                .placeholder(R.drawable.user)

                                .error(R.drawable.user)
                                // .transform(new CircleTransformation())
                                .into(toolbar_profile_image);
                }
            },500);
        }
    }
}
