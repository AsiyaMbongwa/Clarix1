package com.niquewrld.tutoringapp.chat.fragments.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cometchat.chatuikit.details.CometChatDetails;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.niquewrld.tutoringapp.R;

public class UsersDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list_data_item, container, false);
        CometChatDetails cometChatDetails = view.findViewById(R.id.users_details);
        cometChatDetails.setUser(CometChatUIKit.getLoggedInUser());
        return view;
    }
}