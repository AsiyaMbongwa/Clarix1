package ell.one.clarix.chat.fragments.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cometchat.chatuikit.messageheader.CometChatMessageHeader;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import ell.one.clarix.R;

public class MessageHeaderFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message_header, container, false);
        CometChatMessageHeader messagesHeader=view.findViewById(R.id.messageHeader);

        //set user Object to the MessageHeader
        messagesHeader.setUser(CometChatUIKit.getLoggedInUser());

        return view;
    }
}