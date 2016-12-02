/* 
 * Copyright (C) 2016 Meghdad Farahmand<meghdad.farahmand@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package s.reg.ex;

import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * Simplified regular expressions (Sregex).
 * 
 * Supports the following operators:
 * lowercase English letters (a, b, c, ...)
 * |: for alternative expressions
 * *: for matching matches zero of more matches of the preceding expression
 * (): for grouping expressions
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 5.10.2016
 */


public final class Sregex {
    
/**
 * Searches NFA for a particular character. 
 * This method was created for dealing with epsilon edges.
 * It searches the outLinks of the input state, or recursively searches the 
 * outLinks of the states that are reachable through epsilon edges from 
 * the input state. 
 * 
 * @param s current state
 * @param x character to search for
 * @return  either null if it does not find a match or the state that has an outLink that
 * matches the input character.
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 */

    public static State searchNFA(State s,char x){
                
        //return if outLinks contain x
        if(s.containsOutLink(x)){
            return s;
        }
        //else if outLinks doesnt contain x:
        for(Edge e : s.getOutLinks()){
            if(e.getLabel() == 'E'){
                return searchNFA(e.getTarget(),x);
            }
        }
        return null;
    }

    
     /**
     * Reads a regular expression as input and transforms it into an NFA.
     * 
     * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
     * 
     * @param sRegex regular expression
     * @return nondeterministic finite automaton (NFA)
     */
    
    public static NFA parse(String sRegex) {

        NFA nfa = new NFA();
        /*
        bcb: begining of the current branch is used for handling possible future 
        alternation branches. 
        */
        State bcb = nfa.getInitialState();
        char buffer = 0; //keeps the previous character for kleene star

        int i = 0;
        Stack<Character> bracketStack = new Stack();

        while (i < sRegex.length()) {

            /*
             If arrive at a left bracket '(', then push it to the bracketStack.
             Read characters until the corresponding right bracket.
             Ignore inner brackets using stack. 
             */
            if (sRegex.charAt(i) == '(') {
                bracketStack.push('(');
                StringBuilder inBracketSubExp = new StringBuilder();
                int j = i + 1;
                while (true) {
                    if (sRegex.charAt(j) == ')') {
                        bracketStack.pop();
                        if (bracketStack.empty()) {
                            break;
                        }
                    } else if (sRegex.charAt(j) == '(') {
                        bracketStack.push('(');
                    }
                    if (!bracketStack.empty()) {
                        inBracketSubExp.append(sRegex.charAt(j));
                    }
                    j++;
                }
                //System.out.println("Group captured: " + inBracketSubExp);
                
                //recursive call on the captured group
                NFA subNFA = parse(inBracketSubExp.toString());
                
                //connect currentState to the begining of inBracketSubExp 
                for(Edge e : subNFA.getInitialState().getOutLinks()){
                    nfa.getCurrentState().addOutLink(e);
                }
                subNFA.removeInitial();
                i = j;
            }

            /*
             If a grouping has not been identified, recursive call is not required. 
             Therefore the automaton can be built.
             First ensure that the character satisfies the condition of being a-z
             *, or |
             */
            if (Character.isAlphabetic(sRegex.charAt(i)) && Character.isLowerCase(sRegex.charAt(i))) {
                
                buffer = sRegex.charAt(i);
                nfa.concat(sRegex.charAt(i));
                
            } else if (sRegex.charAt(i) == '*') {
                
                //make previous character/group wild card
                nfa.kleeneStar(buffer);                
            } else if (sRegex.charAt(i) == '|') {//begining of an alternative branch

                bcb = nfa.alternation(bcb);
                
            }
            i++;
        }
        //System.out.println("NFA Construction completed.");
        return nfa;
    }

    /**
     * Validates whether the input string matches the input regular expression, 
     * i.e., whether it is in the language of the input regex.
     * 
     * @param s input string
     * @param regex 
     * @return 
     */
    
    public static boolean matches(String s,String regex) {

        boolean mtch = false;
        NFA testNfa = parse(regex);
        State currentState = testNfa.getInitialState();

        if (currentState.isAccept()) {
            return true;
        }
        
        char[] elements = s.toCharArray();
        
        State tmpState = new State();
        characters:
        for (char e : elements) {
            
            tmpState = searchNFA(currentState,e);
            if(tmpState == null){
                    return false;
            }
            currentState = tmpState.transition(e);
            
            if (currentState.isAccept()){
                mtch = true;
                continue characters;
            }
            mtch = false;
        }
        return mtch;        
    }

    
    public static void main(String[] args) {

        List<Test> testCases = new ArrayList<>();
        
        //Fields of each testCase of type Test : 
        //the regex, a matching correct and a non-matching string respectively
        
        //concatenation
        testCases.add(new Test("abc","abc","abcm"));
        
        //alternation
        testCases.add(new Test("abc|ihg|mno|uts","mno","mnou"));
        
        //kleene star
        testCases.add(new Test("abc*","abcccc","abd"));
        testCases.add(new Test("abc*","ab","a"));
        
        //kleene star and alternation
        testCases.add(new Test("abc|ihg|mno|uts*","utsssss","ih"));
        
        testCases.add(new Test("abcd*|lmn*","abcdddd","lmno"));
        
        //capturing group
        testCases.add(new Test("abc(d|e)","abce","jkl"));
        testCases.add(new Test("(((a)))","a","b"));
        testCases.add(new Test("abc(d|e*)","abceeee","ab"));

        
        for(Test t : testCases){
            System.out.println("\""+t.inputTrue+"\"\t matches:\t\""+ t.regex+"\"\t"+matches(t.inputTrue,t.regex));
            System.out.println("\""+t.inputFalse+"\"\t matches:\t\""+ t.regex+"\"\t"+matches(t.inputFalse,t.regex));
            System.out.println("---------------------------------------------------------");
        } 
        
    }
    
    
    
    
}
