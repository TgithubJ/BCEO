package chuntaejin.example.com.mybceo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        button = (Button) this.findViewById(R.id.button);

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );
    }
    public void buttonClicked(View view) {
        // Check values from editTexts
        startActivity(new Intent(view.getContext(), MypageActivity.class));
    }
}
