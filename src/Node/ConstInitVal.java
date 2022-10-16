package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class ConstInitVal extends Node{
    private ConstExp constExp;
    private ArrayList<ConstInitVal> constInitVals = new ArrayList<>();

    public ConstInitVal(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof ConstExp) {
            constExp = (ConstExp) node;
        } else if(node instanceof ConstInitVal) {
            constInitVals.add((ConstInitVal) node);
        }
    }
}
