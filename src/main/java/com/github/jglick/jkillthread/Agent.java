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

public class Agent {

    @SuppressWarnings("deprecation") // yeah, yeah, we know, “prone to deadlocks blah blah blah”
    public static void agentmain(String tid) {
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        while (true) {
            ThreadGroup g2 = g.getParent();
            if (g2 == null) {
                break;
            } else {
                g = g2;
            }
        }
        Thread[] threads;
        int size = 256;
        while (true) {
            threads = new Thread[size];
            if (g.enumerate(threads) < size) {
                break;
            } else {
                size *= 2;
            }
        }
        boolean found = false;
        for (Thread thread : threads) {
            if (thread == null) {
                continue;
            }
            String name = thread.getName();
            if (name.contains(tid)) {
                System.err.printf("Killing \"%s\"%n", name);
                found = true;
                thread.stop();
            }
        }
        if (!found) {
            System.err.printf("Did not find \"%s\"%n", tid);
        }
    }

    private Agent() {}

}
