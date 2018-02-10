package com.software.amazing.emotiontelemetry.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.amazing.emotiontelemetry.Emotions;
import com.software.amazing.emotiontelemetry.R;
import com.software.amazing.emotiontelemetry.objects.Emotion;

import java.util.List;

/**
 * Created by myxroft on 10/02/2018.
 */

public class EmotionListAdapter extends BaseAdapter {

    private Activity act;
    private List<Emotion> emotions;

    public EmotionListAdapter(Activity activity, List<Emotion> emotionList){

        this.act = activity;
        this.emotions = emotionList;
    }

    @Override
    public int getCount() {
        return emotions.size();
    }

    @Override
    public Emotion getItem(int position) {
        return this.emotions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = this.act.getLayoutInflater().inflate(R.layout.list_item_emotion,null);
        }

        TextView tvEmotion = (TextView)convertView.findViewById(R.id.tvEmotion);

        Emotion currEmote = emotions.get(position);

        tvEmotion.setText(currEmote.EMOTION_NAME);

        return convertView;
    }
}
