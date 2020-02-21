package wasanawa.hath.fypui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_main);
    }

    public void play_game(View view){
        Intent intent = new Intent(this, splash.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void view_scores(View view){
        Intent intent = new Intent(this, scores_page.class);
        startActivity(intent);
    }

    public void view_instructions(View view) {
        Intent intent = new Intent(this, instructions_page.class);
        startActivity(intent);
    }

    public void exit_game(View view) {
        Log.println( Log.ERROR, "TAG", "Inside openDialog exit game" );

        final AlertDialog.Builder exitGame = new AlertDialog.Builder( this, R.style.AlertDialogStyle);
        exitGame.setMessage("Are you sure you want to Exit?")
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }



                });
        AlertDialog dialog = exitGame.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();

        Log.println( Log.ERROR, "TAG", "Inside openDialog exit game in the end" );
    }

}

