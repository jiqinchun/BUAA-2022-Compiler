package WordAnalysis;

public class Word {

    private String category;
    private String value;
    private int line;

    public Word(String category, String value, int line) {
        this.category = category;
        this.value = value;
        this.line = line;
    }

    public Word(String category, String value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }
}
