import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doctor_appointment.tab1;
import com.example.doctor_appointment.tab2;
import com.example.doctor_appointment.tab3;

public class pageAdapter extends FragmentPagerAdapter {

    private int tabNumber;

    public pageAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new tab1();
            case 1:
                return new tab2();
            case 2:
                return  new tab3();
            default: return null;
        }

    }

    @Override
    public int getCount() {

        return tabNumber;
    }
}
