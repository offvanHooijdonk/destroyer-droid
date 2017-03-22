package by.vanhooijdonk.destroyerdroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import by.vanhooijdonk.destroyerdroid.R;
import by.vanhooijdonk.destroyerdroid.rest.ClientProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageButton btnMotorsOn;
    private ImageButton btnMotorsOff;
    private ImageButton btnMoveForward;
    private ImageButton btnMoveBackward;
    private ImageButton btnMoveLeft;
    private ImageButton btnMoveRight;
    private TextView txtError;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.pref, false);
        ctx = this;

        txtError = (TextView) findViewById(R.id.textError);
        btnMotorsOn = (ImageButton) findViewById(R.id.btnMotorsOn);
        btnMotorsOff = (ImageButton) findViewById(R.id.btnMotorsOff);
        btnMoveForward = (ImageButton) findViewById(R.id.btnForward);
        btnMoveBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnMoveLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnMoveRight = (ImageButton) findViewById(R.id.btnRight);

        showErrorSection(false);

        btnMotorsOn.setOnClickListener(v -> {
            // this approach does not let to wait until request executes an returns
            startCommand(ClientProvider.getApiClient(ctx).motorsAllOn());
            showOnOffButtons(false);
        });
        btnMotorsOff.setOnClickListener(v -> {
            startCommand(ClientProvider.getApiClient(ctx).motorsAllOff());
            showOnOffButtons(true);
        });
        btnMoveForward.setOnTouchListener(this);
        btnMoveBackward.setOnTouchListener(this);
        btnMoveLeft.setOnTouchListener(this);
        btnMoveRight.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, PreferenceActivity.class));
        }

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handleDownEvent(v);
            return true;
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            ClientProvider.getApiClient(ctx).stopMove();
            return true;
        }
        return false;
    }

    private void handleDownEvent(View v) {
        switch (v.getId()) {
            case R.id.btnForward:   ClientProvider.getApiClient(ctx).moveForward();  break;
            case R.id.btnBackward:  ClientProvider.getApiClient(ctx).moveBackward(); break;
            case R.id.btnLeft:      ClientProvider.getApiClient(ctx).moveLeft();     break;
            case R.id.btnRight:     ClientProvider.getApiClient(ctx).moveRight();    break;
        }
    }

    private void startCommand(Observable<Void> obsCall) {
        showErrorSection(false);
        obsCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {}, this::handleError, () -> {});
    }

    private void handleError(Throwable th) {
        showErrorSection(true);

        txtError.setText(th.getMessage());
    }

    private void showOnOffButtons(boolean showOn) {
        btnMotorsOn.setVisibility(showOn ? View.VISIBLE: View.GONE);
        btnMotorsOff.setVisibility(showOn ? View.GONE: View.VISIBLE);
    }

    private void showErrorSection(boolean isShow) {
        txtError.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
