package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class FuncFParams extends Node{
   private ArrayList<FuncFParam> FuncFParams = new ArrayList<>();

    public FuncFParams(Word word) {
         super(word);
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if(node instanceof FuncFParam) {
            FuncFParams.add((FuncFParam) node);
        }
    }
}
