package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class FuncFParam extends Node{
    private BType BType;
    private ArrayList<ConstExp> ConstExps = new ArrayList<>();

    public FuncFParam(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof BType) {
            BType = (BType) node;
        } else if(node instanceof ConstExp) {
            ConstExps.add((ConstExp) node);
        }
    }
}
