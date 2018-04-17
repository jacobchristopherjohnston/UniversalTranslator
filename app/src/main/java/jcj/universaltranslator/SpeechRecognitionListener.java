package jcj.universaltranslator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jake on 3/22/2018.
 */

//FOR ANYONE LOOKING AT THIS, IT IS CURRENTLY BORKED SOOOO YEAH

public class SpeechRecognitionListener implements RecognitionListener {

    List<String> wordList;
    static List<Word> translationList = new ArrayList<>();
    String TAG = "Recognition Listener";
    String definition;
    String usage;
    TextToSpeech tts;
    int definitionCounter;

    public void onReadyForSpeech(Bundle params) {
        wordList = new ArrayList<>();
        definition = "";
        usage = "";
        definitionCounter = 0;

        tts = new TextToSpeech(SuperVar.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    switch (SuperVar.voiceType) {
                        case "us":
                            SuperVar.textToSpeech.setLanguage(Locale.US);
                            break;
                        case "uk":
                            SuperVar.textToSpeech.setLanguage(Locale.UK);
                            break;
                        case "german":
                            SuperVar.textToSpeech.setLanguage(Locale.GERMAN);
                            break;
                        case "japanese":
                            SuperVar.textToSpeech.setLanguage(Locale.JAPAN);
                            break;
                        case "french":
                            SuperVar.textToSpeech.setLanguage(Locale.FRENCH);
                            break;
                    }
                }
            }
        });
        SuperVar.textToSpeech = tts;

        Log.d(TAG, "onReadyForSpeech");
    }

    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech");
    }

    public void onRmsChanged(float rmsdB) {
        //Log.d(TAG, "onRmsChanged");
        if(rmsdB<1&&rmsdB!=-2){
            SuperVar.box1.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box1.animate().scaleY(1);
                }
            });
        }else if(rmsdB<2){
            SuperVar.box2.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box2.animate().scaleY(1);
                }
            });
        }else if(rmsdB<3){
            SuperVar.box3.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box3.animate().scaleY(1);
                }
            });
        }else if(rmsdB<4){
            SuperVar.box4.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box4.animate().scaleY(1);
                }
            });
        }else if(rmsdB<5){
            SuperVar.box5.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box5.animate().scaleY(1);
                }
            });
        }else if(rmsdB<6){
            SuperVar.box6.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box6.animate().scaleY(1);
                }
            });
        }else if(rmsdB<7){
            SuperVar.box7.animate().scaleY(3).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    SuperVar.box7.animate().scaleY(1);
                }
            });
        }
        //System.out.println(Float.toString(rmsdB) + " WITH AUDIO LEVEL " + SuperVar.audioVolume);
    }

    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG, "onBufferReceived");
    }

    public void onEndOfSpeech() {
        Log.d(TAG, "onEndofSpeech");
    }

    public void onError(int error) {
        getErrorText(error);
        SuperVar.speechRecognizer.destroy();
        listen();
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        SuperVar.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 6, 0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        SuperVar.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 6, 0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

    }

    public void onResults(Bundle results){
        Log.d(TAG, "onResults ");
        SuperVar.textViewResponse.setText("");
        definition = "";

        ArrayList<String> words = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //System.out.println("THE NUMBER OF RESULTS IS: " + words.size());

        //System.out.println("THIS IS A TEST STRING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!||||||||||||||||||||||||||    " + words.get(0));
        String newSentence = "";

        try {
            if (words != null) {
                //check if words.get(0) is null, and if it its iterate through all of it to find one which is not

                System.out.println("WORDS AT 0 IS " + words.get(0));

                try{
                    if (words.get(0).length() > 0) {
                        newSentence = translate(words.get(0), SuperVar.context);
                    }
                }catch (JSONException jex){
                    jex.printStackTrace();
                }
            }

        } catch (NullPointerException nEx) {
            nEx.printStackTrace();
        }

        SuperVar.textViewResponse.setText(newSentence);
        SuperVar.textViewEnglish.setText(SuperVar.englishPermutation);

        speakToMe(newSentence);

        SuperVar.speechRecognizer.destroy();

        listen();
    }

    private static String translate(String sentence, Context context) throws JSONException{
        String response = "";

        String[] wordList = sentence.split(" ");
        for(int i = 0; i < wordList.length; i++){
            Word word = new Word(context);
            word.setJson(wordList[i], lookUpKlingon(wordList[i], context));
            word.setPosition(i);
            word.setContext(context);
            if(checkNoun(wordList[i], context)){
                if(checkPronoun(wordList[i], context)){
                    word.setUsage("pronoun");
                }else{
                    word.setUsage("noun");
                }
            }else if(checkVerb(wordList[i], context)){
                word.setUsage("verb");
            }else{
                if(wordList[i].equals("to")||wordList[i].equals("of")||wordList[i].equals("and")||wordList[i].equals("from")||
                        wordList[i].equals("for")||wordList[i].equals("the")||wordList[i].equals("a")||wordList[i].equals("if")){
                    word.setUsage("garbage");
                }else{
                    word.setUsage("adjective");
                }
            }
            System.out.println("ADDED " + word.wordJSON.get("word") + " TO TRANSLATION LIST");
            translationList.add(word);
        }
        StringBuilder builder = new StringBuilder();
        for(Word word : translationList){
            response += word.getJson("klingon");
        }
        response = builder.toString();

        return response;
    }

    public void speakToMe(String talk) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(talk);
        } else {
            ttsUnder20(talk);
        }

    }


    public void onPartialResults(Bundle partialResults) {
        Log.d(TAG, "ON PARTIAL RESULT");

        SuperVar.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);

        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String str = "";

        for (String result : matches) {
            if (result.length() > 0) {
                wordList.add(result);
                str = result;
            }
        }


        SuperVar.textView.setText(str);
    }

    private static void listen() {

        SuperVar.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(SuperVar.context);
        SuperVar.speechRecognizer.setRecognitionListener(new SpeechRecognitionListener());

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, SuperVar.packageName);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000);

        SuperVar.speechRecognizer.startListening(recognizerIntent);
    }


    public void onEvent(int eventType, Bundle params) {
        Log.d(TAG, "onEvent " + eventType);
    }

    public static void getErrorText(int errorCode) {

        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                Toast.makeText(SuperVar.context, message, Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                Toast.makeText(SuperVar.context, message, Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                Toast.makeText(SuperVar.context, message, Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                Toast.makeText(SuperVar.context, message, Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                Toast.makeText(SuperVar.context, message, Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                Toast.makeText(SuperVar.context, message, Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }

        Log.d("Recognition Error", message);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////DICTIONARY STUFF///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////
    //PERHAPS MAKE THIS RUN ON A SEPARATE THREAD YOU PIECE OF SHIT
    ////////////////////////////////////////////////////////////


    public static boolean checkNoun(String word, Context context) {
        boolean isNoun = false;

        try {
            System.out.println("Opening " + word.substring(0, 1).toLowerCase() + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("usage" + File.separator + "nouns" + File.separator + word.substring(0, 1).toLowerCase() + ".txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                //String[] wordList = line.split( ";");
                //boolean isWord = true;

                if (word.toLowerCase().equals(line)) {
                    isNoun = true;
                }
                //System.out.println(line);
            }
            reader.close();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return isNoun;
    }

    public static boolean checkVerb(String word, Context context) {
        boolean isVerb = false;

        try {
            System.out.println("Opening " + word.substring(0, 1).toLowerCase() + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("usage" + File.separator + "verbs" + File.separator + word.substring(0, 1).toLowerCase() + ".txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordList = line.split(",");
                //boolean isWord = true;

                for (String verb : wordList) {
                    if (word.toLowerCase().equals(verb)) {
                        isVerb = true;
                    }
                }
            }
            reader.close();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return isVerb;
    }

    public static boolean checkPronoun(String word, Context context) {
        boolean isPronoun = false;

        try {
            System.out.println("Opening " + word.substring(0, 1).toLowerCase() + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("usage" + File.separator + "pronouns" + File.separator + word.substring(0, 1).toLowerCase() + ".txt")));
            String line;
            List<String> wordList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                wordList.add(line);

                for (String pronoun : wordList) {
                    if (word.toLowerCase().equals(pronoun)) {
                        isPronoun = true;
                    }
                }
            }
            reader.close();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return isPronoun;
    }

    public static String findSynonymRoot(String word, Context context){
        String root = "";
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("usage" + File.separator + "synonyms" + File.separator + word.substring(0, 1).toLowerCase() + ".txt")));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }
        try{
            JSONObject jsonObject = new JSONObject(text.toString());
            JSONArray wordArray = jsonObject.getJSONArray("synonymList");
            for(int i = 0; i < wordArray.length(); i++){
                if(wordArray.getJSONObject(i).getString("word").equals(word)){
                    root = wordArray.getJSONObject(i).getString("root");
                }
            }
        }catch (JSONException JSONex){
            JSONex.printStackTrace();
        }
        return root;
    }



    //
    //this is the new JSON klingon translation method
    //
    public static String lookUpKlingon(String word, Context context) {
        String klingon = "";
        StringBuilder text = new StringBuilder();
        //index out of bound error here when length is 0
        try{
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(word.substring(0, 1).toLowerCase() + ".txt")));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                }
                br.close() ;
            }catch (IOException e) {
                e.printStackTrace();
            }
            try{
                JSONObject jsonObject = new JSONObject(text.toString());
                JSONArray wordArray = jsonObject.getJSONArray("wordList");
                for(int i = 0; i < wordArray.length(); i++){
                    if(wordArray.getJSONObject(i).getString("english").equals(word)){
                        System.out.println("The klingon for "+ word + " is " + wordArray.getJSONObject((i)).getString("klingon"));
                        klingon = wordArray.getJSONObject(i).getString("klingon");
                    }
                }
            }catch (JSONException JSONex){
                JSONex.printStackTrace();
            }
        }catch (IndexOutOfBoundsException in){
            System.out.println("The index is out of bounds dude");
            in.printStackTrace();
        }


        if(klingon.length()<1){
            System.out.println("COULDNT FIND A KLINGON TRANSLATION FOR " + word);

            klingon = word;
        }

        return klingon;
    }

    //
    //THIS IS THE OLD KLINGON TRANSLATION METHOD
    //



}
