import java.io.*;
import java.util.Map;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.api.java.function.Function;
import java.lang.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import scala.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;             
import org.apache.spark.api.java.function.Function2;


public class Spark_Sort {

	public static void main(String[] args) {
		//Creating RDD
		SparkConf conf = new SparkConf().setAppName("sort");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> record = sc.textFile("/input");

		class function_k implements Function<String , String> 
			{
			public String call(String x) {return x.substring(0,10);}
			}
	//end of map key function

		class function_v implements Function<String,String>
			{
			public String call(String x) {return x.substring(12,98);}
			}//end of map value function
		class mapper_kv implements Function<String,Tuple2<String, String>>
			{
			public Tuple2<String, String> call(String x) {return new Tuple2(x.substring(0,10),x.substring(12,98));}
			}
		PairFunction<String, String, String> keyData= 
				new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String x) {return new Tuple2(x.substring(0,10),x.substring(12,98));}
		};
		//translation on record RDD
		//JavaRDD<String> k10 = record.map( new function_k() ); 
		//JavaRDD<String> v90 = record.map( new function_v() ); 

		//JavaPairRDD<String, Integer> rec=k10.zip(v90);
		JavaPairRDD<String, String> rec= record.mapToPair(keyData);

		//JavaPairRDD<String, String> record_kv = k10.mapToPair(new mapper_kv());

		rec.sortByKey(true).saveAsTextFile("/Spark_sorted");
		
	}//end of main method

} //end of sorting class
