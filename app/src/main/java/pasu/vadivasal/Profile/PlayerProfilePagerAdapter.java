package pasu.vadivasal.Profile;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
public class PlayerProfilePagerAdapter extends FragmentStatePagerAdapter {
    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray();
    boolean myProfile = false;
    int tabCount;

    public PlayerProfilePagerAdapter(FragmentManager fm, int tabCount, boolean isMyProfile) {
        super(fm);
        this.tabCount = tabCount;
        this.myProfile = isMyProfile;
    }

    public Fragment getItem(int position) {
       // return new MatchesFragment();
        switch (position) {
            case 0:
                return new MatchesFragment();
//            case 1:
//                return new StateFragment();
//            case 2:
//                return new AwardFragment();
//            case 3:
//                return new BadgesFragment();
//            case 4:
//                return new TeamFragment();
//            case 5:
//                return new MediaFragment();
//            case 6:
//                return new ConnectionsFragment();
            case 1:
                if (this.myProfile) {
                    return new MyInfoFragment();
                }
                return new PlayerInfoFragment();
            default:
                return new MyInfoFragment();
        }
    }

    public int getCount() {
        return this.tabCount;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.instantiatedFragments.put(position, new WeakReference(fragment));
        return fragment;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        this.instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Nullable
    public Fragment getFragment(int position) {
        WeakReference<Fragment> wr = (WeakReference) this.instantiatedFragments.get(position);
        if (wr != null) {
            return (Fragment) wr.get();
        }
        return null;
    }
}
