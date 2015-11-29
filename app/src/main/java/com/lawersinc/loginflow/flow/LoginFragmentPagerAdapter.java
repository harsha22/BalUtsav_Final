package com.lawersinc.loginflow.flow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import login.lawersinc.com.lawersinc.R;

/**
 * Created by mntiwari on 9/16/15.
 */
public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "a", "b", "c" };
    private Context context;
    private int[] imageResId = {
            R.drawable.tabimg2,
            R.drawable.tabimg2,
            R.drawable.tabimg2
    };

    public LoginFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];

        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
            // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
        /*
            Drawable image = context.getResources().getDrawable(imageResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
            */
    }
}
