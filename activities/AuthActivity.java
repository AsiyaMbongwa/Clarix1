package ell.one.clarix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ell.one.clarix.R;
import ell.one.clarix.database_handlers.FirebaseManager;

public class AuthActivity extends AppCompatActivity {

    // Buttons
    AppCompatButton studentLoginButton , tutorLoginButton ;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseManager = new FirebaseManager(this);

        if(user != null){
            firebaseManager.navigateBasedOnRole(AuthActivity.this);
        }

        studentLoginButton = findViewById(R.id.student_login_button);
        tutorLoginButton = findViewById(R.id.tutor_login_button);

        studentLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
            intent.putExtra("ROLE", "student");
            startActivity(intent);
        });

        tutorLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
            intent.putExtra("ROLE", "tutor");
            startActivity(intent);
        });
    }
}