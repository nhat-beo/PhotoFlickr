package com.example.photoflickr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.photoflickr.photo.adapter.OnItemClickListener;
import com.example.photoflickr.photo.adapter.PhotoAdapter;
import com.example.photoflickr.photo.model.FPhoto;
import com.example.photoflickr.photo.model.Photo;
import com.example.photoflickr.photo.model.Photos;
import com.example.photoflickr.photo.retrofit.PhotoService;
import com.example.photoflickr.photo.retrofit.RetrofitInstance;
import com.example.photoflickr.scrollview.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    //Tham so
    private static int PER_PAGE = 1000;
    private static int PAGE = 0;
    private static final String FORMAT = "json";
    private static final String NONJSONCALLBACK = "1";

    private ImageView btnSearch;
    private EditText edtSearch;
    private RecyclerView rcvPhoto;
    private RelativeLayout progressLoadMore;

    PhotoAdapter photoAdapter;

    private List<Photo> photoList;

    StaggeredGridLayoutManager layoutManager;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cap quyen cho may
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        //Anh xa
        initUi();

        //Hien anh
        loadData(PAGE);

        //Tim kiem anh
//        searchPhoto();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotoSearch();
            }
        });
    }

//    private void searchPhoto() {
//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
//    }

//    private void filter(String text) {
//        List<Photo> filteredPhotoList = new ArrayList<>();
//
//        for (Photo item : photoList) {
//            if (item.getTitle().toLowerCase().contains(text.toLowerCase().trim())) {
//                filteredPhotoList.add(item);
//            }
//        }
//
//        photoAdapter.filterList(filteredPhotoList);
//    }

    private void getPhotoSearch() {
        String strTitle = edtSearch.getText().toString().trim();
        if (strTitle.isEmpty()) {
            loadData(1);
        }

        List<Photo> filteredPhotoList = new ArrayList<>();

        for (Photo item : photoList) {
            if (item.getTitle().toLowerCase().contains(strTitle.toLowerCase().trim())) {
                filteredPhotoList.add(item);
            }
        }

        photoAdapter.filterList(filteredPhotoList);
    }

    //Tao option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Photo'fr");
//            builder.setMessage("Are you sure you want to Sign out?");
//            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
//                    MainActivity.this.finish();
//                }
//            });
//            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });

            startActivity(new Intent(MainActivity.this, LogInActivity.class));
            MainActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void initUi() {
        btnSearch = findViewById(R.id.btn_search);
        edtSearch = findViewById(R.id.edt_search);
        rcvPhoto = findViewById(R.id.rcv_photo);
        progressLoadMore = findViewById(R.id.load_more);

        //Thiet lap hien anh cho layout
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //Khoi tao array list
        photoList = new ArrayList<>();

        //Set layout
        rcvPhoto.setLayoutManager(layoutManager);

        //Set adapter
        photoAdapter = new PhotoAdapter(MainActivity.this);
        rcvPhoto.setAdapter(photoAdapter);

        //Load more
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                progressLoadMore.setVisibility(View.VISIBLE);
                // progressDialog.show();
                loadData(page + 1);
            }
        };

        rcvPhoto.addOnScrollListener(scrollListener);
    }

    private void loadData(int PAGE) {
        Retrofit retrofit = RetrofitInstance.getInstance();
        PhotoService photoService = retrofit.create(PhotoService.class);
        photoService.getListPhoto(PER_PAGE, PAGE, FORMAT, NONJSONCALLBACK)
                .enqueue(new Callback<FPhoto>() {
                    @Override
                    public void onResponse(Call<FPhoto> call, Response<FPhoto> response) {
                        Photos photos = response.body().getPhotos();

                        if (progressLoadMore.getVisibility() == View.VISIBLE) {
                            progressLoadMore.setVisibility(View.GONE);
                        }

                        //Kiem tra du lieu bang null
                        if (photos == null) return;

                        //Gan list
                        photoList.addAll(photos.getPhotoList());
                        Log.d("SizeOfPhotos", photoList.size() + "");
                        photoAdapter.setData(photoList, photos, MainActivity.this::onItemClick);
                    }

                    @Override
                    public void onFailure(Call<FPhoto> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onItemClick(Photo photo) {
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photoData", photo);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}