package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class LVal extends Node{
    private ArrayList<Exp> Exps = new ArrayList<Exp>();

    public LVal(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof Exp) {
            Exps.add((Exp) node);
        }
    }
}
