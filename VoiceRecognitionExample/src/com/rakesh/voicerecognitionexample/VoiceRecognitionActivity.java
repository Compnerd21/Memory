package com.rakesh.voicerecognitionexample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceRecognitionActivity extends Activity {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	private EditText metTextHint;
	private TextView Choice;
	private Spinner msTextMatches;
	private Button mbtSpeak;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_recognition);
		metTextHint = (EditText) findViewById(R.id.etTextHint);
		Choice = (TextView)findViewById(R.id.choice);
		//msTextMatches = (Spinner) findViewById(R.id.sNoOfMatches);
		mbtSpeak = (Button) findViewById(R.id.btSpeak);
	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			mbtSpeak.setEnabled(false);
			Toast.makeText(this, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void speak(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());

		// Display an hint to the user about what he should say.
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
				.toString());

		// Given an hint to the recognizer about what the user is going to say
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

		// If number of Matches is not selected then return show toast message
		//if (msTextMatches.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
		//	Toast.makeText(this, "Please select No. of Matches from spinner",
		//			Toast.LENGTH_SHORT).show();
		//	return;
		//}

		//int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem()
			//	.toString());
		// Specify how many results you want to receive. The results will be
		// sorted where the first result is the one with higher confidence.

		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
		{

			//If Voice recognition is successful then it returns RESULT_OK
			if(resultCode == RESULT_OK) 
			{

				ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				String Test;
				String Rec = textMatchList.get(0);
				Rec.toLowerCase();
				if(Rec.contains("quit")|| Rec.contains("exit"))
				{
					System.exit(0);
				}
				else if(Rec.contains("test"))
				{
					Choice.setText("Test");
				}
				else
				{
					Choice.setText("ERROR");
				}

			}
			//Result code for various error.	
			else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
				showToastMessage("Audio Error");
			}else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
				showToastMessage("Client Error");
			}else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
				showToastMessage("Network Error");
			}else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
				showToastMessage("No Match");
			}else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
				showToastMessage("Server Error");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	void showToastMessage(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
