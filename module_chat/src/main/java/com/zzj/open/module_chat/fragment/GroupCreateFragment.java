package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.databinding.ChatFragmentGroupcreateBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupCreateFragment} interface
 * to handle interaction events.
 * Use the {@link GroupCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupCreateFragment extends BaseFragment<ChatFragmentGroupcreateBinding,BaseViewModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GroupCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupCreateFragment newInstance(String param1, String param2) {
        GroupCreateFragment fragment = new GroupCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_groupcreate;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}