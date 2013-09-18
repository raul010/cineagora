package br.com.cinemodel.view;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.model.apresentacao.FilmeCartaz;

public class FilmeCartazView extends FilmeCartaz implements Parcelable{

	private static final long serialVersionUID = 4913251470725845856L;
	FilmeCartazView endereco;

	private FilmeCartazView(Parcel in) {
		endereco = (FilmeCartazView) in.readSerializable();
	}
	private FilmeCartazView (){
		
	}

	@Override
	public String toString() {
		return this.getNomeFilme().getNomeDoFilme() + "\n" + this.getdia() + "\n" + this.getHorarios();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this);
	}

	public static final Parcelable.Creator<FilmeCartazView> CREATOR = new Parcelable.Creator<FilmeCartazView>() {
		public FilmeCartazView createFromParcel(Parcel in) {
			return new FilmeCartazView(in);
		}
		
		@Override
		public FilmeCartazView[] newArray(int size) {
			return new FilmeCartazView[size];
		}
	};

}
