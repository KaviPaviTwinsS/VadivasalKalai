package pasu.vadivasal.contactus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import pasu.vadivasal.R;


public class ContactSpeakFragment extends Fragment {
    RelativeLayout relCall;

    class C05711 implements OnClickListener {
        C05711() {
        }

        public void onClick(View view) {
            Intent i = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + ContactSpeakFragment.this.getString(R.string.contact_phone_number)));
          //  i.addFlags(ClientDefaults.MAX_MSG_SIZE);
           i.addFlags(268435456);
            ContactSpeakFragment.this.startActivity(i);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.speak_screen, container, false);
        relCall=rootView.findViewById(R.id.rel_call);
        return rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.relCall.setOnClickListener(new C05711());
    }
}
