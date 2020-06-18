package br.com.sysdesc.arquivos.model;

import java.util.ArrayList;
import java.util.List;

import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;
import br.com.sysdesc.arquivos.stream.ReadableStream;
import br.com.sysdesc.arquivos.stream.WritableStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdFieldModel extends FieldModel implements ReadableStream, WritableStream {

	private static final long serialVersionUID = 1L;

	private List<FieldModel> fields = new ArrayList<>();

	@Override
	public void read(String dataRecord) throws FormatNotSuportedException {

		setValue(super.readValue(dataRecord));

		for (FieldModel field : fields) {

			field.setValue(field.readValue(dataRecord));
		}
	}

	@Override
	public String write() throws FormatNotSuportedException {

		StringBuilder stringBuilder = new StringBuilder(super.writeValue());

		for (FieldModel field : fields) {

			stringBuilder.append(field.writeValue());
		}

		return stringBuilder.toString();
	}

}
