package br.com.cinemodel.view;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.cinemodel.model.element.CinemaElement;

public class CinemaElementView extends CinemaElement implements Parcelable, Serializable {

	private static final long serialVersionUID = -5539489760398028738L;
	CinemaElementView cinema;
	
	private CinemaElementView(Parcel in) {
		cinema = (CinemaElementView) in.readSerializable();
	}
	private CinemaElementView (){
		
	}

	@Override
	public String toString() {
		return this.getNome();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this);
	}

	public static final Parcelable.Creator<CinemaElementView> CREATOR = new Parcelable.Creator<CinemaElementView>() {
		public CinemaElementView createFromParcel(Parcel in) {
			return new CinemaElementView(in);
		}
		
		@Override
		public CinemaElementView[] newArray(int size) {
			return new CinemaElementView[size];
		}
	};
}
