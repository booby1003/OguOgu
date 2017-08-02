package com.oguogu.demo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.oguogu.fragment.BaseFragment;
import com.oguogu.fragment.BookmarkFragment;
import com.oguogu.fragment.FeedFragment;
import com.oguogu.fragment.HomeFragment;
import com.oguogu.fragment.MyInfoFragment;

import java.util.ArrayList;

/**
 *
 */
public class DemoViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
	private BaseFragment currentFragment;

	public DemoViewPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments.clear();
		fragments.add(new HomeFragment());
		fragments.add(new BookmarkFragment());
		fragments.add(DemoFragment.newInstance(0));
		fragments.add(new FeedFragment());
		fragments.add(new MyInfoFragment());

	}

	@Override
	public BaseFragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		if (getCurrentFragment() != object) {
			currentFragment = ((BaseFragment) object);
		}
		super.setPrimaryItem(container, position, object);
	}

	/**
	 * Get the current fragment
	 */
	public BaseFragment getCurrentFragment() {
		return currentFragment;
	}
}