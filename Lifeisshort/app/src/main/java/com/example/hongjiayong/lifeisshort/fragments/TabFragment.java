package com.example.hongjiayong.lifeisshort.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.fragments.startPages.Start1;
import com.example.hongjiayong.lifeisshort.fragments.startPages.Start2;
import com.example.hongjiayong.lifeisshort.tab.BaseFragment;
import com.example.hongjiayong.lifeisshort.tab.SlidingTabLayout;

import java.util.LinkedList;

/**
 * Created by hongjiayong on 2016/9/30.
 */

public class TabFragment extends Fragment {
    private SlidingTabLayout tabs;
    private ViewPager pager;
    private FragmentPagerAdapter adapter;

    public static Fragment newInstance(){
        TabFragment f = new TabFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //adapter
        final LinkedList<BaseFragment> fragments = getFragments();
        adapter = new TabFragmentPagerAdapter(getFragmentManager(), fragments);
        //pager
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        //tabs
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return fragments.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return fragments.get(position).getDividerColor();
            }
        });
        tabs.setBackgroundResource(R.color.colorPrimary);
        tabs.setCustomTabView(R.layout.tab_title, R.id.txtTabTitle, R.id.imgTabIcon);
        tabs.setViewPager(pager);

    }

    private LinkedList<BaseFragment> getFragments(){
        int dividerColor = Color.TRANSPARENT;

        LinkedList<BaseFragment> fragments = new LinkedList<BaseFragment>();

        fragments.add(Start1.newInstance("Start1", R.color.colorAccent, dividerColor));
        fragments.add(Start2.newInstance("Start2", R.color.colorAccent, dividerColor));
        fragments.add(Login.newInstance("Login", R.color.colorAccent, dividerColor));
        fragments.add(Register.newInstance("Register", R.color.colorAccent, dividerColor));

        return fragments;
    }
}
