package vsmu.testing.android.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import vsmu.testing.android.DialogAccreditationSPO;
import vsmu.testing.android.DialogDismissListener;
import vsmu.testing.android.R;
import vsmu.testing.android.database.DBHelper;

import static vsmu.testing.android.R.color.colorAccent;

public class AccreditationSPOActivity extends AppCompatActivity {

    BubblePicker picker;
    PickerItem item;
    Toolbar mTopToolbar;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accreditation_spo);

        mTopToolbar = findViewById(R.id.my_toolbarASPO);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setTitleTextColor(getResources().getColor(colorAccent));
        setTitle(R.string.Accreditation_SPO);

        picker = findViewById(R.id.pickerASPO);
        picker.setBubbleSize(1);
        picker.setCenterImmediately(true);

        final String[] titles = getResources().getStringArray(R.array.accreditationSPO);
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
                item.setTextColor(ContextCompat.getColor(AccreditationSPOActivity.this, android.R.color.white));
                item.setCustomData(position);
                return item;
            }
        });
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                picker.onPause();
                position = (Integer)item.getCustomData();
                DBHelper.getDataAccreditationSPO().openDB(AccreditationSPOActivity.this, position);
                startActivity(new Intent(AccreditationSPOActivity.this, TestingActivity.class)
                        .putExtra(TestingActivity.POSITION, position)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

    public void showDialogThird(){
        Bundle args = new Bundle();
        args.putString("title", getString(R.string.menu));
        DialogAccreditationSPO dialogAccreditationSPO = new DialogAccreditationSPO();
        dialogAccreditationSPO.setArguments(args);
        dialogAccreditationSPO.show(getSupportFragmentManager(),
                "dialog_disciplines");
        dialogAccreditationSPO.setMyCustomListener(new DialogDismissListener() {

            @Override
            public void onSuccess(boolean dismiss) {
                picker.onResume();
            }
        });
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
            picker.onPause();
            showDialogThird();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}