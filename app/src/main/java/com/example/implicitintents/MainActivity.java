package com.example.implicitintents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.implicitintents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Open Website Button
        binding.buttonWebsite.setOnClickListener(view -> {
            view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
            String rawUrl = binding.editTextWebsite.getText().toString().trim();
            if (rawUrl.isEmpty()) {
                Toast.makeText(this, "Enter URL first", Toast.LENGTH_SHORT).show();
                return;
            }
            String url = (rawUrl.startsWith("http://") || rawUrl.startsWith("https://"))
                    ? rawUrl
                    : "https://" + rawUrl;
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "App not exist!", Toast.LENGTH_SHORT).show();
            }
        });

        // Open Location Button
        binding.buttonLocation.setOnClickListener(view -> {
            view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
            String location = binding.editTextLocation.getText().toString().trim();
            if (location.isEmpty()) {
                Toast.makeText(this, "Enter location first", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "App not exist!", Toast.LENGTH_SHORT).show();
            }
        });

        // Share Text Button
        binding.buttonShare.setOnClickListener(view -> {
            view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
            String text = binding.editTextShare.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Enter text to share first", Toast.LENGTH_SHORT).show();
                return;
            }
            new ShareCompat.IntentBuilder(this)
                    .setType("text/plain")
                    .setChooserTitle("Share Text")
                    .setText(text)
                    .startChooser();
        });
    }
}
