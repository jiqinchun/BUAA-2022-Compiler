package Node;

import WordAnalysis.Word;

public class LOrExp extends Node{
    private LOrExp LOrExp;
    private LAndExp LAndExp;

    public LOrExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof LOrExp) {
            LOrExp = (LOrExp) node;
        }
        if(node instanceof LAndExp) {
            LAndExp = (LAndExp) node;
        }
    }
}
