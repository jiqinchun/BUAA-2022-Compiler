package Node;

import WordAnalysis.Word;

public class Cond extends Node{
    private LOrExp LOrExp;

    public Cond(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof LOrExp) {
            LOrExp = (LOrExp) node;
        }
    }
}
