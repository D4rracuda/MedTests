package vsmu.testing.android.ui.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;
import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.database.DBHelper;
import vsmu.testing.android.model.Section;
import vsmu.testing.android.ui.adapters.SectionAdapter;

/**
 * Created by Dan on 05.04.2016.
 */
public class ChooseSections extends Fragment {

    private ArrayList<RadioButton> segments;
    private ArrayList<Section> sections;
    private int mCount = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_choose_sections, null);
        final ListView listView = view.findViewById(R.id.list);
        SegmentedGroup segmented = view.findViewById(R.id.segmented);
        App.get().setSave(false);
        sections = DBHelper.getDataDisciplines().getListSection();
        final SectionAdapter adapter = new SectionAdapter(view.getContext(), R.layout.item_section, sections);
        segments = new ArrayList<>();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sections.get(position).isCheck()){
                    if(getCheckIdList(sections).size() > 1) sections.get(position).setCheck(false);
                }else {
                    sections.get(position).setCheck(true);
                }
                int count = getCountQuestions(sections);
                if(count < 100) segments.get(2).setEnabled(false);
                else segments.get(2).setEnabled(true);
                if(count < 50) segments.get(1).setEnabled(false);
                else segments.get(1).setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });

        final ArrayList<Integer> items = App.get().db.getDataDisciplines().getSegmented();
        for(int i : items){
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.item_segment, null);
            if(items.get(items.size() - 1) == i)radioButton.setText(getString(R.string.all));
            else radioButton.setText(""+ i);
            radioButton.setTag(i);
            segments.add(radioButton);
            segmented.addView(radioButton);
            segmented.updateBackground();
        }
        setColorStateList(segments, view.getContext());
        segments.get(0).setChecked(true);

        segmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int tag = (int) view.findViewById(group.getCheckedRadioButtonId()).getTag();
                if(tag == items.get(items.size() - 1)) mCount = getCountQuestions(sections);
                else mCount = (int) view.findViewById(group.getCheckedRadioButtonId()).getTag();
            }
        });

        view.findViewById(R.id.begin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.get().setNumber(0);
                App.get().setMax(mCount);
                App.get().setSave(true);
                DBHelper.getProgress().delete_table();
                DBHelper.getDataDisciplines().insertQuestions(TextUtils.join(",", getCheckIdList(sections)), mCount);
                App.get().setCheckedDiscipline(getCheckIdList(sections));
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.in_right, R.anim.out_left).replace(R.id.frame,  new Questions()).commit();
            }
        });

        setSelected();
        return view;
    }
    private void setSelected() {
        int count = getCountQuestions(sections);
        if(count < 100) segments.get(1).setEnabled(false);
        else segments.get(1).setEnabled(true);
        if(count < 50) segments.get(0).setEnabled(false);
        else segments.get(0).setEnabled(true);
    }

    private void setColorStateList(ArrayList<RadioButton> segments, Context context){
        ColorStateList colorStateList = ContextCompat.getColorStateList(context, R.drawable.selector_segment_text);
        for(RadioButton r : segments) {
            r.setTextColor(colorStateList);
        }
    }

    private ArrayList<Integer> getCheckIdList(ArrayList<Section> items){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for(Section s : items){
            if (s.isCheck()) ids.add(s.getId());
        }
        return ids;
    }

    private int getCountQuestions(ArrayList<Section> items){
        int count = 0;
        for(Section s : items){
            if (s.isCheck()) count += s.getCountQuestions();
        }
        return count;
    }
}