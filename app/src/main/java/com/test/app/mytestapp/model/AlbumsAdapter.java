package com.test.app.mytestapp.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.test.app.mytestapp.R;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private List<Album> albumList;
    private final String userId  = "User Id: ";
    private final String id = "Id: ";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, userId, identifier;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            identifier = (TextView) view.findViewById(R.id.id);
            userId = (TextView) view.findViewById(R.id.user_id);
        }
    }

    public AlbumsAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.identifier.setText(id + String.valueOf(album.getId()));
        holder.userId.setText(userId + String.valueOf(album.getUserId()));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}