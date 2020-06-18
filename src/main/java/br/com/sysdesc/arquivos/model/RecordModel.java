package br.com.sysdesc.arquivos.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class RecordModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private boolean repetable;

	private int order;

	private InnerRecordModel innerRecordModel;

}
