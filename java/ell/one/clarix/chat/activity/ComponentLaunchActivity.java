package ell.one.clarix.chat.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cometchat.chatuikit.shared.resources.utils.Utils;
import ell.one.clarix.R;
import ell.one.clarix.chat.AppUtils;
import ell.one.clarix.chat.fragments.calls.CallButtonFragment;
import ell.one.clarix.chat.fragments.calls.CallLogDetailsFragment;
import ell.one.clarix.chat.fragments.calls.CallLogHistoryFragment;
import ell.one.clarix.chat.fragments.calls.CallLogParticipantsFragment;
import ell.one.clarix.chat.fragments.calls.CallLogRecordingFragment;
import ell.one.clarix.chat.fragments.calls.CallLogWithDetailsFragment;
import ell.one.clarix.chat.fragments.calls.CallLogsFragment;
import ell.one.clarix.chat.fragments.conversations.ConversationsWithMessagesFragment;
import ell.one.clarix.chat.fragments.messages.MessageComposerFragment;
import ell.one.clarix.chat.fragments.messages.MessageHeaderFragment;
import ell.one.clarix.chat.fragments.messages.MessageInformationFragment;
import ell.one.clarix.chat.fragments.messages.MessageListFragment;
import ell.one.clarix.chat.fragments.messages.MessagesFragment;
import ell.one.clarix.chat.fragments.shared.resources.LocalizeFragment;
import ell.one.clarix.chat.fragments.shared.views.CardBubbleFragment;
import ell.one.clarix.chat.fragments.shared.views.FileBubbleFragment;
import ell.one.clarix.chat.fragments.shared.views.FormBubbleFragment;
import ell.one.clarix.chat.fragments.shared.views.ImageBubbleFragment;
import ell.one.clarix.chat.fragments.shared.views.ListItemFragment;
import ell.one.clarix.chat.fragments.shared.views.MediaRecorderFragment;
import ell.one.clarix.chat.fragments.shared.views.MessageReceiptFragment;
import ell.one.clarix.chat.fragments.shared.views.SchedulerBubbleFragment;
import ell.one.clarix.chat.fragments.shared.views.StatusIndicatorFragment;
import ell.one.clarix.chat.fragments.shared.views.TextBubbleFragment;

public class ComponentLaunchActivity extends AppCompatActivity {
    private LinearLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_launch);
        int id = getIntent().getIntExtra("component", 0);
        parentView = findViewById(R.id.container);
        setUpUI();
        if (id == R.id.conversationWithMessages) {
            loadFragment(new ConversationsWithMessagesFragment());
        } else if (id == R.id.call_button) {
            loadFragment(new CallButtonFragment());
        }  else if (id == R.id.messages) {
            loadFragment(new MessagesFragment());
        } else if (id == R.id.messageList) {
            loadFragment(new MessageListFragment());
        } else if (id == R.id.messageHeader) {
            loadFragment(new MessageHeaderFragment());
        } else if (id == R.id.messageComposer) {
            loadFragment(new MessageComposerFragment());
        }  else if (id == R.id.messageReceipt) {
            loadFragment(new MessageReceiptFragment());
        } else if (id == R.id.statusIndicator) {
            loadFragment(new StatusIndicatorFragment());
        } else if (id == R.id.localize) {
            loadFragment(new LocalizeFragment());
        } else if (id == R.id.list_item) {
            loadFragment(new ListItemFragment());
        } else if (id == R.id.text_bubble) {
            loadFragment(new TextBubbleFragment());
        } else if (id == R.id.image_bubble) {
            loadFragment(new ImageBubbleFragment());
        } else if (id == R.id.files_bubble) {
            loadFragment(new FileBubbleFragment());
        } else if (id == R.id.form_bubble) {
            loadFragment(new FormBubbleFragment());
        } else if (id == R.id.card_bubble) {
            loadFragment(new CardBubbleFragment());
        } else if (id == R.id.scheduler_bubble) {
            loadFragment(new SchedulerBubbleFragment());
        } else if (id == R.id.media_recorder) {
            loadFragment(new MediaRecorderFragment());
        } else if (id == R.id.messageInformation) {
            loadFragment(new MessageInformationFragment());
        } else if (id == R.id.call_logs) {
            loadFragment(new CallLogsFragment());
        } else if (id == R.id.call_logs_details) {
            loadFragment(new CallLogDetailsFragment());
        } else if (id == R.id.call_logs_with_details) {
            loadFragment(new CallLogWithDetailsFragment());
        } else if (id == R.id.call_log_participants) {
            loadFragment(new CallLogParticipantsFragment());
        } else if (id == R.id.call_log_recording) {
            loadFragment(new CallLogRecordingFragment());
        } else if (id == R.id.call_log_history) {
            loadFragment(new CallLogHistoryFragment());
        }
    }

    private void setUpUI() {
        if (AppUtils.isNightMode(this)) {
            Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorSecondaryVariant));
            parentView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorSecondaryVariant)));
        } else {
            Utils.setStatusBarColor(this, getResources().getColor(R.color.colorSecondaryVariant));
            parentView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSecondaryVariant)));
        }
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

}