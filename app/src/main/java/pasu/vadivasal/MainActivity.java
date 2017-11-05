package pasu.vadivasal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import pasu.vadivasal.Profile.MaterialUpConceptActivity;
import pasu.vadivasal.dashboard.DashboardAdapter;
import pasu.vadivasal.dashboard.DashboardMainFragment;
import pasu.vadivasal.matchList.MatchListMainFragment;
import pasu.vadivasal.moreSettings.MoreListFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //  mTextMessage.setText(R.string.title_home);
                    getSupportFragmentManager().beginTransaction().add(R.id.main_frag,new DashboardMainFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:

                    getSupportFragmentManager().beginTransaction().add(R.id.main_frag,new MatchListMainFragment()).commit();
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //    mTextMessage.setText(R.string.title_notifications);
                    getSupportFragmentManager().beginTransaction().add(R.id.main_frag,new MoreListFragment()).commit();
//                    Intent i=new Intent(MainActivity.this, MaterialUpConceptActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frag,new DashboardMainFragment()).commit();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationViewBehavior());

    }



}
