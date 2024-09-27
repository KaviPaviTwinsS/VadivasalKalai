package pasu.vadivasal;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class PhotoViewActivity extends AppCompatActivity {
    PhotoDraweeView mPhotoDraweeView;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        mPhotoDraweeView = (PhotoDraweeView) findViewById(R.id.photo_drawee_view);
        if (getIntent() != null) {
            imageUrl = getIntent().getStringExtra("imageUrl");
        }

        if (imageUrl != null) {
            mPhotoDraweeView.setPhotoUri(Uri.parse(imageUrl));
            mPhotoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
//                    Toast.makeText(view.getContext(), "onPhotoTap :  x =  " + x + ";" + " y = " + y,
//                            Toast.LENGTH_SHORT).show();
                }
            });
            mPhotoDraweeView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
               //     Toast.makeText(view.getContext(), "onViewTap", Toast.LENGTH_SHORT).show();
                }
            });

            mPhotoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   // Toast.makeText(v.getContext(), "onLongClick", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else {
          //  Toast.makeText(this, "image url null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
