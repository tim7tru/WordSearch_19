package com.example.timmytruong.wordsearch_19;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MainController.MainInterface {

    /**
     * MainController Declaration
     */
    MainController mainController;

    /**
     * UI Declarations
     */
    GridView gridView;
    FrameLayout gridFrame;
    TextView scoreTV;
    Button resetBTN, plusBTN;
    TableLayout wordTableLayout;

    /**
     * Context declaration
     */
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Static context that can be used in other classes
         */
        MainActivity.context = getApplicationContext();

        /**
         * UI Definitions
         */
        scoreTV = findViewById(R.id.score);
        resetBTN = findViewById(R.id.resetBTN);
        plusBTN = findViewById(R.id.plusBTN);
        gridView = findViewById(R.id.gridView);
        gridFrame = findViewById(R.id.gridFrame);
        wordTableLayout = findViewById(R.id.wordTableLayout);

        /**
         * Controller Object and Method Call
         */
        mainController = new MainController(this, gridView, gridFrame, resetBTN, plusBTN, scoreTV, wordTableLayout);
        mainController.setGrid();
    }

    /**
     * DESCRIPTION: Handles results after the edit activity is closed
     * @param requestCode: ??
     * @param resultCode: What the request code was when sent
     * @param data: The data that was sent back.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            ArrayList<String> words = data.getStringArrayListExtra("words");
            mainController.reset(words);
        } else {
            mainController.reset();
        }
    }

    /**
     * DESCRIPTION: Setting the grid view to the created array of letters.
     * @param letterAdapter: Takes care of setting the array, setting tags, and every letter
     * @param gridView: Holds the letters in the form of a letter adapter object
     */
    @Override
    public void setLetters(LetterAdapter letterAdapter, GridView gridView) {
        gridView.setAdapter(letterAdapter);
    }

    /**
     * DESCRIPTION: Setting the onTouchListener (Controls the paint)
     * @param onTouchListener: The on touch listener detects movement inputted from the user and paints accordingly (also checks for correct answer)
     * @param gridView: Holds the views
     */
    @Override
    public void setOnTouchListener(View.OnTouchListener onTouchListener, GridView gridView) {
        gridView.setOnTouchListener(onTouchListener);
    }

    /**
     * DESCRIPTION: Setting the Reset button's on click listener
     * @param onClickListener: Controls the function of the button, Resetting the board, score, and rearranging the letters
     * @param resetBTN: Reset BTN View
     */
    @Override
    public void setResetClickListener(View.OnClickListener onClickListener, Button resetBTN) {
        resetBTN.setOnClickListener(onClickListener);
    }

    /**
     * DESCRIPTION: Sets the score text view
     * @param score: Every time the score is updated, it is passed in to be set
     * @param scoreTV: Score textView UI
     */
    @Override
    public void setScoreTextView(int score, int total, TextView scoreTV) {
        scoreTV.setText(Integer.toString(score) + "/" + Integer.toString(total));
    }

    /**
     * DESCRIPTION: Removes the paint search view based on what is needed to be removed
     * @param startViewCount: The starting point of what views need to be removed
     * @param endViewCount: The ending point of what views need to be removed
     * @param gridFrame: The FrameLayout which holds all these views
     */
    @Override
    public void removeSearchView(int startViewCount, int endViewCount, FrameLayout gridFrame) {
        gridFrame.removeViews(startViewCount, endViewCount - startViewCount);
    }

    /**
     * DESCRIPTION: Strikes out the text whenever a word is successfully found
     * @param word: The word that is found
     * @param tableLayout: The  layout in which to find the textview
     */
    @Override
    public void strikeOutWord(String word, TableLayout tableLayout) {

        // Loop through the tablelayout to get the tablerow
        for (int i = 0; i < tableLayout.getChildCount(); i++) {

            // The tableRow
            View rowView = tableLayout.getChildAt(i);

            // If the table row is a tablerow
            if (rowView instanceof TableRow) {

                // The tableRow
                TableRow row = (TableRow) rowView;

                // Loop through the table row to get the textviews
                for (int k = 0; k < 2; k++) {

                    // The Textview
                    View textView = row.getChildAt(k);

                    // If the textview is a textview and if it equals the word found
                    if (textView instanceof TextView && ((TextView) textView).getText().toString().equals(word)) {

                        // The textview
                        TextView text = (TextView) textView;

                        // Striking the textview out
                        text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            }
        }
    }

    /**
     * DESCRIPTION: Sets the tablelayout with all the words needing to be found
     * @param keySet: The words that need to be found, in a set
     * @param tableLayout: The table layout that holds the words
     */
    @Override
    public void setTableLayout(Set<String> keySet, TableLayout tableLayout) {
        int wordCounter = 0;

        // Initializing the tablerow
        TableRow tableRow = new TableRow(this);

        // Looping through the keyset of words
        for (String word : keySet) {
            // If the wordcounter is divisible by 2, then make a new row
            if (wordCounter % 2 == 0) {

                // Initializing the table row
                tableRow = new TableRow(this);

                // Setting the parameters of the tablerow
                TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
                rowParams.height = TableLayout.LayoutParams.WRAP_CONTENT;
                rowParams.width = TableLayout.LayoutParams.MATCH_PARENT;
                tableRow.setLayoutParams(rowParams);
            }

            // Making a new textview for each word
            TextView wordText = new TextView(this);

            // Setting the parameters for the textview
            TableRow.LayoutParams textParams = new TableRow.LayoutParams();
            textParams.weight = (float) 0.5;
            textParams.height = TableLayout.LayoutParams.WRAP_CONTENT;
            textParams.width = 0;
            wordText.setLayoutParams(textParams);

            // Setting the settings of the textview
            wordText.setTextSize(16);
            wordText.setText(word);
            wordText.setPadding(0, 10, 0, 10);
            wordText.setGravity(Gravity.CENTER);

            // Removing the parents of each view we created to be able to add each to another parent
            if (wordText.getParent() != null) {
                ((ViewGroup) wordText.getParent()).removeView(wordText);
            }
            if (tableRow.getParent() != null) {
                ((ViewGroup) tableRow.getParent()).removeView(tableRow);
            }

            // Adding the textview to the tablerow
            tableRow.addView(wordText);

            // Adding the tablerow to the tablelayout
            tableLayout.addView(tableRow);

            // Incrementing the wordcounter
            wordCounter++;
        }
    }

    /**
     * DESCRIPTION: Removes all table rows from the table layout (WORDBANK)
     * @param tableLayout: holds the table rows and textviews with words
     */
    @Override
    public void clearTableLayout(TableLayout tableLayout) {
        tableLayout.removeAllViews();
    }

    /**
     * DESCRIPTION: Sets the onclicklistener for the plus button (Edit Words)
     * @param wordsAL: words array list to be editted in the other activity
     * @param plusBTN: plus button UI to set the listener
     */
    @Override
    public void setPlusClickListener(final ArrayList<String> wordsAL, Button plusBTN) {
        View.OnClickListener plusClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditWordsActivity.class);
                intent.putStringArrayListExtra("words", wordsAL);
                startActivityForResult(intent, 0);
            }
        };
        plusBTN.setOnClickListener(plusClickListener);
    }

    /**
     * DESCRIPTION: Displays a dialogue box whenever the user wins
     */
    @Override
    public void displayWinDialogue() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.win_title)
                .setMessage(R.string.win_message)
                .setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainController.reset();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}