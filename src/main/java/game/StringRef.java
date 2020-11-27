package game;

/**
 * StringRef class is intended to make a objects with a reference string for calling in various code.
 * The checkReference compares a given reference with the object's REFERENCE String and returns true if they are the same.
 * The OUTPUT_STRING is the string correct reference should be "translated" to.
 */
public class StringRef {
    private final String REFERENCE;
    private final String OUTPUT_STRING;

    public StringRef (String reference, String outputString){
        REFERENCE = reference;
        OUTPUT_STRING = outputString;
    }

    public boolean checkReference(String compareString){ return this.REFERENCE.equals(compareString); }
    public String getString(){ return this.OUTPUT_STRING; }
}
