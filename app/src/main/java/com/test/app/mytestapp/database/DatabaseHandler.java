package com.test.app.mytestapp.database;

import android.util.Log;

import com.test.app.mytestapp.model.Album;
import com.test.app.mytestapp.util.Api;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseHandler {
    private final String TAG = "DatabaseHandler";
    private LocalDatabase localDatabase;

    public DatabaseHandler(LocalDatabase localDatabase
    ){
        this.localDatabase = localDatabase;
    }

    public void getAlbumsData(final Presenter presenter) {
        Log.d(TAG,"getAlbumsData called");
        Api api = getRetrofitApi();
        Call<List<Album>> call = api.getAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                List<Album> albumList = response.body();
                updateLocalDatabase(albumList);
                presenter.onDatabaseReceived(albumList);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                List<Album> localData = localDatabase.getAllAlbums();
                if(null != localData && localData.size() > 0){
                    Log.d(TAG,"onFailure localData:");
                    presenter.onDatabaseReceived(localData);
                }
                else {
                    Log.d(TAG,"onFailure message:"+t.getMessage());
                    presenter.onErrorOccurred(t.getMessage());
                }
            }
        });
    }

    private void updateLocalDatabase(List<Album> albumList){
        Log.d(TAG,"updateLocalDatabase");
        localDatabase.deleteAllRecords();
        for(Album album: albumList){
            localDatabase.addAlbum(album);
        }
    }

    private Api getRetrofitApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        return retrofit.create(Api.class);
    }

    public interface Presenter{
        void onDatabaseReceived(List<Album> albumsData);
        void onErrorOccurred(String errorMessage);
    }
}
