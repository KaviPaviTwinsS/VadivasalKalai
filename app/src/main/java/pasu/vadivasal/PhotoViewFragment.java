package pasu.vadivasal;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Admin on 09-01-2018.
 */

public class PhotoViewFragment extends Fragment {
    private PhotoDraweeView mPhotoDraweeView;
    private String imageUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_photo_view,container,false);
        mPhotoDraweeView = (PhotoDraweeView)view.findViewById(R.id.photo_drawee_view);
        if (getArguments() != null) {
            imageUrl = getArguments().getString("imageUrl");
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
//                    Toast.makeText(view.getContext(), "onViewTap", Toast.LENGTH_SHORT).show();
                }
            });

            mPhotoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Toast.makeText(v.getContext(), "onLongClick", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else {
//            Toast.makeText(getActivity(), "image url null", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
