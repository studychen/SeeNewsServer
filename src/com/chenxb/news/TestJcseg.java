package com.chenxb.news;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class TestJcseg {
	public static void main(String arg[]) throws Exception {
		Analyzer analyzer = new StandardAnalyzer();

	    // Store the index in memory:
	    Directory directory = new RAMDirectory();
	    // To store an index on disk, use this instead:
	    //Directory directory = FSDirectory.open("/tmp/testindex");
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    Document doc = new Document();
	    String text = "This is the text to be indexed.";
	    doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
	    iwriter.addDocument(doc);
	    iwriter.close();
	    
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser("fieldname", analyzer);
	    Query query = parser.parse("text");
	    ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
	      Document hitDoc = isearcher.doc(hits[i].doc);
	    }
	    ireader.close();
	    directory.close();
//	    //lucene 5.x版本
//	    Analyzer analyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);
//	    //非必须(用于修改默认配置): 获取分词任务配置实例
//	    JcsegAnalyzer5X jcseg = (JcsegAnalyzer5X) analyzer;
//	    JcsegTaskConfig config = jcseg.getTaskConfig();
//	    //追加同义词, 需要在 jcseg.properties中配置jcseg.loadsyn=1
//	    config.setAppendCJKSyn(true);
//	    //追加拼音, 需要在jcseg.properties中配置jcseg.loadpinyin=1
//	    config.setAppendCJKPinyin(false);
//	    //更多配置, 请查看 org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig	
	}

}
