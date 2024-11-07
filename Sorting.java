
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; 

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable.
	 * It returns an ArrayList containing all the keys from the map, ordered
	 * in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number
	 * of pairs in the map.
	 */
	public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
		ArrayList<K> sortedUrls = new ArrayList<K>();
		sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

		int N = sortedUrls.size();
		for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);
				}
			}
		}
		return sortedUrls;
	}


	/*
	 * This method takes as input an HashMap with values that are Comparable.
	 * It returns an ArrayList containing all the keys from the map, ordered
	 * in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n*log(n)), where n is the number
	 * of pairs in the map.
	 */
	public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
		ArrayList<K> list = new ArrayList<>(results.keySet());
		//System.out.println("keys are "+ list);
		//System.out.println("map is "+results);
		return mergeSort(results,list);
	}
	private static <K,V extends Comparable> ArrayList<K> mergeSort(HashMap<K,V> result, ArrayList<K> whole){
		ArrayList<K> left = new ArrayList<K>();
		ArrayList<K> right = new ArrayList<K>();
		int center;

		if (whole.size() == 1) {
			return whole;
		} else {
			center = whole.size()/2;
			for (int i=0; i<center; i++) {
				left.add(whole.get(i));
			}

			for (int i=center; i<whole.size(); i++) {
				right.add(whole.get(i));
			}
			mergeSort(result, left);
			mergeSort(result, right);

			merge(result,left, right, whole);
		}
		return whole;

	}
	
	private static <K,V extends Comparable> ArrayList<K> merge(HashMap<K,V> result,ArrayList<K> left,ArrayList<K> right,ArrayList<K> whole){
		int leftIndex = 0;
		int rightIndex = 0;
		int wholeIndex = 0;
		// As long as neither the left nor the right ArrayList has
		// been used up, keep taking the smaller of left.get(leftIndex)
		// or right.get(rightIndex) and adding it at both.get(bothIndex).
		while (leftIndex < left.size() && rightIndex < right.size()) {
			if ( (result.get(left.get(leftIndex))).compareTo(result.get(right.get(rightIndex))) < 0) {
				whole.set(wholeIndex, right.get(rightIndex));
				rightIndex++;
			} else {
				whole.set(wholeIndex, left.get(leftIndex));
				leftIndex++;
			}
			wholeIndex++;
		}

		ArrayList<K> rest;
		int restIndex;
		if (leftIndex >= left.size()) {
			// The left ArrayList has been use up...
			rest = right;
			restIndex = rightIndex;
		} else {
			// The right ArrayList has been used up...
			rest = left;
			restIndex = leftIndex;
		}

		// Copy the rest of whichever ArrayList (left or right) was not used up.
		for (int i=restIndex; i<rest.size(); i++) {
			whole.set(wholeIndex, rest.get(i));
			wholeIndex++;
		}


		return whole;
	}



}
