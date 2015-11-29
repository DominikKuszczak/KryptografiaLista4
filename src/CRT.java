import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;


public class CRT 
{
	public BigInteger CreatePrime(int ileliczb,int bits) throws IOException
	{
		this.bits=bits;
		Worker[] watki = new Worker[4];
		Lista lista=new Lista(ileliczb);
		for(int i=0;i<4;i++)
		{
			watki[i]=new Worker(i,lista,bits,null,null);			
		}		
		try {
			watki[0].t.join();
			watki[1].t.join();
			watki[2].t.join();
			watki[3].t.join();
		} catch (InterruptedException e)
		{
			//System.out.println("Main thread Interrupted");			
		}
		while(liczbyp.size()>ileliczb)
		{
			liczbyp.remove(0);
		}
		return liczbyp.get(0);
}
	BigInteger[] ri;
	BigInteger[] di;
	BigInteger[] ti;	
	BigInteger e;
	BigInteger d;
	BigInteger n;
	BigInteger dP;
	BigInteger dQ;
	BigInteger qInv;
	int ileliczb=2;
	BigInteger q;
	BigInteger p;
	public ArrayList<BigInteger> liczbyp=new ArrayList<BigInteger>();
	public CRT(){}
	int bits=256;
	
	public void liczenie() throws IOException
	{
		ri=new BigInteger[ileliczb];
		String content = new String(Files.readAllBytes(Paths.get("liczbypierwsze.txt")));
		String [] odczytane = content.split(" ");
		int j=0;
		for(String o:odczytane)
		{
			ri[j]=new BigInteger(o);
			j++;
			liczbyp.add(new BigInteger(o));
		}
		di=new BigInteger[ileliczb-2];
		ti=new BigInteger[ileliczb-2];
		n=new BigInteger("1");
		for(BigInteger e:liczbyp)
		{
			
			n=n.multiply(e);
		}
		BigInteger m=new BigInteger("1");
		for(BigInteger e:liczbyp)
		{
			
			m=m.multiply(e.subtract(new BigInteger("1")));
		}
			
		BigInteger e=CreatePrime(1,m.bitLength()-1);
		
        while(!(e.gcd(m)).equals(BigInteger.ONE));
		/*BigInteger m=new BigInteger("1");
		for(BigInteger e:liczbyp)
		{
			m=m.multiply(e.subtract(new BigInteger("1")));
		}
		e = new BigInteger((bits-2),new SecureRandom()).shiftLeft(1).add(new BigInteger("1"));
		while (m.gcd(e).intValue() > 1) 
		{
		      e = e.add(new BigInteger("2"));
		}*/
		//System.out.println(e);
	    d = e.modInverse(m);
	    q=ri[1];
    	p=ri[0];
	    dP = d.remainder(p.subtract(BigInteger.ONE));
        dQ = d.remainder(q.subtract(BigInteger.ONE));
	  
    	
	    qInv=p.modInverse(q);    	
	   
	   /* for(int i=0;i<ileliczb-2;i++)
	    {
	    	di[i]=e.modInverse(liczbyp.get(i+2).subtract(new BigInteger("1")));
	    }
	    for(int i=0;i<ileliczb-2;i++)
	    {
	    	BigInteger R=liczbyp.get(0).multiply(liczbyp.get(1));
	    	for(int j=0;j<i;j++)
	    	{
	    		R=R.multiply(liczbyp.get(j+2));
	    	}
	    	ti[i]=R.modInverse(liczbyp.get(i+2));
	    }*/
		
	   
	}
	
	
	public static void main(String[] args) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter("kurwa.txt"));
		CRT crt=new CRT();
		crt.liczenie();
		String content = new String(Files.readAllBytes(Paths.get("wynik.txt")));
		String [] odczytane = content.split(" ");
		for(String o:odczytane)
		{
			BigInteger c=new BigInteger(o);
			ArrayList<BigInteger> liczbym=new ArrayList<BigInteger>();
			BigInteger m1=(c.modPow(crt.dP, crt.p));
			BigInteger m2=(c.modPow(crt.dQ, crt.q));
		/*	for(int i=0;i<crt.ileliczb-2;i++)
			{
				liczbym.add(c.modPow(crt.di[i], crt.ri[i]));
			}*/
			BigInteger u = ((m2.subtract(m1)).multiply(crt.qInv)).remainder(crt.q);
	        if (u.compareTo(BigInteger.ZERO) < 0) {
	            u = u.add(crt.q);
	        }
			m1=m1.add(u.multiply(crt.p));
			
			/*BigInteger h=(liczbym.get(0).subtract(liczbym.get(1)));
			h=h.multiply(crt.qInv);
			h=h.mod(crt.p);
			BigInteger m=liczbym.get(1);
			m=m.add(crt.q.multiply(h));
		/*	for(int i=0;i<crt.ileliczb-2;i++)
			{
				BigInteger R=crt.liczbyp.get(0);
				R=R.multiply(crt.liczbyp.get(i+1));
				h=(liczbym.get(i+2).subtract(m)).multiply(crt.ti[i]).mod(crt.liczbyp.get(i+2));
				m=m.add(R.multiply(h));
			}*/
			writer.write(new String(u.toByteArray()));
			
		}
		writer.close();
	}
	
}