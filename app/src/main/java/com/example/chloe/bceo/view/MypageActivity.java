package chuntaejin.example.com.mybceo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MypageActivity extends AppCompatActivity {
    private ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        button = (ImageButton) this.findViewById(R.id.imageButton);

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
        startActivity(new Intent(this, SellActivity.class));
    }
}
