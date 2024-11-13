package logica;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSortOfertas {
	public static void quickSort(ArrayList<Oferta> ofertas, int bajo, int alto) {
		if (bajo < alto) {
			int pivotIndex = particion(ofertas, bajo, alto);
			quickSort(ofertas, bajo, pivotIndex - 1);
			quickSort(ofertas, pivotIndex + 1, alto);
		}
	}

	private static int particion(ArrayList<Oferta> ofertas, int bajo, int alto) {

		Oferta pivot = ofertas.get(alto);
		int i = bajo - 1;

		for (int j = bajo; j < alto; j++) {

			if (ofertas.get(j).getHoraInicio() <= pivot.getHoraInicio()) {
				i++;
				Collections.swap(ofertas, i, j);
			}
		}
		Collections.swap(ofertas, i + 1, alto);
		return i + 1;
	}

}
