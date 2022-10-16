package GrammarAnalysis;

import Node.*;
import Node.Number;
import WordAnalysis.Word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GrammarAnalysis {
    private File outputFile;
    private ArrayList<Word> words;
    private int index = 0;
    private Word word;
    private Node root;

    public GrammarAnalysis(ArrayList<Word> words,File outputFile) {
        this.words = words;
        this.outputFile = outputFile;
    }

    public void recursionAnalysis() {
        getWord();
        CompUnit();
    }

    public void getWord() {
        if(index < words.size()) {
            word = words.get(index);
            index++;
        } else {
            word = new Word("EOF", "EOF",words.get(index-1).getLine());
        }
    }

    public String getNextWord_Category(int i) {
        if(index + i  < words.size()) {
            return words.get(index + i ).getCategory();
        } else {
            return "EOF";
        }
    }

    public Boolean isDecl() {
        String Category = word.getCategory();
        if(Category.equals("CONSTTK")) {
            return true;
        } else if((Category.equals("INTTK") || Category.equals("CHARTK")) && !getNextWord_Category(1).equals("LPARENT")) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isFuncDef() {
        String Category = word.getCategory();
        return Category.equals("VOIDTK") || Category.equals("CHARTK") || (Category.equals("INTTK") && !getNextWord_Category(0).equals("MAINTK"));
    }

    public Boolean isFuncFParams() {
        String Category = word.getCategory();
        return Category.equals("INTTK");
    }

    public Boolean isExp() {
        String Category = word.getCategory();
        return Category.equals("PLUS") || Category.equals("MINU") || Category.equals("IDENFR") || Category.equals("INTCON") || Category.equals("LPARENT");
    }

    public boolean isStmt() {
        String Category = word.getCategory();
        return (Category.equals("SEMICN") || Category.equals("LBRACE") || Category.equals("IFTK")
                || Category.equals("WHILETK") || Category.equals("BREAKTK") || Category.equals("CONTINUETK")
                || Category.equals("RETURNTK") || Category.equals("PRINTFTK"))
                || isExp();
    }

    public Boolean isBlockItem(){
        return isDecl() || isStmt();
    }

    public Boolean isPrimaryExp() {
        String Category = word.getCategory();
        return Category.equals("IDENFR") || Category.equals("INTCON") || Category.equals("LPARENT");
    }

    public Boolean isFuncCall() {
        String Category = word.getCategory();
        return Category.equals("IDENFR") && getNextWord_Category(0).equals("LPARENT");
    }

    public Boolean isUnaryOp() {
        String Category = word.getCategory();
        return Category.equals("PLUS") || Category.equals("MINU") || Category.equals("NOT");
    }

    public Boolean isMulOp() {
        String Category = word.getCategory();
        return Category.equals("MULT") || Category.equals("DIV") || Category.equals("MOD");
    }

    public Boolean isAddOp() {
        String Category = word.getCategory();
        return Category.equals("PLUS") || Category.equals("MINU");
    }

    public Boolean isRelOp() {
        String Category = word.getCategory();
        return Category.equals("LSS") || Category.equals("LEQ") || Category.equals("GRE") || Category.equals("GEQ");
    }

    public Boolean isEqOp() {
        String Category = word.getCategory();
        return Category.equals("EQL") || Category.equals("NEQ");
    }

    public void CompUnit() {
        Word CompUnit = new Word("NONE", "<CompUnit>");
        root = new Node(CompUnit);

        while(isDecl()) {
            root.Link(Decl());
        }
        while(isFuncDef()) {
            root.Link(FuncDef());
        }
        root.Link(MainFuncDef());
    }

    public Node Decl() {
        Word Decl = new Word("NONE", "<Decl>");
        Decl node = new Decl(Decl);

        if(word.getCategory().equals("CONSTTK")) {
            node.Link(ConstDecl());
        } else {
            node.Link(VarDecl());
        }
        return node;
    }

    public Node ConstDecl() {
        Word ConstDecl = new Word("NONE", "<ConstDecl>");
        ConstDecl node = new ConstDecl(ConstDecl);
        //word是const
        Word CONST = new Word(word.getCategory(), word.getValue(), word.getLine());
        Node CONSTNode = new Node(CONST);
        node.Link(CONSTNode);

        getWord();
        node.Link(BType());
        node.Link(ConstDef());

        while(word.getCategory().equals("COMMA")) {
            Word COMMATK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node COMMATKNode = new Node(COMMATK);
            node.Link(COMMATKNode);
            getWord();
            node.Link(ConstDef());
        }
        if(word.getCategory().equals("SEMICN")) {
            Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node SEMICNNode = new Node(SEMICN);
            node.Link(SEMICNNode);
            getWord();
        }
        return node;
    }

    public Node BType() {
        Word BType = new Word("NONE", "<BType>");
        BType node = new BType(BType);

        if(word.getCategory().equals("INTTK")) {
            Word INTTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node INTTKNode = new Node(INTTK);
            node.Link(INTTKNode);
            getWord();
        } else if(word.getCategory().equals("CHARTK")) {
            Word CHARTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node CHARTKNode = new Node(CHARTK);
            node.Link(CHARTKNode);
            getWord();
        }
        return node;
    }

    public Node ConstDef() {
        Word ConstDef = new Word("NONE", "<ConstDef>");
        ConstDef node = new ConstDef(ConstDef);

        if(word.getCategory().equals("IDENFR")) {
            Node IDENFRNode = new Node(word);
            node.Link(IDENFRNode);
            getWord();
        }

        while(word.getCategory().equals("LBRACK")) {
            Node LBRACKNode = new Node(word);
            node.Link(LBRACKNode);
            getWord();

            node.Link(ConstExp());

            if(word.getCategory().equals("RBRACK")) {
                Word RBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RBRACKNode = new Node(RBRACK);
                node.Link(RBRACKNode);
                getWord();
            }
        }

        if(word.getCategory().equals("ASSIGN")) {
            Node ASSIGNNode = new Node(word);
            node.Link(ASSIGNNode);
            getWord();
            node.Link(ConstInitVal());
        }

        return node;
    }

    public Node ConstInitVal() {
        Word ConstInitVal = new Word("NONE", "<ConstInitVal>");
        ConstInitVal node = new ConstInitVal(ConstInitVal);

        if (word.getCategory().equals("LBRACE")) {
            Word LBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LBRACENode = new Node(LBRACE);
            node.Link(LBRACENode);
            getWord();

            if (word.getCategory().equals("RBRACE")) {
                Word RBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RBRACENode = new Node(RBRACE);
                node.Link(RBRACENode);
                getWord();
            } else {
                node.Link(ConstInitVal());

                while (word.getCategory().equals("COMMA")) {
                    Word COMMATK = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node COMMATKNode = new Node(COMMATK);
                    node.Link(COMMATKNode);
                    getWord();
                    node.Link(ConstInitVal());
                }
                if (word.getCategory().equals("RBRACE")) {
                    Word RBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RBRACENode = new Node(RBRACE);
                    node.Link(RBRACENode);
                    getWord();
                }
            }
        } else {
            node.Link(ConstExp());
        }

        return node;
    }

    public Node VarDecl(){
        Word VarDecl = new Word("NONE", "<VarDecl>");
        VarDecl node = new VarDecl(VarDecl);
        //word是int
        node.Link(BType());
        node.Link(VarDef());
        while(word.getCategory().equals("COMMA")){
            Word COMMATK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node COMMATKNode = new Node(COMMATK);
            node.Link(COMMATKNode);
            getWord();
            node.Link(VarDef());
        }
        if(word.getCategory().equals("SEMICN")){
            Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node SEMICNNode = new Node(SEMICN);
            node.Link(SEMICNNode);
            getWord();
        }
        return node;
    }

    public Node VarDef(){
        Word VarDef = new Word("NONE", "<VarDef>");
        VarDef node = new VarDef(VarDef);
        if(word.getCategory().equals("IDENFR")){
            Node IDENFRNode = new Node(word);
            node.Link(IDENFRNode);
            getWord();
        }

        while(word.getCategory().equals("LBRACK")){
            Word LBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LBRACKNode = new Node(LBRACK);
            node.Link(LBRACKNode);
            getWord();
            node.Link(ConstExp());
            if(word.getCategory().equals("RBRACK")){
                Word RBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RBRACKNode = new Node(RBRACK);
                node.Link(RBRACKNode);
                getWord();
            }
        }

        if(word.getCategory().equals("ASSIGN")){
            Word ASSIGN = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node ASSIGNNode = new Node(ASSIGN);
            node.Link(ASSIGNNode);
            getWord();
            node.Link(InitVal());
        }
        return node;
    }

    public Node InitVal(){
        Word InitVal = new Word("NONE", "<InitVal>");
        InitVal node = new InitVal(InitVal);
        if(word.getCategory().equals("LBRACE")) {
            Word LBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LBRACENode = new Node(LBRACE);
            node.Link(LBRACENode);
            getWord();

            if (word.getCategory().equals("RBRACE")) {
                Word RBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RBRACENode = new Node(RBRACE);
                node.Link(RBRACENode);
                getWord();
            } else {
                node.Link(InitVal());
                while (word.getCategory().equals("COMMA")) {
                    Word COMMATK = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node COMMATKNode = new Node(COMMATK);
                    node.Link(COMMATKNode);
                    getWord();
                    node.Link(InitVal());
                }
                if (word.getCategory().equals("RBRACE")) {
                    Word RBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RBRACENode = new Node(RBRACE);
                    node.Link(RBRACENode);
                    getWord();
                }
            }
        }else{
            if(isExp()){
                node.Link(Exp());
            }
        }

        return node;
    }

    public Node FuncDef(){
        Word FuncDef = new Word("NONE", "<FuncDef>");
        FuncDef node = new FuncDef(FuncDef);
        node.Link(FuncType());
        if(word.getCategory().equals("IDENFR")){
            Word IDENFR = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node IDENFRNode = new Node(IDENFR);
            node.Link(IDENFRNode);
            getWord();
        }
        if(word.getCategory().equals("LPARENT")){
            Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LPARENTNode = new Node(LPARENT);
            node.Link(LPARENTNode);
            getWord();
            if(isFuncFParams()){
                node.Link(FuncFParams());
            }
            if(word.getCategory().equals("RPARENT")){
                Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RPARENTNode = new Node(RPARENT);
                node.Link(RPARENTNode);
                getWord();
            }
        }
        node.Link(Block());
        return node;
    }

    public Node MainFuncDef(){
        Word MainFuncDef = new Word("NONE", "<MainFuncDef>");
        MainFuncDef node = new MainFuncDef(MainFuncDef);
        if(word.getCategory().equals("INTTK")){
            Word INTTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node INTTKNode = new Node(INTTK);
            node.Link(INTTKNode);
            getWord();
        }
        if(word.getCategory().equals("MAINTK")){
            Word MAINTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node MAINTKNode = new Node(MAINTK);
            node.Link(MAINTKNode);
            getWord();
        }
        if(word.getCategory().equals("LPARENT")){
            Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LPARENTNode = new Node(LPARENT);
            node.Link(LPARENTNode);
            getWord();
            if(word.getCategory().equals("RPARENT")){
                Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RPARENTNode = new Node(RPARENT);
                node.Link(RPARENTNode);
                getWord();
            }
        }
        node.Link(Block());
        return node;
    }

    public Node FuncType(){
        Word FuncType = new Word("NONE", "<FuncType>");
        FuncType node = new FuncType(FuncType);
        if(word.getCategory().equals("INTTK")){
            Word INTTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node INTTKNode = new Node(INTTK);
            node.Link(INTTKNode);
            getWord();
        }else if(word.getCategory().equals("VOIDTK")){
            Word VOIDTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node VOIDTKNode = new Node(VOIDTK);
            node.Link(VOIDTKNode);
            getWord();
        }
        return node;
    }

    public Node FuncFParams(){
        Word FuncFParams = new Word("NONE", "<FuncFParams>");
        FuncFParams node = new FuncFParams(FuncFParams);
        node.Link(FuncFParam());
        while(word.getCategory().equals("COMMA")){
            Word COMMATK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node COMMATKNode = new Node(COMMATK);
            node.Link(COMMATKNode);
            getWord();
            node.Link(FuncFParam());
        }
        return node;
    }

    public Node FuncFParam(){
        Word FuncFParam = new Word("NONE", "<FuncFParam>");
        FuncFParam node = new FuncFParam(FuncFParam);
        node.Link(BType());
        if(word.getCategory().equals("IDENFR")){
            Word IDENFR = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node IDENFRNode = new Node(IDENFR);
            node.Link(IDENFRNode);
            getWord();

            if(word.getCategory().equals("LBRACK")){
                Word LBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node LBRACKNode = new Node(LBRACK);
                node.Link(LBRACKNode);
                getWord();
                if(word.getCategory().equals("RBRACK")){
                    Word RBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RBRACKNode = new Node(RBRACK);
                    node.Link(RBRACKNode);
                    getWord();
                }

                while(word.getCategory().equals("LBRACK")){
                    Word LBRACK_1 = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node LBRACKNode_1 = new Node(LBRACK_1);
                    node.Link(LBRACKNode_1);
                    getWord();
                    node.Link(ConstExp());
                    if(word.getCategory().equals("RBRACK")){
                        Word RBRACK_1 = new Word(word.getCategory(), word.getValue(), word.getLine());
                        Node RBRACKNode_1 = new Node(RBRACK_1);
                        node.Link(RBRACKNode_1);
                        getWord();
                    }
                }
            }
        }
        return node;
    }

    public Node Block(){
        Word Block = new Word("NONE", "<Block>");
        Block node = new Block(Block);
        if(word.getCategory().equals("LBRACE")) {
            Word LBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LBRACENode = new Node(LBRACE);
            node.Link(LBRACENode);
            getWord();

            while (isBlockItem()) {
                node.Link(BlockItem());
            }

            if (word.getCategory().equals("RBRACE")) {
                Word RBRACE = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RBRACENode = new Node(RBRACE);
                node.Link(RBRACENode);
                getWord();
            }
        }

        return node;
    }

    public Node BlockItem(){
        Word BlockItem = new Word("NONE", "<BlockItem>");
        BlockItem node = new BlockItem(BlockItem);
        if(isDecl()){
            node.Link(Decl());
        } else {
            node.Link(Stmt());
        }
        return node;
    }


    public Node Stmt(){
        Word Stmt = new Word("NONE", "<Stmt>");
        Stmt node = new Stmt(Stmt);

        boolean flag_Assign = false;
        boolean flag_getint = false;
        int pos = index - 1; //pos记录当前位置

        LVal(); //提前读取，判断是LVal '=' Exp ';'还是LVal '=' 'getint' '(' ')' ';'中的哪一种
        if(word.getCategory().equals("ASSIGN")){
            flag_Assign = true;
            getWord();
            if(word.getCategory().equals("GETINTTK")){
                flag_getint = true;
            }
        }
        index = pos; //回溯
        getWord();

        //对于word,如果LVal '=' Exp ';'，则word为''，如果LVal '=' 'getint' '(' ')' ';'，则word为'('
        if(flag_Assign) {
            if(flag_getint) {
                node.Link(LVal());
                if(word.getCategory().equals("ASSIGN")){
                    Word ASSIGN = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node ASSIGNNode = new Node(ASSIGN);
                    node.Link(ASSIGNNode);
                    getWord();
                    if(word.getCategory().equals("GETINTTK")){
                        Word GETINTTK = new Word(word.getCategory(), word.getValue(), word.getLine());
                        Node GETINTTKNode = new Node(GETINTTK);
                        node.Link(GETINTTKNode);
                        getWord();
                        if(word.getCategory().equals("LPARENT")){
                            Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                            Node LPARENTNode = new Node(LPARENT);
                            node.Link(LPARENTNode);
                            getWord();
                            if(word.getCategory().equals("RPARENT")){
                                Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                                Node RPARENTNode = new Node(RPARENT);
                                node.Link(RPARENTNode);
                                getWord();
                                if(word.getCategory().equals("SEMICN")){
                                    Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                                    Node SEMICNNode = new Node(SEMICN);
                                    node.Link(SEMICNNode);
                                    getWord();
                                }
                            }
                        }
                    }
                }
            } else {
                node.Link(LVal());
                if(word.getCategory().equals("ASSIGN")){
                    Word ASSIGN = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node ASSIGNNode = new Node(ASSIGN);
                    node.Link(ASSIGNNode);
                    getWord();
                    if(isExp()){
                        node.Link(Exp());
                    }
                    if(word.getCategory().equals("SEMICN")){
                        Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                        Node SEMICNNode = new Node(SEMICN);
                        node.Link(SEMICNNode);
                        getWord();
                    }
                }
            }
        } else if(isExp()) {
            node.Link(Exp());
            if(word.getCategory().equals("SEMICN")){
                Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node SEMICNNode = new Node(SEMICN);
                node.Link(SEMICNNode);
                getWord();
            }
        } else if(word.getCategory().equals("IFTK")){
            Word IFTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node IFTKNode = new Node(IFTK);
            node.Link(IFTKNode);
            getWord();
            if(word.getCategory().equals("LPARENT")){
                Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node LPARENTNode = new Node(LPARENT);
                node.Link(LPARENTNode);
                getWord();
                node.Link(Cond());
                if(word.getCategory().equals("RPARENT")){
                    Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RPARENTNode = new Node(RPARENT);
                    node.Link(RPARENTNode);
                    getWord();
                    node.Link(Stmt());
                    if(word.getCategory().equals("ELSETK")){
                        Word ELSETK = new Word(word.getCategory(), word.getValue(), word.getLine());
                        Node ELSETKNode = new Node(ELSETK);
                        node.Link(ELSETKNode);
                        getWord();
                        node.Link(Stmt());
                    }
                }
            }
        } else if(word.getCategory().equals("WHILETK")){
            Word WHILETK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node WHILETKNode = new Node(WHILETK);
            node.Link(WHILETKNode);
            getWord();
            if(word.getCategory().equals("LPARENT")){
                Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node LPARENTNode = new Node(LPARENT);
                node.Link(LPARENTNode);
                getWord();
                node.Link(Cond());
                if(word.getCategory().equals("RPARENT")){
                    Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RPARENTNode = new Node(RPARENT);
                    node.Link(RPARENTNode);
                    getWord();
                    node.Link(Stmt());
                }
            }
        } else if(word.getCategory().equals("BREAKTK")){
            Word BREAKTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node BREAKTKNode = new Node(BREAKTK);
            node.Link(BREAKTKNode);
            getWord();
            if(word.getCategory().equals("SEMICN")){
                Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node SEMICNNode = new Node(SEMICN);
                node.Link(SEMICNNode);
                getWord();
            }
        } else if(word.getCategory().equals("CONTINUETK")){
            Word CONTINUETK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node CONTINUETKNode = new Node(CONTINUETK);
            node.Link(CONTINUETKNode);
            getWord();
            if(word.getCategory().equals("SEMICN")){
                Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node SEMICNNode = new Node(SEMICN);
                node.Link(SEMICNNode);
                getWord();
            }
        } else if(word.getCategory().equals("RETURNTK")){
            Word RETURNTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node RETURNTKNode = new Node(RETURNTK);
            node.Link(RETURNTKNode);
            getWord();
            if(isExp()){
                node.Link(Exp());
            }
            if(word.getCategory().equals("SEMICN")){
                Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node SEMICNNode = new Node(SEMICN);
                node.Link(SEMICNNode);
                getWord();
            }
        } else if(word.getCategory().equals("SEMICN")){
            Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node SEMICNNode = new Node(SEMICN);
            node.Link(SEMICNNode);
            getWord();
        } else if(word.getCategory().equals("PRINTFTK")) {
            Word PRINTFTK = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node PRINTFTKNode = new Node(PRINTFTK);
            node.Link(PRINTFTKNode);
            getWord();
            if (word.getCategory().equals("LPARENT")) {
                Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node LPARENTNode = new Node(LPARENT);
                node.Link(LPARENTNode);
                getWord();
                if (word.getCategory().equals("STRCON")) {
                    Word STRCON = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node STRCONNode = new Node(STRCON);
                    node.Link(STRCONNode);
                    getWord();
                    while (word.getCategory().equals("COMMA")) {
                        Word COMMA = new Word(word.getCategory(), word.getValue(), word.getLine());
                        Node COMMANode = new Node(COMMA);
                        node.Link(COMMANode);
                        getWord();
                        node.Link(Exp());
                    }
                    if (word.getCategory().equals("RPARENT")) {
                        Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                        Node RPARENTNode = new Node(RPARENT);
                        node.Link(RPARENTNode);
                        getWord();
                        if (word.getCategory().equals("SEMICN")) {
                            Word SEMICN = new Word(word.getCategory(), word.getValue(), word.getLine());
                            Node SEMICNNode = new Node(SEMICN);
                            node.Link(SEMICNNode);
                            getWord();
                        }
                    }
                }
            }
        } else if(word.getCategory().equals("LBRACE")) {
            node.Link(Block());
        }
        return node;
    }

    public Node Exp(){
        Word Exp = new Word("NONE", "<Exp>");
        Exp node = new Exp(Exp);
        node.Link(AddExp());
        return node;
    }

    public Node Cond(){
        Word Cond = new Word("NONE", "<Cond>");
        Cond node = new Cond(Cond);
        node.Link(LOrExp());
        return node;
    }

    public Node LVal(){
        Word LVal = new Word("NONE", "<LVal>");
        LVal node = new LVal(LVal);
        if(word.getCategory().equals("IDENFR")){
            Word IDENFR = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node IDENFRNode = new Node(IDENFR);
            node.Link(IDENFRNode);
            getWord();
            while(word.getCategory().equals("LBRACK")){
                Word LBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node LBRACKNode = new Node(LBRACK);
                node.Link(LBRACKNode);
                getWord();
                node.Link(Exp());
                if(word.getCategory().equals("RBRACK")){
                    Word RBRACK = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RBRACKNode = new Node(RBRACK);
                    node.Link(RBRACKNode);
                    getWord();
                }
            }
        }
        return node;
    }

    public Node PrimaryExp(){
        Word PrimaryExp = new Word("NONE", "<PrimaryExp>");
        PrimaryExp node = new PrimaryExp(PrimaryExp);
        if(word.getCategory().equals("LPARENT")){
            Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LPARENTNode = new Node(LPARENT);
            node.Link(LPARENTNode);
            getWord();
            node.Link(Exp());
            if(word.getCategory().equals("RPARENT")){
                Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node RPARENTNode = new Node(RPARENT);
                node.Link(RPARENTNode);
                getWord();
            }
        } else if(word.getCategory().equals("IDENFR")){
            node.Link(LVal());
        } else if(word.getCategory().equals("INTCON")) {
            node.Link(Number());
        }
        return node;
    }

    public Node Number(){
        Word Number = new Word("NONE", "<Number>");
        Number node = new Number(Number);
        if(word.getCategory().equals("INTCON")){
            Word INTCON = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node INTCONNode = new Node(INTCON);
            node.Link(INTCONNode);
            getWord();
        }
        return node;
    }

    public Node UnaryExp(){
        Word UnaryExp = new Word("NONE", "<UnaryExp>");
        UnaryExp node = new UnaryExp(UnaryExp);
        if(isFuncCall()) {
            Word IDENFR = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node IDENFRNode = new Node(IDENFR);
            node.Link(IDENFRNode);
            getWord();
            if(word.getCategory().equals("LPARENT")){
                Word LPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                Node LPARENTNode = new Node(LPARENT);
                node.Link(LPARENTNode);
                getWord();
                if(isExp()){
                    node.Link(FuncRParams());
                }
                if(word.getCategory().equals("RPARENT")){
                    Word RPARENT = new Word(word.getCategory(), word.getValue(), word.getLine());
                    Node RPARENTNode = new Node(RPARENT);
                    node.Link(RPARENTNode);
                    getWord();
                }
            }
        } else if(isPrimaryExp()) {
            node.Link(PrimaryExp());
        } else if(isUnaryOp()){
            node.Link(UnaryOp());
            node.Link(UnaryExp());
        }
        return node;
    }

    public Node UnaryOp(){
        Word UnaryOp = new Word("NONE", "<UnaryOp>");
        UnaryOp node = new UnaryOp(UnaryOp);
        if(isUnaryOp()){
            Word PLUS = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node PLUSNode = new Node(PLUS);
            node.Link(PLUSNode);
            getWord();
        }
        return node;
    }

    public Node FuncRParams(){
        Word FuncRParams = new Word("NONE", "<FuncRParams>");
        FuncRParams node = new FuncRParams(FuncRParams);
        node.Link(Exp());
        while(word.getCategory().equals("COMMA")){
            Word COMMA = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node COMMANode = new Node(COMMA);
            node.Link(COMMANode);
            getWord();
            node.Link(Exp());
        }
        return node;
    }

    public Node MulExp(){
        Word MulExp = new Word("NONE", "<MulExp>");
        MulExp node = new MulExp(MulExp);
        node.Link(UnaryExp());
        while(isMulOp()){
            Node insert = new Node(MulExp);
            node.insertNode(insert);
            Word MUL = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node MULNode = new Node(MUL);
            node.Link(MULNode);
            getWord();
            node.Link(UnaryExp());
        }
        return node;
    }

    public Node AddExp(){
        Word AddExp = new Word("NONE", "<AddExp>");
        AddExp node = new AddExp(AddExp);
        node.Link(MulExp());
        while(isAddOp()){
            Node insert = new Node(AddExp);
            node.insertNode(insert);
            Word ADD = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node ADDNode = new Node(ADD);
            node.Link(ADDNode);
            getWord();
            node.Link(MulExp());
        }
        return node;
    }

    public Node RelExp(){
        Word RelExp = new Word("NONE", "<RelExp>");
        RelExp node = new RelExp(RelExp);
        node.Link(AddExp());
        while(isRelOp()){
            Node insert = new Node(RelExp);
            node.insertNode(insert);
            Word LSS = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node LSSNode = new Node(LSS);
            node.Link(LSSNode);
            getWord();
            node.Link(AddExp());
        }
        return node;
    }

    public Node EqExp(){
        Word EqExp = new Word("NONE", "<EqExp>");
        EqExp node = new EqExp(EqExp);
        node.Link(RelExp());
        while(isEqOp()){
            Node insert = new Node(EqExp);
            node.insertNode(insert);
            Word EQL = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node EQLNode = new Node(EQL);
            node.Link(EQLNode);
            getWord();
            node.Link(RelExp());
        }
        return node;
    }

    public Node LAndExp(){
        Word LAndExp = new Word("NONE", "<LAndExp>");
        LAndExp node = new LAndExp(LAndExp);
        node.Link(EqExp());
        while(word.getCategory().equals("AND")){
            Node insert = new Node(LAndExp);
            node.insertNode(insert);
            Word AND = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node ANDNode = new Node(AND);
            node.Link(ANDNode);
            getWord();
            node.Link(EqExp());
        }
        return node;
    }

    public Node LOrExp(){
        Word LOrExp = new Word("NONE", "<LOrExp>");
        LOrExp node = new LOrExp(LOrExp);
        node.Link(LAndExp());
        while(word.getCategory().equals("OR")){
            Node insert = new Node(LOrExp);
            node.insertNode(insert);
            Word OR = new Word(word.getCategory(), word.getValue(), word.getLine());
            Node ORNode = new Node(OR);
            node.Link(ORNode);
            getWord();
            node.Link(LAndExp());
        }
        return node;
    }

    public Node ConstExp(){
        Word ConstExp = new Word("NONE", "<ConstExp>");
        ConstExp node = new ConstExp(ConstExp);
        node.Link(AddExp());
        return node;
    }

    public Node getRoot(){
        return root;
    }

    //遍历语法树,由根节点root节点开始遍历,子节点为ArrayList<Node> childs
    public void traverseRoot(Node node, BufferedWriter out){
        ArrayList<String> list = new ArrayList<String>();
        list.add("<BlockItem>");
        list.add("<Decl>");
        list.add("<BType>");
        try {
            if(node.getChilds().size() == 0){
                if(node.getWord().getCategory().equals("NONE")){
                    out.write(node.getWord().getValue()+"\n");
                } else {
                    out.write(node.getWord().getCategory() + " " + node.getWord().getValue() + "\n");
                }
                return;
            }
            for(Node child : node.getChilds()){
                traverseRoot(child,out);
            }
            if(!list.contains(node.getWord().getValue())){
                out.write(node.getWord().getValue()+"\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
