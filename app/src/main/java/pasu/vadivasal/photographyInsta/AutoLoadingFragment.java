package pasu.vadivasal.photographyInsta;

//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Point;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.FileProvider;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.Toast;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.sa90.materialarcmenu.ArcMenu;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import pasu.vadivasal.R;
//import pasu.vadivasal.adapter.base.BaseQuickAdapter;
//import pasu.vadivasal.model.PostModel;
//import pasu.vadivasal.view.FeedContextMenu;
//import pasu.vadivasal.view.FeedContextMenuManager;
//
//
//public class AutoLoadingFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, OnItemSelectedListener,
//        PostAutoAdapter.OnFeedItemClickListener, FeedContextMenu.OnFeedContextMenuItemClickListener {
//    public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
//    private static final String KEY_TITLE = "key_title";
//    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
//    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 103;
//    private static final int REQUEST_CAMARA_ACCESS_PERMISSION = 102;
//    private static final int REQUEST_VIDEO_TRIMMER = 3;
//    int ballType = -1;
//    private ArcMenu arcMenu;
//    private FloatingActionButton fabImage;
//    private FloatingActionButton fabVideo;
//    private PostAutoAdapter adapter;
//    private boolean batsman = true;
//    private List<String> filterList;
//    private List<String> filterListCode;
//    private boolean initSpinListener = true;
//    private ArrayList<PostModel> itemArrayList;
//    private boolean loadmore;
//    private RecyclerView rvMatches;
//    private int GALLERY = 1, CAMERA = 4;
//    private Uri filePath;
//    private DataSnapshot baseResponse;
//    private String TOUR_ID = "tour1";
//    private android.widget.TextView tvTitle;
//    private ChildEventListener childAddListner;
//    private long lastItemId = 0;
//    private Bitmap bitmapImage;
//    private String urlImage;
//    private PostModel postModel;
//    public static AutoLoadingFragment newInstance(String Title) {
//        AutoLoadingFragment fragmentAction = new AutoLoadingFragment();
//        Bundle args = new Bundle();
//        args.putString(KEY_TITLE, Title);
//        fragmentAction.setArguments(args);
//        return fragmentAction;
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setTitle("Firebase Newsfeed");
//        View rootView = inflater.inflate(R.layout.layout_firebasecontent, container, false);
//        System.out.println("Autoloading Clicked");
////        this.TOUR_ID = getActivity().getIntent().getStringExtra(Appconstants.TourID);
////        progressBar = rootView.findViewById(R.id.progressBar);
////        viewEmpty = rootView.findViewById(R.id.viewEmpty);
////        tvDetail = rootView.findViewById(R.id.tvDetail);
////        tvTitle = rootView.findViewById(R.id.tvTitle);
//        itemArrayList = new ArrayList<>();
//        rvMatches = (RecyclerView) rootView.findViewById(R.id.rvFeed);
//        arcMenu = (ArcMenu) rootView.findViewById(R.id.arcMenu);
//        fabImage = (FloatingActionButton) rootView.findViewById(R.id.fabImage);
//        fabVideo = (FloatingActionButton) rootView.findViewById(R.id.fabVideo);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        this.rvMatches.setLayoutManager(layoutManager);
////        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
//        setData();
//
//        this.rvMatches.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (FeedContextMenuManager.getInstance().contextMenuView != null) {
//                    FeedContextMenuManager.getInstance().hideContextMenuWithoutAnim();
//                }
//            }
//        });
//
//        return rootView;
//    }
//
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        this.itemArrayList = new ArrayList();
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        fabImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPictureDialog();
//            }
//        });
//        fabVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickFromGallery();
//
//            }
//        });
//    }
//    private void pickFromGallery() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.permission_read_storage_rationale), REQUEST_STORAGE_READ_ACCESS_PERMISSION);
//        } else {
//            Intent intent = new Intent();
//            intent.setTypeAndNormalize("video/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), REQUEST_VIDEO_TRIMMER);
//        }
//    }
//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Select photo from gallery",
//                "Capture photo from camera"};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                choosePhotoFromGallary();
//                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    }
//
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, GALLERY);
//    }
//
//    private void takePhotoFromCamera() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.CAMERA, getString(R.string.permission_read_storage_rationale), REQUEST_CAMARA_ACCESS_PERMISSION);
//        } else {
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                filePath = FileProvider.getUriForFile(getActivity(),
//                        getActivity().getPackageName(),
//                        photoFile);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
//                startActivityForResult(intent, CAMERA);
//            }
//
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        String mCurrentPhotoPath;
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    private void requestPermission(final String permission, String rationale, final int requestCode) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
//            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
//            builder.setMessage(rationale);
//            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
//                }
//            });
//            builder.setNegativeButton(getString(R.string.cancel), null);
//            builder.show();
//        } else {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
//        }
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    pickFromGallery();
//                }
//                break;
//            case REQUEST_CAMARA_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    takePhotoFromCamera();
//                }
//                break;
//            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    DownloadFileFromURL.execute();
//                    if (bitmapImage != null) {
//                        addWaterMark(bitmapImage, urlImage, postModel);
//                    }
//
//                }
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == getActivity().RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                filePath = data.getData();
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), AddtoFirebaseActivity.class);
//                intent.putExtra("imagePath", filePath.toString());
//                intent.putExtra("flag", 2);
//                startActivity(intent);
//            }
//
//        } else if (requestCode == CAMERA) {
//            if (data != null) {
////            String file = data.getExtras().get("data").toString();
//                Log.d("cameraImage", filePath.toString());
//                Intent intent = new Intent(getActivity(), AddtoFirebaseActivity.class);
//                intent.putExtra("imagePath", filePath.toString());
//                intent.putExtra("flag", 2);
//                startActivity(intent);
//                Toast.makeText(getActivity(), "Success add", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        else if (requestCode == REQUEST_VIDEO_TRIMMER) {
//            final Uri selectedUri = data.getData();
//            if (selectedUri != null) {
//                startTrimActivity(selectedUri);
//            } else {
//                Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_selected_video, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    private void startTrimActivity(@NonNull Uri uri) {
//
////        Intent intent = new Intent(getActivity(), TrimmerActivity.class);
////        intent.putExtra(EXTRA_VIDEO_PATH, FileUtils.getPath(getActivity(), uri));
////        startActivity(intent);
//    }
//    public void setData() {
////        this.progressBar.setVisibility(View.VISIBLE);
//        System.out.println("Setdata");
//        getBattingLeaderboard(null, null, false);
////        AutoLoadingFragment.this.adapter.setOnFeedItemClickListener(this);
//    }
//
//    public void onStop() {
////        ApiCallManager.cancelCall("get_bat_leader_board");
////        ApiCallManager.cancelCall("get_bowl_leader_board");
//        super.onStop();
//    }
//
//    public void getBattingLeaderboard(String page, Long datetime, final boolean refresh) {
//        if (!this.loadmore) {
//            //  this.progressBar.setVisibility(View.VISIBLE);
//        }
//        this.loadmore = false;
//        emptyViewVisibility(false);
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("MyPosts");
//        final String TAG = "Commentary itemArrayList";
//
//        Query queryRef;
//        System.out.println("postionnnncomm" + page + TOUR_ID);
//        if (page == null)
//            queryRef = myRef
//                    .orderByChild("time")
//                    .limitToLast(10);
//        else
//            queryRef = myRef.orderByChild("time").endAt(datetime).limitToFirst(3);
//
//        ValueEventListener valueEventListener = (new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever itemArrayList at this location is updated.
//                // progressBar.setVisibility(View.GONE);
////                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
//                if (getActivity() != null) {
//                    if (!dataSnapshot.exists()) {
//                        AutoLoadingFragment.this.loadmore = true;
//                        if (AutoLoadingFragment.this.adapter != null) {
//                            AutoLoadingFragment.this.adapter.loadMoreFail();
//                        }
//                        if (AutoLoadingFragment.this.itemArrayList.size() <= 0) {
//                            AutoLoadingFragment.this.emptyViewVisibility(true);
//                            AutoLoadingFragment.this.rvMatches.setVisibility(View.GONE);
//                            return;
//                        }
//                        return;
//                    }
//                    AutoLoadingFragment.this.baseResponse = dataSnapshot;
//                    ArrayList<PostModel> arrayList = new ArrayList<>();
//                    for (DataSnapshot md : dataSnapshot.getChildren()) {
//                        if (md.getValue() != null && !md.getValue().equals("")) {
//                            PostModel matchDetails = md.getValue(PostModel.class);
//                            if (arrayList.size() > 0)
//                                System.out.println("datatacccc" + arrayList.size() + "__" + lastItemId + "___" + matchDetails.getTime());
//                            if (arrayList.size() > 0 && lastItemId == matchDetails.getTime()) {
//
//                                System.out.println("datatacccc");
//                            } else {
//
////                                    matchDetails.setMatch_id(Integer.parseInt(md.getTournament_key()));
////                                    matchDetails.setmatchShortSummary(CommanData.toString(md.getValue()));
//                                arrayList.add(matchDetails);
//                            }
//                        }
//                    }
////                    ArrayList<PostModel> arrayList = new ArrayList<>(Arrays.asList(Utils.fromJson(Utils.toString(arrayLists), PostModel[].class)));
////                    for (int i = 0; i < arrayList.size(); i++) {
////                        System.out.println("cameaddbat" + i + "____" + arrayList.get(i).getTime());
////                        itemArrayList.add(arrayList.get(i));
////                    }
//                    Collections.reverse(arrayList);
//                    lastItemId = arrayList.get(arrayList.size() - 1).getTime();
//                    if (itemArrayList.size() == 0)
//                        itemArrayList.addAll(arrayList);
//                    System.out.println("datataccccitem" + itemArrayList.size());
//                    //   itemArrayList.addAll(Arrays.asList(Utils.fromJson(Utils.toString(arrayList),PostModel[].class)));
//                    //    System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav)));
//                    // ArrayList<PostModel> datav = new ArrayList<PostModel>(sh.values());
////                    System.out.println("datavvvf  " + TAG + (pasu.vadivasal.android.Utils.toString(arrayList.get(0))));
//
//                    if (AutoLoadingFragment.this.adapter == null) {
//                        System.out.println("NEW ADAPTER SETbat");
//                        //AutoLoadingFragment .this.itemArrayList.addAll(arrayList);
//                        AutoLoadingFragment.this.adapter = new PostAutoAdapter(AutoLoadingFragment.this.getActivity(), R.layout.item_feed, AutoLoadingFragment.this.itemArrayList);
//                        AutoLoadingFragment.this.adapter.setEnableLoadMore(true);
//                        AutoLoadingFragment.this.rvMatches.setAdapter(AutoLoadingFragment.this.adapter);
////                        AutoLoadingFragment.this.adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//                        // AutoLoadingFragment .this.rvMatches.addOnItemTouchListener(new C13401());
//                        AutoLoadingFragment.this.adapter.setOnLoadMoreListener(AutoLoadingFragment.this, AutoLoadingFragment.this.rvMatches);
//                        AutoLoadingFragment.this.adapter.setOnFeedItemClickListener(AutoLoadingFragment.this);
//                        if (arrayList.size() % 3 != 0) {
//                            AutoLoadingFragment.this.adapter.loadMoreEnd();
//                        }
//                        final DatabaseReference myRefs = database.getReference("MyPostsjyh");
//                        try {
//                            // String firstKey = (String) ((HashMap<String, PostModel>) dataSnapshot.getValue()).keySet().toArray()[0];
//                            System.out.println("datavvv  ssfirst key" + adapter.getData().size() + "__" + itemArrayList.get(0).getTime());
//                            myRefs.orderByChild("time").startAt(itemArrayList.get(0).getTime()).addChildEventListener(childAddListner);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        if (refresh) {
//                            AutoLoadingFragment.this.adapter.getData().clear();
//                            AutoLoadingFragment.this.itemArrayList.clear();
//                            AutoLoadingFragment.this.itemArrayList.addAll(arrayList);
//                            AutoLoadingFragment.this.adapter.setNewData(arrayList);
//                            AutoLoadingFragment.this.adapter.setEnableLoadMore(true);
//                            AutoLoadingFragment.this.rvMatches.scrollToPosition(0);
//                        } else {
//                            System.out.println("datataccccitemaddd" + arrayList.size() + AutoLoadingFragment.this.adapter.getItemCount());
//                            AutoLoadingFragment.this.adapter.addData((Collection) arrayList);
//                            AutoLoadingFragment.this.adapter.loadMoreComplete();
//                            System.out.println("datataccccitemafter" + arrayList.size() + AutoLoadingFragment.this.adapter.getItemCount());
//                        }
//
//                    }
//                    if (AutoLoadingFragment.this.baseResponse != null && arrayList.size() % 3 != 0) {
//                        AutoLoadingFragment.this.adapter.loadMoreEnd();
//                    } else
//                        AutoLoadingFragment.this.loadmore = true;
//                    if (AutoLoadingFragment.this.itemArrayList.size() == 0) {
//                        AutoLoadingFragment.this.emptyViewVisibility(true);
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//
//        });
//
//
//        childAddListner = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                System.out.println("commentary added" + dataSnapshot.getValue());
//
//                if (dataSnapshot != null && itemArrayList != null) {
//
////                    PostModel arrayLists = dataSnapshot.getValue(PostModel.class);
////                    if (itemArrayList.size() > 0 && itemArrayList.get(0).getTime() != (arrayLists).getTime()) {
////                        System.out.println("commentary adding data");
////                        AutoLoadingFragment.this.adapter.addData(0, arrayLists);
////                        AutoLoadingFragment.this.adapter.notifyDataSetChanged();
////                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                //  System.out.println("nagaaaa_chile");
////                PostModel arrayLists = dataSnapshot.getValue(PostModel.class);
////                AutoLoadingFragment.this.adapter.notifyDataSetChanged();
////                System.out.println("arrayLists"+arrayLists.likesCount+"__"+arrayLists.url);
////                if (itemArrayList.size() > 0 && itemArrayList.get(0).getTime() != (arrayLists).getTime()) {
////                    System.out.println("commentary adding data");
////                    AutoLoadingFragment.this.adapter.addData(0, arrayLists);
////                    AutoLoadingFragment.this.adapter.notifyDataSetChanged();
////                }
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//
//        queryRef.addListenerForSingleValueEvent(valueEventListener);
//    }
//
//    private String getHighlightedText(String text, boolean isHighlight) {
//        if (isHighlight) {
//            return "<b>" + text + "</b>";
//        }
//        return text;
//    }
//
//    public void onLoadMoreRequested() {
//        System.out.println("onLoadMoreRequested" + this.loadmore + "___" + (itemArrayList.size() % 10 != 0));
//        if (!this.loadmore || (itemArrayList.size() % 3 != 0)) {
//            new Handler().postDelayed(new C13422(), 1500);
//        } else if (this.batsman) {
//            System.out.println("Load more" + itemArrayList.get(itemArrayList.size() - 1).getTime());
//            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTime()), (itemArrayList.get(itemArrayList.size() - 1).getTime()), false);
//        } else {
//            // getBowlingLeaderboard(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
//        }
//    }
//
////    private View getEmptyView() {
////        if (getActivity() != null) {
////            this.viewEmpty = getActivity().getLayoutInflater().inflate(R.layout.raw_empty_view, null);
////            TextView tvTitle = (TextView) this.viewEmpty.findViewById(R.id.tvTitle);
////            TextView tvDetail = (TextView) this.viewEmpty.findViewById(R.id.tvDetail);
////            ((ImageView) this.viewEmpty.findViewById(R.id.ivImage)).setImageResource(R.drawable.about);
////            //tvTitle.setText(R.string.leaderbord_blank_stat);
////            tvDetail.setVisibility(View.GONE);
////        }
////        return this.viewEmpty;
////    }
//
//    private void emptyViewVisibility(boolean b) {
//        if (b) {
////            this.viewEmpty.setVisibility(View.VISIBLE);
//////            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
//////            this.tvTitle.setText(R.string.commentary_empty);
////            this.tvDetail.setVisibility(View.GONE);
//            return;
//        }
//        // this.viewEmpty.setVisibility(View.GONE);
//    }
//
//    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        if (this.initSpinListener) {
//            this.initSpinListener = false;
//        } else if (this.batsman) {
//            System.out.println("On iTem select");
//            getBattingLeaderboard(null, null, true);
//        }
//    }
//
//    public void onNothingSelected(AdapterView<?> adapterView) {
//    }
//
//    @Override
//    public void onCommentsClick(View v, int position, PostModel postModel) {
//        final Intent intent = new Intent(getActivity(), CommentsAutoActivity.class);
//        int[] startingLocation = new int[2];
//        v.getLocationOnScreen(startingLocation);
//        intent.putExtra(CommentsAutoActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
//        intent.putExtra("postID",postModel.postId);
//        startActivity(intent);
//        getActivity().overridePendingTransition(0, 0);
//    }
//
//    @Override
//    public void onMoreClick(View v, int position) {
//       // FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, position, this);
//onSharePhotoClick(position);
//
////        int[] startingLocation = new int[2];
////        v.getLocationOnScreen(startingLocation);
////        startingLocation[0] += v.getWidth() / 2;
//////        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
////        getActivity().overridePendingTransition(0, 0);
//    }
//
//    @Override
//    public void onProfileClick(View v) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onReportClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onSharePhotoClick(int feedItem) {
//        postModel = itemArrayList.get(feedItem);
//        urlImage = postModel.url;
//        if (postModel.typeOfPost == 1) {
//            Picasso.with(getActivity())
//                    .load(postModel.videoThumbnail)
//                    .into(picassoImageTarget(getActivity(), "imageDir", "my_image.jpeg"));
//        }else {
//            Picasso.with(getActivity())
//                    .load(postModel.url)
//                    .into(picassoImageTarget(getActivity(), "imageDir", "my_image.jpeg"));
//        }
//
////        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onCopyShareUrlClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onCancelClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    public void addWaterMark(Bitmap src, String url, PostModel model) {
//        int w = src.getWidth();
//        int h = src.getHeight();
//        Point point = new Point();
//        point.set((int) (w/1.5),(int) (h/2.8));
//        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
//        Canvas canvas = new Canvas(result);
//        canvas.drawBitmap(src, 0, 0, null);
//
//
//        Bitmap waterMark = Bitmap.createScaledBitmap( BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher),(w/4),(h/4),false);
//
//        canvas.drawBitmap(waterMark,point.x, point.y, null);
//      if (result != null){
//          if (isStoragePermissionGranted()) {
//              String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), result,"title", null);
//              Uri bitmapUri = Uri.parse(bitmapPath);
//              Intent intent = new Intent(Intent.ACTION_SEND);
//              intent.setType("image/*");
//              intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
//              intent.putExtra(Intent.EXTRA_TEXT,"https://ilovejallikattu.000webhostapp.com/images/image.jpg" + "?id=" + model.postId );
//              intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//              startActivity(Intent.createChooser(intent , "Share"));
//          }
//      }
////        return result;
//    }
//
//    class C13422 implements Runnable {
//        C13422() {
//        }
//
//        public void run() {
//            AutoLoadingFragment.this.adapter.loadMoreEnd();
//        }
//    }
//
//    public  boolean isStoragePermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v("TAG","Permission is granted");
//                return true;
//            } else {
//
//                Log.v("TAG","Permission is revoked");
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v("TAG","Permission is granted");
//            return true;
//        }
//    }
//
//    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
//        Log.d("picassoImageTarget", " picassoImageTarget");
//        return new Target() {
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (bitmap != null) {
//                            bitmapImage = bitmap;
//                            addWaterMark(bitmap, urlImage, postModel);
//                        }
//
//                    }
//                }).start();
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//            }
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                if (placeHolderDrawable != null) {}
//            }
//        };
//    }
//}

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sa90.materialarcmenu.ArcMenu;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import life.knowledge4.videotrimmer.utils.FileUtils;
import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.VideoMessenger;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.PostModel;
import pasu.vadivasal.regLogin.SocialLoginCustom;
import pasu.vadivasal.videopackage.TrimmerActivity;
import pasu.vadivasal.view.FeedContextMenu;
import pasu.vadivasal.view.FeedContextMenuManager;
import tourguide.tourguide.ChainTourGuide;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Sequence;
import tourguide.tourguide.ToolTip;


public class AutoLoadingFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, AdapterView.OnItemSelectedListener,
        PostAutoAdapter.OnFeedItemClickListener, FeedContextMenu.OnFeedContextMenuItemClickListener {
    public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    private static final String KEY_TITLE = "key_title";
    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 1010;
    private static final int REQUEST_CAMARA_ACCESS_PERMISSION = 102;
    private static final int REQUEST_VIDEO_TRIMMER = 10;
    int ballType = -1;
    private ViewGroup viewContent, viewToolTip;
    private ArcMenu arcMenu;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabImage;
    private FloatingActionButton fabVideo;
    private PostAutoAdapter adapter;
    private boolean batsman = true;
    private List<String> filterList;
    private List<String> filterListCode;
    private boolean initSpinListener = true;
    private ArrayList<PostModel> itemArrayList;
    private boolean loadmore;
    private RecyclerView rvMatches;
    private int GALLERY = 1, CAMERA = 4;
    private Uri filePath;
    private DataSnapshot baseResponse;
    private String TOUR_ID = "tour1";
    private android.widget.TextView tvTitle;
    private ChildEventListener childAddListner;
    private long lastItemId = 0;
    private Bitmap bitmapImage;
    private String urlImage;
    private PostModel postModel;
    private LinearLayoutManager layoutManager;
    private View progressBar;
    private ProgressDialog mProgressDialog;
    private androidx.appcompat.app.AlertDialog UserRegisterAlert;
    private int currentplaying = -9;
    private int positiveScroll, negativeScroll;
    private Animation mEnterAnimation, mExitAnimation;

    public static final int OVERLAY_METHOD = 1;
    public static final int OVERLAY_LISTENER_METHOD = 2;

    public static final String CONTINUE_METHOD = "continue_method";
    public ChainTourGuide mTourGuideHandler;

    public static AutoLoadingFragment newInstance(String Title) {
        AutoLoadingFragment fragmentAction = new AutoLoadingFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, Title);
        fragmentAction.setArguments(args);
        return fragmentAction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setup enter and exit animation */
        mEnterAnimation = new AlphaAnimation(0f, 1f);
        mEnterAnimation.setDuration(600);
        mEnterAnimation.setFillAfter(true);

        mExitAnimation = new AlphaAnimation(1f, 0f);
        mExitAnimation.setDuration(600);
        mExitAnimation.setFillAfter(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Firebase Newsfeed");
        SessionSave.saveSession("showToolTip", true, getContext());
        ((MainActivity) getActivity()).changeToolbarImage();
        ((MainActivity) getActivity()).tvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SessionSave.getBooleanSession(Appconstants.FORCE_UPDATE, getActivity())) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if (SessionSave.getSessionInt(Appconstants.LOGIN_TYPE, getActivity()) != 0) {
                            showPhotoVideoAlert();
                        } else {
                            ((MainActivity) getActivity()).showUserAlert();
                        }
                    } else {
                        getActivity().startActivity(new Intent(getActivity(), SocialLoginCustom.class));
                    }
                }
            }
        });

        View rootView = inflater.inflate(R.layout.layout_firebasecontent, container, false);
        viewContent = rootView.findViewById(R.id.content);
        if (SessionSave.getBooleanSession("showToolTip", getContext())) {
            viewToolTip = rootView.findViewById(R.id.tooltiplay);
            viewToolTip.setVisibility(View.VISIBLE);
            viewContent.setVisibility(View.GONE);
            ImageButton btnComments = rootView.findViewById(R.id.btnComments);
            ImageButton btnLike = rootView.findViewById(R.id.btnLike);

//            runOverlay_ContinueMethod(btnLike, btnComments);
            runOverlayListener_ContinueMethod(btnLike, btnComments);
        }

