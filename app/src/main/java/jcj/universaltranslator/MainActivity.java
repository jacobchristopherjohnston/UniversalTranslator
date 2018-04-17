package jcj.universaltranslator;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    String packageName;
    LinearLayout voiceLayout;
    CheckBox checkBoxUs, checkBoxUk, checkBoxGerman, checkBoxJapanese, checkBoxFrench;
    Intent speechService;
    Button addWordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceLayout = findViewById(R.id.linearLayoutVoiceSelection);
        checkBoxUs=voiceLayout.findViewById(R.id.checkboxUS);
        checkBoxUk=voiceLayout.findViewById(R.id.checkboxUK);
        checkBoxGerman=voiceLayout.findViewById(R.id.checkboxGerman);
        checkBoxJapanese=voiceLayout.findViewById(R.id.checkboxJapanese);
        checkBoxFrench=voiceLayout.findViewById(R.id.checkboxFrench);
        addWordButton = findViewById(R.id.buttonAddWord);

        SuperVar.voiceType = "us";

        //MAKE THE CHECKBOXES DO SOMETHING
        checkBoxUs.setOnClickListener(this);
        checkBoxUk.setOnClickListener(this);
        checkBoxGerman.setOnClickListener(this);
        checkBoxJapanese.setOnClickListener(this);
        checkBoxFrench.setOnClickListener(this);

        SuperVar.prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);

        SuperVar.timeCounter = 0;
        packageName = this.getPackageName();
        context = this;

        SuperVar.assetManager = getAssets();
        SuperVar.audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        SuperVar.audioVolume = SuperVar.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SuperVar.textViewResponse = findViewById(R.id.textViewResponse);
        SuperVar.translateProgressBar =  findViewById(R.id.progressBarTranslate);
        SuperVar.textView = findViewById(R.id.textViewHello);
        SuperVar.textViewEnglish = findViewById(R.id.textViewEnglishPermutation);
        SuperVar.context = this;
        SuperVar.packageName = this.getPackageName();

        //SuperVar.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,0);

        SuperVar.box1 = findViewById(R.id.imageView1);
        SuperVar.box2 = findViewById(R.id.imageView2);
        SuperVar.box3 = findViewById(R.id.imageView3);
        SuperVar.box4 = findViewById(R.id.imageView4);
        SuperVar.box5 = findViewById(R.id.imageView5);
        SuperVar.box6 = findViewById(R.id.imageView6);
        SuperVar.box7 = findViewById(R.id.imageView7);

        //initSpeech(this, this.getPackageName());

        SuperVar.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);

        speechService = new Intent(this, SpeechRecognitionService.class);
        startService(speechService);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkboxUS:
                checkBoxUk.setChecked(false);
                checkBoxFrench.setChecked(false);
                checkBoxGerman.setChecked(false);
                checkBoxJapanese.setChecked(false);
                SuperVar.voiceType = "us";
                break;
            case R.id.checkboxUK:
                checkBoxUs.setChecked(false);
                checkBoxFrench.setChecked(false);
                checkBoxGerman.setChecked(false);
                checkBoxJapanese.setChecked(false);
                SuperVar.voiceType = "uk";
                break;
            case R.id.checkboxGerman:
                checkBoxUk.setChecked(false);
                checkBoxFrench.setChecked(false);
                checkBoxUs.setChecked(false);
                checkBoxJapanese.setChecked(false);
                SuperVar.voiceType = "german";
                break;
            case R.id.checkboxFrench:
                checkBoxUk.setChecked(false);
                checkBoxFrench.setChecked(false);
                checkBoxUs.setChecked(false);
                checkBoxGerman.setChecked(false);
                SuperVar.voiceType = "japanese";
                break;
            case R.id.checkboxJapanese:
                checkBoxUk.setChecked(false);
                checkBoxGerman.setChecked(false);
                checkBoxUs.setChecked(false);
                checkBoxJapanese.setChecked(false);
                SuperVar.voiceType = "french";
                break;
        }
    }

    @Override
    protected void onDestroy() {
        stopService(speechService);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        stopService(speechService);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Intent newSpeechServiceIntent = new Intent(this, SpeechRecognitionService.class);
        startService(newSpeechServiceIntent);
        super.onResume();
    }
}
