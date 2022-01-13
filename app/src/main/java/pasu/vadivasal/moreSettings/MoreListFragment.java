package pasu.vadivasal.moreSettings;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.contactus.ContactUsActivity;
import pasu.vadivasal.globalModle.Appconstants;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by developer on 20/10/17.
 */


public class MoreListFragment extends Fragment implements View.OnClickListener {

    TextView rateApp, language, contact_us,about_app;
    private int intLanguageType = 1;
    private TextView share_app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).changeToolbarImage();
        View v = inflater.inflate(R.layout.more_settings, container, false);
        rateApp = v.findViewById(R.id.rateApp);
        about_app=v.findViewById(R.id.about_app);
        share_app=v.findViewById(R.id.share_app);
        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra("android.intent.extra.TEXT",SessionSave.getSession(Appconstants.SHARE_CONTENT,getActivity())+ getString(R.string.share_news_msg));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showAlert(getActivity(),getString(R.string.app_name), SessionSave.getSession(Appconstants.ABOUT_APP,getActivity()),getString(R.string.ok),"",null,true);
            }
        });
        contact_us = v.findViewById(R.id.contact_us);
        rateApp.setOnClickListener(this);
        language = v.findViewById(R.id.language);
        language.setOnClickListener(this);
        contact_us.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rateApp:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName()));
                startActivity(browserIntent);
                break;
            case R.id.language:
                Utils.showAlert(getActivity(),"", getString(R.string.tamil_comming),getString(R.string.ok),"",null,true);
               // selectLanguage();
                break;
            case R.id.contact_us:
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
                break;


        }
    }


    /**
     * Dilog pops up which store promo code and verify while save_booking api is called
     */
    public void selectLanguage() {

        // TODO Auto-generated method stub
        final View view = View.inflate(getActivity(), R.layout.languagedialog, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.NewDialog);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        // set the custom dialog components - text, image and button
        final RadioGroup rgrp = (RadioGroup) dialog.findViewById(R.id.paymentdialog_rgrp);
        final RadioButton rbtn_cash = (RadioButton) dialog.findViewById(R.id.paymentdialog_rbtn_cash);
        final RadioButton rbtn_card = (RadioButton) dialog.findViewById(R.id.paymentdialog_rbtn_card);
        final Button btn_submit = (Button) dialog.findViewById(R.id.paymentdialog_btn_submit);
        final Button btn_cancel = (Button) dialog.findViewById(R.id.paymentdialog_btn_cancel);

        if (intLanguageType == 1)
            rbtn_cash.setChecked(true);
        else if (intLanguageType == 2)
            rbtn_card.setChecked(true);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int selectedId = rgrp.getCheckedRadioButtonId();


                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);

                if (radioButton != null) {
                    if (radioButton.getText().toString().equals(getResources().getString(R.string.english))) {
                        intLanguageType = 1;
                        setLocale(new Locale("en"));
                        //   paymenttype.setText(getResources().getString(R.string.payment_cash));
                    } else if (radioButton.getText().toString().equals(getResources().getString(R.string.tamil))) {
                        intLanguageType = 2;
                        setLocale(new Locale("tl"));
                        //paymenttype.setText(getResources().getString(R.string.payment_card));
                    }

                    dialog.dismiss();
                } else {
                    // Toast.makeText(getActivity(), getResources().getString(R.string.select_payment), Toast.LENGTH_LONG).show();
                }

                // Log.e("selec payment type ", String.valueOf(intLanguageType));


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                    intLanguageType = 0;
//                    paymenttype.setText(getResources().getString(R.string.cash_card));
                dialog.dismiss();
            }
        });

    }


    @SuppressWarnings("deprecation")
    private void setLocale(Locale locale){
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
            getApplicationContext().createConfigurationContext(configuration);
        }
        else{
            configuration.locale=locale;
            resources.updateConfiguration(configuration,displayMetrics);
        }
    }

}
