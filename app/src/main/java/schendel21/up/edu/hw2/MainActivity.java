package schendel21.up.edu.hw2;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

/**
 * @author Alex Schendel
 */
public class MainActivity extends AppCompatActivity {
    Point screen;
    FaceView view;
    RadioGroup radioGroup;
    RadioButton hairButton;
    RadioButton skinButton;
    RadioButton eyesButton;
    Button randomButton;
    Spinner hairStyle;
    ArrayAdapter<String> hairStyleAdapter;
    SeekBar redSeek, greenSeek, blueSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.surfaceView);
        radioGroup = findViewById(R.id.radioGroup);
        hairButton = findViewById(R.id.radioButton_hair);
        skinButton = findViewById(R.id.radioButton_skin);
        eyesButton = findViewById(R.id.radioButton_eyes);
        redSeek = findViewById(R.id.seekBar_red);
        redSeek.setMax(255);
        redSeek.setOnSeekBarChangeListener(new NewBarChangeListener(0));
        greenSeek = findViewById(R.id.seekBar_green);
        greenSeek.setMax(255);
        greenSeek.setOnSeekBarChangeListener(new NewBarChangeListener(1));
        blueSeek = findViewById(R.id.seekBar_blue);
        blueSeek.setMax(255);
        blueSeek.setOnSeekBarChangeListener(new NewBarChangeListener(2));
        radioGroup.setOnCheckedChangeListener(new ColorCheckedChangeListener());
        randomButton = findViewById(R.id.button_randomize);
        //TO-DO add hairStyle spinner
        hairStyleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        hairStyleAdapter.addAll("Bowl", "Bob", "Bun");
        hairStyle = findViewById(R.id.spinner_style);
        hairStyle.setAdapter(hairStyleAdapter);
        hairStyle.setOnItemSelectedListener(new HairStyleListener());
        hairStyle.setSelection(0);
    }

    /*
     * External Citation
     Date:     30 September 2018
     Problem:  Did not know proper listener to use.
     Resource: https://developer.android.com/guide/topics/ui/controls/spinner

     Solution: I looked at the "Key classes"
     */
    /**
     * Class to implement the OnItemSelectedListener for the Spinner. Allows for the new hair style
     * to be drawn when selected in the Spinner.
     */
    public class HairStyleListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
            if(view.face != null)
                view.setHairStyle(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    /**
     * Method run during the randomButton's onClick method
     * @param v The view of the button
     */
    public void randomize(View v){
        view.randomize();
        radioGroup.check(hairButton.getId());
        radioGroup.check(skinButton.getId());
        hairStyle.setSelection(view.face.getHairStyle());
    }

    /**
     * Class to implement OnCheckedChangeListener for the RadioGroup. Allows for the SeekBars to
     * update when a new Button in the RadioGroup is selected.
     */
    public class ColorCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(view.face == null)
                return;
            if(radioGroup.getCheckedRadioButtonId()==hairButton.getId()) {
                redSeek.setProgress((view.face.getHairColor() >> 16) & 0xff);
                greenSeek.setProgress((view.face.getHairColor() >> 8) & 0xff);
                blueSeek.setProgress((view.face.getHairColor()) & 0xff);
            }
            else if(radioGroup.getCheckedRadioButtonId()==skinButton.getId()) {
                redSeek.setProgress((view.face.getSkinColor() >> 16) & 0xff);
                greenSeek.setProgress((view.face.getSkinColor() >> 8) & 0xff);
                blueSeek.setProgress((view.face.getSkinColor()) & 0xff);
            }
            else{
                redSeek.setProgress((view.face.getEyeColor() >> 16) & 0xff);
                greenSeek.setProgress((view.face.getEyeColor() >> 8) & 0xff);
                blueSeek.setProgress((view.face.getEyeColor()) & 0xff);
            }
        }
    }

    /**
     * Class to implement OnSeekBarChangeListener for the three RGB SeekBars. Allows for the colors
     * to update properly upon the SeekBar being changed.
     */
    public class NewBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        int color;

        public NewBarChangeListener(int color){
            this.color = color;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if(radioGroup.getCheckedRadioButtonId()==hairButton.getId()) {
                view.setHairColor(i, color);
            }
            else if(radioGroup.getCheckedRadioButtonId()==skinButton.getId()) {
                view.setSkinColor(i, color);
            }
            else {
                view.setEyeColor(i, color);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
