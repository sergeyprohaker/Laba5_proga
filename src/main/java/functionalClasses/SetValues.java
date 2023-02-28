package functionalClasses;

public class SetValues {
    private final int key;
    private final String valueType;
    private final boolean isRequired;
    private final String comment;

    public SetValues(int key, String valueType, boolean isRequired, String comment) {
        this.key = key;
        this.valueType = valueType;
        this.isRequired = isRequired;
        this.comment = comment;
    }

    public int getKey(){
        return key;
    }

    public String getValueType(){
        return valueType;
    }

    public boolean getIsRequired(){
        return isRequired;
    }

    public String getComment(){
        return comment;
    }

}
