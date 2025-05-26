package ell.one.clarix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ell.one.clarix.R;
import ell.one.clarix.database_handlers.FirebaseManager;

public class IntroActivity extends AppCompatActivity {

    // Buttons
    TextView btn_getStarted;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressBar progressBar2;
    private FirebaseManager firebaseManager;
    private static final String TAG = "IntroActivity";
    private final String appID = "273422fe11905443"; // Replace with your App ID
    private final String region = "us"; // Replace with your App Region
    private final String authKey = "21e4261f1cd94d7cae0d998094f8bded2681a922"; // Replace with your Auth Key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intro_screen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        btn_getStarted = findViewById(R.id.wlcm_btn);
        progressBar2 = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseManager = new FirebaseManager(this);

        if(user != null){
            UIKitSettings uiKitSettings = new UIKitSettings.UIKitSettingsBuilder()
                    .setRegion(region)
                    .setAppId(appID)
                    .setAuthKey(authKey)
                    .subscribePresenceForAllUsers()
                    .build();

            CometChatUIKit.init(this, uiKitSettings, new CometChat.CallbackListener<String>() {
                @Override
                public void onSuccess(String success) {
                    Log.d(TAG, "Initialization completed successfully");
                    CometChatUIKit.login(user.getUid(), new CometChat.CallbackListener<User>() {
                        @Override
                        public void onSuccess(User user) {
                            firebaseManager.navigateBasedOnRole(IntroActivity.this);
                        }

                        @Override
                        public void onError(CometChatException e) {
                            User newuser = new User();
                            newuser.setUid(user.getUid());
                            newuser.setName(user.getDisplayName());
                            CometChatUIKit.createUser(newuser, new CometChat.CallbackListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    CometChatUIKit.login(user.getUid(), new CometChat.CallbackListener<User>() {
                                        @Override
                                        public void onSuccess(User user) {
                                            firebaseManager.navigateBasedOnRole(IntroActivity.this);
                                        }
                                        @Override
                                        public void onError(CometChatException e) {
                                            Toast.makeText(IntroActivity.this, "Failed to create user", Toast.LENGTH_LONG).show();
                                        }

                                    });
                                }

                                @Override
                                public void onError(CometChatException e) {
                                    Log.e(TAG, "Create user failed: " + (e != null ? e.getMessage() : "Unknown error"));
                                    Toast.makeText(IntroActivity.this, "Failed to create user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            });


                        }
                    });
                }

                @Override
                public void onError(CometChatException e) {
                    Log.e(TAG, "Initialization failed: " + (e != null ? e.getMessage() : "Unknown error"));
                }
            });

        }
        else{
            progressBar2.setVisibility(View.GONE);
            btn_getStarted.setVisibility(View.VISIBLE);
            btn_getStarted.setOnClickListener(v -> {
                Intent intent = new Intent(IntroActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}