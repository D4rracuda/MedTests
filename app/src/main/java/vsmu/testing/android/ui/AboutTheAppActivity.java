package vsmu.testing.android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import vsmu.testing.android.DialogAboutTheApp;
import vsmu.testing.android.R;

import static vsmu.testing.android.R.color.colorAccent;

public class AboutTheAppActivity extends AppCompatActivity {

    Toolbar mTopToolbar;
    ImageButton vkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_app);

        mTopToolbar = findViewById(R.id.toolbarAPP);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setTitleTextColor(getResources().getColor(colorAccent));
        setTitle(R.string.About_the_app);

        vkBtn = findViewById(R.id.vk_button);
        View.OnClickListener VK = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/volgsmuapp"));
                startActivity(browserIntent);
            }
        };
        vkBtn.setOnClickListener(VK);
    }

    public void showDialogThird(){
        Bundle args = new Bundle();
        args.putString("title", getString(R.string.menu));
        DialogAboutTheApp dialogAboutTheApp = new DialogAboutTheApp();
        dialogAboutTheApp.setArguments(args);
        dialogAboutTheApp.show(getSupportFragmentManager(),
                "dialog_disciplines");
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
