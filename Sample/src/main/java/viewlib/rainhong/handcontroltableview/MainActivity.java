package viewlib.rainhong.handcontroltableview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import viewlib.rainhong.handctviewlib.HandControlTableView;

public class MainActivity extends AppCompatActivity {
    private HandControlTableView HCTViewExample;
    private SeekBar SeekBarLevelQuantity;
    private Switch SwitchTouchEventEnabled;
    private Switch SwitchReturnOriginEnabled;
    private TextView TextViewLevelQuantity;
    private TextView TextViewOnLevelDegreeChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HCTViewExample = (HandControlTableView) findViewById(R.id.HCTViewExample);
        SwitchTouchEventEnabled = (Switch) findViewById(R.id.switchTouchEventEnabled);
        SwitchReturnOriginEnabled = (Switch) findViewById(R.id.switchReturnOriginEnabled);
        TextViewLevelQuantity = (TextView) findViewById(R.id.textViewLevelQuantity);
        TextViewOnLevelDegreeChange = (TextView) findViewById(R.id.textViewOnLevelDegreeChange);
        SeekBarLevelQuantity = (SeekBar) findViewById(R.id.seekBarLevelQuantity);

        TextViewLevelQuantity.setText(getString(R.string.LevelQuantity, 1));
        TextViewOnLevelDegreeChange.setText(getString(R.string.OnLevelDegreeChanged, 0, 0));
        SwitchTouchEventEnabled.setText(getString(R.string.TouchEventEnabled, "OFF"));
        SwitchReturnOriginEnabled.setText(getString(R.string.ReturnOriginEnabled, "OFF"));
        HCTViewExample.setReturnOriginEnabled(false);

        // DrawVisible Setting
        //HCTViewExample.setCircleDrawVisible(false);
        //HCTViewExample.setIndicatorDrawVisible(false);
        //HCTViewExample.setXAxisDrawVisible(false);
        //HCTViewExample.setLineDrawVisible(false);

        SwitchTouchEventEnabled.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SwitchTouchEventEnabled.isChecked()) {
                    HCTViewExample.setTouchEventEnabled(true);
                    SwitchTouchEventEnabled.setText(getString(R.string.TouchEventEnabled, "ON"));
                } else {
                    HCTViewExample.setTouchEventEnabled(false);
                    SwitchTouchEventEnabled.setText(getString(R.string.TouchEventEnabled, "OFF"));
                }
            }
        });

        SwitchReturnOriginEnabled.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SwitchReturnOriginEnabled.isChecked()) {
                    HCTViewExample.setReturnOriginEnabled(true);
                    SwitchReturnOriginEnabled.setText(getString(R.string.ReturnOriginEnabled, "ON"));
                } else {
                    HCTViewExample.setReturnOriginEnabled(false);
                    SwitchReturnOriginEnabled.setText(getString(R.string.ReturnOriginEnabled, "OFF"));
                }
            }
        });

        HCTViewExample.setOnLevelDegreeChangedListener(new HandControlTableView.OnLevelDegreeChangedListener() {
            @Override
            public void onLevelDegreeChanged(int level, int degree) {
                TextViewOnLevelDegreeChange.setText(getString(R.string.OnLevelDegreeChanged, level, degree));
            }
        });

        SeekBarLevelQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextViewLevelQuantity.setText(getString(R.string.LevelQuantity, progress + 1));
                HCTViewExample.setZoneLevelQuantity(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
