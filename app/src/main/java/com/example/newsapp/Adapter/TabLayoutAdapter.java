package com.example.newsapp.Adapter;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private Context context;
    int TotalTab;

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitle = new ArrayList<>();


    public TabLayoutAdapter(Context context, FragmentManager fm,int TotalTab) {
        super(fm);
        context = context;
        this.TotalTab = TotalTab;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitle.get( position );
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return TotalTab;
    }

    public void  AddFragment(Fragment fragment,String Title){
        fragmentList.add( fragment );
        FragmentListTitle.add( Title );
    }


}

