package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.model.Cart;

public class BadgeCartFragment {
	private static BadgeCartFragment instance;
	public static int cartCount = 0;
	private BadgeCartFragment(){
		cartCount = 0;
	}


	public static synchronized BadgeCartFragment getInstance(){
		if(instance==null){
			instance = new BadgeCartFragment();


		}
		return instance;
	}

	BottomNavigationView bottomNavigationView;


	public void updateCartCount(int cartCount) {
		BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.navMain);
		badge.setNumber(cartCount);
	}
}
