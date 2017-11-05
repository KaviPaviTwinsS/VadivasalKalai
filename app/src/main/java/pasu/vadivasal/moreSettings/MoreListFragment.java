package pasu.vadivasal.moreSettings;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import pasu.vadivasal.R;
import pasu.vadivasal.contactus.ContactUsActivity;

/**
 * Created by developer on 20/10/17.
 */


public class MoreListFragment extends Fragment implements View.OnClickListener {

    TextView rateApp, language, contact_us;
    private int intLanguageType = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.more_settings, container, false);
        rateApp = v.findViewById(R.id.rateApp);
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
                selectLanguage();
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
                        //   paymenttype.setText(getResources().getString(R.string.payment_cash));
                    } else if (radioButton.getText().toString().equals(getResources().getString(R.string.tamil))) {
                        intLanguageType = 2;
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
}
