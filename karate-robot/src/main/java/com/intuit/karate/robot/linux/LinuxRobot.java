/*
 * The MIT License
 *
 * Copyright 2020 Intuit Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate.robot.linux;

import com.intuit.karate.StringUtils;
import com.intuit.karate.core.Plugin;
import com.intuit.karate.core.ScenarioContext;
import com.intuit.karate.robot.Element;
import com.intuit.karate.robot.ImageElement;
import com.intuit.karate.robot.Robot;
import com.intuit.karate.shell.Command;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * @author pthomas3
 */
public class LinuxRobot extends Robot {
    
    public LinuxRobot(ScenarioContext context, Map<String, Object> options) {
        super(context, options);
    }

    @Override
    public Map<String, Object> afterScenario() {
        return Collections.EMPTY_MAP;
    }        
    
    private static final List<String> METHOD_NAMES = Plugin.methodNames(LinuxRobot.class);

    @Override
    public List<String> methodNames() {
        return METHOD_NAMES;
    }        

    @Override
    protected boolean windowInternal(String title) {
        Command.exec(true, null, "wmctrl", "-FR", title);
        return true; // TODO ?
    }

    @Override
    public boolean window(Predicate<String> condition) {
        String res = Command.exec(true, null, "wmctrl", "-l");
        List<String> lines = StringUtils.split(res, '\n');
        for (String line : lines) {
            List<String> cols = StringUtils.split(line, ' ');
            String id = cols.get(0);
            String host = cols.get(2);
            int pos = line.indexOf(host);
            String name = line.substring(pos + host.length() + 1);
            if (condition.test(name)) {
                Command.exec(true, null, "wmctrl", "-iR", id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Element locateElement(String locator) {
        throw new UnsupportedOperationException("not supported yet.");
    }        

    @Override
    public Element locateElementInternal(Element root, String locator) {
        throw new UnsupportedOperationException("not supported yet.");
    }        

    @Override
    public Element getRoot() {
        return new ImageElement(screen); // TODO
    }        

    @Override
    protected Element getSearchRoot() {
        return new ImageElement(screen); // TODO
    }    
    
    @Override
    public Element locateFocus() {
        return new ImageElement(screen); // TODO
    }       
    
}
