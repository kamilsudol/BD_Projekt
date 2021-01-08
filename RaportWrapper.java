import java.util.*;

/**
 * Klasa RaportWrapper
 * Pomocnicza klasa opakowujaca zmienne.
 */

public class RaportWrapper{
    public ArrayList<String> countable;
    public ArrayList<ArrayList<String>> calosc;
    public ArrayList<String> atrybuty;
    public ArrayList<String> agregatable;
    public ArrayList<String> agregaty;

    public RaportWrapper(ArrayList<ArrayList<String>> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d, ArrayList<String> e){
        calosc = a;
        atrybuty = b;
        countable = c;
        agregatable = d;
        agregaty = e;
    }

    public RaportWrapper(){
        calosc = null;
        atrybuty = null;
        countable = null;
        agregatable = null;
        agregaty = null;
    }
}