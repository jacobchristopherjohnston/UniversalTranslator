package jcj.universaltranslator;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;

/**
 * Created by Jake on 3/22/2018.
 */

public class SpeechRecognitionService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initSpeech(SuperVar.context, SuperVar.context.getPackageName());
    }

    public static void initSpeech(Context context, String packageName){
        SpeechRecognizer speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(new SpeechRecognitionListener());
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000);
        SuperVar.speechRecognizer = speech;
        SuperVar.recognizerIntent = recognizerIntent;
        speech.startListening(recognizerIntent);
    }

    public static void shutDownSpeech(){
        SuperVar.speechRecognizer.destroy();
        SuperVar.textToSpeech.stop();
        SuperVar.textToSpeech.shutdown();
        SuperVar.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, SuperVar.audioVolume, 0);
    }

    @Override
    public void onDestroy() {
        shutDownSpeech();
    }
}
