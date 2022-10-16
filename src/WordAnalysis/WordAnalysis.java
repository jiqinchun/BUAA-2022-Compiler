package WordAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

public class WordAnalysis {
    private RandomAccessFile raf;
    private int sym;
    private char ch;
    private int EOF = -1;
    private int line = 1;
    private String token;
    private HashMap<String, String> ReservedWords = new HashMap<>();
    private ArrayList<Word> words = new ArrayList<>();

    public WordAnalysis(File infile) {
        try {
            this.raf = new RandomAccessFile(infile, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ReservedWords.put("main","MAINTK");
        ReservedWords.put("const","CONSTTK");
        ReservedWords.put("int","INTTK");
        ReservedWords.put("char","CHARTK");
        ReservedWords.put("break","BREAKTK");
        ReservedWords.put("continue","CONTINUETK");
        ReservedWords.put("if","IFTK");
        ReservedWords.put("else","ELSETK");
        ReservedWords.put("while","WHILETK");
        ReservedWords.put("getint","GETINTTK");
        ReservedWords.put("printf","PRINTFTK");
        ReservedWords.put("return","RETURNTK");
        ReservedWords.put("void","VOIDTK");
    }

    public ArrayList<Word> getWords() {
        Word word;
        while(true) {
            word = getWord();
//            if(word == null) {
//                continue;
//            }
            if(word.getCategory().equals("EOF")) {
                break;
            }
            if(!word.getCategory().equals("COMMENT")) {
                words.add(word);
            }
        }
        return words;
    }

    public Word getWord(){
        this.token = "";
        Word word = null;
        jumpBlank();
        if(Character.isLetter(ch) || ch == '_'){
            while((Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') && sym != EOF){
                token += ch;
                getChar();
            }
            if(ch != EOF){
                retract();
            }
            if(ReservedWords.containsKey(token)){
                word = new Word(ReservedWords.get(token), token, line);
            }else{
                word = new Word("IDENFR", token, line);
            }
        }
        else if(Character.isDigit(ch)){
            while(Character.isDigit(ch) && sym != EOF){
                token += ch;
                getChar();
            }
            if(sym != EOF){
                retract();
            }
            word = new Word("INTCON", token, line);
        }
        else if(ch == '"'){
            do {
                token += ch;
                getChar();
            } while (ch != '"');
            token += ch;
            word = new Word("STRCON", token, line);
        }
        else if(ch == '+'){
            token += ch;
            word = new Word("PLUS", token, line);
        }
        else if(ch == '-'){
            token += ch;
            word = new Word("MINU", token, line);
        }
        else if(ch == '*'){
            token += ch;
            word = new Word("MULT", token, line);
        }
        else if(ch == '/'){
            token += ch;
            getChar();
            if(ch == '*'){
                while(true){
                    do {
                        token += ch;
                        getChar();
                    } while (ch != '*');
                    char temp = ch;
                    getChar();
                    if(ch == '/'){
                        token += temp;
                        token += ch;
                        word = new Word("COMMENT", token, line);
                        break;
                    }
                    else{
                        retract();
                        ch = temp;
                    }
                }
            }
            else if(ch == '/'){
                do {
                    token += ch;
                    getChar();
                    addLine();
                } while (ch != '\n' && sym != EOF);
                word = new Word("COMMENT", token, line);
            }
            else{
                retract();
                word = new Word("DIV", token, line);
            }
        }
        else if(ch == '%'){
            token += ch;
            word = new Word("MOD",token, line);
        }
        else if(ch == '<'){
            token += ch;
            getChar();
            if(ch == '='){
                token += ch;
                word = new Word("LEQ", token, line);
            }else if(ch == '>'){
                token += ch;
                word = new Word("NEQ", token, line);
            }else{
                retract();
                word = new Word("LSS", token, line);
            }
        }
        else if(ch == '>'){
            token += ch;
            getChar();
            if(ch == '='){
                token += ch;
                word = new Word("GEQ", token, line);
            }else{
                retract();
                word = new Word("GRE", token, line);
            }
        }
        else if(ch == '='){
            token += ch;
            getChar();
            if(ch == '=') {
                token += ch;
                word = new Word("EQL", token, line);
            }
            else{
                retract();
                word = new Word("ASSIGN", token, line);
            }
        }
        else if(ch == ';'){
            token += ch;
            word = new Word("SEMICN", token, line);
        }
        else if(ch == ','){
            token += ch;
            word = new Word("COMMA", token, line);
        }
        else if(ch == '('){
            token += ch;
            word = new Word("LPARENT", token, line);
        }
        else if(ch == ')'){
            token += ch;
            word = new Word("RPARENT", token, line);
        }
        else if(ch == '['){
            token += ch;
            word = new Word("LBRACK", token, line);
        }
        else if(ch == ']'){
            token += ch;
            word = new Word("RBRACK", token, line);
        }
        else if(ch == '{'){
            token += ch;
            word = new Word("LBRACE", token, line);
        }
        else if(ch == '}'){
            token += ch;
            word = new Word("RBRACE", token, line);
        }
        else if(ch == '&'){
            token += ch;
            getChar();
            if(ch == '&'){
                token += ch;
                word = new Word("AND", token, line);
            }
        }
        else if(ch == '|'){
            token += ch;
            getChar();
            if(ch == '|'){
                token += ch;
                word = new Word("OR", token, line);
            }
        }
        else if(ch == '!'){
            token += ch;
            getChar();
            if(ch == '='){
                token += ch;
                word = new Word("NEQ", token, line);
            }
            else {
                retract();
                word = new Word("NOT", token, line);
            }
        }
        else if(sym == EOF){
            word = new Word("EOF", "-1", line);
        }
        return word;
    }

    public void jumpBlank(){
        do {
            getChar();
            addLine();
        } while(sym == ' ' || sym == '\t' || sym == '\n' || sym == '\r');
    }


    public void getChar() {
        try {
            sym = raf.read();
            ch = (char) sym;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLine() {
        if(sym == '\n'){
            this.line++;
        }
    }

    public void retract() {
        try {
            raf.seek(raf.getFilePointer() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}