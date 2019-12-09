package org.mac.dominosorter;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.hamcrest.core.IsEqual;

/**
 * Unit test for simple App.
 */
public class AppTest {

	static class DominoSorterImpl<T> extends DominoSorter<T> {
		private Map<Integer[], T> m = new HashMap<>();

		public void insert(T source, T target) {
			Integer[] sp = null;
			Integer[] tp = null;
			for (Entry<Integer[], T> e : m.entrySet()) {
				if (tp == null && e.getValue().equals(target)) {
					tp = e.getKey();
				} else if (sp == null && e.getValue().equals(source)) {
					sp = e.getKey();
				}
			}
			m.putIfAbsent(tp == null ? getNextAvailable(new Integer[] { (sp != null ? sp[0] + 1 : 1), 0 }) : tp, target);
			m.putIfAbsent(sp == null ? getNextAvailable(new Integer[] { (tp != null ? tp[0] - 1 : 0), 0 }) : sp, source);
		}

		private Integer[] getNextAvailable(Integer[] p) {
			if (!m.isEmpty()) {
				for (Entry<Integer[], T> e : m.entrySet()) {
					if ( p[1] == e.getKey()[1]) {
						return getNextAvailable(new Integer[] {p[0], p[1] + 1});
					}
					//System.out.println("Jamfor punkt: (" + p[0] + "," + p[1]+ ") med (" + e.getKey()[0] + "," + e.getKey()[1]+")");
				}
			}
			return p;
		}

		public int getSize() {
			return this.m.size();
		}

		@Override
		public String toString() {
			return m.entrySet().stream().map(e -> "" + Arrays.toString(e.getKey())+", " + e.getValue()).collect(Collectors.joining(System.lineSeparator()));
		}

	}

	/**
	 * Rigourous Test :-)
	 */
	@org.junit.Test
	public void testApp() {
		DominoSorterImpl<String> sorter = new DominoSorterImpl<>();
		sorter.insert("A", "B");
		sorter.insert("C", "B");
		sorter.insert("B", "D");
		sorter.insert("D", "E");
		sorter.insert("D", "F");
		
		
		/*
		sorter.insert("A", "B");
		sorter.insert("K", "F");
sorter.insert("B", "C");
		
		sorter.insert("F", "B");
		sorter.insert("C", "E");
		sorter.insert("C", "G");
		sorter.insert("P", "G");
		sorter.insert("Q", "P");
		sorter.insert("B", "T");
		*/

		System.out.println(sorter.toString());
		assertThat("", sorter.getSize(), IsEqual.equalTo(5));
		assertTrue(true);

	}
}
