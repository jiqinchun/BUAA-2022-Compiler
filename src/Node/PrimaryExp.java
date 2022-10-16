package Node;

import WordAnalysis.Word;

public class PrimaryExp extends Node{
    private Exp Exp;
    private LVal LVal;
    private Number Number;

    public PrimaryExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof Exp) {
            Exp = (Exp) node;
        }
        if(node instanceof LVal) {
            LVal = (LVal) node;
        }
        if(node instanceof Number) {
            Number = (Number) node;
        }
    }
}
