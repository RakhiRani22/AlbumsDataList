package com.test.app.mytestapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.test.app.mytestapp.R;
import com.test.app.mytestapp.model.Album;
import com.test.app.mytestapp.model.AlbumsAdapter;
import com.test.app.mytestapp.database.DatabaseHandler;
import com.test.app.mytestapp.database.LocalDatabase;
import com.test.app.mytestapp.presenter.MainActivityPresenter;
import java.util.List;

public class AlbumListFragment extends Fragment implements MainActivityPresenter.View, View.OnClickListener{
    public static final String TAG = "AlbumListFragment";
    private RecyclerView listView;
    private MainActivityPresenter presenter;
    private RelativeLayout progressLoader;
    private AlbumsAdapter mAdapter;
    private boolean errorOccurred = false;
    private AlertDialog.Builder alertDialogBuilder = null;
    private AlertDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_fragment, container, false);
        progressLoader = (RelativeLayout) view.findViewById(R.id.loadingPanel);
        progressLoader.setVisibility(View.VISIBLE);

        Button refreshData = (Button) view.findViewById(R.id.refresh_data);
        refreshData.setOnClickListener(this);

        listView = (RecyclerView) view.findViewById(R.id.listViewAlbums);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());

        LocalDatabase localDatabaseHandler = new LocalDatabase(getContext());
        DatabaseHandler databaseHandler = new DatabaseHandler(localDatabaseHandler);
        presenter = new MainActivityPresenter(this, databaseHandler);

        //This is to handle the orientation configuration change
        if(null == savedInstanceState) {
            presenter.getAlbumData();
        }
        else{
            if(null != mAdapter) {
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            if(errorOccurred){
                onErrorOccurred(null);
            }
            hideProgressBar();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void updateAlbumsDataList(List<Album> albumsData) {
        mAdapter = new AlbumsAdapter(albumsData);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        progressLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressLoader.setVisibility(View.GONE);
    }

    @Override
    public void onErrorOccurred(String errorMessage) {
        Log.d(TAG,"onErrorOccurred");

        if(null == alertDialogBuilder) {
            Resources resources = getContext().getResources();
            alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle(resources.getString(R.string.alert));
            alertDialogBuilder.setMessage(resources.getString(R.string.error_msg));
            alertDialogBuilder.setPositiveButton(resources.getString(R.string.ok), null);
            alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    errorOccurred = false;
                }
            });
            dialog = alertDialogBuilder.create();

        }
        errorOccurred = true;
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.refresh_data:
                presenter.getAlbumData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView "+dialog);
        if(null != dialog && dialog.isShowing()){
            Log.d(TAG,"onDestroyView "+dialog.isShowing());
            dialog.dismiss();
            errorOccurred = true;
        }
    }
}