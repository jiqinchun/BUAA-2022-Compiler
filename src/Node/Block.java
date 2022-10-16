package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class Block extends Node{
    private ArrayList<BlockItem> BlockItems = new ArrayList<>();

    public Block(Word word){
        super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof BlockItem) {
            BlockItems.add((BlockItem) node);
        }
    }
}
