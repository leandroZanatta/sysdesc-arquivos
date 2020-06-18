package br.com.sysdesc.arquivos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GroupRecordsModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RecordModel> records = new ArrayList<>();
}
