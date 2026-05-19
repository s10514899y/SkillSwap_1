package resources;

public enum Level {
    BEGINNER(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    private final int value;

    Level(int value){ this.value = value; }

    public int getValue(){ return value;}

    public boolean isSufficient(Level required){
        return this.value >= required.value;
    }

}
