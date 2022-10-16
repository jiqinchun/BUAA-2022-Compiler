package Node;

import WordAnalysis.Word;

public class UnaryExp extends Node{
    private PrimaryExp PrimaryExp;
    private FuncFParams FuncFParams;
    private UnaryOp UnaryOp;
    private UnaryExp UnaryExp;

    public UnaryExp(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof PrimaryExp) {
            PrimaryExp = (PrimaryExp) node;
        }
        if(node instanceof FuncFParams) {
            FuncFParams = (FuncFParams) node;
        }
        if(node instanceof UnaryOp) {
            UnaryOp = (UnaryOp) node;
        }
        if(node instanceof UnaryExp) {
            UnaryExp = (UnaryExp) node;
        }
    }
}
