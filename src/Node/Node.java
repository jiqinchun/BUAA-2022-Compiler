package Node;

import WordAnalysis.Word;

import java.util.ArrayList;

public class Node {
    private Word word;
    private Node father;
    private ArrayList<Node> childs = new ArrayList<>();

    public Node(Word word) {
        this.word = word;
    }

    public Node() {
        this.word = null;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public Node getFather() {
        return father;
    }

    public void addChild(Node child) {
        this.childs.add(child);
    }

    public ArrayList<Node> getChilds() {
        return childs;
    }

    public void Link(Node node) {
        node.setFather(this);
        this.addChild(node);
    }

    public void insertNode(Node insert){
        Node node = this.getChilds().get(this.getChilds().size() - 1);
        insert.Link(node);
        this.getChilds().remove(this.getChilds().size() - 1);
        this.Link(insert);
    }
}
