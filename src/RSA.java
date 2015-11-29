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


public class RSA 
{
	BigInteger e;
	BigInteger d;
	BigInteger n;
	public ArrayList<BigInteger> liczbyp;
	public RSA(){}
	int bits;
	public void CreatePrime(int ileliczb,int bits,RSA rsa) throws IOException
	{
		this.bits=bits;
		Worker[] watki = new Worker[4];
		Lista lista=new Lista(ileliczb);
		for(int i=0;i<4;i++)
		{
			watki[i]=new Worker(i,lista,bits,rsa,null);			
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
		BufferedWriter writer = new BufferedWriter(new FileWriter("liczbypierwsze.txt"));
		for(BigInteger a:liczbyp)
		{
			writer.write(a.toString());
			writer.write(" ");
		}
		writer.close();
	}
	public void liczenie()
	{
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
		e = new BigInteger((bits-2),new SecureRandom()).shiftLeft(1).add(new BigInteger("1"));
		while (m.gcd(e).intValue() > 1) 
		{
		      e = e.add(new BigInteger("2"));
		}
		//System.out.println(e);
	    d = e.modInverse(m);
		liczbyp=null;
	}
	
	public static void main(String[] args) throws IOException
	{
		long startTime = System.currentTimeMillis();
		String cozrobic="e";
		if(cozrobic=="e")
		{
		RSA rsa=new RSA();	
		rsa.CreatePrime(2,256,rsa);
		rsa.liczenie();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("wynik.txt"));
		BufferedWriter writern = new BufferedWriter(new FileWriter("n.txt"));
		BufferedWriter writerd = new BufferedWriter(new FileWriter("d.txt"));
		writern.write(new String(rsa.n.toString()));
		writerd.write(new String(rsa.d.toString()));
		writern.close();
		writerd.close();
		FileInputStream fileInput = new FileInputStream("test.txt");
		int r;
		BigInteger asd=new BigInteger("1");
		String text="";
		while ((r = fileInput.read()) != -1) 
		{
		   char c = (char) r;
		   text+=(char)c;
		   asd=new BigInteger(text.getBytes());
		   if(asd.compareTo(rsa.n) >0 )
		   {
			   text=text.substring(0,text.length()-1);
			   asd=new BigInteger(text.getBytes());
			   BigInteger wynik=asd.modPow(rsa.e, rsa.n);
			   //System.out.println(wynik);
			   writer.write(new String(wynik.toString()));
			   writer.write(" ");
			   text=""+ c;
		   }
		}
		   BigInteger wynik=asd.modPow(rsa.e, rsa.n);
		   writer.write(new String(wynik.toString()));
		   writer.close();
		   fileInput.close();
		}
		else if(cozrobic=="d")
		{
		//dekrypt
		BufferedWriter writer = new BufferedWriter(new FileWriter("odszyfrowanie.txt"));
		FileInputStream fileInput = new FileInputStream("wynik.txt");
		FileInputStream filen = new FileInputStream("n.txt");
		FileInputStream filed = new FileInputStream("d.txt");
		String nfile="";
		String dfile="";
		int r;		
		while ((r = filen.read()) != -1) 
		{
			nfile+=(char)r;
		}
		while ((r = filed.read()) != -1) 
		{
			dfile+=(char)r;
		}
		filen.close();
		filed.close();
		System.out.println(dfile);
		System.out.println(nfile);
		BigInteger n=new BigInteger(nfile);
		BigInteger d=new BigInteger(dfile);
		String content = new String(Files.readAllBytes(Paths.get("wynik.txt")));
		String [] odczytane = content.split(" ");
		for(String o:odczytane)
		{		
			BigInteger wynik=new BigInteger(o).modPow(d, n);
			writer.write(new String(wynik.toByteArray()));
		}
		writer.close();
		fileInput.close();
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
	
}