package Node;

import WordAnalysis.Word;

public class FuncDef extends Node{
    private FuncType FuncType;
    private FuncFParams FuncFParams;
    private Block Block;

    public FuncDef(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof FuncType) {
            FuncType = (FuncType) node;
        } else if(node instanceof FuncFParams) {
            FuncFParams = (FuncFParams) node;
        } else if(node instanceof Block) {
            Block = (Block) node;
        }
    }
}
