package pasu.vadivasal.contactus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;


public class ContactUsActivity extends BaseActivity implements OnTabSelectedListener {
    ContactUsPager contactUsPager;
    //    FloatingActionButton fabStartMatch;
    LinearLayout layoutNoInternet;
    // TabLayout tabLayoutMatches;
    AppBarLayout
            app_bar_layout;
    CollapsingToolbarLayout
            collapsing_toolbar;
    androidx.appcompat.widget.Toolbar
            toolbar;
    TabLayout
            tabLayoutMatches;
    RelativeLayout
            mainLayoutForTab;
    ViewPager
            viewPager;
    FloatingActionButton
            fabStartMatch;
    pasu.vadivasal.view.Button
            btnApplyFilter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_contact));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        //init

        layoutNoInternet = (LinearLayout
                ) findViewById(R.id.layoutNoInternet);
        app_bar_layout = (AppBarLayout
                ) findViewById(R.id.app_bar_layout);
        collapsing_toolbar = (CollapsingToolbarLayout
                ) findViewById(R.id.collapsing_toolbar);

        tabLayoutMatches = (TabLayout
                ) findViewById(R.id.tabLayoutMatches);
        mainLayoutForTab = (RelativeLayout
                ) findViewById(R.id.mainLayoutForTab);
        viewPager = (ViewPager
                ) findViewById(R.id.pagerMatches);
        fabStartMatch = (FloatingActionButton
                ) findViewById(R.id.fabStartMatch);
        btnApplyFilter = (pasu.vadivasal.view.Button) findViewById(R.id.btnApplyFilter);


        this.layoutNoInternet.setVisibility(View.GONE);
        this.tabLayoutMatches.addTab(this.tabLayoutMatches.newTab().setText(getString(R.string.tab_write)));
        this.tabLayoutMatches.addTab(this.tabLayoutMatches.newTab().setText(getString(R.string.tab_speak)));
        this.tabLayoutMatches.setTabGravity(0);
        this.tabLayoutMatches.setTabMode(1);
        this.contactUsPager = new ContactUsPager(getSupportFragmentManager(), this.tabLayoutMatches.getTabCount());
        this.viewPager.setOffscreenPageLimit(2);
        this.viewPager.setAdapter(this.contactUsPager);
        this.viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this.tabLayoutMatches));
        this.tabLayoutMatches.addOnTabSelectedListener(this);
        this.fabStartMatch.setVisibility(View.GONE);
        getSupportActionBar().setElevation(0.0f);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            Utils.finishActivitySlide(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Utils.finishActivitySlide(this);
    }

    public void onTabSelected(Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
        Utils.hideKeyboard(getApplicationContext(), getCurrentFocus());
        switch (tab.getPosition()) {
        }
    }

    public void onTabUnselected(Tab tab) {
    }

    public void onTabReselected(Tab tab) {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
