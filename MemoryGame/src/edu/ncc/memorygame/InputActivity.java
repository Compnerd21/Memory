package edu.ncc.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends Activity
{
	public String name;
	protected void onCreate(Bundle savedInstanceState) 
	{
 		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
	}
	public void Input(View v)
	{
		EditText N = (EditText) findViewById(R.id.uName);
		name = N.getText().toString();
		Intent intent = new Intent(this, MainActivity.class);
		Bundle b = new Bundle();
		b.putString("User", name);
		intent.putExtras(b);
		startActivity(intent);
	}

}
