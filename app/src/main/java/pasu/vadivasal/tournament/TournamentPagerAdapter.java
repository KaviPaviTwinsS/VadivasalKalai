package pasu.vadivasal.tournament;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;


import java.lang.ref.WeakReference;

import pasu.vadivasal.globalModle.Appconstants;

public class TournamentPagerAdapter extends FragmentStatePagerAdapter {
    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray();
    private final Bundle bundle;
    int tabCount;
    String id, aboutData;

    public TournamentPagerAdapter(FragmentManager fm, int tabCount, String tournamentID) {
        super(fm);
        this.tabCount = tabCount;
        id = tournamentID;
        bundle = new Bundle();
        this.aboutData = aboutData;
        bundle.putString(Appconstants.TourID, id);
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                InfoFragment infoFragment = new InfoFragment();
                infoFragment.setArguments(bundle);
                return infoFragment;
            case 1:
                TournamentCommentary tournamentCommentary = new TournamentCommentary();
                tournamentCommentary.setArguments(bundle);
                return tournamentCommentary;
            case 2:
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                statisticsFragment.setArguments(bundle);
                return statisticsFragment;
            case 3:
                TournamentGalleryFragment tournamentGalleryFragment = new TournamentGalleryFragment();
                Bundle bundles = (Bundle) bundle.clone();
                bundles.putInt("type", 0);
                tournamentGalleryFragment.setArguments(bundles);
                return tournamentGalleryFragment;
            case 4:
                TournamentVideoFragment tournamentGalleryFragment1 = new TournamentVideoFragment();
                Bundle bundls = (Bundle) bundle.clone();
                bundls.putInt("type", 1);
                tournamentGalleryFragment1.setArguments(bundls);
                return tournamentGalleryFragment1;
            case 5:
                TournamentSponsors tournamentGalleryFragment2 = new TournamentSponsors();
                Bundle bunls = (Bundle) bundle.clone();
                bunls.putInt("type", 2);
                tournamentGalleryFragment2.setArguments(bunls);
                return tournamentGalleryFragment2;

            default:
                return new InfoFragment();
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
