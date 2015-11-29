import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lista {
public List<String> lista = Collections.synchronizedList(new ArrayList<String>());
public ArrayList<BigInteger> liczbyp=new ArrayList<BigInteger>();
public int ileliczb;
public boolean check=false;
public Lista(int ile)
{
	ileliczb=ile;
}
}
