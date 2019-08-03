package vsmu.testing.android.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.model.Answer;
import vsmu.testing.android.model.Question;
import vsmu.testing.android.ui.adapters.QuestionsAdapter;

/**
 * Created by Dan on 06.04.2016.
 */
public class Questions extends Fragment {

    private int number, max;
    private boolean isSelecred = false;
    private boolean isCheck = false;

    private Button next;
    private QuestionsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_question, null);
        number = App.get().getNumber();
        max = App.get().getMax();
        if (number == max) getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Results()).commit();
        final ListView listView = (ListView) view.findViewById(R.id.list);
        next = (Button) view.findViewById(R.id.next);
        final Question question = App.get().db.getProgress().getQuestion();
        if (question != null) {
            question.getAnswers().add(0, new Answer("", 0));
            number++;
            ((ImageView) getActivity().findViewById(R.id.navigationIcon)).setImageResource(R.mipmap.ic_close);
            ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText("" + number + "/" + max);
            adapter = new QuestionsAdapter(view.getContext(), R.layout.item_answer, question.getAnswers(), question.getText(), question.getRight_id());
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0 && !isCheck) {
                        for (int j = 0; j < parent.getChildCount(); j++)
                            parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.true_color_tr));
                        App.get().db.getProgress().update(question.getId(), question.getAnswers().get(position).getId(), question.getAnswers().get(position).getText());
                        App.get().setNumber(number);
                        App.get().select_id = question.getAnswers().get(position).getId();
                        isSelecred = true;
                    }
                }
            });

        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelecred) {
                    if (App.get().isExam() || isCheck) {
                        next();
                    } else {
                        isCheck = true;
                        for (Answer ans : question.getAnswers()) ans.setCheck(true);
                        adapter.notifyDataSetChanged();
                        next.setText(getString(R.string.next));
                    }
                }
            }
        });

        return view;
    }


    private void next(){
        if (number == max) {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.in_right, R.anim.out_left).replace(R.id.frame, new Results()).commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.in_right, R.anim.out_left).replace(R.id.frame, new Questions()).commit();
        }
    }
}
