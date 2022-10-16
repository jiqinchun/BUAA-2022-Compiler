package Node;

import WordAnalysis.Word;

public class BlockItem extends Node{
    private Decl Decl;
    private Stmt Stmt;

    public BlockItem(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof Decl) {
            Decl = (Decl) node;
        } else if(node instanceof Stmt) {
            Stmt = (Stmt) node;
        }
    }
}
