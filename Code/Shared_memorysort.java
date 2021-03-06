
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class Shared_memorysort extends Thread {
	int num,iter;  int num_lines;
	public static int file_num=0;
	BufferedReader in;
	multi_threading(BufferedReader a, int b,int c,int d){
		in=a;
		num=b;
		iter=c;
		num_lines=d;
		}

	public void run()
	{   int i,j;String aline; 
		System.out.println("here we sort " +num);
		try{
		ArrayList<String> data = new ArrayList<String>(); 
		for(j=0;j<iter;j++)
		{
    	for (i=0;i<num_lines;i++)
    	//while((aline = in.readLine()) != null )
		{            	
    		aline = in.readLine();
    		//System.out.println(aline);
        	data.add(aline);
    	}
    	
		sorting(data, 0, data.size()-1);
    	FileWriter fos = new FileWriter("/home/ec2-user/Workspace/Shared_memory/File"+file_num+".txt");
        for(i=0;i<data.size();i++)
		{	
		//	System.out.print("The sequence at "+i+" is : "+data.get(i) + "\n");
        	fos.write(data.get(i)+"\n");
		}//end of for loop to write data to file
        fos.close();
        file_num++;
     	data.clear();
		}//end of iter loop
    	}catch(Exception e)
    	{
    		System.out.println(e);
    		try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}//end of catch
	} //end of run
	

	public static void sorting(ArrayList<String> x, int left, int right){
		int i=0,p=left,q=right,res;
		String pivot,tmp;
		pivot=x.get((p+q)/2);
		while (p <= q) {
//instead of compare the literal , compare the numeric equivalent of string
			res=x.get(p).compareTo(pivot);
			while (res < 0)
				{p++;
				res=x.get(p).compareTo(pivot);}
			res=x.get(q).compareTo(pivot);
			while (res > 0)
				{q--;
				res=x.get(q).compareTo(pivot);}
			if (p <= q) {
//instead of copy int , copy bytes of data 100 bytes.
				tmp = x.get(p);
				x.set(p, x.get(q));
				x.set(q, tmp);
				p++;
				q--;
			}
		}//end of while
		if(p-1>left){
			sorting(x,left,p-1);
		}
		if(p<right){
			sorting(x,p,right);
		}		
	}//end of sorting method


	
	public static void main(String[] args) throws Exception{
		long startTime = System.currentTimeMillis();
		try{ 
		long mem=1000, siz; int i,num_chunk=0,num_lines=0,k=1,j=0;
		File input =new File("/home/ec2-user/Workspace/input");
		int threads =2;int ite=1;
		mem/=threads;
		if(input.exists())
		{
		siz = input.length();
		System.out.println("siz= "+siz);
		System.out.println("mem= "+mem);
		num_chunk=(int) (siz/mem);
		System.out.println("Number of chunks required = "+num_chunk);
		num_lines=(int) mem/100;	
		ite=num_chunk/threads;
   
		FileReader fr = new FileReader(input);
		BufferedReader fis = new BufferedReader(fr);

		for (i=1;i<=threads;i++)
		{multi_threading t=new multi_threading(fis,i,ite,num_lines);  
		t.start();
		t.join();}
		fis.close();
	
//~~~~~~~~~~~~~~~~~~~~~~~~ordered chunks are formed. Now Merge.~~~~~~~~~~~~~~//
	
	// read first line of each chunk into an array
	String aline;		ArrayList<String> ip_chunks = new ArrayList<String>();
	ArrayList<String> op_chunks = new ArrayList<String>();
	int cmp;
    FileWriter fos = new FileWriter("/home/ec2-user/Workspace/Shared_memory/sorted_File.txt");
	int[] offset= new int[file_num];
	file_num-=1;
	int min=0;
    for (i=0;i<=file_num;i++)
    	{	FileReader fin = new FileReader("/home/ec2-user/Workspace/Shared_memory/File"+i+".txt");
			BufferedReader inp = new BufferedReader(fin);
			aline = inp.readLine();
			ip_chunks.add(aline);
			offset[i]=1;
    	}
    //System.out.print(ip_chunks.size());
//limit counter to count till there exist a record
    for (int counter=0;counter<1000000;counter++)
    {min=0;
    	for (i=0;i<file_num;i++)
		{
			cmp=ip_chunks.get(min).compareTo(ip_chunks.get(i));
			if (cmp>0)
			{min =i; }
		}
		    //write min element to output
			fos.write(ip_chunks.get(min)+"\n");
			offset[min]+=1;
		    FileReader fin = new FileReader("/home/niharika/Documents/cs553/Prog2_Karia_Niharika/data/File"+i+".txt");
			BufferedReader inp = new BufferedReader(fin);
			aline = inp.readLine();///add offset to read line
			ip_chunks.set(i,aline);
    }
    
         
    fos.close();
    //read next entry of "THAT" file
    
    

	
	}//end of if condition for file exist check
	}catch(Exception e)
	{
		System.out.println(e);
		throw e;
	}//end of catch
	long elapsedTime = System.currentTimeMillis() - startTime;
    System.out.println(elapsedTime);
	System.out.print("Program ended successfully");
	}//end of main method

}  //end of class
