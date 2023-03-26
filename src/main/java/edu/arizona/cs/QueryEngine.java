package edu.arizona.cs;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class QueryEngine {
    boolean indexExists=false;
    String inputFilePath ="";
    ArrayList<String> toIndex = new ArrayList<>();
    StandardAnalyzer analyzer = new StandardAnalyzer();
    Directory index = new ByteBuffersDirectory();
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter w;
    ArrayList<String> documents = new ArrayList<>();

    public QueryEngine(String inputFile){
        inputFilePath =inputFile;
        buildIndex();
    }

    private void buildIndex() {
        //Get file from resources folder


        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(inputFilePath).getFile());

        try (Scanner inputScanner = new Scanner(file)) {
            w = new IndexWriter(index, config);
            while (inputScanner.hasNextLine()) {
                String str = inputScanner.nextLine();
                documents.add(str.substring(0,4));
                String str2 = str.substring(5,str.length());
                String str3 = str.substring(0,4);
                Document doc = new Document();
                doc.add(new TextField("text",str.substring(4, str.length()), Field.Store.YES));
                doc.add(new StringField("docid", str.substring(0,4), Field.Store.YES));
                w.addDocument(doc);

            }
            w.close();
            inputScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
           indexExists = true;
    }

    public static void main(String[] args ) {
        try {
            String fileName = "input.txt";
            System.out.println("********Welcome to  Homework 3!");
            String[] query13a = {"information", "retrieval"};
            QueryEngine objQueryEngine = new QueryEngine(fileName);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<ResultClass> runQ1_1(String[] query) throws java.io.FileNotFoundException, java.io.IOException {
        if(!indexExists) {
            buildIndex();
        }
        Query q;
        
        ArrayList<ResultClass> results = new ArrayList<>();
        int hitsPerPage = 10;
        String str = Arrays.toString(query);
        str = str.substring(1, str.length()-1);
        try {
            q = new QueryParser("text", analyzer).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int id = hits[i].doc;
            Document d = searcher.doc(id);
            ResultClass resulted = new ResultClass();
            resulted.DocName = d;
            resulted.docScore = hits[i].score;
            results.add(resulted);

            
        }


        return results;
    }

    public List<ResultClass> runQ1_2_a(String[] query) throws java.io.FileNotFoundException,java.io.IOException {
        if(!indexExists) {
            buildIndex();
        }
        Query q;

        ArrayList<ResultClass> results = new ArrayList<>();
        int hitsPerPage = 10;
        String str = Arrays.toString(query);
        str = query[0] + " AND " + query[1];
        try {
            q = new QueryParser("text", analyzer).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int id = hits[i].doc;
            Document d = searcher.doc(id);
            ResultClass resulted = new ResultClass();
            resulted.DocName = d;
            resulted.docScore = hits[i].score;
            results.add(resulted);


        }


        return results;
    }

    public List<ResultClass> runQ1_2_b(String[] query) throws java.io.FileNotFoundException,java.io.IOException {
        if(!indexExists) {
            buildIndex();
        }
        Query q;

        ArrayList<ResultClass> results = new ArrayList<>();
        int hitsPerPage = 10;
        String str = Arrays.toString(query);
        str = query[0] + " AND NOT " + query[1];
        try {
            q = new QueryParser("text", analyzer).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int id = hits[i].doc;
            Document d = searcher.doc(id);
            ResultClass resulted = new ResultClass();
            resulted.DocName = d;
            resulted.docScore = hits[i].score;
            results.add(resulted);


        }


        return results;
    }

    public List<ResultClass> runQ1_2_c(String[] query) throws java.io.FileNotFoundException,java.io.IOException {
        if(!indexExists) {
            buildIndex();
        }
        Query q;

        ArrayList<ResultClass> results = new ArrayList<>();
        int hitsPerPage = 10;
        String str = Arrays.toString(query);
        str =  "\"" + query[0] + " " + query[1] +  "\"" + "~1";
        try {
            q = new QueryParser("text", analyzer).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int id = hits[i].doc;
            Document d = searcher.doc(id);
            ResultClass resulted = new ResultClass();
            resulted.DocName = d;
            resulted.docScore = hits[i].score;
            results.add(resulted);


        }


        return results;
    }

    public List<ResultClass> runQ1_3(String[] query) throws java.io.FileNotFoundException,java.io.IOException {
        if(!indexExists) {
            buildIndex();
        }
        Query q;

        ArrayList<ResultClass> results = new ArrayList<>();
        int hitsPerPage = 10;
        String str = Arrays.toString(query);
        str = str.substring(1, str.length()-1);
        try {
            q = new QueryParser("text", analyzer).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        ClassicSimilarity similarity = new ClassicSimilarity();
        searcher.setSimilarity(similarity);

        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int id = hits[i].doc;
            Document d = searcher.doc(id);
            ResultClass resulted = new ResultClass();
            resulted.DocName = d;
            resulted.docScore = hits[i].score;
            results.add(resulted);


        }


        return results;
    }


    private  List<ResultClass> returnDummyResults(int maxNoOfDocs) {

        List<ResultClass> doc_score_list = new ArrayList<ResultClass>();
            for (int i = 0; i < maxNoOfDocs; ++i) {
                Document doc = new Document();
                doc.add(new TextField("title", "", Field.Store.YES));
                doc.add(new StringField("docid", "Doc"+Integer.toString(i+1), Field.Store.YES));
                ResultClass objResultClass= new ResultClass();
                objResultClass.DocName =doc;
                doc_score_list.add(objResultClass);
            }

        return doc_score_list;
    }

}
