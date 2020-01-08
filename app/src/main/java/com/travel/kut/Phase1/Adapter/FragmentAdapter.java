package com.travel.kut.Phase1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.travel.kut.Phase1.LoginSignUp.LoginFragment;
import com.travel.kut.Phase1.LoginSignUp.SignUp;

    public class FragmentAdapter extends FragmentPagerAdapter {

        private static int FRAGMENT_NUM = 2;
        private String[] tabTitles = new String[]{"Sign In", "Sign Up"};

        public FragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new LoginFragment();
                case 1: return new SignUp();
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return FRAGMENT_NUM;
        }
    }

