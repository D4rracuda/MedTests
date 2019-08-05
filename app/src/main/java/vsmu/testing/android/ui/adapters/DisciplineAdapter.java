package vsmu.testing.android.ui.adapters;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vsmu.testing.android.R;

/**
 * Created by Dan on 01.04.2016.
 */
public class DisciplineAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mDisciplines;

    public DisciplineAdapter(Context context){
        this.mContext = context;
        this.mDisciplines = mContext.getResources().getStringArray(R.array.disсiplines);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_discipline, collection, false);
        ((TextView) layout.findViewById(R.id.name)).setText(mDisciplines[position]);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mDisciplines.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
