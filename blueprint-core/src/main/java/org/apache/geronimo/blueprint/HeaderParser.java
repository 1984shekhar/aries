/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.geronimo.blueprint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: javadoc
 *
 * @author <a href="mailto:dev@geronimo.apache.org">Apache Geronimo Project</a>
 * @version $Rev$, $Date$
 */
public class HeaderParser  {
   
    public static List<PathElement> parseHeader(String header) {
        List<PathElement> elements = new ArrayList<PathElement>();
        String[] clauses = header.split(",");
        for (String clause : clauses) {
            String[] tokens = clause.split(";");
            if (tokens.length < 1) {
                throw new RuntimeException("Invalid header clause: " + clause);
            }
            PathElement elem = new PathElement(tokens[0].trim());
            elements.add(elem);
            for (int i = 1; i < tokens.length; i++) {
                int pos = tokens[i].indexOf('=');
                if (pos != -1) {
                    if (pos > 0 && tokens[i].charAt(pos - 1) == ':') {
                        String name = tokens[i].substring(0, pos - 1).trim();
                        String value = tokens[i].substring(pos + 1).trim();
                        elem.addDirective(name, value);
                    } else {
                        String name = tokens[i].substring(0, pos).trim();
                        String value = tokens[i].substring(pos + 1).trim();
                        elem.addAttribute(name, value);
                    }
                } else {
                    elem = new PathElement(tokens[i].trim());
                    elements.add(elem);
                }
            }
        }
        return elements;
    }

    public static class PathElement {
        
        private String path;
        private Map<String, String> attributes;
        private Map<String, String> directives;
        
        public PathElement(String path) {
            this.path = path;
            this.attributes = new HashMap<String, String>();
            this.directives = new HashMap<String, String>();
        }
        
        public String getName() {
            return this.path;
        }
        
        public Map<String, String> getAttributes() {
            return attributes;
        }
        
        public String getAttribute(String name) {
            return attributes.get(name);
        }
        
        public void addAttribute(String name, String value) {
            attributes.put(name, value);
        }
        
        public Map<String, String> getDirectives() {
            return directives;
        }
        
        public String getDirective(String name) {
            return directives.get(name);
        }
        
        public void addDirective(String name, String value) {
            directives.put(name, value);
        }        
        
    }
}
