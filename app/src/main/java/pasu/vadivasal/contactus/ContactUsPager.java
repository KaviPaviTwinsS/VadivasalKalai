package pasu.vadivasal.contactus;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class ContactUsPager extends FragmentStatePagerAdapter {
    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray();
    int tabCount;

    public ContactUsPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ContactWriteFragment();
            case 1:
                return new ContactSpeakFragment();
            default:
                return null;
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
