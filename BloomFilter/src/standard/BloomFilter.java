package standard;
/**
 * 
 * @author Filippo Todeschini
 *
 */

public class BloomFilter {
	private byte[] set;
	private long m;
	private long k;
	private int p;
	private int prime1=13;
	private int prime2=7;
	private int n;
	
	/**
	 * Constructor of standard bloom filter
	 * @param aP universe of hash function
	 * @param init array to be represented in bloom filter
	 */
	public BloomFilter(int aP,String[] init){
		n=init.length;
		p=aP;
		k=Math.round((double)p/(double)n* Math.log(2));
		m=k*p;
		set=new byte[p];
		for(int i=0;i<init.length;i++){
			int hash1=hash(init[i],prime1);
			int hash2=hash(init[i],prime2);
			for(int j=0;j<k;j++){
				int gHash=(hash1+ hash2 * j) % p;
				if( gHash < 0 ) gHash += p;
				set[gHash]=1;
			}
		}
		for(int i=0;i<set.length;i++){
			if(set[i]!=1)set[i]=0;
		}
		
	}
	/**
	 * method for hashing an element 
	 * @param key string that it have to be hashed
	 * @param prime prime number for hashing
	 * @return
	 */
	private int hash( String key, int prime){ 
		int hashVal = prime;
		for( int i = 0; i < key.length( ); i++ ) hashVal = 31 * hashVal + key.charAt( i );
		hashVal %= p; 
		if( hashVal < 0 ) hashVal += p;
		return hashVal; 
	}
	/**
	 * method for add operation into bloom filter
	 * @param element element to be added
	 */
	public void add(String element){
		int hash1=hash(element,prime1);
		int hash2=hash(element,prime2);
		for(int j=0;j<k;j++){
			int gHash=(hash1+ hash2 * j) % p;
			if( gHash < 0 ) gHash += p;
			set[gHash]=1;
		}
	}
	/**
	 * method for lookup operation
	 * @param element element to be lookup
	 * @return true if element is find, false otherwise
	 */
	public boolean lookup(String element){
		int hash1=hash(element,prime1);
		int hash2=hash(element,prime2);
		for(int j=0;j<k;j++){
			int gHash=(hash1+ hash2 * j) % p;
			if( gHash < 0 ) gHash += p;
			if(set[gHash]==0){
				return false;
			}
		}
		return true;
	}

	/**
	 * method for printing the bloom filter
	 */
	public void printSet(){
		System.out.print("[");
		for(int i=0;i<set.length-1;i++)
			System.out.print(set[i]+", ");
		System.out.print(set[set.length-1]+"]");
		System.out.println();
	}
	/**
	 * method to get the probability of false positive
	 * @return the probability of false positive
	 */
	public double getProbabilityFP(){
		// p = k/m 
		// fpProb= 1-(1-(1/p)^2)^n= 1-(1-(k/m)^2)^n
		double fpProb=1-Math.pow(1-Math.pow((double)k/(double)m,2),(double)n);
		return fpProb;
	}
	

}
