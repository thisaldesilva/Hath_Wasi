package wasanawa.hath.fypui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {


    private Handler mHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);


        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), game_page.class));
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 2200 );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null && mRunnable != null)
            mHandler.removeCallbacks(mRunnable);
    }
}
