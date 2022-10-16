package Node;

import java.util.ArrayList;

public class CompUnit extends Node {
    private ArrayList<Decl> Decls = new ArrayList<>();
    private ArrayList<FuncDef> FuncDefs = new ArrayList<>();
    private MainFuncDef MainFuncDef;

    public CompUnit() {
        super();
    }

    @Override
    public void Link(Node node) {
        super.Link(node);
        if (node instanceof Decl) {
            Decls.add((Decl) node);
        } else if (node instanceof FuncDef) {
            FuncDefs.add((FuncDef) node);
        } else if (node instanceof MainFuncDef) {
            MainFuncDef = (MainFuncDef) node;
        }
    }
}
