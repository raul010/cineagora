package br.com.cineagora;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DetalhesFilmeFragment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhes_filme);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhes_filme, menu);
		return true;
	}

}
