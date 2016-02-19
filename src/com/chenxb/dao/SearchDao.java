package com.chenxb.dao;

import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

/**
 *  利用 Lucene 搜索 mysql 里的记录
 *  全文搜索
 * 
 */
public class SearchDao {
	/**
	 * this is index directory path where all index file will be stored which lucene uses internally.
	 */

	/**
	 * to create index on simple database table
	 */
	public void createIndex() {

		System.out.println("-- Indexing --");

		try {
			/** JDBC Section */
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// 后面unicode和utf8设置防止中文乱码
			String url = "jdbc:mysql://127.0.0.1:3306/see_news?useSSL=false&useUnicode=true&characterEncoding=utf-8";
			String name = "root";
			String password = "chenxb123";

			Connection conn = DriverManager.getConnection(url, name, password);

			Statement stmt = conn.createStatement();
			String sql = "select *  from bachelor  order by id desc limit 1000";
			ResultSet rs = stmt.executeQuery(sql);

			/** defining Analyzer */

			// 1. create the index
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("./index2222"));

			// 创建标准文本分析器， 标准的是可以支持的中文的

			// StandardAnalyzer luceneAnalyzer = new StandardAnalyzer();

			Analyzer luceneAnalyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);

			/** preparing config for indexWriter */
			IndexWriterConfig writerConfig = new IndexWriterConfig(luceneAnalyzer);

			/** Create a new index in the directory, removing any previously indexed documents */
			writerConfig.setOpenMode(OpenMode.CREATE);
			/**
			 * Optional: for better indexing performance, if you are indexing many documents,<BR>
			 * increase the RAM buffer. But if you do this, increase the max heap size to the JVM (eg add -Xmx512m or -Xmx1g):
			 */
			// writerConfig.setRAMBufferSizeMB(256.0);

			IndexWriter iWriter = new IndexWriter(directory, writerConfig);

			int count = 0;
			Document doc = null;
			Field field = null;

			/** declaring string type */
			FieldType stringType = new FieldType();
			stringType.setTokenized(true);

			/** Looping through resultset and adding data to index file */
			while (rs.next()) {
				doc = new Document();

				/** adding id in document */
				field = new IntField("id", rs.getInt("id"), Field.Store.YES);
				doc.add(field);

				/** adding name in document */
				field = new TextField("title", rs.getString("title"), Field.Store.YES);
				doc.add(field);

				/** adding details in document */
				field = new TextField("body", rs.getString("body"), Field.Store.YES);
				doc.add(field);

				/** Adding doc to iWriter */
				iWriter.addDocument(doc);
				count++;
			}

			System.out.println(count + " record indexed");

			/** Closing iWriter */
			iWriter.commit();
			iWriter.close();

			/** Closing JDBC connection */
			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * to search the keywords
	 * 
	 * @param keyword
	 */
	public void search(String keyword) {

		System.out.println("-- Seaching --");

		try {
			/** Searching */
			Directory index = FSDirectory.open(FileSystems.getDefault().getPath("./index2222"));

			IndexReader directoryReader = DirectoryReader.open(index);

			// IndexReader directoryReader = DirectoryReader
			// .open(FSDirectory.open(FileSystems.getDefault().getPath("./index2222")));

			IndexSearcher searcher = new IndexSearcher(directoryReader);
			// StandardAnalyzer keywordAnalyzer = new StandardAnalyzer();
			Analyzer luceneAnalyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);

			/** MultiFieldQueryParser is used to search multiple fields */
			String[] filesToSearch = { "id", "title", "body" };
			MultiFieldQueryParser mqp = new MultiFieldQueryParser(filesToSearch, luceneAnalyzer);

			/** search the given keyword */
			Query query = mqp.parse(keyword);
			System.out.println("query >> " + query);
			//
			// /** defining the sorting on filed "name" */
			Sort nameSort = new Sort(new SortField("id", Type.STRING));

			/** run the query */
			TopDocs hits = searcher.search(query, 1000);
			System.out.println("Results found >> " + hits.totalHits);

			Document doc = null;
			for (int i = 0; i < hits.totalHits; i++) {
				/** get the next document */
				doc = searcher.doc(hits.scoreDocs[i].doc);
				System.out.println("==========" + (i + 1) + " : Start Record=========\nId :: " + doc.get("id")
						+ "\ntitle :: " + doc.get("title") + "\n==========End Record=========\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * main method to check the output
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SearchDao obj = new SearchDao();

		/** creating index */
		// obj.createIndex();

		/** searching simple keyword */
		System.out.println("==================searching simple keyword===========================");
		obj.search("电院课表");

		// /** searching simple keyword */
		// System.out.println("==================searching simple
		// keyword===========================");
		// obj.search("褚");
		//
		// /** searching using wild card */
		// System.out.println("==================searching using wild
		// card===========================");
		// obj.search("791");
		//
		// /** searching using logical OR operator */
		// System.out.println("==================searching using logical OR
		// operator===========================");
		// obj.search("院");
	}

}