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
import java.util.Iterator;
import java.util.List;

/**
 * Implements the state of automata.
 *
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 */
class State {

    private boolean acceptState = false; //whether or not this is an accept state
    private List<Edge> outLinks = new ArrayList<>();
    private List<Edge> inLinks = new ArrayList<>();

    /**
     * Validates whether the this state is an accept state.
     * 
     * @return the isAccept
     */
    public boolean isAccept() {
        return acceptState;
    }

    /**
     * Set this as an accept state.
     */
    public void makeAccept() {
        this.acceptState = true;
    }

    /**
     * Set as a non-accept state.
     */
    public void notAccept() {
        this.acceptState = false;
    }

    /**
     * Returns the list of this state's outgoing links (edges).
     * @return the outLinks
     */
    public List<Edge> getOutLinks() {
        return outLinks;
    }

    /**
     * Add a link (edge) to the list of outgoing links (edges).
     * @param e the outgoing edge to be added.
     */
    public void addOutLink(Edge e) {
        this.outLinks.add(e);
    }

    /**
     * Returns the list of this state's incoming links (edges).
     * 
     * @return the inLinks
     */
    public List<Edge> getInLinks() {
        return inLinks;
    }

    /**
     * Add a link (edge) to the list of incoming links.
     * @param e the incoming edge to be added.
     */
    public void addInLink(Edge e) {
        this.inLinks.add(e);
    }

    /**
     * Checks whether this state contains an outgoing link (edge) labeled x.
     *
     * @param x edge to be checked. 
     * @return 
     */
    public boolean containsOutLink(char x) {
        for (Edge e : outLinks) {
            if (e.getLabel() == x) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes a transition from the current state to the next state through input
     * character x (edge label), and returns the new state.
     *
     * @param x the edge label through which the transition will be made
     * @return new state
     */
    public State transition(char x) {

        State ret = new State();

        for (Edge e : outLinks) {
            if (e.getLabel() == x) {
                ret = e.getTarget();
            }
        }
        return ret;
    }

    /**
     * Returns the source of the edge whose label is passed as an argument.
     *
     * @param x edge label
     * @return source of the edge x that is an inLink for the currentState
     */
    public State sourceOf(char x) {

        State ret = new State();

        for (Edge e : inLinks) {
            if (e.getLabel() == x) {
                ret = e.getSource();
            }
        }
        return ret;
    }

    /**
     * Removes outLink.
     *
     * @param x edge label whose corresponding edge will be removed.
     *
     */
    public void removeOutLink(char x) {

        Iterator<Edge> itr = outLinks.iterator();
        while (itr.hasNext()) {
            Edge e = itr.next();
            if (e.getLabel() == x) {
                itr.remove();
            }
        }

    }

}
