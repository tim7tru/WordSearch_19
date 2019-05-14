package com.example.timmytruong.wordsearch_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LetterAdapter extends BaseAdapter {

    /**
     * Helper variables!
     */
    private final Context mContext;
    private final ArrayList<Character> letters;

    /**
     * DESCRIPTION: LetterAdapter constructor that initializes the letters arraylist
     * @param context: Context of mainActivity passed in
     * @param letters: Letters ArrayList
     */
    LetterAdapter(Context context, ArrayList<Character> letters) {
        this.mContext = context;
        this.letters = letters;
    }

    /**
     * DESCRIPTION: Gets count of the adapter
     * @return letters arraylist size
     */
    @Override
    public int getCount() {
        return letters.size();
    }

    /**
     * DESCRIPTION: Gets the id of the item at this position
     * @param position: position of the letter
     * @return the id of the letter view
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * DESCRIPTION: Gets the actual item as an object
     * @param position: postion of view
     * @return the view as an object
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * DESCRIPTION: Gets the view
     * @param position: position of view
     * @param convertView: the layout of the adapter
     * @param parent: ??
     * @return the view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.letter_layout, null);
        }

        TextView letter = convertView.findViewById(R.id.letter);
        letter.setTag(Integer.valueOf(position));
        letter.setText(Character.toString(letters.get(position)));

        return convertView;
    }
}
