package com.apnatutorials.texttovoice;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech textToSpeech;
    Button btnSpeakNow;
    EditText etTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTextToSpeech = (EditText) findViewById(R.id.etTextToSpeech);
        btnSpeakNow = (Button) findViewById(R.id.btnSpeakNow);

        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(MainActivity.this, "Sorry ! Language data missing", Toast.LENGTH_SHORT).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(MainActivity.this, "Sorry ! Language not supported", Toast.LENGTH_SHORT).show();
            } else {
                btnSpeakNow.setEnabled(true);
            }
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(MainActivity.this, "Sorry ! Text to speech can't be initialized", Toast.LENGTH_SHORT).show();
            btnSpeakNow.setEnabled(false);
        }
    }

    public void textToVoiceHandler(View view) {
        String toSpeak = etTextToSpeech.getText().toString();
        Toast.makeText(MainActivity.this, toSpeak, Toast.LENGTH_SHORT).show();
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
