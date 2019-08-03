package vsmu.testing.android.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.database.DBHelper;
import vsmu.testing.android.model.Result;

/**
 * Created by Dan on 07.04.2016.
 */
public class ResultAdapter extends ArrayAdapter<Result> {

    private LayoutInflater inflater;

    public ResultAdapter(Context context, int resource) {
        super(context, resource, DBHelper.getProgress().getResult());
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView text;
        TextView answer;
        TextView true_answer;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isRight() ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if(getItemViewType(position) == 0){
                convertView = inflater.inflate(R.layout.item_false, null);
                holder.text = convertView.findViewById(R.id.text);
                holder.answer = convertView.findViewById(R.id.answer);
                holder.true_answer = convertView.findViewById(R.id.true_answer);
            }
            else {
                convertView = inflater.inflate(R.layout.item_true, null);
                holder.text = convertView.findViewById(R.id.text);
                holder.answer = convertView.findViewById(R.id.answer);
            }
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(getItemViewType(position) == 0) {
            holder.text.setText(getItem(position).getText());
            holder.answer.setText(getItem(position).getAnswer());
            holder.true_answer.setText(getItem(position).getTrue_answer());
            convertView.setBackgroundResource(R.color.false_color);
        } else {
            holder.text.setText(getItem(position).getText());
            holder.answer.setText(getItem(position).getTrue_answer());
            convertView.setBackgroundResource(R.color.true_color);
        }
        return convertView;
    }

}
