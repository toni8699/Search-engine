// Tony Le
// 260933631


import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 *
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		ArrayList<String> links = new ArrayList<>();
		crawHelper(url);
		for ( String k: internet.getVertices()){
			for (String t: parser.getLinks(k)){
				internet.addEdge(k,t);
			}
		}
		indexHelper();

	}
	private void indexHelper(){
		for (String node : internet.getVertices()){
			for ( String key:parser.getContent(node)){
				if (wordIndex.containsKey(key)){
					if(!wordIndex.get(key).contains(node))
					{wordIndex.get(key).add(node);}
				}else{
					ArrayList<String> contents = new ArrayList<>();
					contents.add(node);
					wordIndex.put(key.toLowerCase(),contents);
				}
			}
		}

	}
	/* takes a URL and add it to "internet",
	keeping track of each visited nodes*/
	private void crawHelper(String url){
		internet.addVertex(url);
		internet.setVisited(url,true);
		for ( String t: parser.getLinks(url)){
			if (!internet.getVisited(t)){
				crawHelper(t);
			}
		}
	}



	/*
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex().
	 * To implement this method, refer to the algorithm described in the
	 * assignment pdf.
	 *
	 *
	 */

	public void assignPageRanks(double epsilon) {
		for (String vertex:internet.getVertices()){  //initilize with 1
			internet.setPageRank(vertex,1.0);
		}
		ArrayList<Double> oldRank = computeRanks(internet.getVertices());
		for (int i=0;i<internet.getVertices().size();i++){  //assign new rank//
			internet.setPageRank(internet.getVertices().get(i),oldRank.get(i));
		}
		ArrayList<Double> newranks =computeRanks(internet.getVertices()); //compute new rank//
		for (int i=0;i<internet.getVertices().size();i++){  //assign new rank//
			internet.setPageRank(internet.getVertices().get(i),newranks.get(i));
		}

		while ( !Converged(oldRank,newranks,epsilon)){   // while not converged
			oldRank = newranks;
			newranks = computeRanks(internet.getVertices());
			for (int i=0;i<internet.getVertices().size();i++){  //assign new rank//
				internet.setPageRank(internet.getVertices().get(i),newranks.get(i));
			}


		}


	}
	private boolean Converged(ArrayList<Double> oldRank, ArrayList<Double> newRank, double epsilon){
		for (int i=0;i<newRank.size();i++){
			if ( Math.abs(newRank.get(i) -oldRank.get(i)) >=epsilon){
				return false;
			}
		}
		return true;
	}









	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls.
	 * Note that the double in the output list is matched to the url in the input list using
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		ArrayList<Double> rank = new ArrayList<>();
		for (String vertex : vertices) {
			double temp =0.0;
			ArrayList<String> inVertex = internet.getEdgesInto(vertex);
			for (String edge : inVertex) {
				temp = temp + 0.5 *(internet.getPageRank(edge) / internet.getOutDegree(edge));
			}
			rank.add(temp+0.5);
		}
		return rank;

	}


	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 *
	 *
	 */
	public ArrayList<String> getResults(String query) {
		ArrayList<String> results = new ArrayList<>();
		results= wordIndex.get(query);
		HashMap<String,Double> newMap = new HashMap<>();
		for ( String url:results){
			newMap.put(url,internet.getPageRank(url));
		}
		results=Sorting.fastSort(newMap);
		System.out.println(newMap.entrySet());
		return results;
	}
}
