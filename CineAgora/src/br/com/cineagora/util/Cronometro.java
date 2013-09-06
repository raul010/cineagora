package br.com.cineagora.util;

import android.util.Log;

public class Cronometro {
	
	private long inicioAuditoria;
	private boolean auditoriaAtivada = false;
	private String titulo;
	
	public Cronometro(String titulo) {
		this.titulo = titulo;
	}
	
	public void inicia() {
		inicioAuditoria = System.nanoTime();
		auditoriaAtivada = true;
	}

	public void finaliza() {
		fechaCronometroEImprime(null);
	}
	public void finaliza(String mensagemAdicional) {
		fechaCronometroEImprime(mensagemAdicional);
	}

	private void fechaCronometroEImprime(String mensagemAdicional) {
		if (!auditoriaAtivada) {
			Log.d("[Cronometro]", "pediu para encerrar cronometro, sem inicia-lo");
			return;
		}
		long fim = System.nanoTime() - inicioAuditoria;
		double segundos = (double) fim / 1000000000.0;
		
		if (mensagemAdicional != null) 
			Log.d("[Cronometro]", titulo + ": " + segundos + " --> " + mensagemAdicional);
		 else 
			Log.d("[Cronometro]", titulo + ": " + segundos);
		
		inicioAuditoria = 0;
		auditoriaAtivada = false;
	}

}
