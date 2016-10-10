package com.example.hongjiayong.lifeisshort.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongjiayong.lifeisshort.Book;
import com.example.hongjiayong.lifeisshort.BooksAdapter;
import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.dialog.AddFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagBooksFragment extends Fragment {

    private static String tagName;
    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<Book> bookList;
    private FloatingActionButton fab;
    private TextView title;

    public TagBooksFragment() {
        // Required empty public constructor
    }

    public static TagBooksFragment newInstence(String tag){
        TagBooksFragment t = new TagBooksFragment();
        tagName = tag;
        return t;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_tag_books, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initCollapsingToolbar(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) view.findViewById(R.id.tag_book_add_fab);
        bookList = new ArrayList<>();
        adapter = new BooksAdapter(this.getContext(), bookList);
        title = (TextView) view.findViewById(R.id.tag_book_love_music);
        title.setText(tagName);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new TagBooksFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try {
            prepareBooks();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        adapter.setmOnItemClickListener(new BooksAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Book temp = bookList.get(Integer.parseInt(data));

                // start transition
                BooksFragment fragmentOne = new BooksFragment();
                ContentFragment fragmentTwo = ContentFragment.newInstance(temp.getName(), temp.getAuthor(), temp.getPublisher(), temp.getDescription(), temp.getTag(), temp.getCover());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Transition changeTransform = TransitionInflater.from(getContext()).inflateTransition(R.transition.change_image_transform);
                    Transition explodeTransition = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.explode);

                    fragmentOne.setSharedElementReturnTransition(changeTransform);
                    fragmentOne.setExitTransition(explodeTransition);

                    fragmentTwo.setSharedElementEnterTransition(changeTransform);
                    fragmentTwo.setEnterTransition(explodeTransition);

                    ImageView ivProfile = (ImageView) view.findViewById(R.id.thumbnail);
                    TextView title = (TextView) view.findViewById(R.id.book_title);

                    FragmentTransaction ft = getFragmentManager().beginTransaction()
                            .replace(R.id.flContent, fragmentTwo)
                            .addToBackStack("transaction")
                            .addSharedElement(ivProfile, "cool")
                            .addSharedElement(title, "cool");
                    ft.commit();
                }else{

                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        try {
            Glide.with(this).load(R.drawable.tagcover).into((ImageView) view.findViewById(R.id.tag_book_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar(View view) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.tag_book_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.tag_book_appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(" ");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void prepareBooks() throws UnsupportedEncodingException {
        final int[] flag = {0};
        final int[] covers = new int[]{
                R.drawable.book_1,
                R.drawable.book_2,
                R.drawable.book_3,
                R.drawable.book_4,
                R.drawable.book_5,
                R.drawable.book_6,
                R.drawable.book_7,
                R.drawable.book_8,
                R.drawable.book_9,
                R.drawable.book_10};


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", "xiaohong");

        String url = "http://www.hjyheart.com/getTagBooks?username=" + username + "&tag=" + URLEncoder.encode(tagName, "utf-8");
        Log.e("url_getBook", url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(getView(), "connect error", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        String name = json.getString("name");
                        String author = json.getString("author");
                        String publisher = json.getString("publisher");
                        String tag = json.getString("tag");
                        String description = json.getString("description");
                        String state = json.getString("state");
                        String like = json.getString("like");
                        Book temp = new Book(name, description, author, tag, username, state, publisher,like, covers[i % 10]);
                        bookList.add(temp);
                    }
                    flag[0] = 1;
                }catch (JSONException e){

                }
            }
        });
        while (flag[0] == 0){}
        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void showAddDialog() {
        FragmentManager fm = getFragmentManager();
        AddFragment addFragment = AddFragment.newInstance();
        addFragment.setTargetFragment(TagBooksFragment.this, 300);
        addFragment.show(fm, "fragment_add");
    }

}