//        this.TOUR_ID = getActivity().getIntent().getStringExtra(Appconstants.TourID);
        progressBar = rootView.findViewById(R.id.progressBar);
//        viewEmpty = rootView.findViewById(R.id.viewEmpty);
//        tvDetail = rootView.findViewById(R.id.tvDetail);
//        tvTitle = rootView.findViewById(R.id.tvTitle);
        itemArrayList = new ArrayList<>();
        rvMatches = (RecyclerView) rootView.findViewById(R.id.rvFeed);
        arcMenu = (ArcMenu) rootView.findViewById(R.id.arcMenu);
        fabImage = (FloatingActionButton) rootView.findViewById(R.id.fabImage);
        fabVideo = (FloatingActionButton) rootView.findViewById(R.id.fabVideo);
//        bottomNavigationView = (BottomNavigationView)rootView.
//                findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        layoutManager = new LinearLayoutManager(getActivity());
        this.rvMatches.setLayoutManager(layoutManager);
//        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        setData();

        this.rvMatches.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (FeedContextMenuManager.getInstance().contextMenuView != null) {
                    FeedContextMenuManager.getInstance().hideContextMenuWithoutAnim();
                }

                System.out.println("Cureeeeeeeeeeeeee" + layoutManager.findLastCompletelyVisibleItemPosition() +
                        "__" + layoutManager.findFirstVisibleItemPosition() + "__" + dy + "__" + positiveScroll);

                if (dy > 0) {
                    positiveScroll += dy;
                    if (positiveScroll > 30 && layoutManager.findLastCompletelyVisibleItemPosition() != currentplaying && layoutManager.findLastCompletelyVisibleItemPosition() != -1) {
//                        View firstItemView = mLayoutManager.findViewByPosition(mLayoutManager.findFirstVisibleItemPosition());
//                        System.out.println("need to play jjjjj" + Math.abs(firstItemView.getY()) / firstItemView.getHeight() + "__" + mLayoutManager.findFirstVisibleItemPosition());
//                        if ((Math.abs(firstItemView.getY()) / firstItemView.getHeight()) > 0.50) {
                        currentplaying = layoutManager.findLastCompletelyVisibleItemPosition();
                        System.out.println("need to play " + currentplaying);
                        ((VideoMessenger) recyclerView.getAdapter()).videoToPlay(currentplaying);
                        positiveScroll = 0;
//                        }
                    }
                } else {
                    negativeScroll += dy;
                    if (negativeScroll < -200 && layoutManager.findFirstVisibleItemPosition() != currentplaying && layoutManager.findFirstVisibleItemPosition() != -1) {
//                        View firstItemView = mLayoutManager.findViewByPosition(mLayoutManager.findFirstVisibleItemPosition());
//                        System.out.println("need to play jjjjj" + Math.abs(firstItemView.getY()) / firstItemView.getHeight());
//                        if ((Math.abs(firstItemView.getY()) / firstItemView.getHeight()) > 0.50) {
                        currentplaying = layoutManager.findFirstVisibleItemPosition();
                        System.out.println("need to play " + currentplaying);
                        ((VideoMessenger) recyclerView.getAdapter()).videoToPlay(currentplaying);
//                        }
                    }
                }
            }
        });
        return rootView;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.itemArrayList = new ArrayList();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        System.out.println("naganagnanganag"+FirebaseAuth.getInstance().getCurrentUser() +"___"+ SessionSave.getSessionInt(Appconstants.LOGIN_TYPE,getActivity()));
        if (FirebaseAuth.getInstance().getCurrentUser() != null && SessionSave.getSessionInt(Appconstants.LOGIN_TYPE, getActivity()) != 0)
            fabImage.setVisibility(View.GONE);
        fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
            }
        });
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStoragePermissionGranted())
                    pickFromGallery();

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (FeedContextMenuManager.getInstance().contextMenuView != null) {
            FeedContextMenuManager.getInstance().hideContextMenuWithoutAnim();
        }
    }

    private void pickFromGallery() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.permission_read_storage_rationale), REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setTypeAndNormalize("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), REQUEST_VIDEO_TRIMMER);
        }
    }

    private void runOverlay_ContinueMethod(ImageButton btnLike, ImageButton btnComments) {
        // the return handler is used to manipulate the cleanup of all the tutorial elements
        ChainTourGuide tourGuide3 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Welcome!")
                        .setDescription("Add Image or Video")
                        .setGravity(Gravity.RIGHT)
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(((MainActivity) getActivity()).tvAddImage);

        ChainTourGuide tourGuide1 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Like")
                        .setDescription("Give Your Likes By Clicking Button")
                        .setGravity(Gravity.RIGHT | Gravity.TOP)
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(btnLike);

        final ChainTourGuide tourGuide2 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                                .setTitle("Comment")
                                .setDescription("See Comments of the post by clicking this button")
                                .setGravity(Gravity.RIGHT | Gravity.TOP)
//                        .setBackgroundColor(Color.parseColor("#c0392b"))
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("calleddddd " + "sssssssss");
                            }
                        })
                )
                .playLater(btnComments);

        /*Overlay overlay = new Overlay()
                .setBackgroundColor(Color.parseColor("#AAFF0000"))
                // Note: disable click has no effect when setOnClickListener is used, this is here for demo purpose
                // if setOnClickListener is not used, disableClick() will take effect
                .disableClick(false)
                .disableClickThroughHole(false)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTutorialHandler.cleanUp();
                    }
                });
*/

        final Sequence sequence = new Sequence.SequenceBuilder()
                .add(tourGuide3, tourGuide1, tourGuide2)
                .setDefaultPointer(null)
                .setContinueMethod(Sequence.ContinueMethod.Overlay)
                .build();

        ChainTourGuide.init(getActivity()).playInSequence(sequence);

