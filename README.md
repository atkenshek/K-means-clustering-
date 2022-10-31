# K-means-clustering

### The k-means clustering method is an unsupervised machine learning technique used to identify clusters of data objects in a dataset. There are many different types of clustering methods, but k-means is one of the oldest and most approachable


*Calculating TF:*

```java
public static Double calculateTF(Integer wordCountInDocument, Integer allDocumentWordsCount) {
        return Double.valueOf(wordCountInDocument) / Double.valueOf(allDocumentWordsCount);
    }
```

*Calculating IDF:*
```java
public static Double calculateIDF(String word) {
        Double numOfDocuments = Double.valueOf(documentsWordCount.size());
        Double wordCountWithUnique = Double.valueOf(wordsWithUniqueCount.get(word));
        return Math.log(numOfDocuments / wordCountWithUnique);
    }
```

*Finding distance:*
```java
public static void calcDistances1() {
        for (int i = 0; i < realVectors.size(); i++) {
            Double distance = 0.0;
            List<Double> vector1 = realVectors.get(i);
            for (int j = 0; j < realVectors.get(i).size(); j++) {
                distance += Math.pow(vector1.get(j) - randomVectors.get(0).get(j), 2);
            }
            distance = Math.sqrt(distance);
            distances1.add(distance);
        }
    }
```

*Finding nearest centroid:*

```java
public static void findWhichCentroid() {
        for (int i = 0; i < realVectors.size(); i++) {
            if (distances1.get(i) <= distances2.get(i)) {
                centroid1Vectors.add(realVectors.get(i));
            } else {
                centroid2Vectors.add(realVectors.get(i));
            }
        }
    }
```


*Changing the position:*

```java
 for (int i = 0; i < centroid1Vectors.size(); i++) {
    for (int j = 0; j < centroid1Vectors.get(i).size(); j++) {
        randomVectors.get(0).set(j, randomVectors.get(0).get(j) + centroid1Vectors.get(i).get(j));
    }
 }
 for (int i = 0; i < centroid1Vectors.size(); i++) {
     for (int j = 0; j < centroid1Vectors.get(i).size(); j++) {
         randomVectors.get(0).set(j, randomVectors.get(0).get(j) / centroid1Vectors.size());
     }
 }
```