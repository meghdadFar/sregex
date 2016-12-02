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

/**
 *
 * Implements a nondeterministic finite automaton (NFA).
 *
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * 
*/
class NFA {

    private State initialState;
    private State currentState;

    /**
     * Constructor.
     *
     * Creates an empty NFA by instantiating a start state.
     */
    public NFA() {

        initialState = new State();
        currentState = initialState;
    }

    /**
     * Returns initialState.
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * Returns currentState.
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Removes the initial state. Used when connecting a capturing group to the
     * rest of the automaton.
     */
    public void removeInitial() {
        initialState = null;
    }

    /**
     * Add the non-operator character x to NFA.
     *
     * @param x must be an alphabetic char. It is a lowercase char with respect
     * to the problem definition. However it can also be an uppercase char as
     * well except "E".
     */
    public void concat(char x) {

        State nextState = new State();
        Edge e = new Edge(currentState, nextState, x);

        currentState.addOutLink(e);
        nextState.addInLink(e);

        nextState.makeAccept();
        currentState.notAccept();

        currentState = nextState;

    }

    /**
     * Create an alternation branch.
     *
     * This method is used when the input is |, in order to create an
     * alternation branch on the NFA.
     *
     * @param bcb State at the beginning of the current brach
     * @return the beginning of the new branch.
     *
     */
    public State alternation(State bcb) {
        State s = new State();
        Edge epsilon = new Edge(bcb, s, 'E');
        bcb.addOutLink(epsilon);
        s.addInLink(epsilon);
        currentState = s;
        return s;
    }

    /**
     * Creates a (sub) NFA for kleene star.
     *
     * Unlike concatenation and |, this method leaves the currentState as is. It
     * only adds new links to the current state
     *
     * @param x the character to be transformed by *.
     */
    public void kleeneStar(char x) {

        //Simplified version of kleene star:
        //move backward one state (through x)
        currentState = currentState.sourceOf(x);
        currentState.makeAccept();

        //go forward (through x). The new currentState has already set as
        //an accept state through concat().
        currentState = currentState.transition(x);

        Edge e = new Edge(currentState, currentState, x);
        currentState.addInLink(e);
        currentState.addOutLink(e);

        /*
         Thompson's construction model of kleene star.
         The three intermediating states and five edges that are required for 
         a kleene star expression
         */
//        State one = new State();
//        State two = new State();
//        State three = new State();
//        
//        three.makeAccept();
//        
//
//        Edge epsilon1 = new Edge(currentState,three,'E');
//        Edge epsilon2 = new Edge(currentState,one,'E');
//        Edge epsilon3 = new Edge(two,three,'E');
//        Edge epsilon4 = new Edge(two,one,'E');
//        Edge e = new Edge(one,two,x);
//        
//        //Update the inLinks and outLinks attributes with the added edges
//        currentState.addOutLink(epsilon1);
//        currentState.addOutLink(epsilon2);
//        
//        one.addInLink(epsilon2);
//        one.addInLink(epsilon4);
//        one.addOutLink(e);
//        
//        two.addOutLink(epsilon3);
//        two.addOutLink(epsilon4);
//        two.addInLink(e);
//        
//        three.addInLink(epsilon3);
//        three.addInLink(epsilon1);
    }

    /**
     * Overloaded kleeneStar for groups.
     *
     * @param x
     */
    public void kleeneStar(String s) {
        //TODO implement kleene star for capturing groups. 
    }

}
