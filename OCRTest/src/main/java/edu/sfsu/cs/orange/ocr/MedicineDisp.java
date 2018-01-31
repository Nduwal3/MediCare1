package edu.sfsu.cs.orange.ocr;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.R.attr.name;
import static android.view.View.INVISIBLE;

public class MedicineDisp extends AppCompatActivity {
    TextView displayText;
    String medicine;
   // Map<String, String> myMap;
    //String nameOfPerson;
    TextView responseDisplay;
     ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_disp);
        responseDisplay = (TextView)findViewById(R.id.responseArea);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        medicine = getIntent().getExtras().getString("MEDICINE");
        displayText = (TextView)findViewById(R.id.displayText);
        displayText.setText( "  " +medicine);
       // myMap = new LinkedHashMap<String , String>();
       // nameOfPerson = name.getText().toString();
      //  nameOfPerson = medicine ;
        new runAPI().execute(); //API vaneko background ma partial refresh hune ho, so when the button is pressed, this class is executed
    }
    class runAPI extends AsyncTask<Void,Void, String>
    {


       // private Exception exception;
        protected void onPreExecute() //what to do after request is sent and before the data arrives
        {
           // responseDisplay.setText("Not executed anything");
            spinner.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) //what to do when data has arrived
        {

            try
            {

                URL url = new URL ( "https://kefeed123.000webhostapp.com/getMeds.php?name="+medicine);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try
                {
                    BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine())!= null)
                    {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally
                {
                    urlConnection.disconnect();
                }
            }
            catch (Exception e)
            {
                return null;
            }

           // spinner.setVisibility(View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected void onPostExecute(String response) //what to do after data has been collected
        {
            spinner.setVisibility(INVISIBLE);
            if ( response == null)
            {

                response = "There no medicine with such names";
                responseDisplay.setText(response);
                return;
            }
            try
            {
                //JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray list = new JSONArray(response);
                if ( list.length() == 0)
                {
                    response = "There no medicine with such names";
                    responseDisplay.setText(response);
                    return;
                }
                responseDisplay.setText("");
                for( int i = 0 ; i < list.length() ; i++)
                {
                    String temp = responseDisplay.getText().toString();
                    responseDisplay.setText("");
                    JSONObject meds = list.getJSONObject(i);
                    String MedName = meds.getString("name");
                    String Usage = meds.getString("usage");
                    String Dosage = meds.getString("dosage");
                    String SideEffect = meds.getString("sideEffects");
                    responseDisplay.setText(temp + "Medicine Name : "+ MedName+"\n\nUsage : "+Usage+"\n\nDosage : "+ Dosage+"\n\nSide Effects : "+ SideEffect+"\n\n\n");
                }


            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            spinner.setVisibility(INVISIBLE);

        }
       // spinner.setVisibility(View.INVISIBLE)
    }
}
