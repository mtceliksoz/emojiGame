package com.example.emojigame;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText input;
    EditText output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiManager.install(new GoogleEmojiProvider());
        setContentView(R.layout.activity_main);
        input=findViewById(R.id.input);
        output=findViewById(R.id.output);

        RequestQueue queue = Volley.newRequestQueue(this);


        Button btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String url ="\"http://www."+input.getText().toString().trim()+"\"";
                String url ="http://elsishirdavat.com/Emoji/";

                // Sağlanan URL’den bir dize yanıtı istenmektedir.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String description = response.getString("Description");
                                    String answer = response.getString("answer");
                                    output.setText("Description: " + description+"\nAnswer: "+ answer);
                                } catch (Exception e){
                                    output.setText("Exception!!!");
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                output.setText("ERROR!!!!");
                            }
                        });



                queue.add(jsonObjectRequest);
                //output.setText(url);



                //Log.d(TAG,"emoji : " + emoji);

                //input.getText().clear();


            }
        });
    }

    public static int toUnicode(String emoji) {
        String text="";
        if(emoji.length()>2) {
            text = emoji.substring(0, 2);
        }
        else{
            text = emoji;
        }
        StringBuffer sb = new StringBuffer();
        int codePoint=0;
        String hex="";
        for (int i = 0; i < text.length(); i++) {
            codePoint = text.codePointAt(i);
            // Skip over the second char in a surrogate pair
            if (codePoint > 0xffff) {
                i++;
            }
            hex = Integer.toHexString(codePoint);
            sb.append("0x");
            for (int j = 0; j < 4 - hex.length(); j++) {
                sb.append("0");
            }
            sb.append(hex);
        }
        int unicode = Integer.parseInt(hex, 16) ;
        return unicode;
    }

    public static String toEmoji(int unicode) {
        String emoji = new String(Character.toChars(unicode));
        return emoji;
    }


}
