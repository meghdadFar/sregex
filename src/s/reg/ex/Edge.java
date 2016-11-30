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
 * Implements edge (link) of automaton.
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 */
class Edge {
    
    private char label;
    private State source;
    private State target;
    
    /**
     * Constructor.
     * 
     * @param source
     * @param target
     * 
     */
    Edge(State source, State target, char label){
        this.source = source;
        this.target = target;
        this.label = label;
    }
    

    /**
     * Return the source of this edge.
     */
    public State getSource() {
        return source;
    }


    /**
     * Return the target of this edge.
     */
    public State getTarget() {
        return target;
    }
    
    /**
     * Return the label.
     */
    public char getLabel() {
        return label;
    }
    
}