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
package net.protocol.common.exception;

/**
 * @author jinhongw@gmail.com
 */
public class EnumValueNotPresentException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5622399624187048076L;


    public EnumValueNotPresentException() {

    }

    /**
     * Constructs an <tt>EnumValueNotPresentException</tt> for the specified
     * constant.
     *
     * @param enumType the type of the missing enum constant
     * @param value    the value of the missing enum constant
     */
    @SuppressWarnings("rawtypes")
    public EnumValueNotPresentException(Class<? extends Enum> enumType,
                                        Object value) {
        super(enumType.getName() + "." + value);
    }
}