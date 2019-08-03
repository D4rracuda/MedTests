package vsmu.testing.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by Dan on 08.04.2016.
 */
public class Utils {

    public static int dp2sp(Context context, float dpVal) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics()));
    }

    public static void showArlet(final Context context){
        if(App.get().getNumber() == App.get().getMax() && App.get().isShowDialog() || App.get().getNumber() == App.get().getMax() && App.get().isMonthLaterDialog()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.likeApp));
            builder.setItems(context.getResources().getStringArray(R.array.dialog), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            try {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=vsmu.testing.android")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=vsmu.testing.android")));
                            }
                            break;
                        case 1:
                            App.get().setMonthLater();
                            break;
                    }
                    App.get().setShowDialog(false);
                }
            });
            builder.show();
        }
    }

    public static void sendEmail(Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + context.getString(R.string.email)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.title));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\n" + context.getString(R.string.sendfrom) + " Android " + Build.VERSION.RELEASE);
        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.feedback)));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, context.getString(R.string.er_email), Toast.LENGTH_SHORT).show();
        }
    }

    public static void share(Context context, String shareBody){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share)));
    }

}
