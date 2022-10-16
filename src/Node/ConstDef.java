package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class ConstDef extends Node{
    private ArrayList<ConstExp> constExps = new ArrayList<>();
    private ConstInitVal constInitVal;

    public ConstDef(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof ConstExp) {
            constExps.add((ConstExp) node);
        } else if(node instanceof ConstInitVal) {
            constInitVal = (ConstInitVal) node;
        }
    }
}
