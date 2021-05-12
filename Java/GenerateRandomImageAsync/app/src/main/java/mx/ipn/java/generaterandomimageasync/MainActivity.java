package mx.ipn.java.generaterandomimageasync;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonDrawBitmap;
    ImageView imageViewBitmap;
    List<Integer> Images = new ArrayList<Integer>() {{
        add(R.drawable.img1);
        add(R.drawable.img2);
        add(R.drawable.img3);
        add(R.drawable.img4);
        add(R.drawable.img5);
        add(R.drawable.img6);
        add(R.drawable.img7);
        add(R.drawable.img8);
        add(R.drawable.img9);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonDrawBitmap = findViewById(R.id.buttonDrawBitmap);
        this.imageViewBitmap = this.findViewById(R.id.imageViewBitmap);
        this.buttonDrawBitmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int index = (new Random()).nextInt(9);

        if (cancelPotentialWork(Images.get(index), imageViewBitmap)) {
            Log.i("onOptionsItemSelected", "Creating async task object");
            final BitmapWorkerTask task = new BitmapWorkerTask(imageViewBitmap,
                    getResources(), imageViewBitmap.getWidth(), imageViewBitmap.getHeight());
            final AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), null, task);
            imageViewBitmap.setImageDrawable(asyncDrawable);
            task.execute(Images.get(index));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemMenuLoadBitmap:
                this.onClick(null);
                break;

            case R.id.itemMenuClearBitmap:
                imageViewBitmap.setImageResource(android.R.color.transparent);
                break;

            case R.id.itemMenuExitApp:
                this.finish();
                System.exit(RESULT_OK);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            Log.i("cancelPotentialWork", "Looking for previous processes...");
            final int bitmapData = bitmapWorkerTask.getBitmapData();
            if (bitmapData != data) {
                // Cancel previous task
                Log.i("cancelPotentialWork", "Canceling async task...");
                bitmapWorkerTask.cancel(true);
                return true;
            } else {
                // The same work is already in progress
                return false;
            }
        }
        Log.i("cancelPotentialWork", "There're no previous threads or they already finished");
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = this.imageViewBitmap.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}