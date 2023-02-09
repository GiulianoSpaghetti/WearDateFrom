package org.altervista.numerone.weardatefrom;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.altervista.numerone.weardatefrom.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

        private EditText date, name;
        private TextView result, anniversary;
        private ActivityMainBinding binding;
        private DatePickerDialog datePickerDialog;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            date=(EditText) findViewById(R.id.editText2);
            name=(EditText) findViewById(R.id.editText1);
            result=(TextView) findViewById(R.id.textView1);
            anniversary=(TextView) findViewById(R.id.textView2);
            SharedPreferences mPrefs = getSharedPreferences(getLocalClassName(), MODE_PRIVATE);
            name.setText(mPrefs.getString("nome", ""));
            date.setText(mPrefs.getString("data", ""));
            Button b = (Button) findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Date d = new Date();
                    Date d1;
                    anniversary.setText("");
                    result.setText("");
                    try
                    {
                        d1 = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(date.getText()));
                    }
                    catch (ParseException ex)
                    {
                        result.setText(R.string.invalid_rvalue);
                        return;
                    }
                    long diff = d.getTime() - d1.getTime();
                    diff = diff / (1000 * 3600 * 24);
                    result.setText(getString(R.string.you_meet) + " " + name.getText() + " " + getString(R.string.about)+ " " + diff + " " + getString(R.string.days_ago)+ ".");
                    if (d.getDate() == d1.getDate())
                        if (d.getMonth()== d1.getMonth())
                            anniversary.setText(R.string.is_your_anniversary);
                        else
                            anniversary.setText(R.string.is_your_mesiversary);

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("nome", String.valueOf(name.getText()));
                    editor.putString("data", String.valueOf(date.getText()));
                    editor.apply();
                }
            });
        }

}