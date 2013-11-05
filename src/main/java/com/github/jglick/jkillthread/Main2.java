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

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;

public class Main2 {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage:");
            System.err.println("  java -jar jkillthread.jar <PID> <TID>");
            System.err.println("    where <PID> is as in jps, or some substring of the text after a PID visible in jps -lm");
            System.err.println("    and <TID> is a thread name (like pool-9-thread-1), or substring (like pool-)");
            System.err.println("Requires JDK 6+ for both this tool and the target VM.");
            System.exit(2);
        }
        String vmid = args[0];
        File self = new File(URI.create(Main.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm()));
        VirtualMachine m;
        try {
            m = VirtualMachine.attach(vmid);
        } catch (AttachNotSupportedException e) {
            VirtualMachineDescriptor match = null;
            for (VirtualMachineDescriptor desc : VirtualMachine.list()) {
                if (desc.displayName().contains("jkillthread")) {
                    continue;
                }
                if (desc.displayName().contains(vmid)) {
                    if (match != null) {
                        System.err.println("Multiple Java processes found matching '" + vmid + "'");
                        System.exit(1);
                    } else {
                        match = desc;
                    }
                }
            }
            if (match == null) {
                System.err.println("No Java processes found matching '" + vmid + "'");
                System.exit(1);
            }
            m = VirtualMachine.attach(match);
        }
        m.loadAgent(self.getAbsolutePath(), args[1]);
    }

}
