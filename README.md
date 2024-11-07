
# Search Engine Implementation

## Overview
This project implements a basic search engine that performs web crawling, indexing of words, and ranking of pages using sorting algorithms. The project includes two main components: a `SearchEngine` class for managing crawling and indexing, and a `Sorting` class for sorting results based on relevance.

## Project Components

### 1. `SearchEngine.java`
- **Description**:  
  The `SearchEngine` class is responsible for crawling web pages starting from a specified URL, indexing words from each page, and managing a web graph that represents links between pages.
- **Key Attributes**:
  - `wordIndex`: A `HashMap<String, ArrayList<String>>` that stores words as keys and a list of URLs containing those words as values.
  - `internet`: A `MyWebGraph` object that represents the graph structure of the crawled web pages.
  - `parser`: An `XmlParser` used to extract content and links from web pages.
- **Main Methods**:
  - `crawlAndIndex(String url)`: This method initiates the crawling process from a specified URL. It traverses the web graph, updating the word index and storing links between pages.
  - `indexHelper()`: This is a helper function for processing and storing words during crawling.
  
### 2. `Sorting.java`
- **Description**:  
  The `Sorting` class provides utility methods for sorting search results based on their relevance using a map of keys and values.
- **Key Methods**:
  - `slowSort(HashMap<K, V>)`: Implements a simple bubble sort algorithm to sort keys in descending order based on their mapped values. This method has a time complexity of \(O(n^2)\), where \(n\) is the number of elements in the map.
  - `fastSort(HashMap<K, V>)`: Implements a merge sort algorithm to efficiently sort keys based on their mapped values in descending order. Merge sort has a time complexity of \(O(n \log n)\) and is well-suited for larger datasets due to its better performance compared to bubble sort.

## Data Structures and Algorithms Used
- **Data Structures**:
  - `HashMap`: Used to store the mapping of words to the list of URLs where they appear.
  - `ArrayList`: Utilized for storing and manipulating lists of URLs and links.
- **Algorithms**:
  - **Graph Traversal**: The `crawlAndIndex` method employs graph traversal techniques to explore and index web pages.
  - **Bubble Sort**: The `slowSort` method uses a basic bubble sort algorithm to order URLs based on their relevance scores, which is simple but suboptimal for large data sets.
  - **Merge Sort**: The `fastSort` method leverages merge sort for efficiently sorting keys based on values, providing better performance on larger datasets.
