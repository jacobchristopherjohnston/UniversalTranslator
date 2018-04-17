package jcj.universaltranslator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Jake on 3/22/2018.
 */

public class SuperVar {
    public static String englishPermutation;
    public static String voiceType;
    public static TextView textView;
    public static TextView textViewResponse;
    public static TextView textViewEnglish;
    public static ProgressBar translateProgressBar;
    public static SpeechRecognizer speechRecognizer;
    public static Intent recognizerIntent;
    public static Context context;
    public static String packageName;
    public static AssetManager assetManager;
    public static AudioManager audioManager;
    public static int audioVolume;
    public static TextToSpeech textToSpeech;
    public static long timeCounter;
    public static SharedPreferences prefs;
    public static ImageView box1, box2, box3, box4, box5, box6, box7;
}
