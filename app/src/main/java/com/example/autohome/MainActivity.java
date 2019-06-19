package com.example.autohome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String shutters[] = {"192.168.1.103", "192.168.1.105", "192.168.1.102", "192.168.1.106"};
    static ArrayList<RollerBlinds> rb = new ArrayList<>();
    int settings[][] = {{0, 10, 50, 0}, {99, 100, 0, 0}, {99, 100, 100, 99}};
    public String answer = "";

    ImageButton iBtnAllOff, iBtnBreakfast, iBtnSleep;
    TextView infoBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defDevices();

        infoBox = findViewById(R.id.textView);
        iBtnAllOff = findViewById(R.id.imageButton1);
        iBtnBreakfast = findViewById(R.id.imageButton2);
        iBtnSleep = findViewById(R.id.imageButton3);

        String allIPs = "";
        for (RollerBlinds i : rb) {
            allIPs += i.ip + " | ";
        }
        infoBox.setText(allIPs);
    }

    public void btn1_Click(View v) {
        // Toast.makeText(this, "Sending command", Toast.LENGTH_SHORT).show();
        // Log.i("interaction", "Sending command");

        String url;
        for (int i = 0; i < 4; i++) {
            url = "http://" + shutters[i] + "/s/p/" + settings[0][i];
            String answ = commREST(url);
        }
        //url = "http://192.168.1.105/s/p/0";
        //String answ = commREST(url);
        infoBox.setText(answer);
    }

    public void btn2_Click(View v) {
        String url;
        for (int i = 0; i < 4; i++) {
            url = "http://" + shutters[i] + "/s/p/" + settings[1][i];
            String answ = commREST(url);
        }
        infoBox.setText(answer);
    }

    public void btn3_Click(View v) {
        String url;
        for (int i = 0; i < 4; i++) {
            url = "http://" + shutters[i] + "/s/p/" + settings[2][i];
            String answ = commREST(url);
        }
        infoBox.setText(answer);
    }

    public String commREST(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //e.printStackTrace();
                Log.e("error", "Request failed" + "| " + e.getMessage());
                answer = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //final String myResponse = response.body().string();
                    answer = response.body().string();
                    // Toast.makeText(MainActivity.this,"Command sent", Toast.LENGTH_LONG).show();
                    // Log.e("interaction", "Command sent");
                }

            }
        });

        return answer;

    }

    public void defDevices() {
        rb.add(new RollerBlinds("192.168.1.102"));
        rb.add(new RollerBlinds("192.168.1.103"));
        rb.add(new RollerBlinds("192.168.1.105"));
        rb.add(new RollerBlinds("192.168.1.106"));
    }

    class RollerBlinds {

        private String ip;
        private int currPos;

        public RollerBlinds(String ip) {
            this.ip = ip;
            this.currPos = 0;
            System.out.println(this.ip + " | " + this.currPos);
        }

        public RollerBlinds(String ip, int currPos) {
            this.ip = ip;
            this.currPos = currPos;
            System.out.println(this.ip + " | " + this.currPos);
        }


        public String getIP() {
            return this.ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getCurrPos() {
            return this.currPos;
        }

        public void setCurrPos(int currPos) {
            this.currPos = currPos;
        }
    }

}
