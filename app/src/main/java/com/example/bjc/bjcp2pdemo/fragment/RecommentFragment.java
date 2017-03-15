package com.example.bjc.bjcp2pdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bjc.bjcp2pdemo.R;

/**
 * Created by 毕静存 on 2016/12/5.
 */
public class RecommentFragment extends Fragment {
    public RecommentFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(container.getContext(), R.layout.recomment_fragment,null);
    }
}
