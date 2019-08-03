package vsmu.testing.android.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vsmu.testing.android.R;
import vsmu.testing.android.model.Section;

/**
 * Created by Dan on 05.04.2016.
 */
public class SectionAdapter extends ArrayAdapter<Section> {

    private LayoutInflater inflater;

    public SectionAdapter(Context context, int resource, ArrayList<Section> items) {
        super(context, resource, items);
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView name;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_section, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.img = convertView.findViewById(R.id.mark);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(getItem(position).getName());
        holder.img.setVisibility(getItem(position).isCheck() ? View.VISIBLE : View.GONE);
        return convertView;
    }

}
