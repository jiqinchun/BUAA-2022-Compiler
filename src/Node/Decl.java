package Node;

import WordAnalysis.Word;

public class Decl extends Node{
    private ConstDecl ConstDecl;
    private VarDecl VarDecl;

    public Decl(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof ConstDecl) {
            ConstDecl = (ConstDecl) node;
        } else if(node instanceof VarDecl) {
            VarDecl = (VarDecl) node;
        }
    }
}
