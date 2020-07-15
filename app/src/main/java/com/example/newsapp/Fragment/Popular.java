package com.example.newsapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Popular extends Fragment {

    final String API_KEY="9e03be5f4cc3461582b880b287cbd9d6";
    String TAG = "All Fragment";


    androidx.recyclerview.widget.RecyclerView RecyclerView;
    AllNewsRecyclerViewAdapter allNewsRecyclerViewAdapter;
    LinearLayout loding,no_data;
    Button refresh_btn;
    SwipeRefreshLayout SwipeRefresh;

    private List<Article> articleList = new ArrayList<>();

    public Popular() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        SwipeRefresh = view.findViewById(R.id.SwipeRefresh);

        RecyclerView = view.findViewById(R.id.RecyclerView);
        RecyclerView.setVisibility( View.GONE );

        loding = view.findViewById( R.id.loding );
        loding.setVisibility( View.VISIBLE );

        no_data = view.findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);

        refresh_btn = view.findViewById( R.id.refresh_btn );

        allNewsRecyclerViewAdapter = new AllNewsRecyclerViewAdapter(getContext(),articleList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.setAdapter(allNewsRecyclerViewAdapter);



        FetchPopularNews();

        //SwipeRefresh
        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchPopularNews();
                SwipeRefresh.setRefreshing(true);
            }
        });

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loding.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);

                FetchPopularNews();
            }
        });

        return view;
    }

    private void FetchPopularNews() {

        SwipeRefresh.setRefreshing(false);

       // String from = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

        int dayMain_value = Integer.valueOf(day);
        int dayresult = dayMain_value-2;
        String resultDay = String.valueOf(dayresult);

        String from = year+"-"+month+"-"+day;
        String to = year+"-"+month+"-"+resultDay;


        String q = "apple";
        String sortBy = "popularity";

        Call<AllNewsModel> call = ApiClient.getInstance().getApiInterface().popularNews(q,from,to,sortBy,API_KEY);
        call.enqueue(new Callback<AllNewsModel>() {
            @Override
            public void onResponse(Call<AllNewsModel> call, Response<AllNewsModel> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(getContext(), "response:"+response.body().getTotalResults().toString(), Toast.LENGTH_SHORT).show();

                    if (response.body().getArticles() != null){
                        articleList = response.body().getArticles();

                        Log.d(TAG, "response:"+response.body().getTotalResults().toString());

                        allNewsRecyclerViewAdapter.updateList( articleList );
                        loding.setVisibility( View.GONE );
                        RecyclerView.setVisibility( View.VISIBLE );
                        SwipeRefresh.setRefreshing(false);
                    }else {
                        Toast.makeText(getContext(), "Sorry no news found", Toast.LENGTH_SHORT).show();

                        loding.setVisibility( View.GONE );
                        RecyclerView.setVisibility( View.GONE );
                        no_data.setVisibility(View.VISIBLE);

                        Log.d(TAG, "Else: error" );

                        Toast.makeText(getContext(), "Else: error", Toast.LENGTH_SHORT).show();
                        SwipeRefresh.setRefreshing(false);

                    }


                }
                else {
                    Toast.makeText(getContext(), "Responce: error", Toast.LENGTH_SHORT).show();
                    no_data.setVisibility(View.VISIBLE);
                    SwipeRefresh.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<AllNewsModel> call, Throwable t) {
                loding.setVisibility( View.GONE );
                RecyclerView.setVisibility( View.GONE );
                no_data.setVisibility(View.VISIBLE);
                t.getLocalizedMessage();
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
                SwipeRefresh.setRefreshing(false);
            }
        });
    }
}
