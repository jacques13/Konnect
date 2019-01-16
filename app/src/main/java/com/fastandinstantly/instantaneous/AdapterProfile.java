package com.fastandinstantly.instantaneous;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/***
 * ADAPTER
 */

public class AdapterProfile extends ArrayAdapter<User> {






    private static final String TAG = "SampleAdapter";

    static class ViewHolder {

        TextView txtLineOne;
        DynamicHeightImageView dynamicHeightImageView;
        TextView txtGender;
      }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public AdapterProfile(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.colorPrimary);
        mBackgroundColors.add(R.color.colorAccent);
        mBackgroundColors.add(R.color.colorPrimaryDark);
        mBackgroundColors.add(R.color.colorAccent);
        mBackgroundColors.add(R.color.colorPrimary);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_profile, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (TextView) convertView.findViewById(R.id.txt_line1);
            vh.txtGender = (TextView) convertView.findViewById(R.id.txtGender);
            vh.dynamicHeightImageView = (DynamicHeightImageView) convertView.findViewById(R.id.image);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

       // convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));

        Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

        vh.txtLineOne.setText(getItem(position).getName());
        vh.txtGender.setText("");
        vh.dynamicHeightImageView.setHeightRatio(1.15);
        Picasso.with(getContext())
                .load(getItem(position).getIconID())
                .placeholder(R.color.colorAccent)
                //.resize(200, 200)
                .fit()

                //.error(R.)
                .into(vh.dynamicHeightImageView);



        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.clear();
            sPositionHeightRatios.append(position, ratio);

            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2) ; // height will be 1.0 - 1.5 the width
    }
}