package ryuunoakaihitomi.javacppperfcomparison;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    public static final String TAG = "JCPC";

    static {
        System.loadLibrary("native-lib");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("logcat -s JCPC");
        Log.i(TAG, "Finding palindromic primes within 100,000.(Waiting for 3s)");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final long jTime = pcTimer(true);
                        final long cTime = pcTimer(false);
                        runOnUiThread(new Runnable() {
                            @SuppressLint("DefaultLocale")
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), String.format("java:%d\ncpp:%d", jTime, cTime), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }
                }).start();
            }
        }, 3000);
    }

    public native void cpp();

    long pcTimer(boolean isJava) {
        long lStart = System.currentTimeMillis();
        if (isJava)
            Java.kernel();
        else
            cpp();
        long lTime = System.currentTimeMillis() - lStart;
        Log.i(TAG, "total time:" + lTime);
        return lTime;
    }
}