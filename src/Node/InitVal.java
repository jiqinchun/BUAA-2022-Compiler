package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class InitVal extends Node{
    private Exp Exp;
    private ArrayList<InitVal> InitVal = new ArrayList<>();

    public InitVal(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof Exp) {
            Exp = (Exp) node;
        } else if(node instanceof InitVal) {
            InitVal.add((InitVal) node);
        }
    }
}
