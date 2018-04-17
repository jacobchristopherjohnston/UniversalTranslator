package jcj.universaltranslator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jake on 3/22/2018.
 */

public class Word implements View.OnClickListener {
    String json, usage;
    JSONObject wordJSON;
    int position;
    Context context;

    public Word(Context context){
        this.json = "{\"word\":\"\", \"klingon\":\"\",\"conjugate\":\"\"}";
        try{
            wordJSON = new JSONObject(this.json);
        }catch (JSONException jsonException){
            jsonException.printStackTrace();
        }
    }

    public void setJson(String key, String value) throws JSONException {
        wordJSON.put(key, value);
    }

    public String getJson(String key) throws JSONException {
        return wordJSON.get(key).toString();
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onClick(View view) {

    }
}
