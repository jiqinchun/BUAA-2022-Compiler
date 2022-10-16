package Node;

import WordAnalysis.Word;

public class EqExp extends Node{
    private RelExp RelExp;
    private EqExp EqExp;

    public EqExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof RelExp) {
            RelExp = (RelExp) node;
        }
        if(node instanceof EqExp) {
            EqExp = (EqExp) node;
        }
    }
}
