package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class Stmt extends Node{
    private LVal LVal;
    private ArrayList<Exp> Exps = new ArrayList<Exp>();
    private Block Block;
    private Cond Cond;
    private Stmt Stmt;

    public Stmt(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof LVal) {
            LVal = (LVal) node;
        } else if(node instanceof Exp) {
            Exps.add((Exp) node);
        } else if(node instanceof Block) {
            Block = (Block) node;
        } else if(node instanceof Cond) {
            Cond = (Cond) node;
        } else if(node instanceof Stmt) {
            Stmt = (Stmt) node;
        }
    }
}