//        final ChainTourGuide[] array = sequence.getTourGuideArray();
//        tourGuides.setOverlay(new Overlay()
//                .setBackgroundColor(Color.parseColor("#EE2c3e50"))
//                .setEnterAnimation(mEnterAnimation)
//                .setExitAnimation(mExitAnimation)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SessionSave.saveSession("showToolTip", false, getActivity());
//                        System.out.println("calleddddd "+"sssssssss");
//                        viewToolTip.setVisibility(View.GONE);
////                        viewContent.setVisibility(View.VISIBLE);
//                        tourGuides.cleanUp();
//                    }
//                }));

    }

    private void runOverlayListener_ContinueMethod(ImageButton btnLike, ImageButton btnComments) {
        // the return handler is used to manipulate the cleanup of all the tutorial elements
        ChainTourGuide tourGuide1 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Welcome!")
                        .setDescription("Add Image or Video")
                        .setGravity(Gravity.BOTTOM)
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTourGuideHandler.next();
                            }
                        })
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(((MainActivity) getActivity()).tvAddImage);

        ChainTourGuide tourGuide2 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Like")
                        .setDescription("Give Your Likes By Clicking Button")
                        .setGravity(Gravity.TOP | Gravity.RIGHT)
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTourGuideHandler.next();
                            }
                        })
                )
                .playLater(btnLike);

        ChainTourGuide tourGuide3 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Comment")
                        .setDescription("See Comments of the post by clicking this button")
                        .setGravity(Gravity.TOP | Gravity.RIGHT)
                )
                // note that there is not Overlay here, so the default one will be used
                .playLater(btnComments);

        Sequence sequence = new Sequence.SequenceBuilder()
                .add(tourGuide1, tourGuide2, tourGuide3)
                .setDefaultOverlay(new Overlay()
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SessionSave.saveSession("showToolTip", false, getActivity());
                                System.out.println("calleddddd " + "sssssssss");
                                viewToolTip.setVisibility(View.GONE);
                                viewContent.setVisibility(View.VISIBLE);
                                mTourGuideHandler.next();
                            }
                        })
                )
                .setDefaultPointer(null)
                .setContinueMethod(Sequence.ContinueMethod.OverlayListener)
                .build();

        mTourGuideHandler = ChainTourGuide.init(getActivity()).playInSequence(sequence);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.CAMERA, getString(R.string.permission_read_storage_rationale), REQUEST_CAMARA_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                filePath = FileProvider.getUriForFile(getActivity(),
                        "com.example.developer.coordinatortasks",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
                startActivityForResult(intent, CAMERA);
            }

        }
    }

    private File createImageFile() throws IOException {
//        String mCurrentPhotoPath;
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
////        String imageFileName = "JPEG_" + timeStamp + "_";
//        String imageFileName = "image";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
        File path = new File(getActivity().getFilesDir(), "allimages/captured");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, "image.jpg");
        return image;
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.permission_title_rationale));
            builder.setMessage(rationale);
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                }
            });
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pickFromGallery();
                    isStoragePermissionGranted();
                }
                break;
            case REQUEST_CAMARA_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoFromCamera();
                }
                break;
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    DownloadFileFromURL.execute();
                    if (bitmapImage != null) {
                        pickFromGallery();
                        // addWaterMark(bitmapImage, urlImage, postModel);
                    }

                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                filePath = data.getData();
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddtoFirebaseActivity.class);
                intent.putExtra("imagePath", filePath.toString());
                intent.putExtra("flag", 2);
                startActivity(intent);
            }

        } else if (requestCode == CAMERA) {
            File path = new File(getActivity().getFilesDir(), "allimages/captured");
            if (!path.exists()) path.mkdirs();
            // use imageFile to open your image
            if (path != null) {
                File imageFile = new File(path, "image.jpg");
                Uri imageUri = Uri.fromFile(imageFile);
                Intent intent = new Intent(getActivity(), AddtoFirebaseActivity.class);
                intent.putExtra("imagePath", imageUri.toString());
                intent.putExtra("flag", 2);
                startActivity(intent);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

//            if (data != null) {
////            String file = data.getExtras().get("data").toString();
//                Log.d("cameraImage", filePath.toString());
//                Intent intent = new Intent(getActivity(), AddtoFirebaseActivity.class);
//                intent.putExtra("imagePath", filePath.toString());
//                intent.putExtra("flag", 2);
//                startActivity(intent);
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//            }

        } else if (requestCode == REQUEST_VIDEO_TRIMMER) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                startTrimActivity(selectedUri);
            } else {
                Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_selected_video, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startTrimActivity(@NonNull Uri uri) {

        Intent intent = new Intent(getActivity(), TrimmerActivity.class);
        intent.putExtra(EXTRA_VIDEO_PATH, FileUtils.getPath(getActivity(), uri));
        startActivity(intent);
    }

    public void setData() {
        this.progressBar.setVisibility(View.VISIBLE);
        System.out.println("Setdata");
        getBattingLeaderboard(null, null, false);
//        AutoLoadingFragment.this.adapter.setOnFeedItemClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void onStop() {
//        ApiCallManager.cancelCall("get_bat_leader_board");
//        ApiCallManager.cancelCall("get_bowl_leader_board");
        hideProgressDialog();
        if (UserRegisterAlert != null && UserRegisterAlert.isShowing())
            UserRegisterAlert.dismiss();
        super.onStop();

    }


    public void getBattingLeaderboard(String page, Long datetime, final boolean refresh) {
        if (!this.loadmore) {
            //  this.progressBar.setVisibility(View.VISIBLE);
        }
        this.loadmore = false;
        emptyViewVisibility(false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("MyPosts");
        final String TAG = "Commentary itemArrayList";

        Query queryRef;
        System.out.println("postionnnncomm" + page + TOUR_ID);
        if (page == null)
            queryRef = myRef
                    .orderByChild("time")
                    .limitToLast(10);
        else
            queryRef = myRef.orderByChild("time").endAt(datetime).limitToFirst(10);

        ValueEventListener valueEventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever itemArrayList at this location is updated.
                progressBar.setVisibility(View.GONE);
//                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
                if (getActivity() != null) {
                    if (!dataSnapshot.exists()) {
                        AutoLoadingFragment.this.loadmore = true;
                        if (AutoLoadingFragment.this.adapter != null) {
                            AutoLoadingFragment.this.adapter.loadMoreFail();
                        }
                        if (AutoLoadingFragment.this.itemArrayList.size() <= 0) {
                            AutoLoadingFragment.this.emptyViewVisibility(true);
                            AutoLoadingFragment.this.rvMatches.setVisibility(View.GONE);
                            return;
                        }
                        return;
                    }
                    AutoLoadingFragment.this.baseResponse = dataSnapshot;
                    ArrayList<PostModel> arrayList = new ArrayList<>();
                    for (DataSnapshot md : dataSnapshot.getChildren()) {
                        if (md.getValue() != null && !md.getValue().equals("")) {
                            PostModel matchDetails = md.getValue(PostModel.class);
                            if (itemArrayList.size() > 0)
                                System.out.println("datatacccc" + arrayList.size() + "__" + lastItemId + "___" + matchDetails.getTime());
                            if (itemArrayList.size() > 0 && lastItemId == matchDetails.getTime()) {
                                System.out.println("datatacccc");
                            } else {
                                arrayList.add(matchDetails);
                            }
                        }
                    }
                    if (arrayList.size() != 0) {
                        Collections.reverse(arrayList);
                        if (arrayList.size() != 0) {
                            lastItemId = arrayList.get(arrayList.size() - 1).getTime();
                        }

                        if (itemArrayList.size() == 0)
                            itemArrayList.addAll(arrayList);
                        System.out.println("datataccccitem" + itemArrayList.size());
                        //   itemArrayList.addAll(Arrays.asList(Utils.fromJson(Utils.toString(arrayList),PostModel[].class)));
                        //    System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav)));
                        // ArrayList<PostModel> datav = new ArrayList<PostModel>(sh.values());
//                    System.out.println("datavvvf  " + TAG + (pasu.vadivasal.android.Utils.toString(arrayList.get(0))));

                        if (AutoLoadingFragment.this.adapter == null) {
                            System.out.println("NEW ADAPTER SETbat");
                            //AutoLoadingFragment .this.itemArrayList.addAll(arrayList);
                            AutoLoadingFragment.this.adapter = new PostAutoAdapter(AutoLoadingFragment.this.getActivity(), R.layout.item_feed, AutoLoadingFragment.this.itemArrayList);
//                            adapter.setHasStableIds(true);
                            AutoLoadingFragment.this.adapter.setEnableLoadMore(true);
                            AutoLoadingFragment.this.rvMatches.setAdapter(AutoLoadingFragment.this.adapter);
//                        AutoLoadingFragment.this.adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                            // AutoLoadingFragment .this.rvMatches.addOnItemTouchListener(new C110401());
                            AutoLoadingFragment.this.adapter.setOnLoadMoreListener(AutoLoadingFragment.this, AutoLoadingFragment.this.rvMatches);
                            AutoLoadingFragment.this.adapter.setOnFeedItemClickListener(AutoLoadingFragment.this);
                            if (arrayList.size() % 10 != 0) {
                                AutoLoadingFragment.this.adapter.loadMoreEnd();
                            }
                            final DatabaseReference myRefs = database.getReference("MyPosts");
                            try {
                                // String firstKey = (String) ((HashMap<String, PostModel>) dataSnapshot.getValue()).keySet().toArray()[0];
                                System.out.println("datavvv  ssfirst key" + adapter.getData().size() + "__" + itemArrayList.get(0).getTime());
                                myRefs.orderByChild("time").startAt(itemArrayList.get(0).getTime()).addChildEventListener(childAddListner);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (refresh) {
                                AutoLoadingFragment.this.adapter.getData().clear();
                                AutoLoadingFragment.this.itemArrayList.clear();
                                AutoLoadingFragment.this.itemArrayList.addAll(arrayList);
                                AutoLoadingFragment.this.adapter.setNewData(arrayList);
                                AutoLoadingFragment.this.adapter.setEnableLoadMore(true);
                                AutoLoadingFragment.this.rvMatches.scrollToPosition(0);
                            } else {
                                System.out.println("datataccccitemaddd" + arrayList.size() + AutoLoadingFragment.this.adapter.getItemCount());
                                AutoLoadingFragment.this.adapter.addData((Collection) arrayList);
                                AutoLoadingFragment.this.adapter.loadMoreComplete();
                                System.out.println("datataccccitemafter" + arrayList.size() + AutoLoadingFragment.this.adapter.getItemCount());
                            }
                        }
                        if (AutoLoadingFragment.this.baseResponse != null && arrayList.size() % 10 != 0) {
                            AutoLoadingFragment.this.adapter.loadMoreEnd();
                        } else
                            AutoLoadingFragment.this.loadmore = true;
                        if (AutoLoadingFragment.this.itemArrayList.size() == 0) {
                            AutoLoadingFragment.this.emptyViewVisibility(true);
                        }
                    } else {
                        AutoLoadingFragment.this.adapter.loadMoreComplete();
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }

        });


        childAddListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("commentary added" + dataSnapshot.getValue());

                if (dataSnapshot != null && itemArrayList != null) {

//                    PostModel arrayLists = dataSnapshot.getValue(PostModel.class);
//                    if (itemArrayList.size() > 0 && itemArrayList.get(0).getTime() != (arrayLists).getTime()) {
//                        System.out.println("commentary adding data");
//                        AutoLoadingFragment.this.adapter.addData(0, arrayLists);
//                        AutoLoadingFragment.this.adapter.notifyDataSetChanged();
//                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //  System.out.println("nagaaaa_chile");
//                PostModel arrayLists = dataSnapshot.getValue(PostModel.class);
//                AutoLoadingFragment.this.adapter.notifyDataSetChanged();
//                System.out.println("arrayLists"+arrayLists.likesCount+"__"+arrayLists.url);
//                if (itemArrayList.size() > 0 && itemArrayList.get(0).getTime() != (arrayLists).getTime()) {
//                    System.out.println("commentary adding data");
//                    AutoLoadingFragment.this.adapter.addData(0, arrayLists);
//                    AutoLoadingFragment.this.adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        queryRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private String getHighlightedText(String text, boolean isHighlight) {
        if (isHighlight) {
            return "<b>" + text + "</b>";
        }
        return text;
    }

    public void onLoadMoreRequested() {
        System.out.println("onLoadMoreRequested" + this.loadmore + "___" + (itemArrayList.size() % 10 != 0));
        if (!this.loadmore || (itemArrayList.size() % 10 != 0)) {
            new Handler().postDelayed(new C110422(), 1500);
        } else if (this.batsman) {
            System.out.println("Load more" + itemArrayList.get(itemArrayList.size() - 1).getTime());
            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTime()), (itemArrayList.get(itemArrayList.size() - 1).getTime()), false);
        } else {
            // getBowlingLeaderboard(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
        }
    }

//    private View getEmptyView() {
//        if (getActivity() != null) {
//            this.viewEmpty = getActivity().getLayoutInflater().inflate(R.layout.raw_empty_view, null);
//            TextView tvTitle = (TextView) this.viewEmpty.findViewById(R.id.tvTitle);
//            TextView tvDetail = (TextView) this.viewEmpty.findViewById(R.id.tvDetail);
//            ((ImageView) this.viewEmpty.findViewById(R.id.ivImage)).setImageResource(R.drawable.about);
//            //tvTitle.setText(R.string.leaderbord_blank_stat);
//            tvDetail.setVisibility(View.GONE);
//        }
//        return this.viewEmpty;
//    }

    private void emptyViewVisibility(boolean b) {
        if (b) {
//            this.viewEmpty.setVisibility(View.VISIBLE);
////            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
////            this.tvTitle.setText(R.string.commentary_empty);
//            this.tvDetail.setVisibility(View.GONE);
            return;
        }
        // this.viewEmpty.setVisibility(View.GONE);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.initSpinListener) {
            this.initSpinListener = false;
        } else if (this.batsman) {
            System.out.println("On iTem select");
            getBattingLeaderboard(null, null, true);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCommentsClick(View v, int position, PostModel postModel) {
        final Intent intent = new Intent(getActivity(), CommentsAutoActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsAutoActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        intent.putExtra("postID", postModel.postId);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(getString(R.string.processing));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onMoreClick(View v, int position) {
        showProgressDialog();
        onSharePhotoClick(position);
//        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, position, this);
//        int[] startingLocation = new int[2];
//        v.getLocationOnScreen(startingLocation);
//        startingLocation[0] += v.getWidth() / 2;
////        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
//        getActivity().overridePendingTransition(0, 0);
    }


    @Override
    public void onProfileClick(View v) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        postModel = itemArrayList.get(feedItem);
        urlImage = postModel.url;
        if (postModel.typeOfPost == 1) {
            Picasso.with(getActivity())
                    .load(postModel.thumbnail)
                    .into(picassoImageTarget(getActivity(), "imageDir", "my_image.jpeg"));
        } else {
            Picasso.with(getActivity())
                    .load(postModel.url)
                    .into(picassoImageTarget(getActivity(), "imageDir", "my_image.jpeg"));
        }

//        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    public void addWaterMark(Bitmap src, String url, PostModel model) {
        int w = src.getWidth();
        int h = src.getHeight();
        Point point = new Point();
        point.set((int) (w / 1.5), (int) (h / 2.8));
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);


        // Bitmap waterMark =Bitmap.createScaledBitmap( BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_stat_logosmall),(w/6),(h/6),false);

        // canvas.drawBitmap(src,point.x, point.y, null);
        if (result != null) {
            if (isStoragePermissionGranted()) {
                String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), result, "title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.putExtra(Intent.EXTRA_TEXT, "https://ilovejallikattu.000webhostapp.com/images/images.html" + "?id=" + model.postId);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        }
//        return result;
    }

    class C110422 implements Runnable {
        C110422() {
        }

        public void run() {
            AutoLoadingFragment.this.adapter.loadMoreEnd();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                pickFromGallery();
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                return false;
            }
        } else { //permission is automatically granted on sdk<210 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            bitmapImage = bitmap;
                            addWaterMark(bitmap, urlImage, postModel);
                        }

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }

    public void showPhotoVideoAlert() {
        Context context = getContext();

        if (!((Activity) context).isFinishing()) {
            try {
                final androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
                View dialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.image_or_video, null);
                ab.setView(dialogView);
                ab.setCancelable(true);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {


                    dialogView.findViewById(R.id.camera_lay).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            choosePhotoFromGallary();
                            UserRegisterAlert.dismiss();
                        }
                    });
                    dialogView.findViewById(R.id.video_lay).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pickFromGallery();
                            UserRegisterAlert.dismiss();
                        }
                    });

                    UserRegisterAlert = ab.create();
                    UserRegisterAlert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}