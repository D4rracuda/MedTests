package vsmu.testing.android.ui;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import vsmu.testing.android.App;
import vsmu.testing.android.R;
import vsmu.testing.android.ui.fragments.ChooseSections;
import vsmu.testing.android.ui.fragments.Questions;

public class TestingActivity extends FragmentActivity {

    final public static String POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        int position = getIntent().getIntExtra(POSITION, App.get().getDisciplinePosition());
        ((TextView) findViewById(R.id.toolbar_title)).setText(getString(R.string.chooseSections));
        findViewById(R.id.navigationIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.get().setSave(false);
                finish();
                overridePendingTransition(R.anim.in_left, R.anim.out_right);
            }
        });

        if(App.get().isSave() && position == App.get().getDisciplinePosition()) getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Questions()).commit();
        else getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ChooseSections()).commit();
        App.get().setDisciplinePosition(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }
}
