package br.com.sysdesc.arquivos.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import lombok.Data;

public class ListUtil {

	public static boolean isNullOrEmpty(List<?> list) {

		return list == null || list.isEmpty();
	}

	@SafeVarargs
	public static <T> void iterate(List<T> list, IterationList<T>... iterationLists) {

		List<IterationList<T>> iterations = Arrays.asList(iterationLists);

		int listSize = list.size();

		IntStream.range(0, list.size()).forEach(index -> {

			List<IterationList<T>> iterationsItem = getIterationsFromItem(index, listSize, iterations);

			iterationsItem.forEach(iteration -> iteration.getConsumer().accept(list.get(index)));
		});
	}

	private static <T> List<IterationList<T>> getIterationsFromItem(int index, int listSize,
			List<IterationList<T>> iterations) {

		List<IterationList<T>> returns = new ArrayList<>();

		if (index == 0) {

			findIteration(iterations, returns, TipoIteracao.FIRST);
		}

		findIndexPosition(index, iterations, returns);

		if (index == listSize - 1) {

			findIteration(iterations, returns, TipoIteracao.LAST);
		}

		findIteration(iterations, returns, TipoIteracao.ALL);

		if (returns.isEmpty()) {

			findIteration(iterations, returns, TipoIteracao.OTHERS);
		}

		return returns;
	}

	private static <T> void findIndexPosition(int index, List<IterationList<T>> iterations,
			List<IterationList<T>> returns) {

		Optional<IterationList<T>> optionalPosition = iterations.stream()
				.filter(iteration -> iteration.getTipoIteracao().equals(TipoIteracao.POSITION)
						&& iteration.getPosition() == index)
				.findFirst();

		if (optionalPosition.isPresent()) {
			returns.add(optionalPosition.get());
		}
	}

	private static <T> void findIteration(List<IterationList<T>> iterations, List<IterationList<T>> returns,
			TipoIteracao tipoIteracao) {

		Optional<IterationList<T>> optional = iterations.stream()
				.filter(iteration -> iteration.getTipoIteracao().equals(tipoIteracao)).findFirst();

		if (optional.isPresent()) {

			returns.add(optional.get());
		}
	}

	public static void main(String[] args) {

		List<Integer> teste = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		iterate(teste, new IterationList<Integer>(TipoIteracao.FIRST, i -> System.out.println("Primeiro Item:" + i)),
				new IterationList<Integer>(TipoIteracao.LAST, i -> System.out.println("Ultimo ítem Item:" + i)),
				new IterationList<Integer>(TipoIteracao.OTHERS, i -> System.out.println("Item padrão:" + i)));
	}
}

@Data
class IterationList<T> {

	private TipoIteracao tipoIteracao;

	private Consumer<T> consumer;

	private int position = 0;

	public IterationList(TipoIteracao tipoIteracao, Consumer<T> consumer) {
		this.tipoIteracao = tipoIteracao;
		this.consumer = consumer;
	}

	public IterationList(Consumer<T> consumer, int position) {
		this.tipoIteracao = TipoIteracao.POSITION;
		this.consumer = consumer;
		this.position = position;
	}
}

enum TipoIteracao {

	FIRST,

	POSITION,

	ALL,

	OTHERS,

	LAST;
}
