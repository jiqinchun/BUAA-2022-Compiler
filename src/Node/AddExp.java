package Node;

import WordAnalysis.Word;

public class AddExp extends Node{
    private AddExp AddExp;
    private MulExp MulExp;

    public AddExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof AddExp) {
            AddExp = (AddExp) node;
        }
        if(node instanceof MulExp) {
            MulExp = (MulExp) node;
        }
    }
}
