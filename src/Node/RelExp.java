package Node;

import WordAnalysis.Word;

public class RelExp extends Node{
    private AddExp AddExp;
    private RelExp RelExp;

    public RelExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof AddExp) {
            AddExp = (AddExp) node;
        }
        if(node instanceof RelExp) {
            RelExp = (RelExp) node;
        }
    }
}
