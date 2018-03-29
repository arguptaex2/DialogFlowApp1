package gupta.kumar.arup.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;
import gupta.kumar.arup.calculator.calc.InfixToPostFix;

public class MainActivity extends AppCompatActivity implements AIListener{

        AIService aiService;
        TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AIConfiguration config = new AIConfiguration("bedf268bf0f74125885094356ea804aa",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        output = findViewById(R.id.result);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {
        Result result = response.getResult();

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }

        // Show results in TextView.
        /*resultTextView.setText("Query:" + result.getResolvedQuery() +
                "\nAction: " + result.getAction() +
                "\nParameters: " + parameterString);*/
        Log.d("Response",result.toString());
        String query = result.getResolvedQuery();
        output.setText(calculate(query).toString());
    }

    private String calculate(String resolvedQuery) {
        return  InfixToPostFix.calculateResult(resolvedQuery);
    }

    @Override
    public void onError(ai.api.model.AIError error) {
        Log.d("Response",error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    public void fetchSpeechData(View view) {
        aiService.startListening();
        Toast.makeText(this, "started Listening", Toast.LENGTH_SHORT).show();


    }
}
