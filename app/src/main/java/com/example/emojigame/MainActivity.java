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


//isteği RequestQueue ekliyoruz.


        Button btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String url ="\""+input.getText().toString()+"\"";
                //String url ="https://www.http://google.com";
                String url ="https://elsishirdavat.com/Emoji/";
                //String url ="http://www.oguzhanozdemir.com.tr/ogzhnozdmrcom/site/json_yazilar";
                // Sağlanan URL’den bir dize yanıtı istenmektedir.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    output.setText("Response: " + response.toString());
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

// Access the RequestQueue through your singleton class.
                //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

                //queue.add(jsonObjectRequest);

                String emoji=input.getText().toString();
                int emoji_unicode=toUnicode(emoji);

                //output.setText(new String(Character.toChars(0x1F60A)));
                //output.setText(emoji);
                //int emoji_val=0;
                //if(emoji!=null)
                //int emoji_val= Integer.parseInt("0x"+emoji_str.substring(2,7));

                //int emoji_val= Integer.parseInt(emoji_str);
                output.setText(toEmoji(emoji_unicode));


                //output.setText(new String(Character.toChars(emoji_val)));
                Log.d(TAG,"emoji : " + emoji);
                Log.d(TAG,"emoji_unicode value: " + emoji_unicode);
                Log.d(TAG,"input value: " + input.getText().toString());
                Log.d(TAG,"output value: " + output.getText().toString());
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
