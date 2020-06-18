package br.com.sysdesc.arquivos.model;

import java.io.Serializable;

import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;
import lombok.Data;

@Data
public class InnerRecordModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean optional;

	private GroupRecordsModel innerRecords;

	private IdFieldModel idField;

	public boolean isIdField(String dataRecord) throws FormatNotSuportedException {

		return idField != null && idField.getValue().equals(idField.readValue(dataRecord));
	}

}
