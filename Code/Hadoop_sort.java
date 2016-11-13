import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//similar to the libraries used in word count example.

public class Hadoop_Sort
{
//Mapper class 
	public static class Hadoop_Sort_Mapper extends Mapper<LongWritable, Text, Text, Text> 
	{
		private Text k10 = new Text();
		private Text v90 = new Text();
/*Keys are objects which implement writableComparable.Two writableComparables can be compared against each other to determine their ‘order’. Keys are used as WritableComparables because they are passed to the Reducer in sorted order.Values are objects which implement the writable interface.*/
//Map function
		public void map(LongWritable key, Text value, Context context) throws IOException,InterruptedException{ //taking one line at a time and tokenizing the same
		  StringTokenizer s = new StringTokenizer(value.toString(),"\n");
		  String kv=""; String k="";String v="";
		  while (s.hasMoreTokens()) {
		  kv=s.nextToken();
		  k=kv.substring(0,10);
		  v=kv.substring(12,98);
        	  k10.set(k);
		  v90.set(v);
		  context.write(k10,v90);
      		  }//end of while
    		}//end of map method
  	}//end of mapper class

	public static class Hadoop_Sort_Reducer extends Reducer<Text, Text, Text, Text>
	{
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,InterruptedException 
		{ String result ="";String k="";
		  for (Text value : values)
			{
			result = value.toString()+ "\n";
			}
		  context.write(key , new Text(result));
		}//end of reduce method
  	}//end of reducer class


	
	public static void main(String[] args) throws Exception
       {
	/*The data passed to the Mapper is specified by an InputFormat. It defines the file or directory of the input data on HDFS.FilelnputFormat is the base class used for all file-based InputFormats. */

/*	configuring the job, then submiting it to the cluster.*/
	Configuration conf = new Configuration();
    	Job job = Job.getInstance(conf, "Hadoop_Sort");        
	//JobConf conf= new JobConf(getConf(), Hadoop_Sort.class);
	job.setJarByClass(Hadoop_Sort.class);
    	job.setMapperClass(Hadoop_Sort_Mapper.class);
    	job.setCombinerClass(Hadoop_Sort_Reducer.class);
    	job.setReducerClass(Hadoop_Sort_Reducer.class);
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(Text.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//	JobClient.runJob(job);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

