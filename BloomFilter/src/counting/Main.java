package counting;

import java.util.Arrays;
import java.util.Random;
/**
 * 
 * @author Filippo Todeschini
 *
 */
public class Main {
	
	static String[] init;
	static final char[] charSet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	static final int strSize=20;
	
	public static void main(String[] args) {
		//Genero l'arrray di stringhe alfanumeriche iniziale
		int initSize=(int) Math.pow(10, 5);
		init=getArrayRandomString(initSize);
		System.out.println("N. elementi nell'insieme iniziale: "+initSize);
		
		int lookupListSize=(int) Math.pow(10, 5);
		String[] lookupList=getArrayRandomString(lookupListSize);
		
		int p=10000000;
		
		CountingBloomFilter bl=new CountingBloomFilter(p,init);
		System.out.println("Counting Bloom filter 1");
		System.out.println("FP Probability: "+bl.getProbabilityFP());
		System.out.println();

		//Add
		System.out.println("Add");
		String addString="benvenutoal2016ABCD";
		System.out.println("Lookup \""+addString+"\": "+bl.lookup(addString));
		bl.add("benvenutoal2016ABCD");
		System.out.println("Lookup dopo l'operazione di add \""+addString+"\": "+bl.lookup(addString));
		System.out.println();
		
		// Lookup 
		System.out.println("Lookup");
		System.out.println("N. elementi nell'insieme del lookup: "+lookupListSize);
		int countFP=0;
		for(int i=0;i<lookupList.length;i++){
			if(bl.lookup(lookupList[i])==true)
				if(Arrays.asList(init).contains(lookupList[i])==false)
					countFP++;
		}
		System.out.println("FP sul lookup degli elementi dell'ultimo array: "+countFP);
		System.out.println();
		
		//Delete
		System.out.println("Delete");
		String deleteString="benvenutoal2016ABCD";
		System.out.println("Lookup \""+deleteString+"\": "+bl.lookup(deleteString));
		bl.delete("benvenutoal2016ABCD");
		System.out.println("Lookup dopo l'operazione di delete \""+deleteString+"\": "+bl.lookup(deleteString));
		System.out.println();
	}
	
	public static String[] getArrayRandomString(int size){
		String[] str=new String[size];
		for (int i=0;i<size;i++){
			str[i]=getRandomString(strSize);
		}
		return str;
	}
	
	
	public static String getRandomString(int strSize){
		Random r = new Random();
	    char[] rs = new char[strSize];
	    for (int i = 0; i < rs.length; i++) {
	        int randIndexChar = r.nextInt(charSet.length);
	        rs[i] = charSet[randIndexChar];
	    }
		return new String(rs);
	}

}


