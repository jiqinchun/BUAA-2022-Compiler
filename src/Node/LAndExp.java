package Node;

import WordAnalysis.Word;

public class LAndExp extends Node{
    private LAndExp LAndExp;
    private EqExp EqExp;

    public LAndExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof LAndExp) {
            LAndExp = (LAndExp) node;
        }
        if(node instanceof EqExp) {
            EqExp = (EqExp) node;
        }
    }
}
