package ell.one.clarix.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ell.one.clarix.R;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "MockPayment";

    private EditText cardNumberInput, expiryDateInput, cvvInput;
    private Button payButton;
    private TextView paymentStatusText;
FirebaseUser user;
    private double sessionPrice;
    private String tutorId, date, startTime, endTime, docId;
    private String studentName, studentEmail, studentPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = FirebaseAuth.getInstance().getCurrentUser();

        cardNumberInput = findViewById(R.id.cardNumberInput);
        expiryDateInput = findViewById(R.id.expiryDateInput);
        cvvInput = findViewById(R.id.cvvInput);
        payButton = findViewById(R.id.payButton);
        paymentStatusText = findViewById(R.id.paymentStatusText);

        // Get payment session details
        tutorId = getIntent().getStringExtra("tutorId");
        date = getIntent().getStringExtra("date");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        docId = getIntent().getStringExtra("docId");
        studentName = getIntent().getStringExtra("studentName");
        studentEmail = getIntent().getStringExtra("studentEmail");
        studentPhone = getIntent().getStringExtra("studentPhone");
        sessionPrice = getIntent().getDoubleExtra("price", 0.0);

        expiryDateInput.setOnClickListener(v -> showExpiryDatePicker());
        payButton.setOnClickListener(v -> simulateMockPayment());
    }

    private void showExpiryDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String expiry = String.format(Locale.US, "%02d/%02d", month + 1, year % 100); // MM/YY
            expiryDateInput.setText(expiry);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Disable past dates
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Hide day picker using reflection
        try {
            Field[] fields = dialog.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("mDatePicker")) {
                    field.setAccessible(true);
                    DatePicker datePicker = (DatePicker) field.get(dialog);
                    Field[] dpFields = datePicker.getClass().getDeclaredFields();
                    for (Field dpField : dpFields) {
                        if (dpField.getName().equals("mDaySpinner") || dpField.getName().equals("mDayPicker")) {
                            dpField.setAccessible(true);
                            Object dayPicker = dpField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.show();
    }


    private void simulateMockPayment() {
        String cardNumber = cardNumberInput.getText().toString().trim();
        String expiry = expiryDateInput.getText().toString().trim();
        String cvv = cvvInput.getText().toString().trim();

        if (cardNumber.length() != 16) {
            cardNumberInput.setError("Card number must be 16 digits");
            return;
        }

        if (!expiry.matches("\\d{2}/\\d{2}")) {
            expiryDateInput.setError("Expiry must be in MM/YY format");
            return;
        }

        // Validate expiry date is in the future
        String[] parts = expiry.split("/");
        int expMonth = Integer.parseInt(parts[0]);
        int expYear = Integer.parseInt(parts[1]) + 2000;

        Calendar now = Calendar.getInstance();
        Calendar expDate = Calendar.getInstance();
        expDate.set(Calendar.YEAR, expYear);
        expDate.set(Calendar.MONTH, expMonth - 1);
        expDate.set(Calendar.DAY_OF_MONTH, 1);
        expDate.set(Calendar.HOUR_OF_DAY, 0);
        expDate.set(Calendar.MINUTE, 0);
        expDate.set(Calendar.SECOND, 0);

        if (expDate.before(now)) {
            expiryDateInput.setError("Card is expired");
            return;
        }

        if (cvv.length() < 3 || cvv.length() > 4) {
            cvvInput.setError("CVV must be 3 or 4 digits");
            return;
        }

        if (sessionPrice <= 0) {
            paymentStatusText.setText("❌ Invalid session price.");
            Toast.makeText(this, "Payment failed: Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        paymentStatusText.setText("🔄 Processing mock payment...");

        new Handler().postDelayed(() -> {
            String ref = "REF_" + System.currentTimeMillis();
            String msg = "✅Payment Success\nRef: " + ref;
            paymentStatusText.setText(msg);
            Log.d(TAG, msg);

            // Optionally send result back
            setResult(RESULT_OK, getIntent().putExtra("paymentStatus", "success"));
            finish();

        }, 2000);
    }
}
