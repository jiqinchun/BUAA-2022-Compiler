package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class Exp extends Node{
    private AddExp AddExp;

    public Exp(Word word) {
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
