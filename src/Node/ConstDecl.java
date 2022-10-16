package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class ConstDecl extends Node{
    private BType BType;
    private ArrayList<ConstDef> constDefs = new ArrayList<>();

    public ConstDecl(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof BType) {
            BType = (BType) node;
        } else if(node instanceof ConstDef) {
            constDefs.add((ConstDef) node);
        }
    }
}
