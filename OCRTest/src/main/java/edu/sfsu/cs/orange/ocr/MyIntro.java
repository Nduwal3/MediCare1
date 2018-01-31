package edu.sfsu.cs.orange.ocr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MyIntro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Health",
                "It's important.",
                R.drawable.ic_health,
                Color.parseColor("#002171")));//#51e2b7

        addSlide(AppIntroFragment.newInstance("Medical History",
                "Keep your datas.",
                R.drawable.ic_cloud_white_36dp,
                Color.parseColor("#560027")));//#8c50e3

        addSlide(AppIntroFragment.newInstance("Remainder",
                "Remaind your med time.",
                R.drawable.ic_note,
                Color.parseColor("#bf360c")));//#4fd7ff

        addSlide(AppIntroFragment.newInstance("OCR",
                "Get info from your camera",
                R.drawable.ic_camera,
                Color.parseColor("#004c40")));//#00bcd4



        showStatusBar(false);
        setBarColor(Color.parseColor("#7f0000"));//#333639
        //setSeparatorColor(Color.parseColor("#2196F3"));
    }

    @Override
    public void onDonePressed()
    {
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }

    @Override
    public void onSkipPressed() {
        finish();
    }
}
