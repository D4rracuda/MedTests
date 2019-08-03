package vsmu.testing.android.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import vsmu.testing.android.R;
import vsmu.testing.android.Utils;
import vsmu.testing.android.database.DBHelper;

import static vsmu.testing.android.R.color.colorAccent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BubblePicker picker;
    PickerItem item;
    Toolbar mTopToolbar;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setTitleTextColor(getResources().getColor(colorAccent));
        setTitle("Дисциплины");

        picker = findViewById(R.id.picker);
        picker.setBubbleSize(1);
        picker.setCenterImmediately(true);

        final String[] titles = getResources().getStringArray(R.array.disсipline);
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);

        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }
            @NotNull
            @Override
            public PickerItem getItem(int position) {
                item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                item.setCustomData(position);
                return item;
            }
        });
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                position = (Integer)item.getCustomData();
                DBHelper.getData().openDB(MainActivity.this, position);
                startActivity(new Intent(MainActivity.this, TestingActivity.class).putExtra(TestingActivity.POSITION, position));
                overridePendingTransition(R.anim.in_right, R.anim.out_left);
                //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback:
                Utils.sendEmail(MainActivity.this);
                break;
            case R.id.visitSite:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site))));
                break;
        }
    }

    public void showDialogThird(){
        Bundle args = new Bundle();
        args.putString("title", "Меню");
        ActionBarDialog actionbarDialog = new ActionBarDialog();
        actionbarDialog.setArguments(args);
        actionbarDialog.show(getSupportFragmentManager(),
                "third_dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu) {
            showDialogThird();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}