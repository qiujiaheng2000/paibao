/*
 * $HeadURL$
 * $Revision$
 * $Date$
 *
 * ====================================================================
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
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.play.treasure.network.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

/**
 * 〈一句话功能简述〉gzip decompress entity 
 * 〈功能详细描述〉
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class GzipDecompressingEntity extends HttpEntityWrapper
{
    
    /**
     * Instantiates a new gzip decompressing entity.
     * 
     * @param entity
     *            the entity
     */
    public GzipDecompressingEntity(final HttpEntity entity)
    {
        super(entity);
    }

    /**
     * 
     * @return the content
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws IllegalStateException
     *             the illegal state exception
     */
    public InputStream getContent() throws IOException, IllegalStateException
    {

        // the wrapped entity's getContent() decides about repeatability
        InputStream wrappedin = wrappedEntity.getContent();
        return new GZIPInputStream(wrappedin);
    }

    /**
     * @return the content length
     */
    public long getContentLength()
    {
        // length of ungzipped content is not known
        return -1;
    }

}