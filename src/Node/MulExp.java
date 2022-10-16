package Node;

import WordAnalysis.Word;

public class MulExp extends Node{
    private UnaryExp UnaryExp;
    private MulExp MulExp;

    public MulExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof UnaryExp) {
            UnaryExp = (UnaryExp) node;
        }
        if(node instanceof MulExp) {
            MulExp = (MulExp) node;
        }
    }
}
