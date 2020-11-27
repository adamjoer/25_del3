package game;

/**
 * StringHandler makes an array of StringRefs in the constructor, from a specified XML file.
 */
public class StringHandler {

    private final StringRef[] STRING_REFS;

    public StringHandler (String filePath){
        STRING_REFS = Utility.stringRefGenerator(filePath);
    }

    /**
     * getString looks up a reference in the STRING_REF array, as soon as one call of checkReference returns true,
     * the for loop breaks and returns the corresponding String.
     * @param reference Unique reference/identifier.
     * @return The requested String.
     */
    public String getString (String reference){
        int stringRefIndex = -1;
        for (int i = 0; i < STRING_REFS.length; i++) {
            if(STRING_REFS[i].checkReference(reference)){
                stringRefIndex = i;
                break;
            }
        }
        if(stringRefIndex == -1){
            return "Faulty reference.";
        } else {
            return STRING_REFS[stringRefIndex].getString();
        }
    }
}