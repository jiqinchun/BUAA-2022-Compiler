package Node;

import WordAnalysis.Word;

public class ConstExp extends Node{
    private AddExp AddExp;

    public ConstExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof AddExp) {
            AddExp = (AddExp) node;
        }
    }
}
