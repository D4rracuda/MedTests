package vsmu.testing.android.ui.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.ui.adapters.ResultAdapter;

/**
 * Created by Dan on 07.04.2016.
 */
public class Results extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results, null);
        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText(getString(R.string.results));
        ((TextView) view.findViewById(R.id.title)).setText(App.get().db.getProgress().getCountTrue() + "/" + App.get().getMax());
        ((ListView) view.findViewById(R.id.list)).setAdapter(new ResultAdapter(view.getContext(), R.layout.item_false));
        App.get().setSave(false);
        return view;
    }
}
