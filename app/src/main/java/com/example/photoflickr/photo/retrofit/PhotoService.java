package com.example.photoflickr.photo.retrofit;


import com.example.photoflickr.photo.model.FPhoto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {
    //https://www.flickr.com/
    //services/rest/
    //?method=flickr.favorites.getList&
    //api_key=f8b975571fa3d6c9a4e842093fd1f6f8&
    //user_id=191847702%40N04&
    //extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+
    //url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&
    //per_page=10&
    //page=0&
    //format=json&
    //nojsoncallback=1

    @GET("services/rest/?method=flickr.favorites.getList&api_key=f8b975571fa3d6c9a4e842093fd1f6f8&user_id=191847702%40N04&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o")
    Call<FPhoto> getListPhoto(@Query("per_page") int per_page,
                              @Query("page") int page,
                              @Query("format") String format,
                              @Query("nojsoncallback") String nojsoncallback);

//    @GET("services/rest/?method=flickr.favorites.getList&api_key=f8b975571fa3d6c9a4e842093fd1f6f8&user_id=191847702%40N04&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=100&page=1&format=json&nojsoncallback=1")
//    Call<ResponseBody> getPhotos();
}
