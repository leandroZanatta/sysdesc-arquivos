package br.com.sysdesc.arquivos.util;

public class RecordIterator {

	private int row = 0;

	public Integer getRow() {

		return this.row;
	}

	public void nextRow() {
		row = row + 1;
	}
}
