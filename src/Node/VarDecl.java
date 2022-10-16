package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class VarDecl extends Node{
    private BType BType;
    private ArrayList<VarDef> varDefs = new ArrayList<>();

    public VarDecl(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof BType) {
            BType = (BType) node;
        } else if(node instanceof VarDef) {
            varDefs.add((VarDef) node);
        }
    }
}
