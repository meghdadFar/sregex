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
 * Simple un-encapsulated class to incorporate my test cases.
 *
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 */
public class Test {

    String regex = "";
    String inputTrue = "";
    String inputFalse = "";

    public Test(String regex, String inputTrue, String inputFalse) {

        this.regex = regex;
        this.inputTrue = inputTrue;
        this.inputFalse = inputFalse;

    }

}
