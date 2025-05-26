package com.niquewrld.tutoringapp.chat.fragments.calls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cometchat.chatuikit.calls.callhistory.CometChatCallLogHistory;
import com.niquewrld.tutoringapp.R;


public class CallLogHistoryFragment extends Fragment {
    private CometChatCallLogHistory cometChatCallLogHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_log_history, container, false);

        cometChatCallLogHistory = view.findViewById(R.id.call_logs_history);
        return view;
    }
}