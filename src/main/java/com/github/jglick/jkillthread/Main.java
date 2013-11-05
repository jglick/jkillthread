/*
 * Copyright 2013 Jesse Glick.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jglick.jkillthread;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        File toolsJar = new File(new File(new File(System.getProperty("java.home")).getParentFile(), "lib"), "tools.jar");
        if (!toolsJar.isFile()) {
            System.err.println("Cannot load " + toolsJar);
            System.exit(2);
        }
        URL self = Main.class.getProtectionDomain().getCodeSource().getLocation();
        URLClassLoader l = new URLClassLoader(new URL[] {
            self,
            toolsJar.toURI().toURL(),
        }, Main.class.getClassLoader().getParent()) {
            public @Override Class<?> loadClass(String name) throws ClassNotFoundException {
                if (name.contains("Main2")) {
                    return findClass(name);
                } else {
                    return super.loadClass(name);
                }
            }
        };
        Class<?> main = l.loadClass("com.github.jglick.jkillthread.Main2");
        main.getMethod("main", String[].class).invoke(null, new Object[] {args});
    }

}
