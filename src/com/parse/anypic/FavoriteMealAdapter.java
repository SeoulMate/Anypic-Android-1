package com.parse.anypic;

import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite meals, including a 
 * bigger preview image, the meal's rating, and a "favorite"
 * star. 
 */

public class FavoriteMealAdapter extends ParseQueryAdapter<Picture> {

	public FavoriteMealAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Picture>() {
			public ParseQuery<Picture> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("Picture");
				query.whereContainedIn("rating", Arrays.asList("5", "4"));
				query.orderByDescending("rating");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Picture picture, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_favorites, null);
		}

		super.getItemView(picture, v, parent);

		ParseImageView mealImage = (ParseImageView) v.findViewById(R.id.icon);
		ParseFile photoFile = picture.getParseFile("photo");
		if (photoFile != null) {
			mealImage.setParseFile(photoFile);
			mealImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text1);
		titleTextView.setText(picture.getTitle());
		TextView ratingTextView = (TextView) v
				.findViewById(R.id.favorite_meal_rating);
		ratingTextView.setText(picture.getRating());
		return v;
	}

}
