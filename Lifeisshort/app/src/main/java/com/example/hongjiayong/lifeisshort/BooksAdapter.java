package com.example.hongjiayong.lifeisshort;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hongjiayong.lifeisshort.fragments.BooksFragment;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hongjiayong on 2016/10/5.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<Book> bookList;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tag;
        public ImageView thumbnail, overflow;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.book_title);
            tag = (TextView) itemView.findViewById(R.id.count);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
        }
    }

    public BooksAdapter(Context mContext, List<Book> bookList){
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card, parent,false);

        itemView.setOnClickListener(this);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Book book = bookList.get(position);
        holder.title.setText(book.getName());
        holder.tag.setText(book.getTag());
        holder.itemView.setTag(String.valueOf(position));

        // loading album cover using Glide library
        Glide.with(mContext).load(book.getCover()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, book.getName(), book.getLike());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, String name, String like) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_book, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(name, like));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private String name;
        private String like;

        public MyMenuItemClickListener(String name, String like) {
            this.name = name;
            this.like = like;
        }

        @Override
        public boolean onMenuItemClick(final MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    if (like.equals("dislike")) {
                        OkHttpClient client = new OkHttpClient();
                        String url = "http://www.hjyheart.com/editBook?name=" + name + "&id=like&like=like";
                        Log.e("like_url", url);
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {}

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {}
                        });
                        Toast.makeText(mContext, "add success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "have been added", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                case R.id.action_delete:
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://www.hjyheart.com/deleteBook?name=" + name;
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {}
                    });
                    Toast.makeText(mContext, "delete success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    return true;
                case R.id.action_share:
                    Toast.makeText(mContext, "can be shared", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
