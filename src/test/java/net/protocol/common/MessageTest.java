/**
 * Copyright (C) 2011 * Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.protocol.common;

import net.protocol.common.util.Ints;

import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public abstract class MessageTest extends AbstractTest {

    /**
     * @param array byte[]
     * @return ByteBuffer
     */
    public static ByteBuffer getBuffer(byte[] array) {
        return getBuffer(array, Ints.multiple4(array.length));
    }

    /**
     *
     * @param array
     * @param capacity
     * @return ByteBuffer
     */
    public static ByteBuffer getBuffer(byte[] array, int capacity) {
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

    /**
     * @param buf ByteBuffer
     * @return String
     */
    public static String array(ByteBuffer buf) {
        StringBuilder builder = new StringBuilder("{");
        while (buf.hasRemaining()) {
            builder.append(buf.get()).append(", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
}
