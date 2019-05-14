package com.example.timmytruong.wordsearch_19;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWordsActivity extends AppCompatActivity {

    /**
     * HELPER VARIABLES
     */
    ArrayList<String> words;
    ArrayList<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_words);

        /**
         * UI DECLARATIONS
         */
        Button cancelBTN, saveBTN;
        EditText editText0, editText1, editText2, editText3, editText4, editText5, editText6, editText7;

        /**
         * DESCRIPTION: Getting the words passed into this activity
         */
        Intent intent = getIntent();
        words = intent.getStringArrayListExtra("words");

        /**
         * Creating new arraylist for the editTexts
         */
        editTexts = new ArrayList<>();

        /**
         * Cancel Button Definition & Setting onclick Listener for that button (sends nothing back to MainActivity)
         */
        cancelBTN = findViewById(R.id.cancelBTN);
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });

        /**
         * Save Button definition & Setting onclicklistener for that button
         * Sends the saved text from edittexts back to the mainActivity for it to be used in the wordsearch
         */
        saveBTN = findViewById(R.id.saveBTN);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                words = new ArrayList<>();
                for (int i = 0; i < editTexts.size(); i++) {
                    if (!editTexts.get(i).getText().toString().equals("") && editTexts.get(i).getText().toString().length() <= 10) {
                        words.add(editTexts.get(i).getText().toString().toUpperCase());
                    } else if (editTexts.get(i).getText().toString().length() > 10) {
                        Toast.makeText(EditWordsActivity.this, editTexts.get(i).getText().toString() + " is too long, Please keep words under 11 characters.", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("words", words);
                setResult(0, intentWithResult);
                finish();
            }
        });

        /**
         * EditTexts UI Definition
         */
        editText0 = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText2);
        editText2 = findViewById(R.id.editText3);
        editText3 = findViewById(R.id.editText4);
        editText4 = findViewById(R.id.editText5);
        editText5 = findViewById(R.id.editText6);
        editText6 = findViewById(R.id.editText7);
        editText7 = findViewById(R.id.editText8);

        /**
         * EditTexts ArrayList population
         */
        editTexts.add(editText0);
        editTexts.add(editText1);
        editTexts.add(editText2);
        editTexts.add(editText3);
        editTexts.add(editText4);
        editTexts.add(editText5);
        editTexts.add(editText6);
        editTexts.add(editText7);

        /**
         * Setting the edittexts to words already inputted
         */
        for (int i = 0; i < words.size(); i++) {
            editTexts.get(i).setText(words.get(i));
        }
    }
}
