package com.example.faith.upandeapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by justus on 6/9/15.
 */
public class AlertDialogManager extends Activity{
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon(null);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Intent intent = new Intent(getBaseContext(),LogInPage.class);
                //Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
