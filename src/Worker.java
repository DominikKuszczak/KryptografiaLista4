import java.math.BigInteger;
public class Worker implements Runnable {
	int id;
	Lista lista;
	int bity;
	public RSA rsa=null;
	public CRT crt=null;
	
	Thread t;
	//ArrayList<String> lista;
	Worker(int d,Lista lista2,int bits,RSA rsa,CRT crt)
	{
		this.crt=crt;
		this.rsa=rsa;
		bity=bits;
		id=d;
		this.lista=lista2;
		t=new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		//ArrayList<String> lista=new ArrayList<String>();
		int k=50;			
		while(lista.check==false)
		{
			String liczba=MillerRabin.GenerateString(bity);
			while(lista.lista.contains(liczba)==true)
			{
				liczba=MillerRabin.GenerateString(bity);
			}
			lista.lista.add(liczba);
			if(MillerRabin.isProbablePrime(new BigInteger(liczba,2),k)==true)
			{
				if(!lista.liczbyp.contains(new BigInteger(liczba,2)))
				{
				lista.liczbyp.add(new BigInteger(liczba,2));
				}
				//System.out.println("true watek "+ id);
				if(lista.liczbyp.size()==lista.ileliczb)
				{
					if(rsa!=null)
					{
					rsa.liczbyp=lista.liczbyp;
					}
					lista.check=true;
				}
			}
			else
			{
			//System.out.println("false watek "+ id + "Lista rozmiar " + lista.lista.size());
			}
		}
		lista.check=true;
		
		
	}
	

}
