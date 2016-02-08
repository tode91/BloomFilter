package standard;

public class BloomFilter {
	private byte[] set;
	private int m;
	private int k;
	private int p;
	private int prime1=13;
	private int prime2=7;
	private int n;
	
	public BloomFilter(int aP, int aK,String[] init){
		p=aP;
		k=aK;
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
		n=init.length;
	}
	
	public BloomFilter(int aP, int aK){
		p=aP;
		k=aK;
		m=k*p;
		set=new byte[p];
		for(int i=0;i<set.length;i++){
			set[i]=0;
		}
		n=0;
	}
	
	private int hash( String key, int prime){ 
		int hashVal = prime;
		for( int i = 0; i < key.length( ); i++ ) hashVal = 31 * hashVal + key.charAt( i );
		hashVal %= p; 
		if( hashVal < 0 ) hashVal += p;
		return hashVal; 
	}
	
	public void add(String element){
		int hash1=hash(element,prime1);
		int hash2=hash(element,prime2);
		for(int j=0;j<k;j++){
			int gHash=(hash1+ hash2 * j) % p;
			if( gHash < 0 ) gHash += p;
			set[gHash]=1;
		}
		n++;
	}
	
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

	
	public void printSet(){
		System.out.print("[");
		for(int i=0;i<set.length-1;i++)
			System.out.print(set[i]+", ");
		System.out.print(set[set.length-1]+"]");
		System.out.println();
	}
	
	public double getProbabilityFP(){
		// p = k/m 
		// fpProb= 1-(1-(1/p)^2)^n= 1-(1-(k/m)^2)^n
		double fpProb=1-Math.pow(1-Math.pow((double)k/(double)m,2),(double)n);
		return fpProb;
	}
	

}
