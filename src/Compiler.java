import GrammarAnalysis.GrammarAnalysis;
import Node.Node;
import WordAnalysis.WordAnalysis;
import WordAnalysis.Word;

import java.io.*;
import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) {
        // 读入文件
        File infile = new File("testfile.txt");
        File outfile = new File("output.txt");

        // 词法分析
        WordAnalysis wordAnalysis = new WordAnalysis(infile);
        ArrayList<Word> words = wordAnalysis.getWords();

        // 语法分析
        GrammarAnalysis grammarAnalysis = new GrammarAnalysis(words,outfile);
        grammarAnalysis.recursionAnalysis();
        Node root = grammarAnalysis.getRoot();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
            grammarAnalysis.traverseRoot(root,out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
