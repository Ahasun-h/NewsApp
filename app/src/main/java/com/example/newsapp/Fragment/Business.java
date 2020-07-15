package com.example.newsapp.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.Adapter.AllNewsRecyclerViewAdapter;
import com.example.newsapp.Model.AllNews.AllNewsModel;
import com.example.newsapp.Model.AllNews.Article;
import com.example.newsapp.Network.ApiClient;
import com.example.newsapp.R;
import com.example.newsapp.Adapter.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Business extends Fragment {

    final String API_KEY="9e03be5f4cc3461582b880b287cbd9d6";
    String TAG = "Business_Fragment";

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    EditText search_bar;
    AllNewsRecyclerViewAdapter allNewsRecyclerViewAdapter;
    SwipeRefreshLayout SwipeRefresh;
    LinearLayout loding,no_data;
    Button refresh_btn;

    private List<Article> articleList = new ArrayList<>();


    public Business() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_business, container, false);

        SwipeRefresh = view.findViewById(R.id.SwipeRefresh);

        RecyclerView = view.findViewById(R.id.RecyclerView);
        RecyclerView.setVisibility( View.GONE );

        search_bar = view.findViewById(R.id.search_bar);
        search_bar.setVisibility(View.GONE);

        loding = view.findViewById( R.id.loding );
        loding.setVisibility( View.VISIBLE );

        no_data = view.findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);

        refresh_btn = view.findViewById( R.id.refresh_btn );

        allNewsRecyclerViewAdapter = new AllNewsRecyclerViewAdapter(getContext(),articleList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.setAdapter(allNewsRecyclerViewAdapter);

        //Search Bar
        search_bar.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        } );

        FetchBusinessNews();

        //SwipeRefresh
        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchBusinessNews();
                SwipeRefresh.setRefreshing(true);
            }
        });

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loding.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);

                FetchBusinessNews();
            }
        });

        return view;
    }

    private void FetchBusinessNews() {

        String country = Utils.getCountry();
        String category = "business";
        SwipeRefresh.setRefreshing(false);

        Call<AllNewsModel> call = ApiClient.getInstance().getApiInterface().businessNews(country,category,API_KEY);
        call.enqueue(new Callback<AllNewsModel>() {
            @Override
            public void onResponse(Call<AllNewsModel> call, Response<AllNewsModel> response) {
                if (response.isSuccessful())
                {
//                    Toast.makeText(getContext(), "response:"+response.body().getTotalResults().toString(), Toast.LENGTH_SHORT).show();

                    if (response.body().getArticles() != null){
                        articleList = response.body().getArticles();

                        Log.d(TAG, "response:"+response.body().getTotalResults().toString());

                        allNewsRecyclerViewAdapter.updateList( articleList );
                        loding.setVisibility( View.GONE );
                        RecyclerView.setVisibility( View.VISIBLE );
                        search_bar.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);

                        SwipeRefresh.setRefreshing(false);
                    }else {
                        Toast.makeText(getContext(), "Sorry no news found", Toast.LENGTH_SHORT).show();

                        loding.setVisibility( View.GONE );
                        RecyclerView.setVisibility( View.GONE );
                        search_bar.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);

                        Log.d(TAG, "Else: error" );

                        SwipeRefresh.setRefreshing(false);

                        Toast.makeText(getContext(), "Else: error", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(getContext(), "Responce: error", Toast.LENGTH_SHORT).show();

                    SwipeRefresh.setRefreshing(false);
                    no_data.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AllNewsModel> call, Throwable t) {
                loding.setVisibility( View.GONE);
                RecyclerView.setVisibility( View.GONE );
                search_bar.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                t.getLocalizedMessage();
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();

                SwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void filter(String text) {
        ArrayList<Article> articles = new ArrayList <>(  );
        for (Article itemList : articleList)
        {
            if (itemList.getTitle().toLowerCase().contains(text.toLowerCase())) {
                articles.add(itemList);
            }
        }
        allNewsRecyclerViewAdapter.updateList(articles);
    }

}
