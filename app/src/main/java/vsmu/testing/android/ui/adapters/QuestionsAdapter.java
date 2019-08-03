package vsmu.testing.android.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.model.Answer;

/**
 * Created by Dan on 06.04.2016.
 */
public class QuestionsAdapter extends ArrayAdapter<Answer> {

    private LayoutInflater inflater;
    private String question;
    private int rightId;

    public QuestionsAdapter(Context context, int resource, ArrayList<Answer> items, String question, int rightId) {
        super(context, resource, items);
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.question = question;
        this.rightId = rightId;
    }

    private class ViewHolder {
        TextView name;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            if(getItemViewType(position) == 0) convertView = inflater.inflate(R.layout.item_answer, null);
            else  convertView = inflater.inflate(R.layout.item_question, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(getItemViewType(position) == 0) {
            holder.name.setText(getItem(position).getText());
            if (getItem(position).isCheck()) {
                if (getItem(position).getId() == rightId) convertView.setBackgroundResource(R.color.true_color_tr);
                if (getItem(position).getId() == rightId && rightId == App.get().select_id) convertView.setBackgroundResource(R.color.true_color_tr);
                else if(getItem(position).getId() == App.get().select_id) convertView.setBackgroundResource(R.color.false_color_tr);
            }
        } else {
            holder.name.setText(question);
        }

        return convertView;
    }

}