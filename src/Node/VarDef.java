package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class VarDef extends Node{
    private ArrayList<ConstExp> constExps = new ArrayList<>();
    private InitVal InitVal;

    public VarDef(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof ConstExp) {
            constExps.add((ConstExp) node);
        } else if(node instanceof InitVal) {
            InitVal = (InitVal) node;
        }
    }
}
