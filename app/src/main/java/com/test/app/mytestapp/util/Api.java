package com.test.app.mytestapp.util;

import com.test.app.mytestapp.model.Album;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("albums")
    Call<List<Album>> getAlbums();
}
