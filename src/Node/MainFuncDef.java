package Node;

import WordAnalysis.Word;

public class MainFuncDef extends Node{
    private Block Block;

    public MainFuncDef(Word word) {
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof Block) {
            Block = (Block) node;
        }
    }
}
