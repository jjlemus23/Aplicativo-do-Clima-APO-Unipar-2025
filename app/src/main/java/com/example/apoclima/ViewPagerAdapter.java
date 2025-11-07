package com.example.apoclima;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HojeFragment();
            case 1:
                return new ProximosDiasFragment();
            default:
                return new HojeFragment(); // Fragment padrão
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Agora temos apenas 2 abas
    }
}
