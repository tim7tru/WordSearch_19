package com.example.timmytruong.wordsearch_19;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class MainController {

    /**
     * UI Declarations
     */
    private MainInterface mainInterface;
    private FrameLayout gridFrame;
    private GridView gridView;
    private Button resetBTN, plusBTN;
    private TextView scoreTV;
    private TableLayout wordTableLayout;

    /**
     * Class Variable Declarations
     */
    private int currentPosition, score, startViewNumber, totalWords;
    private int startCentreX, startCentreY, centreX, centreY;
    private Map<String, Boolean> wordsHM;
    private ArrayList<Character> letters;
    private ArrayList<String> wordsAL;
    private Map<Integer, Boolean> hasChanged;
    private Random random;
    private String formedWord;


    /**
     * DESCRIPTION: MainController constructor to initialize an object of this class
     * @param mainInterface: Context is passed to implement the interface
     * @param gridView: Holds the grid of letters
     * @param gridFrame: Holds the gridview and additional paint views
     * @param resetBTN: Reset button UI
     * @param scoreTV: Score textView UI
     * @param plusBTN:
     * @param wordTableLayout:
     * ArrayLists and HashMaps are initiailized here
     * Same with the random object
     */
    MainController(MainInterface mainInterface, GridView gridView, FrameLayout gridFrame, Button resetBTN, Button plusBTN, TextView scoreTV, TableLayout wordTableLayout) {
        this.gridView = gridView;
        this.gridFrame = gridFrame;
        this.resetBTN = resetBTN;
        this.plusBTN = plusBTN;
        this.scoreTV = scoreTV;
        this.mainInterface = mainInterface;
        this.wordTableLayout = wordTableLayout;
        hasChanged = new HashMap<>();
        wordsHM = new HashMap<>();
        letters = new ArrayList<>();
        random = new Random();

        wordsAL = new ArrayList<>();
        wordsAL.add(MainActivity.context.getString(R.string.kotlin));
        wordsAL.add(MainActivity.context.getString(R.string.objective_c));
        wordsAL.add(MainActivity.context.getString(R.string.swift));
        wordsAL.add(MainActivity.context.getString(R.string.variable));
        wordsAL.add(MainActivity.context.getString(R.string.mobile));
        wordsAL.add(MainActivity.context.getString(R.string.java));


    }

    /**
     * DESCRIPTION: Sets the grid with letters
     * Initializes scores to 0, and formedword to a blank string
     * Sets the answers with correct positions into the arrayadapter
     * Sets the gridView
     * Sets the onTouchListener
     * Sets the ResetClickListener
     */
    void setGrid() {
        score = 0;
        formedWord = "";
        fillWordsArray();
        generateRandomLetters();
        LetterAdapter letterAdapter = new LetterAdapter(MainActivity.context, letters);
        findRandomPosition();

        mainInterface.setScoreTextView(score, totalWords, scoreTV);
        mainInterface.setPlusClickListener(wordsAL, plusBTN);
        mainInterface.setLetters(letterAdapter, gridView);
        mainInterface.setTableLayout(wordsHM.keySet(), wordTableLayout);
        mainInterface.setOnTouchListener(onTouchListener, gridView);
        mainInterface.setResetClickListener(onClickListener, resetBTN);
    }

    /**
     * DESCRIPTION: Resets the entire game
     * Reinitializes everything
     * Removes all additional search views
     * Resets the grid
     */
    void reset() {
        hasChanged = new HashMap<>();
        wordsHM = new HashMap<>();
        letters = new ArrayList<>();
        random = new Random();
        int childCount = gridFrame.getChildCount();
        mainInterface.clearTableLayout(wordTableLayout);
        mainInterface.removeSearchView(1, childCount, gridFrame);
        setGrid();
    }

    /**
     * DESCRIPTION: Resets the entire game and restarts it with a new array list of word
     * @param wordsAL: the new array list of words
     */
    void reset(ArrayList<String> wordsAL) {
        this.wordsAL = wordsAL;
        hasChanged = new HashMap<>();
        wordsHM = new HashMap<>();
        letters = new ArrayList<>();
        random = new Random();
        int childCount = gridFrame.getChildCount();
        mainInterface.clearTableLayout(wordTableLayout);
        mainInterface.removeSearchView(1, childCount, gridFrame);
        setGrid();
    }

    /**
     * DESCRIPTION: Gets all the words needed and puts them into a hashmap
     * HashMap<String, Boolean>
     *     String: word
     *     Boolean: if the word has been found or not (True = found, False = not found)
     * Gets the total number of words needed to be found
     */
    private void fillWordsArray() {
        for (int i = 0; i < wordsAL.size(); i++) {
            wordsHM.put(wordsAL.get(i), false);
        }
        totalWords = wordsHM.size();
    }

    /**
     * DESCRIPTION: Generates a random position and a random direction to be checked by other functions
     * Master function of checks for inserting the words
     */
    private void findRandomPosition() {
        for (String word : wordsHM.keySet()) {
            int length = word.length(), direction, start, counter = 0;
            boolean isSized = false, isPositioned;

            start = random.nextInt(100);
            direction = random.nextInt(8);

            while (!isSized) {
                isSized = isSizedCorrectly(start, length, direction);

                if (isSized) {
                    isPositioned = isPositionedCorrectly(word, start, length, direction);
                    if (isPositioned) {
                        break;
                    } else {
                        isSized = false;
                    }
                }

                if (counter != 8 && direction == 7) {
                    direction = 0;
                    counter++;
                } else if (counter != 8){
                    direction++;
                    counter++;
                } else if (counter == 8) {
                    start = random.nextInt(100);
                    counter = 0;
                }
            }
            Log.i("SET", "SET");
            changeLetterArray(start, length, direction, word);
        }
    }

    /**
     * DESCRIPTION: Once a word clears the checks in findRandomPosition(), this function inserts it into the letters arrayList
     * @param start: starting position (position of cell in the gridView
     * @param length: length of the word to be inserted
     * @param direction: direction of the word to be inserted
     * @param word: the word itself
     */
    private void changeLetterArray(int start, int length, int direction, String word) {
        switch (direction) {
            case 0:
                // STRAIGHT RIGHT
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start++;
                }
                break;
            case 1:
                // DIAGONAL DOWN-RIGHT
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start += 11;
                }
                break;
            case 2:
                // STRAIGHT DOWN
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start += 10;
                }
                break;
            case 3:
                // DIAGONAL DOWN-LEFT
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start += 9;
                }
                break;
            case 4:
                // STRIAGHT LEFT
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start--;
                }
                break;
            case 5:
                // DIAGONAL UP-LEFT
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start -= 11;
                }
                break;
            case 6:
                // STRAIGHT UP
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start -= 10;
                }
                break;
            case 7:
                // DIAGONAL UP-RIGHT
                for (int i = 0; i < length; i++) {
                    hasChanged.put(start, true);
                    letters.set(start, word.charAt(i));
                    start -= 9;
                }
                break;
        }
    }

    /**
     * DESCRIPTION: After seeing if the word can fit into the space length wise, we must ensure that the word does not interfere with another inserted word
     * @param word: word to be inserted
     * @param start: start position of the word (index of cell in gridview)
     * @param length: length of the word to be inserted
     * @param direction: direction of the word
     * @return boolean: if the word can be placed w/out disruption or not
     */
    private boolean isPositionedCorrectly(String word, int start, int length, int direction) {
        boolean canFit = false;

        switch (direction) {
            case 0:
                // STRAIGHT RIGHT
                for (int i = start, j = 0; j < length; i++, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 1:
                // DIAGONAL DOWN-RIGHT
                for (int i = start, j = 0; j < length; i += 11, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 2:
                // STRAIGHT DOWN
                for (int i = start, j = 0; j < length; i += 10, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 3:
                // DIAGONAL DOWN-LEFT
                for (int i = start, j = 0; j < length; i += 9, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 4:
                // STRAIGHT LEFT
                for (int i = start, j = 0; j < length; i--, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 5:
                // DIAGONAL UP-LEFT
                for (int i = start, j = 0; j < length; i -= 11, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 6:
                // STRAIGHT UP
                for (int i = start, j = 0; j < length; i -= 10, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
            case 7:
                // DIAGONAL UP-RIGHT
                for (int i = start, j = 0; j < length; i -= 9, j++) {
                    if (letterChecker(word, i, j)) {
                        canFit = true;
                    } else {
                        return false;
                    }
                }
                break;
        }
        return canFit;
    }

    /**
     * DESCRIPTION: Helper function for isPositionedCorrectly. If the letter conflicts with another word's letter, it checks if they are compatible or not. (same letter?)
     * @param word: word to be inserted
     * @param i: index of cell/letter in gridview / letters arraylist
     * @param j: index of letter in word
     * @return boolean: if the word letter can be inserted there
     */
    private boolean letterChecker(String word, int i, int j) {
        if (i < 100) {
            if (hasChanged.get(i)) {
                if (letters.get(i).equals(word.charAt(j))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * DESCRIPTION: Checks to see if a word can fit into a direction length-wise
     * @param start: start position (position of cell/letter in arraylist/gridview)
     * @param length: length of word to be inserted
     * @param direction: direction of the word to be inserted
     * @return boolean: if the word can fit into the direction
     */
    private boolean isSizedCorrectly(int start, int length, int direction) {
        if (start >= 0) {
            switch (direction) {
                case 0:
                    // STRAIGHT RIGHT
                    return (((((start / 10) * 10) + 9) - start) >= length);
                case 1:
                    // DIAGONAL DOWN-RIGHT
                    return (((((start / 10) * 10) + 9) - start) >= length && 9 - (start / 10) >= length);
                case 2:
                    // STRAIGHT DOWN
                    return (9 - (start / 10) >= length);
                case 3:
                    // DIAGONAL DOWN-LEFT
                    return ((start - ((start / 10) * 10) >= length && (start / 10) + 1 >= length));
                case 4:
                    // STRAIGHT LEFT
                    return ((start - ((start / 10) * 10) >= length));
                case 5:
                    // DIAGONAL UP-LEFT
                    return ((start - ((start / 10) * 10) >= length && (start / 10) + 1 >= length));
                case 6:
                    // STRAIGHT UP
                    return ((start / 10) + 1 >= length);
                case 7:
                    // DIAGONAL UP-RIGHT
                    return (((((start / 10) * 10) + 9) - start) >= length && (start / 10) + 1 >= length);
            }
        }
        return false;
    }

    /**
     * DESCRIPTION: Generates random letters and inserts them into the arrayList of letters
     * These make up all the letters not altered by the words
     */
    private void generateRandomLetters() {
        // Filling an arraylist with random characters
        for(int i = 0; i < 100; i++) {
            char randomChar = (char) ((int)'A' + Math.random() * ((int)'Z' - (int)'A' + 1));
            hasChanged.put(i, false);
            letters.add(randomChar);
        }
    }

    /**
     * DESCRIPTION: OnclickListener for the resetButton, this is passed into a view function
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reset();
        }
    };

    /**
     * DESCRIPTION: PaintHolder Class to hold the views of paint
     */
    private class PaintViewHolder {
        private SearchDraw searchDraw;
    }

    /**
     * DESCRIPTION: Deals with all the technicalities of detecting motion and paints accordingly
     * DOWN: Starts painting and collects the letter the input passes over
     * MOVE: Continues to collect letters
     * UP: If the collected letters match a word in the words hashmap
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // Gets the type of motion detected
            int action = event.getActionMasked();
            int globalX, globalY, endViewNumber;

            // Gets the gridView into a variable
            GridView grid = (GridView) v;

            // Object to hold the search draw object (paint)
            PaintViewHolder newPaint = new PaintViewHolder();

            // Touched X-Y Coords
            int x = (int) event.getX();
            int y = (int) event.getY();

            // Getting the position/index of the cell from the X-Y Coordinates
            int position = grid.pointToPosition(x, y);

            // If the position of the cell is a valid position/index in the gridview
            if (position >= 0 && position < 100) {

                // Getting the cell's view (TextView) from the position by Tag set in letterAdapter
                TextView cellView = grid.findViewWithTag(position);

                // Getting Global Position of the cell
                Rect cellViewRect = new Rect();
                cellView.getDrawingRect(cellViewRect);
                grid.offsetDescendantRectToMyCoords(cellView, cellViewRect);
                globalX = cellViewRect.left;
                globalY = cellViewRect.top;

                // From the global positions, get the centre of the cells position
                centreX = globalX + cellView.getWidth() / 2;
                centreY = globalY + cellView.getHeight() / 2;

                // Create a new searchDraw object to fit into the PaintViewHolder class
                newPaint.searchDraw = new SearchDraw(MainActivity.context);

                // Add the object into the stack of views (FrameLayout)
                gridFrame.addView(newPaint.searchDraw);

                // Switch case for different types of motion
                switch (action) {
                    // Movements:
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:

                        // If the position is not the previously set position
                        // [PURPOSE]: To formulate the searched word without replicating the same letter
                        if (currentPosition != position) {

                            // Prevent parent from stealing the event
                            v.getParent().requestDisallowInterceptTouchEvent(true);

                            // Getting the letter of the cell and adding it to another variable to form a searched word
                            String letter = cellView.getText().toString();
                            formedWord += letter;

                            // Specific Movements: (ex. Things that apply to ONLY ACTION_DOWN, etc.)
                            switch (action) {
                                // Start of Draw/Search
                                case MotionEvent.ACTION_DOWN:
                                    // Get the start view count before the drawing begins
                                    startViewNumber = gridFrame.getChildCount();

                                    // Getting the start cell coordinates (centre)
                                    startCentreX = globalX + cellView.getWidth() / 2;
                                    startCentreY = globalY + cellView.getHeight() / 2;

                                    // Start Drawing
                                    newPaint.searchDraw.drawLine(startCentreX, startCentreY, centreX, centreY, 0);
                                    break;
                                case MotionEvent.ACTION_MOVE: // When the input is moved
                                    // Continue to draw
                                    newPaint.searchDraw.drawLine(centreX, centreY, centreX, centreY, 0);
                                    break;
                            }
                        }
                        // Sets the new position
                        currentPosition = position;
                        break;

                    case MotionEvent.ACTION_UP:
                        // When the search is completed (finger lifted, etc.), tje ending ChildCount is taken
                        endViewNumber = gridFrame.getChildCount();

                        // If the formed word is inside the answers hashmap (words)
                        if (wordsHM.containsKey(formedWord)) {

                            // If the word has not been found
                            if (!wordsHM.get(formedWord)) {
                                // Increment the score
                                score++;

                                // Set the new score text view
                                mainInterface.setScoreTextView(score, totalWords, scoreTV);

                                // Make a new paint view and add it to the frame layout
                                newPaint.searchDraw = new SearchDraw(MainActivity.context);
                                gridFrame.addView(newPaint.searchDraw);

                                // Draw a new Green (since the answer is correct) line from the start to the finish
                                newPaint.searchDraw.drawLine(startCentreX, startCentreY, centreX, centreY, 1);

                                // Change the word to found
                                wordsHM.put(formedWord, true);

                                // Strike out the word in the tableLayout
                                mainInterface.strikeOutWord(formedWord, wordTableLayout);

                                if (score == totalWords) {
                                    // If the user finds all the words, display a dialog
                                    mainInterface.displayWinDialogue();
                                }
                            }
                        }

                        // Remove the painted search view
                        mainInterface.removeSearchView(startViewNumber - 1, endViewNumber, gridFrame);

                        // Reset the formed word
                        formedWord = "";
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        // Reset the formed word
                        formedWord = "";
                        break;
                }

            } else if (position == -1 && action == MotionEvent.ACTION_UP) { // This is for if the word is found but if the user stopped the input on a position that is invalid (position == -1)
                // When the search is completed, the ending childcount is taken
                endViewNumber = gridFrame.getChildCount();

                // If the formed word is inside the answers hashmap (words)
                if (wordsHM.containsKey(formedWord)) {

                    // If the word has not been found
                    if (!wordsHM.get(formedWord)) {
                        // Increment the score
                        score++;

                        // Set the new score text view
                        mainInterface.setScoreTextView(score, totalWords, scoreTV);

                        // Make a new paint view and add it to the frame layout
                        newPaint.searchDraw = new SearchDraw(MainActivity.context);
                        gridFrame.addView(newPaint.searchDraw);

                        // Draw a new Green (since the answer is correct) line from the start to the finish
                        newPaint.searchDraw.drawLine(startCentreX, startCentreY, centreX, centreY, 1);

                        // Change the word to found
                        wordsHM.put(formedWord, true);

                        // Strike out the word in the tableLayout
                        mainInterface.strikeOutWord(formedWord, wordTableLayout);

                        if (score == totalWords) {
                            // If the user finds all the words, display a dialog
                            mainInterface.displayWinDialogue();
                        }
                    }
                }

                // Remove the yellow search view
                mainInterface.removeSearchView(startViewNumber - 1 , endViewNumber, gridFrame);

                // Reset the formed word
                formedWord = "";
            }
            return true;
        }
    };


    /**
     * DESCRIPTION: View Functions Implemented in MainActivity
     */
    public interface MainInterface {
        void setLetters(LetterAdapter letterAdapter, GridView gridView);
        void setOnTouchListener(View.OnTouchListener onTouchListener, GridView gridView);
        void setResetClickListener(View.OnClickListener onClickListener, Button resetBTN);
        void setScoreTextView(int score, int total, TextView scoreTV);
        void removeSearchView(int startViewCount, int endViewCount, FrameLayout gridFrame);
        void strikeOutWord(String word, TableLayout tableLayout);
        void setTableLayout(Set<String> keySet, TableLayout tableLayout);
        void clearTableLayout(TableLayout tableLayout);
        void setPlusClickListener(ArrayList<String> wordsAL, Button plusBTN);
        void displayWinDialogue();
    }
}
