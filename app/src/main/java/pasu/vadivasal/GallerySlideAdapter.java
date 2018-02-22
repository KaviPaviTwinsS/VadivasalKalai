package pasu.vadivasal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Admin on 26-11-2017.
 */



public class GallerySlideAdapter extends AppCompatActivity implements GalleryAdapter.ItemClickListener {
    GalleryAdapter adapter;
//    int[] data = {
//            R.drawable.j1, R.drawable.j2,
//            R.drawable.j3, R.drawable.j4,
//            R.drawable.j5, R.drawable.j6,
//            R.drawable.j7, R.drawable.j8
//    };
    /* int[] data = {
             R.drawable.ic_stars_black_24dp, R.drawable.ic_star_border_black_24dp,
             R.drawable.ic_settings_black_24dp, R.drawable.ic_image_black_24dp,
             R.drawable.ic_chat_black_24dp, R.drawable.ic_group_black_24dp,
             R.drawable.ic_arrow_back_black_24dp, R.drawable.ic_perm_identity_black_24dp
     };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        ImageView backImg=(ImageView)findViewById(R.id.slideImg);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(GallerySlideAdapter.this,PhotosAct.class));
          finish();
            }
        });

//        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photoGalleryRecycler);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        //adapter = new GalleryAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {


    }

    @Override
    protected void onResume() {
        super.onResume();
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photoGalleryRecycler);
//        int numberOfColumns = 3;
//        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//        adapter = new GalleryAdapter(this, data);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);
    }
}
