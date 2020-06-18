package br.com.sysdesc.arquivos.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TexFileModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private GroupRecordsModel groupRecords = new GroupRecordsModel();

}
