package pasu.vadivasal.contactus;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.Utils;


public class ContactWriteFragment extends Fragment {
//    Button btn_send;
//    EditText edt_comment;
//    EditText edt_name;
//    EditText edt_mobile;
//    TextInputLayout ilName;
//    TextView txt_count;
    private String type;


    android.support.design.widget.TextInputLayout
            ilName;
    pasu.vadivasal.view.EditText
            edt_name, edt_mobile, edt_comment;
    pasu.vadivasal.view.TextView
            txt_count;
    pasu.vadivasal.view.Button
            btn_send;

    class C05721 implements OnClickListener {
        C05721() {
        }

        public void onClick(View view) {
            ContactWriteFragment.this.validation();
        }
    }

    class C05732 implements TextWatcher {
        C05732() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().trim().length() == 0) {
                ContactWriteFragment.this.txt_count.setText(R.string.words);
            }
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            ContactWriteFragment.this.txt_count.setText(Utils.wordCount(ContactWriteFragment.this.edt_comment.getText().toString()) + "/200 (words)");
            if (ContactWriteFragment.this.edt_comment.getText().toString().trim().length() == 0) {
                ContactWriteFragment.this.txt_count.setText(R.string.words);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.write_screen, container, false);
        //   ButterKnife.bind((Object) this, rootView);


        ilName = (android.support.design.widget.TextInputLayout
                ) rootView.findViewById(R.id.ilName);
        edt_name = (pasu.vadivasal.view.EditText
                ) rootView.findViewById(R.id.edt_name);
        edt_mobile = (pasu.vadivasal.view.EditText
                ) rootView.findViewById(R.id.edt_mobile);
        edt_comment = (pasu.vadivasal.view.EditText
                ) rootView.findViewById(R.id.edt_comment);
        txt_count = (pasu.vadivasal.view.TextView
                ) rootView.findViewById(R.id.txt_count);
        btn_send = (pasu.vadivasal.view.Button
                ) rootView.findViewById(R.id.btn_send);
//        this.type = getActivity().getIntent().getExtras().getString(AppConstants.EXTRA_CONTACT_TYPE, "");
        return rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (!CricHeroes.getApp().isGuestUser()) {
//            setInitialValues();
//        }
        this.btn_send.setOnClickListener(new C05721());
        this.edt_comment.addTextChangedListener(new C05732());
    }

    private void setInitialValues() {
//        this.edt_name.setText(CricHeroes.getApp().getCurrentUser().getName());
//        this.edt_mobile.setText(CricHeroes.getApp().getCurrentUser().getMobile());
        this.edt_comment.requestFocus();
        this.edt_comment.setFocusable(true);
        Utils.showKeyboard(getActivity(), this.edt_comment);
    }

    private void validation() {
        if (Utils.isEmptyString(this.edt_name.getText().toString())) {
            Utils.showToast(getActivity(), getString(R.string.error_please_enter_name), 1, false);
        } else if (Utils.isEmptyString(this.edt_mobile.getText().toString())) {
            Utils.showToast(getActivity(), getString(R.string.error_please_enter_phone_number), 1, false);
        } else if (!Utils.isNetworkAvailable(getActivity())) {
            Utils.showToast(getActivity(), getString(R.string.alert_no_internet_found), 1, false);
        } else if (Utils.wordCount(this.edt_comment.getText().toString()) > 200) {
            Utils.showToast(getActivity(), getString(R.string.comment_limit), 1, false);
        } else {
            writeContactApi();
        }
    }

    public void onStop() {
        //  ApiCallManager.cancelCall("contact_us");
        super.onStop();
    }

    public void writeContactApi() {
        final Dialog dialog = Utils.showProgress(getActivity(), true);
//        ApiCallManager.enqueue("contact_us", CricHeroes.apiClient.contactUs(Utils.udid(getActivity()), new ContactUsRequest(this.edt_name.getText().toString(), this.edt_mobile.getText().toString(), this.edt_comment.getText().toString(), this.type)), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                Utils.hideProgress(dialog);
//                if (err != null) {
//                    Logger.m176d("writeContactApi err " + err);
//                    Utils.showToast(ContactWriteFragment.this.getActivity(), err.getMessage(), 1, false);
//                    return;
//                }
//                try {
//                    Utils.showToast(ContactWriteFragment.this.getActivity(), new JSONObject(response.getData().toString()).optString("message"), 2, true);
//                    ContactWriteFragment.this.edt_comment.setText("");
//                    ContactWriteFragment.this.txt_count.setText(R.string.words);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
