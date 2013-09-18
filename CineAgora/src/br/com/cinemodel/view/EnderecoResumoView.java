package br.com.cinemodel.view;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.model.apresentacao.EnderecoResumo;

public class EnderecoResumoView extends EnderecoResumo implements Parcelable {
	
	private static final long serialVersionUID = -8766643804781902351L;
	EnderecoResumoView endereco;

	private EnderecoResumoView(Parcel in) {
		endereco = (EnderecoResumoView) in.readSerializable();
	}
	private EnderecoResumoView (){
		
	}

	@Override
	public String toString() {
		return this.getDadosRecebidos();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this);
	}

	public static final Parcelable.Creator<EnderecoResumoView> CREATOR = new Parcelable.Creator<EnderecoResumoView>() {
		public EnderecoResumoView createFromParcel(Parcel in) {
			return new EnderecoResumoView(in);
		}
		
		@Override
		public EnderecoResumoView[] newArray(int size) {
			return new EnderecoResumoView[size];
		}
	};

}
