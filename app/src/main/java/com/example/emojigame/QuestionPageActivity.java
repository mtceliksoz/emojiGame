package com.example.emojigame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import org.json.JSONObject;

public class QuestionPageActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText etDescription, etAnswer;
    RadioGroup choices;
    RadioButton btnChoice_1,btnChoice_2,btnChoice_3,btnChoice_4;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiManager.install(new GoogleEmojiProvider());
        setContentView(R.layout.activity_question_page);
        etDescription = findViewById(R.id.etDescription);
        btnChoice_1 = findViewById(R.id.btnChoice_1);
        btnChoice_2 = findViewById(R.id.btnChoice_2);
        btnChoice_3 = findViewById(R.id.btnChoice_3);
        btnChoice_4 = findViewById(R.id.btnChoice_4);
        choices = findViewById(R.id.choices);
        etAnswer = findViewById(R.id.etAnswer);


        String url = "http://elsishirdavat.com/Emoji/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String description = response.getString("Description");
                            String choice_1 = response.getString("choice_1");
                            String choice_2 = response.getString("choice_2");
                            String choice_3 = response.getString("choice_3");
                            String choice_4 = response.getString("choice_4");
                            String answer = response.getString("answer");

                            etDescription.setText("Question: " + description);
                            btnChoice_1.setText("Choice 1: " + choice_1);
                            btnChoice_2.setText("Choice 2: " + choice_2);
                            btnChoice_3.setText("Choice 3: " + choice_3);
                            btnChoice_4.setText("Choice 4: " + choice_4);
                            etAnswer.setVisibility(View.INVISIBLE);
                            etAnswer.setText("answer: " + answer);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });


        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


        Button btnAnswer = findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = choices.getCheckedRadioButtonId();

                if(findViewById(selectedId)==findViewById(R.id.btnChoice_1)){
                    Toast.makeText(QuestionPageActivity.this, "incorrect answer!", Toast.LENGTH_SHORT).show();
                    etAnswer.setVisibility(View.VISIBLE);

                }
                else if(findViewById(selectedId)==findViewById(R.id.btnChoice_2)){
                    Toast.makeText(QuestionPageActivity.this, "incorrect answer!", Toast.LENGTH_SHORT).show();
                    etAnswer.setVisibility(View.VISIBLE);

                }
                else if(findViewById(selectedId)==findViewById(R.id.btnChoice_3)){
                    Toast.makeText(QuestionPageActivity.this, "correct answer!", Toast.LENGTH_SHORT).show();
                    etAnswer.setVisibility(View.VISIBLE);

                }
                else if(findViewById(selectedId)==findViewById(R.id.btnChoice_4)){
                    Toast.makeText(QuestionPageActivity.this, "incorrect answer!", Toast.LENGTH_SHORT).show();
                    etAnswer.setVisibility(View.VISIBLE);

                }
                else {
                    Toast.makeText(QuestionPageActivity.this, "select a choice!", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    public static int toUnicode(String emoji) {
        String text = "";
        if (emoji.length() > 2) {
            text = emoji.substring(0, 2);
        } else {
            text = emoji;
        }
        StringBuffer sb = new StringBuffer();
        int codePoint = 0;
        String hex = "";
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
        int unicode = Integer.parseInt(hex, 16);
        return unicode;
    }

    public static String toEmoji(int unicode) {
        String emoji = new String(Character.toChars(unicode));
        return emoji;
    }


}
