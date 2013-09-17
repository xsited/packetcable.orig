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
package net.protocol.common.coding;

import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public interface ByteMe {

	/**
	 * Reads a sequence of bytes from this object into the given buffer.
	 * 
	 * <pre>
	 * ByteMe o = ...
	 * {@code o.byteMe(dst).flip()}
	 * </pre>
	 * 
	 * @param dst
	 *            The buffer into which bytes are to be transferred
	 * @return This buffer
	 */
	ByteBuffer byteMe(ByteBuffer dst);
}
