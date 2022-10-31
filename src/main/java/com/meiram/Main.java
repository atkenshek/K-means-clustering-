package com.meiram;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Main {

    public static List<String> specialCaseWords = new ArrayList<>();

    public static HashMap<String, Integer> allWords = new HashMap<>();
    public static Map<String, Integer> sortedWords;
    public static List<String> sortedWordsAsList = new ArrayList<>();
    public static final boolean ASC = true;
    public static final boolean DESC = false;

    public static List<Integer> documentsWordCount = new ArrayList<>();

    public static HashMap<String, Integer> wordsWithUniqueCount = new HashMap<>();

    public static List<HashMap<String, Integer>> docs = new ArrayList<>();

    //Created vectors
    public static List<List<Double>> vectors = new ArrayList<>();
    public static List<List<Double>> realVectors = new ArrayList<>();
    public static List<List<Double>> randomVectors = new ArrayList<>();

    //Created two distances
    public static List<Double> distances1 = new ArrayList<>();
    public static List<Double> distances2 = new ArrayList<>();

    //Created two centroids
    public static List<List<Double>> centroid1Vectors = new ArrayList<>();
    public static List<List<Double>> centroid2Vectors = new ArrayList<>();

    public static void readFile(String path) throws IOException {
        File file = new File(path);
        InputStream targetStream = new FileInputStream(file);
        XWPFDocument xwpfDocument = new XWPFDocument(targetStream);
        XWPFParagraph para;
        Iterator<XWPFParagraph> iterator = xwpfDocument.getParagraphsIterator();

        Integer count = 0;

        HashMap<String, Integer> allWordsInDocument = new HashMap<>();

        while (iterator.hasNext()) {
            para = iterator.next();
            String[] arr = para.getText().split(" ");
            for(String word : arr) {
                word = word.replaceAll("[^a-zA-Z]","");
                word = word.toLowerCase(Locale.ROOT);
                if (!word.isEmpty()) {
                    if (word.length() >= 2) {
                        if (!specialCaseWords.contains(word)) {
                            count++;
                            if (!allWordsInDocument.containsKey(word)) {
                                allWordsInDocument.put(word, 1);
                            } else {
                                allWordsInDocument.put(word, allWordsInDocument.get(word) + 1);
                            }
                        }
                    }
                }
            }
        }
        documentsWordCount.add(count);

        for(Map.Entry<String, Integer> word : allWordsInDocument.entrySet()) {
            if (allWords.containsKey(word.getKey())) {
                allWords.put(word.getKey(), allWords.get(word.getKey()) + word.getValue());
            } else {
                allWords.put(word.getKey(), word.getValue());
            }

            if (wordsWithUniqueCount.containsKey(word.getKey())) {
                wordsWithUniqueCount.put(word.getKey(), wordsWithUniqueCount.get(word.getKey()) + 1);
            } else {
                wordsWithUniqueCount.put(word.getKey(), 1);
            }
        }

        docs.add(allWordsInDocument);
    }

    public static void main(String[] args) throws IOException {
        //Adding special words to the list
        specialCaseWords.add("the");
        specialCaseWords.add("and");
        specialCaseWords.add("to");
        specialCaseWords.add("c");
        specialCaseWords.add("in");
        specialCaseWords.add("as");
        specialCaseWords.add("a");
        specialCaseWords.add("is");
        specialCaseWords.add("are");
        specialCaseWords.add("of");
        specialCaseWords.add("by");
        specialCaseWords.add("that");
        specialCaseWords.add("which");
        specialCaseWords.add("such");
        specialCaseWords.add("their");
        specialCaseWords.add("from");
        specialCaseWords.add("for");
        specialCaseWords.add("an");
        specialCaseWords.add("all");
        specialCaseWords.add("there");
        specialCaseWords.add("or");
        specialCaseWords.add("also");
        specialCaseWords.add("he");
        specialCaseWords.add("with");
        specialCaseWords.add("be");
        specialCaseWords.add("these");
        specialCaseWords.add("on");
        specialCaseWords.add("it");
        specialCaseWords.add("was");
        specialCaseWords.add("other");
        specialCaseWords.add("his");
        specialCaseWords.add("ccc");
        specialCaseWords.add("this");
        specialCaseWords.add("at");
        specialCaseWords.add("if");
        specialCaseWords.add("has");
        specialCaseWords.add("will");
        specialCaseWords.add("d");
        specialCaseWords.add("b");

        //There reading ten articles
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\1.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\2.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\3.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\4.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\5.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\6.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\7.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\8.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\9.docx");
        readFile("C:\\Users\\Meiram Sopy\\Desktop\\ML\\10.docx");

        sortedWords = sortByValue(allWords, DESC);

        createVectors();
        convertToRealVector();
        addRandomVectors(2);
        System.out.println(sortedWords.size());
        System.out.println(sortedWordsAsList.toString());

        for (int k = 0 ; k < 5; k++) {
            distances1 = new ArrayList<>();
            distances2 = new ArrayList<>();
            calcDistances1();
            calcDistances2();
            centroid1Vectors = new ArrayList<>();
            centroid2Vectors = new ArrayList<>();
            findWhichCentroid();

            System.out.println("c1:" + centroid1Vectors.size());
            System.out.println("c2:" + centroid2Vectors.size());



            for (int i = 0; i < randomVectors.size(); i++) {
                for (int j = 0; j < randomVectors.get(i).size(); j++) {
                    randomVectors.get(i).set(j, 0.0);
                }
            }


            //There i'm changing the position of centered line
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

            for (int i = 0; i < centroid2Vectors.size(); i++) {
                for (int j = 0; j < centroid2Vectors.get(i).size(); j++) {
                    randomVectors.get(1).set(j, randomVectors.get(1).get(j) + centroid2Vectors.get(i).get(j));
                }
            }
            for (int i = 0; i < centroid2Vectors.size(); i++) {
                for (int j = 0; j < centroid2Vectors.get(i).size(); j++) {
                    randomVectors.get(1).set(j, randomVectors.get(1).get(j) / centroid2Vectors.size());
                }
            }
//          Printing TF IDF
//            for (List<Double> randomVector : randomVectors) {
//                System.out.println(randomVector.toString());
//            }
            System.out.println("***************************");
        }

        System.out.println("***************************");
    }

    public static Integer getWordCountInDocument(Integer docIndex, String word) {
        return docs.get(docIndex).get(word) == null ? 0 : docs.get(docIndex).get(word);
    }

    public static Double calculateIDF(String word) {
        Double numOfDocuments = Double.valueOf(documentsWordCount.size());
        Double wordCountWithUnique = Double.valueOf(wordsWithUniqueCount.get(word));
        return Math.log(numOfDocuments / wordCountWithUnique);
    }

    public static Double calculateTF(Integer wordCountInDocument, Integer allDocumentWordsCount) {
        return Double.valueOf(wordCountInDocument) / Double.valueOf(allDocumentWordsCount);
    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on given values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
            ? o1.getKey().compareTo(o2.getKey())
            : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
            ? o2.getKey().compareTo(o1.getKey())
            : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    public static void createVectors() throws IOException {
        BufferedWriter out = new BufferedWriter(
                new FileWriter("C:\\Users\\Meiram Sopy\\Desktop\\ML\\newfile.txt"));

        for(Map.Entry<String, Integer> word : sortedWords.entrySet()) {
            sortedWordsAsList.add(word.getKey());
            List<Double> vector = new ArrayList<>();
            Double IDF_word = calculateIDF(word.getKey());
            for(int i = 0; i < documentsWordCount.size(); i++) {
                Double TF_word = calculateTF(getWordCountInDocument(i, word.getKey()),documentsWordCount.get(i));
                out.write(String.valueOf(IDF_word * TF_word) + " ");
                vector.add(IDF_word * TF_word);
            }
            vectors.add(vector);
        }
        out.close();
    }

    public static void convertToRealVector() {
        for (int j = 0; j < documentsWordCount.size(); j++) {
            List<Double> realVector = new ArrayList<>();
            for (int i = 0; i < vectors.size(); i++) {
                realVector.add(vectors.get(i).get(j));
            }
            realVectors.add(realVector);
        }
    }

    public static void addRandomVectors(int count) {
        for (int j = 0; j < count; j++) {
            List<Double> randomVector = new ArrayList<>();
            for (int i = 0; i < sortedWords.size(); i++) {
                List<Double> eachWordValues = vectors.get(i);

                Double start = 999999.0;
                Double end = -999999.9;
                for (Double value : eachWordValues) {
                    if (value >= end) {
                        end = value;
                    }
                    if (value <= start) {
                        start = value;
                    }
                }
                Random r = new Random();
                Double randomValue = r.nextDouble();
                Double result = start + (randomValue * (end - start));
                randomVector.add(result);
            }
            randomVectors.add(randomVector);
        }

    }

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
    public static void calcDistances2() {
        for (int i = 0; i < realVectors.size(); i++) {
            Double distance = 0.0;
            List<Double> vector1 = realVectors.get(i);
            for (int j = 0; j < realVectors.get(i).size(); j++) {
                distance += Math.pow(vector1.get(j) - randomVectors.get(1).get(j), 2);
            }
            distance = Math.sqrt(distance);
            distances2.add(distance);
        }
    }

    public static void findWhichCentroid() {
        for (int i = 0; i < realVectors.size(); i++) {
            if (distances1.get(i) <= distances2.get(i)) {
                centroid1Vectors.add(realVectors.get(i));
            } else {
                centroid2Vectors.add(realVectors.get(i));
            }
        }
    }
}

