package vsmu.testing.android;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import vsmu.testing.android.ui.AccreditationActivity;
import vsmu.testing.android.ui.AccreditationSPOActivity;
import vsmu.testing.android.ui.MainActivity;

public class ActionBarDialog extends DialogFragment implements View.OnClickListener {

    Dialog dialog;
    TextView textView;
    Intent intent;
    Boolean dismiss = false;
    private DismissListener listener;

    public void setMyCustomListener(DismissListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title");
        View v = inflater.inflate(R.layout.third_dialog, container, false);
        v.findViewById(R.id.textView3).setOnClickListener(this);
        v.findViewById(R.id.textView5).setOnClickListener(this);
        v.findViewById(R.id.textView6).setOnClickListener(this);
        v.findViewById(R.id.textView7).setOnClickListener(this);
        v.findViewById(R.id.feedback).setOnClickListener(this);
        v.findViewById(R.id.textView9).setOnClickListener(this);
        Toolbar toolbar = v.findViewById(R.id.toolbar_second);
        toolbar.inflateMenu(R.menu.menu_second);
        toolbar.setTitle(title);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("click","click");
                dialog.dismiss();
                dismiss = true;
                listener.onSuccess(dismiss);
                return true;
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView3:
                    intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.textView5:
                    intent = new Intent(getContext(), AccreditationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.textView6:
                    intent = new Intent(getContext(), AccreditationSPOActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.textView7:

                    break;
                case R.id.feedback:
                    Utils.sendEmail(getActivity());
                    dismiss = true;
                    listener.onSuccess(dismiss);
                    break;
                case R.id.textView9:
                    dismiss = true;
                    listener.onSuccess(dismiss);
                    break;
                default:
                    break;
            }
        }

    }