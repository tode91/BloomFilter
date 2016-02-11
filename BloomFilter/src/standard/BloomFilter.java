package standard;

public class BloomFilter {
	private byte[] set;
	private int m;
	private int k;
	private int p;
	private int n;
	/**
	 * Constructor of standard bloom filter
	 * @param aP universe of hash function
	 * @param init array to be represented in bloom filter
	 */
	public BloomFilter(int aP,String[] init){
		n=init.length;
		p=aP;
		k=(int)Math.round((double)p/(double)n* Math.log(2));
		m=k*p;
		set=new byte[m];
		for(int i=0;i<init.length;i++){
			for(int j=0;j<k;j++){
				int hash=getHashing(init[i], j);
				set[hash]=1;
			}
		}
		for(int i=0;i<set.length;i++){
			if(set[i]!=1)set[i]=0;
		}
		
	}
	/**
	 * method to get the hash value of an element
	 * @param element element to be hashed
	 * @param i index of hashing function
	 * @return hashing value
	 */
	private int getHashing(String element, int i){
		int hash=(hash1(element)+ hash2(element) * i) % p;
		if( hash < 0 ) hash += p;
		return hash;
	}
	/**
	 * method for hashing function 1
	 * @param key element to be hashed
	 * @return value of hash function
	 */
	private int hash1( String key){ 
		int val = 0;
		for( int i = 0; i < key.length( ); i++ ) val = 31 * val + key.charAt( i );
		return val; 
	}
	/**
	 * method for hashing function 2
	 * @param key element to be hashed
	 * @return value of hash function
	 */
	private int hash2( String key){ 
		int val = 1;
		for( int i = 0; i < key.length( ); i++ ) val = 31 * val + key.charAt( i );
		return val; 
	}
	
	/**
	 * method for add operation into bloom filter
	 * @param element element to be added
	 */
	public void add(String element){
		for(int j=0;j<k;j++){
			int hash=getHashing(element, j);
			set[hash]=1;
		}
	}
	
	/**
	 * method for lookup operation
	 * @param element element to be lookup
	 * @return true if element is find, false otherwise
	 */
	public boolean lookup(String element){
		for(int j=0;j<k;j++){
			int hash=getHashing(element, j);
			if(set[hash]==0){
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
		for(int i=0;i<set.length-1;i++){
			System.out.print(set[i]+", ");
		}
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
